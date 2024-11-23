package vn.edu.ou.zalo.data.sources.remote;

import android.net.Uri;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IFileStorageDataSource;

public class FileStorageCloudinaryDataSource implements IFileStorageDataSource {
    private final MediaManager storage;

    @Inject
    public FileStorageCloudinaryDataSource(MediaManager storage) {
        this.storage = storage;
    }

    @Override
    public void upload(List<Uri> uris, IRepositoryCallback<String> cb) {
        for (Uri uri : uris) {
            storage
                    .upload(uri)
                    .unsigned("ggdm0pz0")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            Log.i("Cloudinary", "Start uploading: " + requestId);
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            Log.i("Cloudinary", "Uploading progress: " + "Request: " + requestId + " " + (bytes / totalBytes) * 100 + "%");
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            Log.i("Cloudinary", "Uploaded successfully: " + requestId + ", URL: " + resultData.get("secure_url"));
                            cb.onSuccess((String) resultData.get("secure_url"));
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Log.i("Cloudinary", "Uploaded failed: " + requestId + ", Message: " + error.getDescription());
                            cb.onFailure(new Exception(error.getDescription()));
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            Log.i("Cloudinary", "Upload rescheduled: " + requestId + ", Message: " + error.getDescription());
                        }
                    })
                    .dispatch();
        }
    }
}
