
var ssnComfortSupportName = "SsnComfortSupportPlugin";

var ssncomfortsupport = {
	
	openSettingsApp: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, ssnComfortSupportName, "openSettingsApp", []); 
	},
	getWifiName: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, ssnComfortSupportName, "getWifiName", []); 
	}
	/*startServer: function(successCallback, errorCallback, params) {
		cordova.exec(successCallback, errorCallback, gattServerName, "startServer", [params]); 
	},
	alarm: function(successCallback, errorCallback, params) {
		cordova.exec(successCallback, errorCallback, gattServerName, "alarm", []); 
	},
	registerNotifications: function(successCallback, errorCallback, params) {
		cordova.exec(successCallback, errorCallback, gattServerName, "registerNotifications", [params]); 
	},
	isBluetoothSharingAuthorized: function(successCallback) {
		cordova.exec(successCallback, successCallback, gattServerName, "isBluetoothSharingAuthorized", []); 
	},
	getAlarmSettings: function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, gattServerName, "getAlarmSettings", []); 
	},
	setAlarmSettings: function(successCallback, errorCallback, alerts, sound, vibration, log) {
		cordova.exec(successCallback, errorCallback, gattServerName, "setAlarmSettings", [{
			"alerts": alerts,
			"sound": sound,
			"vibration": vibration,
			"log":log
		}]); 
	}*/
}

module.exports = ssncomfortsupport;
