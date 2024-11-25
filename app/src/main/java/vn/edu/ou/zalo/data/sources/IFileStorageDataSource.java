package vn.edu.ou.zalo.data.sources;

import android.net.Uri;

import java.util.List;

import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IFileStorageDataSource {
    default void upload(Uri uri, IRepositoryCallback<String> cb) {
        upload(List.of(uri), cb);
    }

    void upload(List<Uri> uri, IRepositoryCallback<String> cb);
}
