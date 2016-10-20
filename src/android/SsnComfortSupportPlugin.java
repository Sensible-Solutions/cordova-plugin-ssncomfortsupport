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
import android.media.RingtoneManager;
//import android.media.Ringtone;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Vibrator;
import android.Manifest.permission;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
	private final boolean mDEBUG = true;									// Debug flag, setting to true will show debug message boxes
	//private final static String mAPP_PACKAGE_NAME = "com.sensiblesolutions.sensesoftnotificationscomfort";	// Application package name
	
 	// General callback variables
	private CallbackContext openSettingsAppCallbackContext = null;
	
	// Action Name Strings
	private final static String OPEN_SETTINGS_APP = "openSettingsApp";
	private final static String GET_WIFI_NAME = "getWifiName";
	private final static String PLAY_NOTIFICATION_SOUND = "playNotificationSound";
	
	// Object keys
	private final static String keyStatus = "status";
	private final static String keyError = "error";
	private final static String keyMessage = "message";
	private final static String keySsid = "ssid";
	
	// Status Types
	private final static String statusOpenSettingsApp = "settingsAppOpened";
	private final static String statusNotificationSoundPlayed = "notificationSoundPlayed";
	//private final static String statusGetWifiName = "wifiName";
  
	// Error Types
	private final static String errorOpenSettingsApp = "settingsApp";
	private final static String errorGetWifiName = "wifiName";
	private final static String errorPlayNotificationSound = "playNotificationSound";
	
	// Error Messages
 	private final static String logSettingsApp = "Could not open settings app for application";
 	private final static String logGetWifiName = "Could not get wifi name";
 	private final static String logWifidisabled = "Wifi is disabled";
	//private final static String logPlayNotificationSound = "Exception thrown";
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
			else if (PLAY_NOTIFICATION_SOUND.equals(action)) {
				cordova.getThreadPool().execute(new Runnable() {
					public void run() {
						playNotificationSoundAction(args, callbackContext);
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
		//openSettingsAppCallbackContext = callbackContext;
		
		// See http://developer.android.com/intl/vi/reference/android/provider/Settings.html for activity actions
		//cordova.getActivity().startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS)); // Works
		Intent settingsAppIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + cordova.getActivity().getApplicationContext().getPackageName())); // Works
		cordova.getActivity().startActivity(settingsAppIntent); // Works
		
		// Notify user
		addProperty(returnObj, keyStatus, statusOpenSettingsApp);
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, returnObj);
		pluginResult.setKeepCallback(false);
		callbackContext.sendPluginResult(pluginResult);
		
		// Test also with (check if onActivityResult callaback triggers?) instead of above
		//cordova.getActivity().startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS), 1665); // Works (but onActivityResults(...) is not called since this particular activity is not returning any results)
		// or below
		//Intent settingsAppIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + cordova.getActivity().getApplicationContext().getPackageName())); // Works (but onActivityResults(...) is not called since this particular action is not returning any results)
		//Intent settingsAppIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + cordova.getActivity().getApplicationContext().getPackageName())); // Works also (but onActivityResults(...) is not called since this particular action is not returning any results)
		//cordova.getActivity().startActivityForResult(settingsAppIntent, 1665);
	}
	
	/*@Override
	//protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// Testing if startActivityForResult calls this (Update: only called if activity actually is returning any results)
		
		JSONObject returnObj = new JSONObject();
		// Check which request we're responding to
		if (requestCode == 1665) {
			// Make sure the request was successful
			if (resultCode == Activity.RESULT_OK) {
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
	}*/
	
	private void getWifiNameAction(JSONArray args, CallbackContext callbackContext)
	{
		WifiManager wifiManager = (WifiManager) cordova.getActivity().getSystemService(Context.WIFI_SERVICE);
		JSONObject returnObj = new JSONObject();
		
		if (!wifiManager.isWifiEnabled()) {
			addProperty(returnObj, keyError, errorGetWifiName);
			addProperty(returnObj, keyMessage, logWifidisabled);
			PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, returnObj);
			pluginResult.setKeepCallback(false);
			callbackContext.sendPluginResult(pluginResult);
            		return;
        	}

        	WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        	if (wifiInfo == null) {
        		addProperty(returnObj, keyError, errorGetWifiName);
			addProperty(returnObj, keyMessage, logGetWifiName);
			PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, returnObj);
			pluginResult.setKeepCallback(false);
			callbackContext.sendPluginResult(pluginResult);
            		return;
        	}

        	String ssid = wifiInfo.getSSID();
		if (ssid != null) {
        		if (ssid.isEmpty()) {
              			ssid = wifiInfo.getBSSID();
        		}
		}
		
		if (ssid != null) {
			if (ssid.isEmpty()) {
				addProperty(returnObj, keyError, errorGetWifiName);
				addProperty(returnObj, keyMessage, logGetWifiName);
				PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, returnObj);
				pluginResult.setKeepCallback(false);
				callbackContext.sendPluginResult(pluginResult);
				return;
			}
		}
		else {
			addProperty(returnObj, keyError, errorGetWifiName);
			addProperty(returnObj, keyMessage, logGetWifiName);
			PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, returnObj);
			pluginResult.setKeepCallback(false);
			callbackContext.sendPluginResult(pluginResult);
			return;
		}
		
        	addProperty(returnObj, keySsid, ssid);
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, returnObj);
		pluginResult.setKeepCallback(false);
		callbackContext.sendPluginResult(pluginResult);
	}
	
	private void playNotificationSoundAction(JSONArray args, CallbackContext callbackContext)
	{
		// Plays the default notification sound (no need to check if notifications are enabled for the app if it is checked before calling this plugin function) 
		Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		MediaPlayer mediaPlayer = new MediaPlayer();
		JSONObject returnObj = new JSONObject();
		
		try {
			mediaPlayer.setDataSource(cordova.getActivity().getApplicationContext(), defaultRingtoneUri);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					mp.release();
				}
			});
			mediaPlayer.start();
			// Vibrate the device if it has hardware vibrator and permission
			Vibrator vib = (Vibrator) cordova.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			if (vib.hasVibrator()){
				if (ContextCompat.checkSelfPermission(cordova.getActivity(), permission.VIBRATE) != PackageManager.PERMISSION_GRANTED){
					vib.vibrate(1000);
				}
			}
		} catch (Exception ex) {
			addProperty(returnObj, keyError, errorPlayNotificationSound);
			addProperty(returnObj, keyMessage, ex.getMessage());
			PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, returnObj);
			pluginResult.setKeepCallback(false);
			callbackContext.sendPluginResult(pluginResult);
			return;
		}
		addProperty(returnObj, keyStatus, statusNotificationSoundPlayed);
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, returnObj);
		pluginResult.setKeepCallback(false);
		callbackContext.sendPluginResult(pluginResult);
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
