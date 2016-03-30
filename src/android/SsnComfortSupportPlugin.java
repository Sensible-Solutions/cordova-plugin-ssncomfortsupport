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
import android.content.Intent;
import android.provider.Settings;
//import android.media.RingtoneManager;
//import android.media.Ringtone;
import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//import android.app.NotificationManager;
//import android.R;
import android.app.Activity;
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
	private CallbackContext openSettingsAppCallbackContext = null;
	
	// Action Name Strings
	private final static String OPEN_SETTINGS_APP = "openSettingsApp";
	private final static String GET_WIFI_NAME = "getWifiName";
	
	// Object keys
	private final static String keyStatus = "status";
	private final static String keyError = "error";
	private final static String keyMessage = "message";
	
	// Status Types
	private final static String statusOpenSettingsApp = "settingsAppOpened";
	private final static String statusGetWifiName = "wifiName";
  
	// Error Types
	private final static String errorOpenSettingsApp = "settingsApp";
	private final static String errorGetWifiName = "wifiName";
	
	// Error Messages
 	private final static String logSettingsApp = "Could not open settings app for application";
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
	
	private void openSettingsAppAction(JSONArray args, CallbackContext callbackContext)
	{
		JSONObject returnObj = new JSONObject();
		
		// Save the callback context for open the settings app
		openSettingsAppCallbackContext = callbackContext;
		
		// See http://developer.android.com/intl/vi/reference/android/provider/Settings.html for activity actions
		startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS));
		//Intent settingsAppIntent = new Intent(Intent.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.sensiblesolutions.ssncomfortsupport"));
		//Intent settingsAppIntent = new Intent(Intent.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package://com.example.app"));
		//startActivity(settingsAppIntent);
		
		// Test also with (check if onActivityResult callaback triggers?) instead of above
		//startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS), 1665);
		// or below
		//Intent settingsAppIntent = new Intent(Intent.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.sensiblesolutions.ssncomfortsupport"));
		//Intent settingsAppIntent = new Intent(Intent.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package://com.example.app"));
		//startActivityForResult(settingsAppIntent, 1665);
		
		// Notify user (if startActivityForResult not working else can remove below)
		addProperty(returnObj, keyStatus, statusOpenSettingsApp);
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, returnObj);
		pluginResult.setKeepCallback(true);			// Save the callback so it can be invoked several time
		openSettingsAppCallbackContext.sendPluginResult(pluginResult);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// Testing if startActivityForResult calls this. Else you can use startActivity instead and remove this function.
		
		JSONObject returnObj = new JSONObject();
		// Check which request we're responding to
		if (requestCode == 1665) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// Notify user that the operation was successful
				addProperty(returnObj, keyStatus, statusOpenSettingsApp);
				PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, returnObj);
				pluginResult.setKeepCallback(true);			// Save the callback so it can be invoked several time
				openSettingsAppCallbackContext.sendPluginResult(pluginResult);
			}
			else {
				// Notify user of operation failure
				addProperty(returnObj, keyError, errorOpenSettingsApp);
				addProperty(returnObj, keyMessage, logSettingsApp);
				PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, returnObj);
				pluginResult.setKeepCallback(false);
				openSettingsAppCallbackContext.sendPluginResult(pluginResult);
			}
		}
	}
	
	private void getWifiNameAction(JSONArray args, CallbackContext callbackContext)
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
	
	
	/*****************************************************************************************************
	* Cordova Plugin (see CordovaPlugin.java)
	*****************************************************************************************************/
	
	/*@override
	 protected void pluginInitialize() {
	 	// Called after plugin construction and fields have been initialized
	 	super.pluginInitialize();
	 }
	
	@Override
	public void onDestroy() {
		 // The final call you receive before your activity is destroyed
		super.onDestroy();
	}
	
	@override
	 public void onStart() {
		 // Called when the activity is becoming visible to the user
		 super.onStart();
    }
	@override
	 public void onStop() {
		 // Called when the activity is no longer visible to the user
		 super.onStop();
    }
	
	@Override
	public void onPause(boolean multitasking) {
		// Called when the system is about to start resuming a previous activity
		super.onPause(multitasking);
    }
	
	@Override
	public void onResume(boolean multitasking) {
		// Called when the activity will start interacting with the user
		super.onResume(multitasking);
    }
	
	@Override
    public void onReset() {
		// Called when the WebView does a top-level navigation or refreshes
		// Plugins should stop any long-running processes and clean up internal state
		super.onReset();
    }*/
}
