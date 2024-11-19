package vn.edu.ou.zalo.data.sources.remote;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IUserDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class UserRemoteDataSource implements IUserDataSource {
    private final FirebaseFirestore db;

    @Inject
    public UserRemoteDataSource(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void getUsers(Map<String, String> query, @Nullable User signedInUser, IRepositoryCallback<List<User>> callback) {
        Query baseQuery = db.collection(Constants.USER_COLLECTION_NAME);
        List<User> users = new ArrayList<>();

        if (query != null && query.containsKey("query")) {
            String q = query.get("query");

            Task<QuerySnapshot> phoneQueryTask = baseQuery.whereEqualTo("phoneNumber", q).get();
            Task<QuerySnapshot> nameQueryTask = baseQuery.whereEqualTo("name", q).get();

            Tasks.whenAllComplete(phoneQueryTask, nameQueryTask).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (phoneQueryTask.isSuccessful() && phoneQueryTask.getResult() != null) {
                        for (DocumentSnapshot document : phoneQueryTask.getResult()) {
                            User user = document.toObject(User.class);
                            if (user != null) {
                                user.setId(document.getId());
                                users.add(user);
                            }
                        }
                    }

                    if (nameQueryTask.isSuccessful() && nameQueryTask.getResult() != null) {
                        for (DocumentSnapshot document : nameQueryTask.getResult()) {
                            User user = document.toObject(User.class);
                            if (user != null && !containsUser(users, user)) {
                                user.setId(document.getId());
                                users.add(user);
                            }
                        }
                    }

                    callback.onSuccess(users);
                } else {
                    callback.onFailure(task.getException());
                }
            }).addOnFailureListener(callback::onFailure);

        } else {
            baseQuery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (DocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        if (user != null) {
                            user.setId(document.getId());
                            users.add(user);
                        }
                    }
                    callback.onSuccess(users);
                } else {
                    callback.onFailure(task.getException());
                }
            });
        }
    }

    private boolean containsUser(List<User> users, User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
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
