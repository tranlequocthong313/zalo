package vn.edu.ou.zalo.ui.states;

import android.telecom.Call;

import java.util.Map;

import vn.edu.ou.zalo.data.models.CallEvent;
import vn.edu.ou.zalo.data.models.CallSignalEvent;

public class CallUiState extends BaseUiState {
    private final Map<String, Object> callEventMap;

    public CallUiState(boolean isLoading, String errorMessage, Map<String, Object> callEventMap) {
        super(isLoading, errorMessage);
        this.callEventMap = callEventMap;
    }

    public Map<String, Object> getCallEventMap() {
        return callEventMap;
    }

    public CallEvent getCallEvent() {
        if (callEventMap == null) {
            return null;
        }
        if (!callEventMap.containsKey("event")) {
            return null;
        }
        return (CallEvent) callEventMap.get("event");
    }

    public boolean isVideoCall() {
        if (getCallEvent() != CallEvent.INCOMING_CALL) {
            return false;
        }
        Boolean isVideoCall = (Boolean) callEventMap.get("isVideoCall");
        return Boolean.TRUE.equals(isVideoCall);
    }

    public String getCallId() {
        return (String) callEventMap.get("callId");
    }
}
