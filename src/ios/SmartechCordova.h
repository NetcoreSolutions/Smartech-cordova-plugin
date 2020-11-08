//
//  SmartechCordovaPlugin.h
//
//  Created by Jobin Kurian on 10/09/20.
//

#import <Cordova/CDV.h>

NS_ASSUME_NONNULL_BEGIN

static NSString *const kSMTDeeplinkNotificationIdentifier = @"SmartechDeeplinkNotification";
static NSString *const kSMTDeeplinkIdentifier = @"deeplink";
static NSString *const kSMTCustomPayloadIdentifier = @"customPayload";
static NSString *const kSMTDeeplinkJavaScriptCallbackIdentifier = @"SmartechHandleDeeplink";

@interface SmartechCordova : CDVPlugin

#pragma mark - Event Tracking Methods

/**
 @brief This method is used to track app install event.
 
 @discussion This method should be called by the developer to track the app install event to Smartech.
 */
- (void)trackAppInstall:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to track app update event.
 
 @discussion This method should be called by the developer to track the app updates event to Smartech.
 */
- (void)trackAppUpdate:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to track app install or update event by Smartech SDK itself.
 
 @discussion This method should be called by the developer to track the app install or update event by Smartech SDK itself. If you are calling this method then you should not call trackAppInstall or trackAppUpdate method.
 */
- (void)trackAppInstallUpdateBySmartech:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to track custom event done by the user.
 
 @discussion This method should be called by the developer to track any custom activites that is performed by the user in the app to Smartech backend.
 */
- (void)trackEvent:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to send login event to Smartech backend.
 
 @discussion This method should be called only when the app gets the user's identity or when the user does a login activity in the application.
 */
- (void)login:(CDVInvokedUrlCommand *)command;

/**
 @brief This method would logout the user and clear identity on Smartech backend.
 
 @discussion This method should be called only when the user log out of the application.
 */
- (void)logoutAndClearUserIdentity:(CDVInvokedUrlCommand *)command;

/**
 @brief This method would set the user identity locally and with all subsequent events this identity will be send.
 
 @discussion This method should be called only when the user gets the identity.
 */
- (void)setUserIdentity:(CDVInvokedUrlCommand *)command;

/**
 @brief This method would get the user identity that is stored in the SDK.
 
 @discussion This method should be called to get the user's identity.
 */
- (void)getUserIdentity:(CDVInvokedUrlCommand *)command;

/**
 @brief This method would clear the identity that is stored in the SDK.
 
 @discussion This method will clear the user's identity by removing it from.
 */
- (void)clearUserIdentity:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to update the user profile.
 
 @discussion This method should be called by the developer to update all the user related attributes to Smartech.
 */
- (void)updateUserProfile:(CDVInvokedUrlCommand *)command;

#pragma mark - GDPR Methods

/**
 @brief This method is used to opt tracking.
 
 @discussion If you call this method then we will opt in or opt out the user of tracking.
 */
- (void)optTracking:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the current status of opt tracking.
 
 @discussion If you call this method you will get the current status of the tracking which can be used to render the UI at app level.
 */
- (void)hasOptedTracking:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to opt push notifications.
 
 @discussion If you call this method then we will opt in or opt out the user of recieving push notifications.
 */
- (void)optPushNotification:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the current status of opt push notification.
 
 @discussion If you call this method you will get the current status of the tracking which can be used to render the UI at app level.
 */
- (void)hasOptedPushNotification:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to opt in-app messages.
 
 @discussion If you call this method then we will opt in or opt out the user of in-app messages.
 */
- (void)optInAppMessage:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the current status of opt in-app messages.
 
 @discussion If you call this method you will get the current status of the opt in-app messages which can be used to render the UI at app level.
 */
- (void)hasOptedInAppMessage:(CDVInvokedUrlCommand *)command;

#pragma mark - Location Methods

/**
 @brief This method is used to set the user's location to the SDK.
 
 @discussion You need to call this method to set location which will be passed on the Smartech SDK.
 */
- (void)setUserLocation:(CDVInvokedUrlCommand *)command;

#pragma mark - Helper Methods

/**
 @brief This method is used to get the app id used by the Smartech SDK.
 
 @discussion If you call this method you will get the app id used by the Smartech SDK.
 */
- (void)getAppId:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the device push token used by Smartech SDK.
 
 @discussion If you call this method you will get the device push token which is used for sending push notification.
 */
- (void)getDevicePushToken:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the device unique id used by Smartech SDK.
 
 @discussion If you call this method you will get the device unique id which is used to identify a device on Smartech.
 */
- (void)getDeviceGuid:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used to get the current Smartech SDK version.
 
 @discussion If you call this method you will get the current Smartech SDK version used inside the app.
 */
- (void)getSDKVersion:(CDVInvokedUrlCommand *)command;

#pragma mark - Empty Methods Used In Android

/**
 @brief This method is used in the Android SDK to initialise the SDK for handling deeplink. In iOS it will be an empty implementation.
 */
- (void)initSDK:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used in the Android SDK to set the device push token. In iOS it will be an empty implementation.
 */
- (void)setDevicePushToken:(CDVInvokedUrlCommand *)command;

/**
 @brief This method is used in the Android SDK to fetch the existing device Token already generated by FCM. In iOS it will be an empty implementation.
 */
- (void)fetchAlreadyGeneratedTokenFromFCM:(CDVInvokedUrlCommand *)command;

@end

NS_ASSUME_NONNULL_END
