package com.netcore.cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import android.util.Log;
import android.location.Location;
import android.content.Intent;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;

import com.netcore.android.inapp.InAppCustomHTMLListener;

import org.jetbrains.annotations.Nullable;

import com.netcore.android.Smartech;
import com.netcore.android.notification.SMTNotificationClickListener;


public class SmartechCordova extends CordovaPlugin implements SMTNotificationClickListener, InAppCustomHTMLListener {

    private static final String CLEAR_USER_IDENTITY = "clearUserIdentity";
    private static final String INITSDK = "initSDK";
    private static final String SET_USER_IDENTITY = "setUserIdentity";
    private static final String GET_USER_IDENTITY = "getUserIdentity";
    private static final String LOGIN = "login";
    private static final String LOGOUT_CLEAR_USER_IDENTITY = "logoutAndClearUserIdentity";
    private static final String TRACK_EVENT = "trackEvent";
    private static final String SET_USER_LOCATION = "setUserLocation";
    private static final String SET_DEVICE_PUSH_TOKEN = "setDevicePushToken";
    private static final String GET_DEVICE_PUSH_TOKEN = "getDevicePushToken";
    private static final String TRACK_APP_INSTALL = "trackAppInstall";
    private static final String TRACK_APP_UPDATE = "trackAppUpdate";
    private static final String TRACK_APP_INSTALL_UPDATE_SMARTECH = "trackAppInstallUpdateBySmartech";
    private static final String UPDATE_USER_PROFILE = "updateUserProfile";
    private static final String FETCH_ALREADY_GENERATED_TOKEN = "fetchAlreadyGeneratedTokenFromFCM";
    private static final String HAS_OPTED_TRACKING = "hasOptedTracking";
    private static final String HAS_OPTED_PUSHNOTIFICATION = "hasOptedPushNotification";
    private static final String HAS_OPTED_INAPPMESSAGE = "hasOptedInAppMessage";
    private static final String OPT_TRACKING = "optTracking";
    private static final String OPT_PUSHNOTIFICATION = "optPushNotification";
    private static final String OPT_INAPP_MESSAGE = "optInAppMessage";
    private static final String GET_DEVICE_UNIQUE_ID = "getDeviceGuid";
    private static final String GET_APP_ID = "getAppId";
    private static final String GET_SDK_VERSION = "getSDKVersion";

    private Smartech smartech;
    private static final String TAG = "SmartechCordova";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        smartech = Smartech.getInstance((new WeakReference<>(cordova.getActivity().getApplicationContext())));
        smartech.setSMTNotificationClickListener(this);
        smartech.setInAppCustomHTMLListener(this);
        handleNotificationIntent(cordova.getActivity().getIntent());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        try {
            Context context = cordova.getActivity().getApplicationContext();
            switch (action) {

                case INITSDK:
                    this.initSDK();
                    break;

                case TRACK_APP_INSTALL:
                    this.trackAppInstall(callbackContext);
                    break;

                case TRACK_APP_UPDATE:
                    this.trackAppUpdate(callbackContext);
                    break;

                case TRACK_APP_INSTALL_UPDATE_SMARTECH:
                    this.trackAppInstallUpdateBySmartech(callbackContext);
                    break;

                case TRACK_EVENT:
                    String eventName = args.getString(0);
                    JSONObject payload = args.getJSONObject(1);
                    this.trackEvent(eventName, payload, callbackContext);
                    break;

                case LOGIN:
                    String userIdentity = args.getString(0);
                    this.login(userIdentity, callbackContext);
                    break;

                case LOGOUT_CLEAR_USER_IDENTITY:
                    Boolean isLogout = args.getBoolean(0);
                    this.logoutAndClearUserIdentity(isLogout, callbackContext);
                    break;

                case SET_USER_IDENTITY:
                    String identity = args.getString(0);
                    this.setUserIdentity(identity, callbackContext);
                    break;

                case GET_USER_IDENTITY:
                    this.getUserIdentity(callbackContext);
                    break;

                case CLEAR_USER_IDENTITY:
                    this.clearUserIdentity(callbackContext);
                    break;

                case UPDATE_USER_PROFILE:
                    JSONObject profilePayload = args.getJSONObject(0);
                    this.updateUserProfile(profilePayload, callbackContext);
                    break;

                case OPT_TRACKING:
                    boolean isTracking = args.getBoolean(0);
                    this.optTracking(isTracking, callbackContext);
                    break;

                case HAS_OPTED_TRACKING:
                    this.hasOptedTracking(callbackContext);
                    break;

                case OPT_PUSHNOTIFICATION:
                    boolean isPushNotificationOptIn = args.getBoolean(0);
                    this.optPushNotification(isPushNotificationOptIn, callbackContext);
                    break;

                case HAS_OPTED_PUSHNOTIFICATION:
                    this.hasOptedPushNotification(callbackContext);
                    break;

                case OPT_INAPP_MESSAGE:
                    boolean isInAppMessagesOptIn = args.getBoolean(0);
                    this.optInAppMessage(isInAppMessagesOptIn, callbackContext);
                    break;

                case HAS_OPTED_INAPPMESSAGE:
                    this.hasOptedInAppMessage(callbackContext);
                    break;

                case SET_USER_LOCATION:
                    Double latitude = args.getDouble(0);
                    Double longitude = args.getDouble(1);
                    this.setUserLocation(latitude, longitude, callbackContext);
                    break;

                case GET_APP_ID:
                    this.getAppId(callbackContext);
                    break;

                case GET_DEVICE_PUSH_TOKEN:
                    this.getDevicePushToken(callbackContext);
                    break;

                case SET_DEVICE_PUSH_TOKEN:
                    String token = args.getString(0);
                    this.setDevicePushToken(token, callbackContext);
                    break;

                case GET_DEVICE_UNIQUE_ID:
                    this.getDeviceUniqueId(callbackContext);
                    break;

                case GET_SDK_VERSION:
                    this.getSDKVersion(callbackContext);
                    break;

                case FETCH_ALREADY_GENERATED_TOKEN:
                    this.fetchAlreadyGeneratedTokenFromFCM(callbackContext);
                    break;

                default:
                    return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * This method is responsible for processing intent and parsing data received via intent.
     *
     * @param intent Notification.
     */
    public void handleNotificationIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String value = extras.toString();
            String deeplinkPath = "";
            String customPayload = "";
            if (extras.containsKey("clickDeepLinkPath")) {
                deeplinkPath = extras.getString("clickDeepLinkPath");
                if (extras.containsKey("clickCustomPayload")) {
                    customPayload = extras.getString("clickCustomPayload");
                }
                try {
                    JSONObject object = new JSONObject();
                    object.put("deeplink", deeplinkPath);
                    object.put("customPayload", customPayload);
                    final String jsonPayload = object.toString();
                    cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:cordova.fireDocumentEvent('SmartechHandleDeeplink'," + jsonPayload + ");");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is responsible for handling notification intent received on notification click.
     *
     * @param intent Received on notification click.
     */
    public void onNotificationClick(Intent intent) {
        handleNotificationIntent(intent);
    }

    public void customHTMLCallback(@Nullable HashMap<String, Object> hashMap) {
        if (hashMap != null) {
            try {
                JSONObject objPayload = new JSONObject(hashMap);
                final String jsonPayload = objPayload.toString();
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:cordova.fireDocumentEvent('onCustomHTMLInapp'," + jsonPayload + ");");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to initialize cordova plugin.
     *
     * @param context
     * @param callbackContext
     */
    private void initSDK() {
        try {
            // Intentionally blank.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will be used track app install event.
     */
    private void trackAppInstall(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.trackAppInstall();
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });


    }

    /**
     * This method will be used track app update event.
     */
    private void trackAppUpdate(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.trackAppUpdate();
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will be used to install app install and update event will be track by SmartechSDK.
     */
    private void trackAppInstallUpdateBySmartech(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.trackAppInstallUpdateBySmartech();
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will be used to track event.
     *
     * @param eventName       Name of event which needs to be tracked.
     * @param payload         JSONObject
     * @param callbackContext app
     */
    private void trackEvent(String eventName, JSONObject payload, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    HashMap<String, Object> hmapPayload = jsonToHashMap(payload);
                    smartech.trackEvent(eventName, hmapPayload);
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "Event track is successfull.");
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will be used to set users identity.
     *
     * @param identity        users identity provided by user.
     * @param callbackContext
     */
    private void login(String identity, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.setUserIdentity(identity);
                    smartech.login(identity);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will be used to logout and clear identity saved inside the SDK.
     *
     * @param logout          If its value is true the clear identity otherwise just logout .
     * @param callbackContext
     */
    private void logoutAndClearUserIdentity(Boolean logout, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.logoutAndClearUserIdentity(logout);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * Used to set users identity,
     *
     * @param identity        provided by client.
     * @param callbackContext
     */
    private void setUserIdentity(String identity, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.setUserIdentity(identity);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * It will provide identity saved inside the SDK.
     */
    private void getUserIdentity(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String userIdentity = smartech.getUserIdentity();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, userIdentity);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    private void clearUserIdentity(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.clearUserIdentity();
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to update the user profile.
     * It should be called by the developer to update all the user related attributes to Smartech.
     *
     * @param profileData Profile payload.
     */
    private void updateUserProfile(JSONObject profileData, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    HashMap<String, Object> hmapProfile = jsonToHashMap(profileData);
                    smartech.updateUserProfile(hmapProfile);
                    PluginResult result = new PluginResult(PluginResult.Status.OK, "Profile is updated successfully.");
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to opt tracking.
     *
     * @param isTracking      if true then track events or dont trac. Its default value is true.
     * @param callbackContext
     */
    private void optTracking(Boolean isTracking, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.optTracking(isTracking);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to get the current status of opt tracking.
     */
    private void hasOptedTracking(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    boolean value = smartech.hasOptedTracking();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, value);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }

        });
    }

    /**
     * This method is used to opt push notifications.
     *
     * @param isPushNotificationOptIn if true then user will receive push notifications. Its default value is true.
     */
    private void optPushNotification(Boolean isPushNotificationOptIn, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.optPushNotification(isPushNotificationOptIn);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to get the current status of opt push notifications.
     */
    private void hasOptedPushNotification(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    boolean value = smartech.hasOptedPushNotification();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, value);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to opt InApp messages.
     *
     * @param isInMessagesOptIn true: Inapp will be displayed to user
     *                          false: InApp will be not displayed to user.
     *                          Its default value is true.
     */
    private void optInAppMessage(Boolean isInMessagesOptIn, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.optInAppMessage(isInMessagesOptIn);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * It will return the status of opt InApp messages.
     */
    private void hasOptedInAppMessage(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    boolean value = smartech.hasOptedInAppMessage();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, value);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will be used to set user's location in SDK.
     *
     * @param latitude  user's
     * @param longitude user's
     */
    private void setUserLocation(Double latitude, Double longitude, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    PluginResult result;
                    if (latitude != null && longitude != null) {
                        Location location = new Location("SmartechCordova");
                        location.setLatitude(latitude);
                        location.setLongitude(longitude);
                        smartech.setUserLocation(location);
                        result = new PluginResult(PluginResult.Status.OK, "Location is updated successfully.");
                    } else {
                        result = new PluginResult(PluginResult.Status.ERROR, "Check value of lattitude and longitude.");
                    }
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);

                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to get the app id used by the Smartech SDK.
     */
    private void getAppId(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String appId = smartech.getAppID();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, appId);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to get the device push token used by Smartech SDK.
     */
    private void getDevicePushToken(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String token = smartech.getDevicePushToken();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, token);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to get the device unique id used by Smartech SDK.
     */
    private void getDeviceUniqueId(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String deviceId = smartech.getDeviceUniqueId();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, deviceId);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method is used to get the current Smartech SDK version.
     */
    private void getSDKVersion(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    String sdkVersion = smartech.getSDKVersion();
                    PluginResult result = new PluginResult(PluginResult.Status.OK, sdkVersion);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will be used to set token inside the SDK.
     *
     * @param token generated by FCM.
     */
    private void setDevicePushToken(String token, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.setDevicePushToken(token);
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    /**
     * This method will fetch existing token genertaed by FCM. It needs to be used only if you dont have
     * users existiing token.
     *
     * @param callbackContext
     */
    private void fetchAlreadyGeneratedTokenFromFCM(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    smartech.fetchAlreadyGeneratedTokenFromFCM();
                    PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                } catch (Exception e) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error: " + e.getMessage());
                    result.setKeepCallback(true);
                    callbackContext.sendPluginResult(result);
                }
            }
        });
    }

    public static HashMap<String, Object> jsonToHashMap(JSONObject jsonObject) throws JSONException {
        HashMap<String, Object> hashMap = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            hashMap.put(key, value);
        }
        return hashMap;
    }
}

