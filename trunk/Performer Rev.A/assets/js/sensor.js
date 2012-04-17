
/*
 * Class Sensor History 2010/10/02 : class is created.
 */
var Orientation = createClass();

Orientation.prototype.initialize = function(azimuth, pitch, roll) {
	this.Azimuth = azimuth;
	this.Pitch = pitch;
	this.Roll = roll;
};

/*
 * Class Sensor History 2010/10/02 : class is created.
 */
var MagneticField = createClass();

MagneticField.prototype.initialize = function(x, y, z) {
	this.x = x;
	this.y = y;
	this.z = z;
};

/*
 * Class Sensor History 2010/10/02 : class is created.
 */
var Sensor = createClass();

// Sensor.SENSOR_DELAY_FASTEST = 0;
Sensor.SENSOR_ORIENTATION = 1;
Sensor.SENSOR_ACCELEROMETER = 2;
Sensor.SENSOR_TEMPERATURE = 4;
Sensor.SENSOR_MAGNETIC_FIELD = 8;
Sensor.SENSOR_LIGHT = 16;
Sensor.SENSOR_PROXIMITY = 32;
Sensor.SENSOR_TRICORDER = 64;
Sensor.SENSOR_ORIENTATION_RAW = 128;

Sensor.prototype.initialize = function(type, values) {
	this.type = type;

	switch (this.type) {
	case Sensor.SENSOR_ORIENTATION:
		this.orientation = values;
		break;
	case Sensor.SENSOR_ACCELEROMETER:
		this.accelerometer = values;
		break;
	case Sensor.SENSOR_TEMPERATURE:
		this.temperature = values;
		break;
	case Sensor.SENSOR_MAGNETIC_FIELD:
		this.magnetic_field = values;
		break;
	case Sensor.SENSOR_LIGHT:
		this.light = values;
		break;
	case Sensor.SENSOR_PROXIMITY:
		this.proximity = values;
		break;
	case Sensor.SENSOR_TRICORDER:
		this.tricorder = values;
		break;
	case Sensor.SENSOR_ORIENTATION_RAW:
		this.orientation_raw = values;
		break;
	default:
		break;
	}
};

/*
 * Class SensorManager History 2010/10/02 : class is created.
 */
var SensorManager = createClass();

SensorManager.prototype.initialize = function(name) {
	this.name = name;
};

SensorManager.prototype.start = function(sensor, successCallback, errorCallback) {
	window.sensor.start(sensor, getFuncName(successCallback),
			getFuncName(errorCallback));
};

SensorManager.prototype.stop = function() {
	window.sensor.stop();
};

SensorManager.prototype.resume = function() {
	window.sensor.resume();
};

SensorManager.prototype.pause = function() {
	window.sensor.pause();
};

SensorManager.prototype.terminate = function() {
	window.sensor.stop();
};