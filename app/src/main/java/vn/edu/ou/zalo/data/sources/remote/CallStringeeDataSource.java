//package vn.edu.ou.zalo.data.sources.remote;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.google.firebase.functions.FirebaseFunctions;
//import com.stringee.StringeeClient;
//import com.stringee.call.StringeeCall;
//import com.stringee.call.StringeeCall2;
//import com.stringee.common.StringeeAudioManager;
//import com.stringee.exception.StringeeError;
//import com.stringee.listener.StatusListener;
//import com.stringee.listener.StringeeConnectionListener;
//import com.stringee.video.StringeeVideoTrack;
//
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import javax.inject.Inject;
//
//import dagger.hilt.android.qualifiers.ApplicationContext;
//import vn.edu.ou.zalo.data.models.CallEvent;
//import vn.edu.ou.zalo.data.models.CallSignalEvent;
//import vn.edu.ou.zalo.data.models.User;
//import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
//import vn.edu.ou.zalo.data.sources.ICallDataSource;
//import vn.edu.ou.zalo.utils.Json;
//
//public class CallStringeeDataSource {
//    private final StringeeClient client;
//    private final AuthRemoteDataSource authRemoteDataSource;
//    private final FirebaseFunctions func;
//    private final Context context;
//    //    private final String answerUrl;
//    private final Map<String, StringeeCall2> callsMap;
//    private StringeeCall2 stringeeCall2;
//    private StringeeAudioManager audioManager;
//
//    @Inject
//    public CallStringeeDataSource(StringeeClient client, AuthRemoteDataSource authRemoteDataSource, FirebaseFunctions func, @ApplicationContext Context context) {
//        this.client = client;
//        this.authRemoteDataSource = authRemoteDataSource;
//        this.func = func;
//        this.context = context;
//        this.callsMap = new HashMap<>();
//    }
//
//    public void answerCall(String callId, IRepositoryCallback<CallSignalEvent> cb) {
//        if (!callsMap.containsKey(callId)) {
//            cb.onFailure(new Exception("Call id is not found"));
//            return;
//        }
//
//        stringeeCall2 = callsMap.get(callId);
//        audioManager = StringeeAudioManager.create(context);
//        audioManager.start((audioDevice, set) -> {
//        });
//        assert stringeeCall2 != null;
//        audioManager.setSpeakerphoneOn(stringeeCall2.isVideoCall());
//        stringeeCall2.ringing(new StatusListener() {
//            @Override
//            public void onSuccess() {
//            }
//
//            @Override
//            public void onError(StringeeError stringeeError) {
//                super.onError(stringeeError);
//                cb.onFailure(new Exception(stringeeError.getMessage()));
//            }
//        });
//        stringeeCall2.answer(new StatusListener() {
//            @Override
//            public void onSuccess() {
//            }
//
//            @Override
//            public void onError(StringeeError stringeeError) {
//                super.onError(stringeeError);
//                cb.onFailure(new Exception(stringeeError.getMessage()));
//            }
//        });
//        stringeeCall2.setCallListener(new StringeeCall2.StringeeCallListener() {
//            @Override
//            public void onSignalingStateChange(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s, int i, String s1) {
//                Log.i("AnswerCall", "onSignalingStateChange::: Id: " + stringeeCall2.getCallId() + ", " + "State: " + signalingState.name());
//                cb.onSuccess(CallSignalEvent.valueOf(signalingState.name()));
//            }
//
//            @Override
//            public void onError(StringeeCall2 stringeeCall2, int i, String s) {
//                Log.i("AnswerCall", "onError::: Id: " + stringeeCall2.getCallId() + ", " + "Code: " + i + ", " + "Description: " + s);
//                cb.onFailure(new Exception(s));
//            }
//
//            @Override
//            public void onHandledOnAnotherDevice(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s) {
//
//            }
//
//            @Override
//            public void onMediaStateChange(StringeeCall2 stringeeCall2, StringeeCall2.MediaState mediaState) {
//                Log.i("AnswerCall", "onMediaStateChange::: Id: " + stringeeCall2.getCallId() + ", " + "State: " + mediaState.name());
//            }
//
//            @Override
//            public void onLocalStream(StringeeCall2 stringeeCall2) {
//
//            }
//
//            @Override
//            public void onRemoteStream(StringeeCall2 stringeeCall2) {
//
//            }
//
//            @Override
//            public void onVideoTrackAdded(StringeeVideoTrack stringeeVideoTrack) {
//
//            }
//
//            @Override
//            public void onVideoTrackRemoved(StringeeVideoTrack stringeeVideoTrack) {
//
//            }
//
//            @Override
//            public void onCallInfo(StringeeCall2 stringeeCall2, JSONObject jsonObject) {
//
//            }
//
//            @Override
//            public void onTrackMediaStateChange(String s, StringeeVideoTrack.MediaType mediaType, boolean b) {
//
//            }
//
//            @Override
//            public void onLocalTrackAdded(StringeeCall2 stringeeCall2, StringeeVideoTrack stringeeVideoTrack) {
//
//            }
//
//            @Override
//            public void onRemoteTrackAdded(StringeeCall2 stringeeCall2, StringeeVideoTrack stringeeVideoTrack) {
//
//            }
//        });
//    }
//
//    public void makeCall(User to, boolean isVideoCall, IRepositoryCallback<CallSignalEvent> cb) {
//        if (!client.isConnected()) {
//            authenticate(new IRepositoryCallback<Void>() {
//                @Override
//                public void onSuccess(Void data) {
//                    makeCallHandler(to, isVideoCall, cb);
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    Log.d("MakeCall", "authenticate failed: " + e.getMessage());
//                }
//            });
//        } else {
//            makeCallHandler(to, isVideoCall, cb);
//        }
//    }
//
//    private void makeCallHandler(User to, boolean isVideoCall, IRepositoryCallback<CallSignalEvent> cb) {
//        stringeeCall2 = new StringeeCall2(client, client.getUserId(), to.getId());
//        audioManager = StringeeAudioManager.create(context);
//        audioManager.start((audioDevice, set) -> Log.d("MakeCall", "selectedAudioDevice: " + audioDevice + " - availableAudioDevices: " + set));
//        audioManager.setSpeakerphoneOn(isVideoCall);
//        stringeeCall2.setVideoCall(isVideoCall);
//        stringeeCall2.makeCall(new StatusListener() {
//            @Override
//            public void onSuccess() {
//                Log.d("MakeCall", "onSuccess");
//            }
//
//            @Override
//            public void onError(StringeeError stringeeError) {
//                super.onError(stringeeError);
//                Log.d("MakeCall", stringeeError.getMessage());
//                cb.onFailure(new Exception(stringeeError.getMessage()));
//            }
//        });
//        stringeeCall2.setCallListener(new StringeeCall2.StringeeCallListener() {
//            @Override
//            public void onSignalingStateChange(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s, int i, String s1) {
//                Log.i("MakeCall", "onSignalingStateChange::: Id: " + stringeeCall2.getCallId() + ", " + "State: " + signalingState.name());
//                cb.onSuccess(CallSignalEvent.valueOf(signalingState.name()));
//            }
//
//            @Override
//            public void onError(StringeeCall2 stringeeCall2, int i, String s) {
//                Log.i("MakeCall", "onError::: Id: " + stringeeCall2.getCallId() + ", " + "Code: " + i + ", " + "Description: " + s);
//                cb.onFailure(new Exception(s));
//            }
//
//            @Override
//            public void onHandledOnAnotherDevice(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s) {
//                Log.i("MakeCall", "onHandledOnAnotherDevice:::");
//            }
//
//            @Override
//            public void onMediaStateChange(StringeeCall2 stringeeCall2, StringeeCall2.MediaState mediaState) {
//                Log.i("MakeCall", "onMediaStateChange::: Id: " + stringeeCall2.getCallId() + ", " + "State: " + mediaState.name());
//            }
//
//            @Override
//            public void onLocalStream(StringeeCall2 stringeeCall2) {
//                Log.i("MakeCall", "onLocalStream:::");
//            }
//
//            @Override
//            public void onRemoteStream(StringeeCall2 stringeeCall2) {
//                Log.i("MakeCall", "onRemoteStream:::");
//            }
//
//            @Override
//            public void onVideoTrackAdded(StringeeVideoTrack stringeeVideoTrack) {
//                Log.i("MakeCall", "onVideoTrackAdded:::");
//            }
//
//            @Override
//            public void onVideoTrackRemoved(StringeeVideoTrack stringeeVideoTrack) {
//                Log.i("MakeCall", "onVideoTrackRemoved:::");
//            }
//
//            @Override
//            public void onCallInfo(StringeeCall2 stringeeCall2, JSONObject jsonObject) {
//                Log.i("MakeCall", "onCallInfo::: Id: " + stringeeCall2.getCallId());
//            }
//
//            @Override
//            public void onTrackMediaStateChange(String s, StringeeVideoTrack.MediaType mediaType, boolean b) {
//                Log.i("MakeCall", "onTrackMediaStateChange:::");
//            }
//
//            @Override
//            public void onLocalTrackAdded(StringeeCall2 stringeeCall2, StringeeVideoTrack stringeeVideoTrack) {
//                Log.i("MakeCall", "onLocalTrackAdded::: Id: " + stringeeCall2.getCallId());
//            }
//
//            @Override
//            public void onRemoteTrackAdded(StringeeCall2 stringeeCall2, StringeeVideoTrack stringeeVideoTrack) {
//                Log.i("MakeCall", "onRemoteTrackAdded::: Id: " + stringeeCall2.getCallId());
//            }
//        });
//    }
//
//    public void authenticate(IRepositoryCallback<Void> cb) {
//        assert authRemoteDataSource != null;
//        authRemoteDataSource.getSignedInUser(new IRepositoryCallback<User>() {
//            @Override
//            public void onSuccess(User user) {
//                Map<String, String> data = new HashMap<>();
//                data.put("userId", user.getId());
//
//                func.getHttpsCallable("getCallServiceAccessToken")
//                        .call(data)
//                        .continueWith(task -> {
//                            if (!task.isSuccessful()) {
//                                Log.d("authenticate", Objects.requireNonNull(task.getResult().getData()).toString());
//                                throw Objects.requireNonNull(task.getException());
//                            }
//                            return Json.getStringObjectMap(task.getResult().getData());
//                        })
//                        .addOnSuccessListener(responseData -> {
//                            String token = (String) responseData.get("token");
//                            if (token == null || token.isEmpty()) {
//                                cb.onFailure(new Exception("Token is not found"));
//                                return;
//                            }
//
//                            // Authenticate with Call Service by giving it the access token
//                            client.connect(token);
//
//                            cb.onSuccess(null);
//                        })
//                        .addOnFailureListener(cb::onFailure);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                cb.onFailure(e);
//            }
//        });
//    }
//
//    public void listenEvents(IRepositoryCallback<Map<String, Object>> cb) {
//        if (!client.isConnected()) {
//            authenticate(new IRepositoryCallback<Void>() {
//                @Override
//                public void onSuccess(Void data) {
//                    Log.d("listenEvents", "start listen events " + client.isConnected());
//                    client.setConnectionListener(new StringeeConnectionListener() {
//                        @Override
//                        public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
//                            Log.d("listenEvents", "onConnectionConnected " + stringeeClient.getUserId() + b);
//                        }
//
//                        @Override
//                        public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
//                            Log.d("listenEvents", "onConnectionDisconnected " + stringeeClient.getUserId() + b);
//                        }
//
//                        @Override
//                        public void onIncomingCall(StringeeCall stringeeCall) {
//                            Log.d("listenEvents", "onIncomingCall " + stringeeCall.getCallId());
//                        }
//
//                        @Override
//                        public void onIncomingCall2(StringeeCall2 stringeeCall2) {
//                            callsMap.put(stringeeCall2.getCallId(), stringeeCall2);
//                            cb.onSuccess(
//                                    Map.of(
//                                            "callId", stringeeCall2.getCallId(),
//                                            "event", CallEvent.INCOMING_CALL,
//                                            "isVideoCall", stringeeCall2.isVideoCall()
//                                    )
//                            );
//                        }
//
//                        @Override
//                        public void onConnectionError(StringeeClient stringeeClient, StringeeError stringeeError) {
//                            Log.d("listenEvents", "onConnectionError " + stringeeClient.getUserId() + " " + stringeeError.getMessage());
//                        }
//
//                        @Override
//                        public void onRequestNewToken(StringeeClient stringeeClient) {
//                            Log.d("listenEvents", "onRequestNewToken " + stringeeClient.getUserId());
//                        }
//
//                        @Override
//                        public void onCustomMessage(String s, JSONObject jsonObject) {
//                            Log.d("listenEvents", "onCustomMessage " + s + " " + jsonObject.toString());
//                        }
//
//                        @Override
//                        public void onTopicMessage(String s, JSONObject jsonObject) {
//                            Log.d("listenEvents", "onTopicMessage " + jsonObject.toString());
//                        }
//                    });
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//
//                }
//            });
//        }
//    }
//}
