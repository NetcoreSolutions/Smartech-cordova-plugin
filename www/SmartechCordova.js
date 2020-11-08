var exec = require('cordova/exec');

var PLUGIN_NAME = 'SmartechCordova';

var Smartech = function () {

}

Smartech.prototype.initSDK = function () {
    exec(null, null, PLUGIN_NAME, 'initSDK', []);
};

// ----- Event Tracking Methods ----- 

/**
 * This method is used to track app install event. 
 * This method should be called by the developer to track the app install event to Smartech.
 */
Smartech.prototype.trackAppInstall = function (success, error) {
    exec(null, null, PLUGIN_NAME, 'trackAppInstall', []);
};

/**
 * This method is used to track app update event.
 * This method should be called by the developer to track the app updates event to Smartech.
 */
Smartech.prototype.trackAppUpdate = function (success, error) {
    exec(null, null, PLUGIN_NAME, 'trackAppUpdate', []);
};

/**
 * This method is used to track app install or update event by Smartech SDK itself.
 * This method should be called by the developer to track the app install or update event by Smartech SDK itself. 
 * If you are calling this method then you should not call trackAppInstall or trackAppUpdate method.
 */
Smartech.prototype.trackAppInstallUpdateBySmartech = function (success, error) {
    exec(null, null, PLUGIN_NAME, 'trackAppInstallUpdateBySmartech', []);
};

/**
 * This method is used to track custom event done by the user.
 * This method should be called by the developer to track any custom activites 
 * that is performed by the user in the app to Smartech backend.
 */
Smartech.prototype.trackEvent = function (eventName, payload, success, error) {
    exec(null, null, PLUGIN_NAME, 'trackEvent', [eventName, payload]);
};

/**
 * This method is used to send login event to Smartech backend.
 * This method should be called only when the app gets the user's identity 
 * or when the user does a login activity in the application.
 */
Smartech.prototype.login = function (identity, success, error) {
    exec(null, null, PLUGIN_NAME, 'login', [identity]);
};

/**
 * This method would logout the user and clear identity on Smartech backend.
 * This method should be called only when the user log out of the application.
 */
Smartech.prototype.logoutAndClearUserIdentity  = function (isLogout, success, error) {
    exec(null, null, PLUGIN_NAME, 'logoutAndClearUserIdentity', [isLogout]);
};

/**
 * This method would set the user identity locally and with all subsequent events this identity will be send.
 * This method should be called only when the user gets the identity.
 */
Smartech.prototype.setUserIdentity = function (identity, success, error) {
    exec(success, error, PLUGIN_NAME, 'setUserIdentity', [identity]);
};

/**
 * This method would get the user identity that is stored in the SDK.
 * This method should be called to get the user's identity.
 */
Smartech.prototype.getUserIdentity = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'getUserIdentity', []);
};

/**
 * This method would clear the identity that is stored in the SDK.
 * This method will clear the user's identity by removing it from.
 */
Smartech.prototype.clearUserIdentity = function (success, error) {
    exec(null, null, PLUGIN_NAME, 'clearUserIdentity', []);
};

/**
 * This method is used to update the user profile.
 * This method should be called by the developer to update all the user related attributes to Smartech.
 */
Smartech.prototype.updateUserProfile = function (payload, success, error) {
    exec(success, error, PLUGIN_NAME, 'updateUserProfile', [payload]);
};


// ----- GDPR Methods ----- 

/**
 * This method is used to opt tracking.
 * If you call this method then we will opt in or opt out the user of tracking.
 */
Smartech.prototype.optTracking = function (payload, success, error) {
    exec(success, error, PLUGIN_NAME, 'optTracking', [payload]);
};

/**
 * This method is used to get the current status of opt tracking.
 * If you call this method you will get the current status of the tracking which can be used to render the UI at app level.
 */
Smartech.prototype.hasOptedTracking = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'hasOptedTracking', []);
};

/**
 * This method is used to opt push notifications.
 * If you call this method then we will opt in or opt out the user of recieving push notifications.
 */
Smartech.prototype.optPushNotification = function (payload, success, error) {
    exec(success, error, PLUGIN_NAME, 'optPushNotification', [payload]);
};

/**
 * This method is used to get the current status of opt push notification.
 * If you call this method you will get the current status of the tracking which can be used to render the UI at app level.
 */
Smartech.prototype.hasOptedPushNotification = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'hasOptedPushNotification', []);
};

/**
 * This method is used to opt in-app messages.
 * If you call this method then we will opt in or opt out the user of in-app messages.
 */
Smartech.prototype.optInAppMessage = function (payload, success, error) {
    exec(success, error, PLUGIN_NAME, 'optInAppMessage', [payload]);
};

/**
 * This method is used to get the current status of opt in-app messages.
 * If you call this method you will get the current status of the opt in-app messages which can be used to render the UI at app level.
 */
Smartech.prototype.hasOptedInAppMessage = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'hasOptedInAppMessage', []);
};


// ----- Location Methods ----- 

/**
 * This method is used to set the user's location to the SDK.
 * You need to call this method to set location which will be passed on the Smartech SDK.
 */
Smartech.prototype.setUserLocation = function (latitude, longitude, success, error) {
    exec(null, null, PLUGIN_NAME, 'setUserLocation', [latitude, longitude]);
};


// ----- Helper Methods ----- 

/**
 * This method is used to get the app id used by the Smartech SDK.
 * If you call this method you will get the app id used by the Smartech SDK.
 */
Smartech.prototype.getAppId = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'getAppId', []);
};

/**
 * This method is used to get the device push token used by Smartech SDK.
 * If you call this method you will get the device push token which is used for sending push notification.
 */
Smartech.prototype.getDevicePushToken = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'getDevicePushToken', []);
};

/**
 * This method is used to set the device push token used by Smartech SDK.
 * If you call this method you will set the device push token which is used for sending push notification.
 */
Smartech.prototype.setDevicePushToken = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'setDevicePushToken', []);
};

/**
 * This method is used to get the device unique id used by Smartech SDK.
 * If you call this method you will get the device unique id which is used to identify a device on Smartech.
 */
Smartech.prototype.getDeviceGuid = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'getDeviceGuid', []);
};

/**
 * This method is used to get the current Smartech SDK version.
 * If you call this method you will get the current Smartech SDK version used inside the app.
 */
Smartech.prototype.getSDKVersion = function (success, error) {
    exec(success, error, PLUGIN_NAME, 'getSDKVersion', []);
};

/**
 * This method is used to fetch the existing device Token already generated by FCM.
 * You should call this method only if you don't have device tokens of existing user's.
 */
Smartech.prototype.fetchAlreadyGeneratedTokenFromFCM = function (token, success, error) {
    exec(success, error, PLUGIN_NAME, 'fetchAlreadyGeneratedTokenFromFCM', [token]);
};


module.exports = new Smartech();
