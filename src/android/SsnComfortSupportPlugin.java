/*
* Copyright (C) 2016 Sensible Solutions Sweden AB
*
* Cordova plugin supporting the SenseSoft Notifications Comfort app.
*/
 
package com.sensiblesolutions.ssncomfortsupport;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
//import android.media.RingtoneManager;
//import android.media.Ringtone;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//import android.app.NotificationManager;
//import android.R;
import android.app.AlertDialog;			        // For showing debug messaages
import android.content.DialogInterface;		  	// For showing debug messaages

//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;

public class SsnComfortSupportPlugin extends CordovaPlugin
{
 	// General callback variables
	//private CallbackContext serverRunningCallbackContext = null;
	
	// Action Name Strings
	private final static String OPEN_SETTINGS_APP = "openSettingsApp";
	private final static String GET_WIFI_NAME = "getWifiName";
	
	// Object keys
	private final static String keyStatus = "status";
	private final static String keyError = "error";
	private final static String keyMessage = "message";
	
	// Status Types
	private final static String statusOpenSettingsApp = "settingsApp";
	private final static String statusGetWifiName = "wifiName";
  
	// Error Types
	private final static String errorOpenSettingsApp = "settingsApp";
	private final static String errorGetWifiName = "wifiName";
	
	// Error Messages
 	//private final static String logServerAlreadyRunning = "GATT server is already running";
	//private final static String logService = "Immediate Alert service could not be added";
	//private final static String logConnectionState = "Connection state changed with error";
	//private final static String logStateUnsupported = "BLE is not supported by device";
	//private final static String logStatePoweredOff = "BLE is turned off for device";
	
	//Actions
	@Override
	public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException
	{
		try {
			if (OPEN_SETTINGS_APP.equals(action)) { 
				openSettingsAppAction(callbackContext);
				return true;
			}
			else if (GET_WIFI_NAME.equals(action)){
				getWifiNameAction(callbackContext);
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} 
		catch(Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
			callbackContext.error(ex.getMessage());
			return false;
		} 
	}
}
