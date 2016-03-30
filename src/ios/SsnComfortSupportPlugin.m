/*
* Copyright (C) 2016 Sensible Solutions Sweden AB
*
* Cordova plugin supporting the SenseSoft Notifications Comfort app.
*/

#import "SsnComfortSupportPlugin.h"

//Plugin Name
NSString *const pluginName = @"ssncomfortsupportplugin";

// Object Keys
NSString *const keyStatus = @"status";
NSString *const keyError = @"error";
NSString *const keyMessage = @"message";

//Status Types
NSString *const statusSettingsAppOpened = @"settingsAppOpened";
NSString *const statusGetWifiName = @"wifiName";

// Error Types
NSString *const errorOpenSettingsApp = @"settingsApp";
NSString *const errorGetWifiName = @"wifiName";

// Error Messages
NSString *const logSettingsApp = @"Could not open settings app for application";
NSString *const logNoArgObj = @"Argument object can not be found";


@implementation SsnComfortSupportPlugin

#pragma mark -
#pragma mark Interface

// Plugin actions
- (void)openSettingsApp:(CDVInvokedUrlCommand *)command
{

}


#pragma mark -
#pragma mark Delegates

// CBPeripheralManager Delegate Methods
//


#pragma mark -
#pragma mark General helpers

-(NSDictionary*) getArgsObject:(NSArray *)args
{
    if (args == nil)
        return nil;
    if (args.count != 1)
        return nil;

    NSObject* arg = [args objectAtIndex:0];

    if (![arg isKindOfClass:[NSDictionary class]])
        return nil;

    return (NSDictionary *)[args objectAtIndex:0];
}

- (BOOL) isNotArgsObject:(NSDictionary*) obj :(CDVInvokedUrlCommand *)command
{
    if (obj != nil)
        return false;

    NSDictionary* returnObj = [NSDictionary dictionaryWithObjectsAndKeys: errorArguments, keyError, logNoArgObj, keyMessage, nil];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:returnObj];
    [pluginResult setKeepCallbackAsBool:false];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

    return true;
}


#pragma mark -
#pragma mark CDVPlugin delegates

// Called after plugin is initialized
- (void) pluginInitialize
{
	// Not implemented
}

// Called before app terminates
- (void) onAppTerminate
{
    	// Not implemeted
}

// Called when plugin resets (navigates to a new page or refreshes)
- (void) onReset
{
	// Not implemented
}

@end
