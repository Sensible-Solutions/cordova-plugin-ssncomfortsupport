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
import android.provider.Settings;
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
	// General variables
	private final boolean mDEBUG = true;		// Debug flag, setting to true will show debug message boxes
	
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
	
	
	/*****************************************************************************************************
	* Actions
	*****************************************************************************************************/
	@Override
	public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException
	{
		try {
			if (OPEN_SETTINGS_APP.equals(action)) { 
				cordova.getThreadPool().execute(new Runnable() {
					public void run() {
						openSettingsAppAction(args, callbackContext);
					}
				});
				return true;
			}
			else if (GET_WIFI_NAME.equals(action)) {
				cordova.getThreadPool().execute(new Runnable() {
					public void run() {
						getWifiNameAction(args, callbackContext);
					}
				});
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} 
		catch(Exception ex) {
			callbackContext.error(ex.getMessage());
			return false;
		} 
	}
	
	private void openSettingsAppAction(CallbackContext callbackContext)
	{
		JSONObject returnObj = new JSONObject();
		
		// See http://developer.android.com/intl/vi/reference/android/provider/Settings.html for activity actions
		startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS), 0);
		//startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS), 0);
	}
	
	private void getWifiNameAction(CallbackContext callbackContext)
	{
	
	}
	
	
	/*****************************************************************************************************
	* Helper functions
	*****************************************************************************************************/
	
	private void addProperty(JSONObject obj, String key, Object value)
	{
		try {
			obj.put(key, value);
		}
		catch (JSONException e) { 
			/* Ignore */ 
		}
	}
	
	private void showDebugMsgBox(String message)
	{
		if (mDEBUG) {
			AlertDialog.Builder debugAlert  = new AlertDialog.Builder(cordova.getActivity());
			debugAlert.setMessage(message);
			debugAlert.setTitle("Debug SsnComfortPlugin");
			debugAlert.setCancelable(false);
			debugAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
			debugAlert.create().show();
		}
	}
}
