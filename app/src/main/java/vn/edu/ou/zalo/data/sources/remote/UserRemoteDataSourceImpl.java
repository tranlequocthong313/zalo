package vn.edu.ou.zalo.data.sources.remote;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.mindrot.jbcrypt.BCrypt;

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
    public UserRemoteDataSourceImpl(FirebaseFirestore db) {
        this.db = db;
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
                        assert user != null;
                        user.setId(document.getId());
                        users.add(user);
                    }
                    callback.onSuccess(users);
                });
    }

    @Override
    public void createUser(User user, IRepositoryCallback<Void> callback) {
        String mockPasswordForDevPurpose = "zalo12345"; // TODO: For dev purpose
        String hashedPassword = hashPassword(mockPasswordForDevPurpose); // Use bcrypt or similar
        user.setHashedPassword(hashedPassword);

        db.collection(Constants.USER_COLLECTION_NAME)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    user.setId(documentReference.getId());
                    callback.onSuccess(null);
                })
                .addOnFailureListener(callback::onFailure);
    }

    private String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
