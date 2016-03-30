/*
* Copyright (C) 2016 Sensible Solutions Sweden AB
*
* Cordova plugin supporting the SenseSoft Notifications Comfort app.
*/

#import "SsnComfortSupportPlugin.h"

// Plugin Name
NSString *const pluginName = @"ssncomfortsupportplugin";

// General variables
bool mDEBUG = true;			// Debug flag, setting to true will show debug message boxes
	
// Object Keys
NSString *const keyStatus = @"status";
NSString *const keyError = @"error";
NSString *const keyMessage = @"message";

// Status Types
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

- (void)getWifiName:(CDVInvokedUrlCommand *)command
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

- (void) showDebugMsgBox: (NSString *const) msg
{
	if (mDEBUG) {
		UIAlertView *debugAlert = [[UIAlertView alloc] initWithTitle: @"Debug SsnComfortPlugin" message:msg delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        	[debugAlert show];
        }
}


#pragma mark -
#pragma mark CDVPlugin delegates

// Called after plugin is initialized
- (void) pluginInitialize
{
	// Not implemented
}

// The final call you receive before your activity is destroyed
- (void) onDestroy
{
	// Not implemented
}

// Called when the activity is becoming visible to the user
- (void) onStart
{
	// Not implemented
}

// Called when the activity is no longer visible to the user
- (void) onStop
{
	// Not implemented
}

- (void) onStop
{
	// Not implemented
}

// Called when the system is about to start resuming a previous activity
- (void) onPause
{
	// Not implemented
}

// Called when the activity will start interacting with the user
- (void) onResume
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
