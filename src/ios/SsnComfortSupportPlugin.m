/*
* Copyright (C) 2016 Sensible Solutions Sweden AB
*
* Cordova plugin supporting the SenseSoft Notifications Comfort app.
*/

#import "SsnComfortSupportPlugin.h"

//Plugin Name
NSString *const pluginName = @"ssncomfortsupportplugin";


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
