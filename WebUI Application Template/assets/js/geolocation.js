
var Coordinates = createClass();

Coordinates.prototype.initialize = function(latitude, longitude, altitude,
		accuracy, altitudeAccuracy, heading, speed) {
	this.latitude = latitude;
	this.longitude = longitude;
	this.altitude = altitude;
	this.accuracy = accuracy;
	this.altitudeAccuracy = altitudeAccuracy;
	this.heading = heading;
	this.speed = speed;
};

var Position = createClass();

Position.prototype.initialize = function(timestamp, coords) {
	this.timestamp = timestamp;
	this.coords = coords;
};

var PositionOptions = createClass();

PositionOptions.prototype.initialize = function(timeout, maximumAge,
		enableHighAccuracy) {
	this.timeout = timeout;
	this.maximumAge = maximumAge;
	this.enableHighAccuracy = enableHighAccuracy;
};

var Geolocation = createClass();

Geolocation.WatchPositionCount = 0;
Geolocation.INVALID_ID = -1;

Geolocation.prototype.initialize = function(name) {
	this.name = name;
};

Geolocation.prototype.getCurrentPosition = function(successCallback,
		errorCallback, postionOptions) {

	var jsonText_PostionOptions 
			= "{" 
			+ "\"timeout\":" + postionOptions.timeout
			+ ", "
			+ "\"maximumAge\":" + postionOptions.maximumAge
			+ ", " 
			+ "\"enableHighAccuracy\":" + postionOptions.enableHighAccuracy
			+ "}";

	window.geolocation.getCurrentPosition(getFuncName(successCallback),
			getFuncName(errorCallback), jsonText_PostionOptions);
};

Geolocation.prototype.watchPosition = function(successCallback,
		errorCallback, postionOptions) {

	var jsonText_PostionOptions 
			= "{" 
			+ "\"timeout\":" + postionOptions.timeout
			+ ", "
			+ "\"maximumAge\":" + postionOptions.maximumAge
			+ ", " 
			+ "\"enableHighAccuracy\":" + postionOptions.enableHighAccuracy
			+ "}";

	var id = window.geolocation.watchPosition(getFuncName(successCallback),
			getFuncName(errorCallback), jsonText_PostionOptions);
	
	if (id != Geolocation.INVALID_ID)
	{
		Geolocation.WatchPositionCount++;
	}
	
	alert("Watch Position ID : " + id);
};

Geolocation.prototype.clearWatch = function(id) {
	if (id < 0)
		return;
	
	alert("Geolocation clear watch ID : " + id);
	window.geolocation.clearWatch(id);
	Geolocation.WatchPositionCount--;
};

Geolocation.prototype.terminate = function(id) {
	window.geolocation.terminate();
};