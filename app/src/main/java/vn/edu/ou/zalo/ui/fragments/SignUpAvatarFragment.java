package vn.edu.ou.zalo.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IFileStorageDataSource;
import vn.edu.ou.zalo.data.sources.remote.FileStorageCloudinaryDataSource;
import vn.edu.ou.zalo.ui.activities.ZaloActivity;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

@AndroidEntryPoint
public class SignUpAvatarFragment extends Fragment {
    private static final String ARGS_USER = "user";
    private ImageView avatarImageView;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private boolean isAvatarUploaded = false;
    private Uri avatarUri;


    @Inject
    AuthViewModel authViewModel;
    @Inject
    IFileStorageDataSource fileStorage;

    public SignUpAvatarFragment() {}


    public static SignUpAvatarFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER, user);

        SignUpAvatarFragment fragment = new SignUpAvatarFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_avatar, container, false);
        avatarImageView = view.findViewById(R.id.fragment_signup_avatar_image_view);

        User user = getArguments() != null ? getArguments().getParcelable(ARGS_USER) : null;
        if (user != null) {
            {
                String avatarUrl = generateAvatarUrl(user);
                user.setAvatarUrl(avatarUrl);
                loadAvatar(avatarUrl);
            }
        }
        avatarImageView.setOnClickListener(v -> showImagePickerDialog());

        Button skipButton = view.findViewById(R.id.fragment_signup_avatar_skip_button);
        skipButton.setOnClickListener(v -> showSkipConfirmationDialog(user));

        Button updateButton = view.findViewById(R.id.fragment_signup_avatar_update_button);
        updateButton.setOnClickListener(v -> {
            if (isAvatarUploaded) {
                // Avatar đã được tải lên, tiến hành đăng ký
                if (avatarUri != null) {
                    if (fileStorage != null) {
                        // Gọi phương thức upload ảnh
                        fileStorage.upload(List.of(avatarUri), new IRepositoryCallback<String>() {
                            @Override
                            public void onSuccess(String avatarUrl) {
                                // Set URL của avatar và cập nhật thông tin người dùng
                                user.setAvatarUrl(avatarUrl);
                                loadAvatar(avatarUrl); // Hiển thị avatar mới
                                authViewModel.signUp(user); // Tiến hành đăng ký tài khoản
                            }

                            @Override
                            public void onFailure(Exception e) {
                                // Hiển thị thông báo lỗi
                                Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Xử lý khi fileStorage là null
                        Toast.makeText(requireContext(), "File storage is not initialized.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // Avatar chưa được tải lên, cho phép chọn ảnh
                showImagePickerDialog();
            }
        });


        authViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isSignedUp()) {
                requireActivity().finish();
                startActivity(ZaloActivity.newIntent(getActivity()));
            }
        });

        return view;
    }

    private String generateAvatarUrl(User user) {
        String randomColor = generateRandomColor();
        String fullName = user.getFullName();
        return "https://ui-avatars.com/api/?name=" + fullName + "&background=" + randomColor + "&color=fff&size=240";
    }
    private String generateRandomColor() {
        Random random = new Random();
        int color = random.nextInt(0xFFFFFF + 1);
        return String.format("%06X", color);
    }

    private void loadAvatar(String avatarUrl) {
        Glide.with(this)
                .load(avatarUrl)
                .into(avatarImageView);
    }
    private void showSkipConfirmationDialog(User user) {
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.Theme_Zalo_AlertDialog)
                .setTitle(R.string.confrim)
                .setMessage(R.string.skip_avatar_confirm_description)
                .setPositiveButton(R.string.add_image, (dialog1, which) -> {
                    // TODO: Handle add image action
                })
                .setNegativeButton(R.string.skip, (dialog12, which) -> {
                    authViewModel.signUp(user);
                })
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            TextView messageTextView = dialog.findViewById(android.R.id.message);
            if (messageTextView != null) {
                messageTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorOutline));
            }
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorError));
            negativeButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorOnSurfaceVariant));
        });

        dialog.show();
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.choose_avatar_source)
                .setItems(new String[]{getString(R.string.gallery), getString(R.string.camera)},
                        (dialog, which) -> {
                            if (which == 0) {
                                openGallery();
                            } else if (which == 1) {
                                openCamera();
                            }
                        })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            cameraLauncher.launch(intent);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            avatarUri = imageUri;
                            loadAvatar(avatarUri.toString());
                            isAvatarUploaded = true;
                        }
                    }
                });

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        if (photo != null) {
                            avatarImageView.setImageBitmap(photo);
                            isAvatarUploaded = true;
                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, b);
                            String path = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), photo, "Title", null);
                            avatarUri = Uri.parse(path);
                            // TODO: Convert Bitmap to file and upload
                        }
                    }
                });
    }

}
