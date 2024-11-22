package vn.edu.ou.zalo.data.sources.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IMessageDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class MessageRemoteDataSource implements IMessageDataSource {

    private final FirebaseFirestore db;

    @Inject
    public MessageRemoteDataSource(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void listenMessages(String chatRoomId, IRepositoryCallback<List<Message>> cb) {
        db.collection(Constants.MESSAGE_COLLECTION_NAME)
                .whereEqualTo("chatRoomId", chatRoomId)
                .addSnapshotListener((q, error) -> {
                    if (error != null) {
                        cb.onFailure(error);
                        return;
                    }
                    assert q != null;
                    List<Message> messages = new ArrayList<>();
                    List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    for (DocumentChange docChange : q.getDocumentChanges()) {
                        DocumentSnapshot doc = docChange.getDocument();
                        Message message = doc.toObject(Message.class);
                        message.setId(doc.getId());
                        messages.add(message);

                        Task<DocumentSnapshot> task = db.collection(Constants.USER_COLLECTION_NAME)
                                .document(message.getSenderId())
                                .get()
                                .addOnSuccessListener(userDoc -> {
                                    User u = userDoc.toObject(User.class);
                                    assert u != null;
                                    u.setId(userDoc.getId());
                                    message.setSender(u);
                                })
                                .addOnFailureListener(cb::onFailure);
                        tasks.add(task);
                    }
                    Tasks.whenAllComplete(tasks)
                            .addOnSuccessListener(t -> cb.onSuccess(messages))
                            .addOnFailureListener(cb::onFailure);

                });
    }

    @Override
    public void getMessages(String chatRoomId, IRepositoryCallback<List<Message>> callback) {
        db.collection(Constants.MESSAGE_COLLECTION_NAME)
                .whereEqualTo("chatRoomId", chatRoomId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(docs -> {
                    List<Message> messages = new ArrayList<>();
                    List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    for (DocumentSnapshot doc : docs) {
                        Message message = doc.toObject(Message.class);
                        assert message != null;
                        message.setId(doc.getId());
                        messages.add(message);

                        Task<DocumentSnapshot> task = db.collection(Constants.USER_COLLECTION_NAME)
                                .document(message.getSenderId())
                                .get()
                                .addOnSuccessListener(userDoc -> {
                                    User u = userDoc.toObject(User.class);
                                    assert u != null;
                                    u.setId(userDoc.getId());
                                    message.setSender(u);
                                })
                                .addOnFailureListener(callback::onFailure);
                        tasks.add(task);
                    }
                    Tasks.whenAllComplete(tasks)
                            .addOnSuccessListener(t -> callback.onSuccess(messages))
                            .addOnFailureListener(callback::onFailure);
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void createMessage(Message message, IRepositoryCallback<Message> cb) {
        Task<DocumentReference> t1 = db.collection(Constants.MESSAGE_COLLECTION_NAME)
                .add(message)
                .addOnSuccessListener(doc -> message.setId(doc.getId()))
                .addOnFailureListener(cb::onFailure);

        ChatRoom.LastMessage lastMessage = ChatRoom.LastMessage.fromMessage(message);
        Task<Void> t2 = db.collection(Constants.CHAT_ROOM_COLLECTION_NAME)
                .document(message.getChatRoomId())
                .update("lastMessage", lastMessage);

        Tasks.whenAllSuccess(t1, t2)
                .addOnSuccessListener(t -> cb.onSuccess(message))
                .addOnFailureListener(cb::onFailure);
    }
}