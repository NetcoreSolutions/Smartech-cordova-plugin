//
//  SmartechCordova.m
//
//  Created by Jobin Kurian on 10/09/20.
//

#import "SmartechCordova.h"
#import <Smartech/Smartech.h>


@implementation SmartechCordova


#pragma mark - Cordova Plugin Lifecycle

- (void)pluginInitialize {
    [super pluginInitialize];
    [self setupObserver];
}


#pragma mark - Event Tracking Methods

- (void)trackAppInstall:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        [[Smartech sharedInstance] trackAppInstall];
    }];
}

- (void)trackAppUpdate:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        [[Smartech sharedInstance] trackAppUpdate];
    }];
}

- (void)trackAppInstallUpdateBySmartech:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        [[Smartech sharedInstance] trackAppInstallUpdateBySmartech];
    }];
}

- (void)trackEvent:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *eventName = [command argumentAtIndex:0];
        NSDictionary *eventPayload = [command argumentAtIndex:1];
        if (eventPayload == nil) {
            eventPayload = @{};
        }
        if (eventName != nil && [eventName isKindOfClass:[NSString class]] && eventPayload != nil && [eventPayload isKindOfClass:[NSDictionary class]]) {
            [[Smartech sharedInstance] trackEvent:eventName andPayload:eventPayload];
        }
    }];
}

- (void)login:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *userIdentity = [command argumentAtIndex:0];
        if (userIdentity != nil && [userIdentity isKindOfClass:[NSString class]]) {
            [[Smartech sharedInstance] login:userIdentity];
        }
    }];
}

- (void)logoutAndClearUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL clearIdentityStatus = [[command argumentAtIndex:0] boolValue];
        [[Smartech sharedInstance] logoutAndClearUserIdentity:clearIdentityStatus];
    }];
}

- (void)setUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *userIdentity = [command.arguments objectAtIndex:0];
        if (userIdentity != nil && [userIdentity isKindOfClass:[NSString class]]) {
            [[Smartech sharedInstance] setUserIdentity:userIdentity];
        }
    }];
}

- (void)getUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *userIdentity = [[Smartech sharedInstance] getUserIdentity];
        if (userIdentity != nil) {
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:userIdentity];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)clearUserIdentity:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        [[Smartech sharedInstance] clearUserIdentity];
    }];
}

- (void)updateUserProfile:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSDictionary *userProfile = [command.arguments objectAtIndex:0];
        [[Smartech sharedInstance] updateUserProfile:userProfile];
    }];
}


#pragma mark - GDPR Methods

- (void)optTracking:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL optTrackingStatus = [[command argumentAtIndex:0] boolValue];
        [[Smartech sharedInstance] optTracking:optTrackingStatus];
    }];
}

- (void)hasOptedTracking:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL optTrackingStatus = [[Smartech sharedInstance] hasOptedTracking];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:optTrackingStatus];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)optPushNotification:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL optPushNotificationStatus = [[command argumentAtIndex:0] boolValue];
        [[Smartech sharedInstance] optPushNotification:optPushNotificationStatus];
    }];
}

- (void)hasOptedPushNotification:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL optPushNotificationStatus = [[Smartech sharedInstance] hasOptedPushNotification];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:optPushNotificationStatus];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)optInAppMessage:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL optInAppMessageStatus = [[command argumentAtIndex:0] boolValue];
        [[Smartech sharedInstance] optInAppMessage:optInAppMessageStatus];
    }];
}

- (void)hasOptedInAppMessage:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        BOOL optInAppMessageStatus = [[Smartech sharedInstance] hasOptedInAppMessage];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:optInAppMessageStatus];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}


#pragma mark - Location Methods

- (void)setUserLocation:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        @try {
            double lat = [[command argumentAtIndex:0] doubleValue];
            double lon = [[command argumentAtIndex:1] doubleValue];
            CLLocationCoordinate2D userLocationCordinate = CLLocationCoordinate2DMake(lat,lon);
            [[Smartech sharedInstance] setUserLocation:userLocationCordinate];
        }
        @catch (NSException *exception) {
            NSLog(@"SMT : error setting location %@", exception.reason);
            return ;
        }
    }];
}


#pragma mark - Helper Methods

- (void)getAppId:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *smartechAppId = [[Smartech sharedInstance] getAppId];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:smartechAppId];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)getDevicePushToken:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *devicePushToken = [[Smartech sharedInstance] getDevicePushToken];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:devicePushToken];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)getDeviceGuid:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *deviceGuid = [[Smartech sharedInstance] getDeviceGuid];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:deviceGuid];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)getSDKVersion:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *sdkVersion = [[Smartech sharedInstance] getSDKVersion];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:sdkVersion];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}


#pragma mark - Deeplink Handling

- (void)handleDeeplinkAndCustomPayload:(NSNotification *)notification {
    NSString *jsonString = [self convertToJSONString:notification.userInfo];
    NSString *js = [NSString stringWithFormat:@"cordova.fireDocumentEvent('%@', %@);", kSMTDeeplinkJavaScriptCallbackIdentifier, jsonString];
    [self.commandDelegate evalJs:js];
}


#pragma mark - Empty Methods Used In Android

- (void)initSDK:(CDVInvokedUrlCommand *)command {
    // Just passing back the success status.
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setDevicePushToken:(CDVInvokedUrlCommand *)command {
    // Just passing back the success status.
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)fetchAlreadyGeneratedTokenFromFCM:(CDVInvokedUrlCommand *)command {
    // Just passing back the success status.
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

#pragma mark - Internal Helper Methods

- (void)setupObserver {
    // Add the observer for deeplink handling.
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleDeeplinkAndCustomPayload:) name:kSMTDeeplinkNotificationIdentifier object:nil];
}

- (NSString *)convertToJSONString:(id)data {
    // To convert the data into JSON format.
    NSString *jsonString = @"{}";
    @try {
        NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:data options:0 error:&error];
        if (jsonData != nil) {
            jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        }
        else {
            NSLog(@"SMT : Could not convert to JSON due to exception : %@", error.localizedDescription);
        }
    }
    @catch (NSException *exception) {
        NSLog(@"SMT : Deeplink & custom payload conversion exception = %@", exception.reason);
    }
    @finally {
        return jsonString;
    }
}


@end
