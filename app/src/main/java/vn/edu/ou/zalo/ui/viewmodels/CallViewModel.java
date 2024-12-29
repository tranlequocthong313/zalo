package vn.edu.ou.zalo.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.CallEvent;
import vn.edu.ou.zalo.data.models.CallPacket;
import vn.edu.ou.zalo.data.models.CallSignalEvent;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.domain.IDomainCallback;
import vn.edu.ou.zalo.domain.impl.AnswerCallUseCase;
import vn.edu.ou.zalo.domain.impl.ListenCallEventUseCase;
import vn.edu.ou.zalo.domain.impl.MakeCallUseCase;
import vn.edu.ou.zalo.ui.states.CallUiState;

public class CallViewModel extends ViewModel {
    private final MutableLiveData<CallUiState> uiState = new MutableLiveData<>(new CallUiState(false, null, null));
    private final MakeCallUseCase makeCallUseCase;
    private final AnswerCallUseCase answerCallUseCase;
    private final ListenCallEventUseCase listenCallEventUseCase;

    @Inject
    public CallViewModel(MakeCallUseCase makeCallUseCase, AnswerCallUseCase answerCallUseCase, ListenCallEventUseCase listenCallEventUseCase) {
        this.makeCallUseCase = makeCallUseCase;
        this.answerCallUseCase = answerCallUseCase;
        this.listenCallEventUseCase = listenCallEventUseCase;
    }

    public MutableLiveData<CallUiState> getUiState() {
        return uiState;
    }

    public void makeCall(User to, boolean isVideoCall) {
        makeCallUseCase.execute(to, isVideoCall, new IDomainCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                uiState.setValue(new CallUiState(false, null, null));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new CallUiState(false, e.getMessage(), null));
            }
        });
    }

    public void answerCall(String callId) {
        answerCallUseCase.execute(callId, new IDomainCallback<CallSignalEvent>() {
            @Override
            public void onSuccess(CallSignalEvent data) {
                uiState.setValue(new CallUiState(false, null, null));
            }

            @Override
            public void onFailure(Exception e) {
                uiState.setValue(new CallUiState(false, e.getMessage(), null));
            }
        });
    }

    public void listenCallEvent() {
        listenCallEventUseCase.execute(new IDomainCallback<CallPacket>() {
            @Override
            public void onSuccess(CallPacket data) {
                if (data.getCallEvent() == CallEvent.START_CALL) {
                    Log.d("listenEvents", data.getSender() + " is calling you");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
