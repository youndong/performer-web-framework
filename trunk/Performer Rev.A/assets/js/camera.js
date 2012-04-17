var CameraManager = createClass();

CameraManager.prototype.initialize = function(name) {
	this.name = name;
	this.id = "";
	this.width = 0;
	this.height = 0;
};
/*
function CameraManager(name) {
	this.name = name;
	this.id = "";
	this.width = 0;
	this.height = 0;
};
*/

CameraManager.prototype.createPreview = function(id, width, height) {
	this.id = id;
	this.width = width;
	this.height = height;
	
	alert("Camera preview is started.");
	document.getElementById(id).innerHTML = "<img id='"+id+"_bounding_client_rect_image' src='file:///android_asset/images/plus-sign-solid.png' width='" + this.width + "px' height='" + this.height + "px'></img>";
	
	var top = document.getElementById(id).getBoundingClientRect().top+document.body.scrollTop;
	var left = document.getElementById(id).getBoundingClientRect().left+document.body.scrollLeft;
	var right = document.getElementById(id).getBoundingClientRect().right+document.body.scrollLeft;
	var bottom = document.getElementById(id).getBoundingClientRect().bottom+document.body.scrollTop;
	
	window.camera.createPreview(id, left, top, right, bottom);
	//window.camera.setClientRect(id, left, top, right, bottom);

};

CameraManager.prototype.terminate = function() {
	document.getElementById(this.id).innerHTML = "";
	window.camera.terminate();
	alert("Camera preview is stopped.");
};

CameraManager.prototype.requestTop = function(id) {

	var rectTop = document.getElementById(id).getBoundingClientRect().top+document.body.scrollTop;
	var rectLeft = document.getElementById(id).getBoundingClientRect().left+document.body.scrollLeft;
	var rectRight = rectLeft + this.width;
	var rectBottom = rectTop + this.height;
	window.camera.setClientRect(id, rectLeft, rectTop, rectRight, rectBottom);
};