package vn.edu.ou.zalo.data.sources.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class UserRemoteDataSourceImpl implements IUserDataSource {
    private final FirebaseFirestore db;

    @Inject
    public UserRemoteDataSourceImpl() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void getUsers(IRepositoryCallback<List<User>> callback) {
        db.collection(Constants.USER_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        callback.onFailure(task.getException());
                        return;
                    }

                    List<User> users = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        users.add(user);
                    }
                    callback.onSuccess(users);
                });
    }

    @Override
    public User getLoginUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User();
        if (firebaseUser != null) {
            user.setId(firebaseUser.getUid());
            user.setAvatarUrl(firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null);

            fetchUserDataFromFirestore(user);
        }
        return user;
    }

    private void fetchUserDataFromFirestore(User user) {
        String userId = user.getId();

        db.collection(Constants.USER_COLLECTION_NAME)
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        return;
                    }

                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {
                        return;
                    }

                    Long birthdate = document.getLong("birthdate");
                    Integer genderIndex = document.get("gender", Integer.class);
                    assert genderIndex != null;
                    User.Gender gender = User.Gender.values()[genderIndex];
                    Long friendCount = document.getLong("friendCount");
                    if (birthdate != null) {
                        user.setBirthdate(birthdate);
                    }
                    if (gender != null) {
                        user.setGender(gender);
                    }
                    if (friendCount != null) {
                        user.setFriendCount(friendCount);
                    }
                    user.setFullName(document.getString("fullName"));
                    user.setPhoneNumber(document.getString("phoneNumber"));
                    user.setBio(document.getString("bio"));
                    user.setBackgroundUrl(document.getString("backgroundUrl"));
                    user.setLastLogin(System.currentTimeMillis());
                });
    }
}
