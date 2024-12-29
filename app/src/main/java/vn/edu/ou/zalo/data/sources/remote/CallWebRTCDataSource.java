package vn.edu.ou.zalo.data.sources.remote;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.CallPacket;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.ICallDataSource;
import vn.edu.ou.zalo.utils.Constants;

public class CallWebRTCDataSource implements ICallDataSource {
    private final FirebaseFirestore db;

    @Inject
    public CallWebRTCDataSource(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void sendMessageToOtherUser(CallPacket callPacket, IRepositoryCallback<Void> cb) {
        db.collection(Constants.CALL_COLLECTION_NAME)
                .document(callPacket.getId())
                .set(callPacket)
                .addOnSuccessListener(cb::onSuccess)
                .addOnFailureListener(cb::onFailure);
    }

    @Override
    public void listenEvents(User signedInUser, IRepositoryCallback<CallPacket> cb) {
        db.collection(Constants.CALL_COLLECTION_NAME)
                .document(signedInUser.getId())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        cb.onFailure(new Exception(error.getMessage()));
                        return;
                    }
                    assert value != null;
                    CallPacket callPacket = value.toObject(CallPacket.class);
                    cb.onSuccess(callPacket);
                });
    }
}
