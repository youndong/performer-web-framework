var CallManager = createClass();

CallManager.prototype.initialize = function(name) {
	this.name = name;
};

CallManager.prototype.call = function(dialNo) {
	window.callmanager.call(dialNo);
};
