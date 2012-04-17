
function getFuncName(func) {
	if (typeof func == 'function')
		return func.name;
	else
		return "";
}

function createClass() {
	return function(param) {
		if (this instanceof arguments.callee) {
			if (typeof this.initialize == 'function')
				this.initialize.apply(this, param.callee ? param : arguments);
		} else
			return new arguments.callee(arguments);
	};
}

function include(filename) {
	document.write('<script language="JavaScript" charset="utf-8" src="' + filename + '"></script>');
}

include("file:///android_asset/js/common.js");
include("file:///android_asset/js/camera.js");
include("file:///android_asset/js/sensor.js");
include("file:///android_asset/js/callmanager.js");
include("file:///android_asset/js/geolocation.js");
include("file:///android_asset/js/contact.js");

var Bondi = createClass();

// Bondi Features
Bondi.FEATURE_APPCONFIG 	= 0x00000001;
Bondi.FEATURE_APPLAUNCHER 	= 0x00000002;
Bondi.FEATURE_CALENDAR 		= 0x00000004;
Bondi.FEATURE_CALL 			= 0x00000008; 
Bondi.FEATURE_CAMERA 		= 0x0000000F;
Bondi.FEATURE_CONTACT 		= 0x00000010;
Bondi.FEATURE_DEVICESTATUS 	= 0x00000020;
Bondi.FEATURE_FILESYSTEM 	= 0x00000040;
Bondi.FEATURE_GALLERY 		= 0x00000080;
Bondi.FEATURE_GEOLOCATION 	= 0x000000F0;
Bondi.FEATURE_MESSAGING 	= 0x00000100;
Bondi.FEATURE_PIM 			= 0x00000200;
Bondi.FEATURE_SENSOR 		= 0x00000400;
Bondi.FEATURE_TASK 			= 0x00000800;
Bondi.FEATURE_TELEPHONY 	= 0x00000F00;
Bondi.FEATURE_UI 			= 0x00001000;


Bondi.prototype.initialize = function() {
	this.contact = null;

	this.camera = CameraManager("CameraManager");
	this.geolocation = Geolocation("Geolocation");
	this.sensor = SensorManager("SensorManager");
	this.callmanager = CallManager("CallManager");

};

Bondi.prototype.requestFeature = function(successCallback, errorCallback, feature) {
	var pendingOperation = window.performer_bondi.requestFeature(getFuncName(successCallback),
			getFuncName(errorCallback), feature);
	trace(pendingOperation);

	return pendingOperation;
};

function trace(message) {
	window.framework.trace(message);
}