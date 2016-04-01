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
NSString *const errorArguments = @"arguments";

// Error Messages
NSString *const logSettingsApp = @"Could not open settings app for application";
NSString *const logNoArgObj = @"Argument object can not be found";


@implementation SsnComfortSupportPlugin

#pragma mark -
#pragma mark Interface

// Plugin actions
- (void)openSettingsApp:(CDVInvokedUrlCommand *)command
{

	// Save the callback
	openSettingsAppCallback = command.callbackId;
	// Launch the Settings app and displays the app’s custom settings
	NSURL *appSettings = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
	if ([[UIApplication sharedApplication] openURL:appSettings]) {
		
		NSDictionary* returnObj = [NSDictionary dictionaryWithObjectsAndKeys: statusSettingsAppOpened, keyStatus, nil];
	        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:returnObj];
		[pluginResult setKeepCallbackAsBool:false];
	        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
	}
	else {
	
		NSDictionary* returnObj = [NSDictionary dictionaryWithObjectsAndKeys: errorOpenSettingsApp, keyError, logSettingsApp, keyMessage, nil];
        	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:returnObj];
		[pluginResult setKeepCallbackAsBool:false];
	 	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
	}
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

/*
#pragma mark -
#pragma mark CDVPlugin delegates (see CDVPlugin.m and CDVPlugin.h)

// Called after plugin is initialized
- (void) pluginInitialize
{
	// Not implemented
}

// Called when running low on memory
- (void) onMemoryWarning
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
	// Override to cancel any long-running requests when the WebView navigates or refreshes
	// Not implemented
}

- (void)handleOpenURL:(NSNotification*)notification
{
    // Override to handle urls sent to your app
    // Also register your url schemes in your App-Info.plist

    NSURL* url = [notification object];

    if ([url isKindOfClass:[NSURL class]]) {
        // Do your thing!
    }
}

// Called when the system is about to start resuming a previous activity
- (void) onPause
{
	// NOTE: if you want to use this, make sure you add the corresponding notification handler in CDVPlugin.m
	// Not implemented
}

// Called when the activity will start interacting with the user
- (void) onResume
{
	// NOTE: if you want to use this, make sure you add the corresponding notification handler in CDVPlugin.m
	// Not implemented
}

- (void) onOrientationWillChange
{
	// NOTE: if you want to use this, make sure you add the corresponding notification handler in CDVPlugin.m
	// Not implemented
}

- (void) onOrientationDidChange
{
	// NOTE: if you want to use this, make sure you add the corresponding notification handler in CDVPlugin.m
	// Not implemented
}
*/

@end
