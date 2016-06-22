/*
* Copyright (C) 2016 Sensible Solutions Sweden AB
*
* Cordova plugin header for support to the SenseSoft Notifications Comfort app.
*/

#import <Cordova/CDV.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import <AudioToolbox/AudioServices.h>

#define notificationSoundID 1104

@interface SsnComfortSupportPlugin : CDVPlugin
{
	
	//NSString *openSettingsAppCallback;
	
}

- (void)openSettingsApp:(CDVInvokedUrlCommand *)command;
- (void)getWifiName:(CDVInvokedUrlCommand *)command;
- (void)playNotificationSound:(CDVInvokedUrlCommand *)command;

@end
