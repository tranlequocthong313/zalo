package vn.edu.ou.zalo.data.models;

public class CallPacket extends BaseModel {
    private String sender;
    private String data;
    private CallEvent callEvent;

    public CallPacket(String sender, String data, CallEvent callEvent) {
        this.sender = sender;
        this.data = data;
        this.callEvent = callEvent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public CallEvent getCallEvent() {
        return callEvent;
    }

    public void setCallEvent(CallEvent callEvent) {
        this.callEvent = callEvent;
    }
}
