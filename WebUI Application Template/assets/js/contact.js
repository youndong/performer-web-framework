var AddressBook = createClass();

AddressBook.prototype.initialize = function(id, name, type) {
	this.id = id;
	this.name = name;
	this.type = type;
};

AddressBook.prototype.getName = function() {
	return this.name;
}

AddressBook.prototype.getID = function() {
	return this.id;
}

AddressBook.prototype.getType = function() {
	return this.type;
}

var Contact = createClass();

Contact.prototype.initialize = function() {

};

Contact.prototype.test = function() {
	window.contact.test();
};

function JSONtoString(object) {

	var ret = [];

	// id
	if (typeof object.id != 'undefined') {
		ret.push("'id':'" + object.id + "'");
	}

	// name
	if (typeof object.name != 'undefined') {
		ret.push("'name':'" + object.name + "'");
	}

	// nicknames
	if (typeof object.nicknames != 'undefined' && object.nicknames.length > 0) {
		var nicknames = [];
		for ( var index = 0; index < object.nicknames.length; index++) {
			nicknames.push("'" + object.nicknames[index] + "'");
		}
		ret.push("'nicknames':[" + nicknames + "]");
	}

	// emails
	if (typeof object.emails != 'undefined' && object.emails.length > 0) {
		var emails = [];
		for ( var index = 0; index < object.emails.length; index++) {
			var tempString = [];
			if (typeof object.emails[index].types != 'undefined' && object.emails[index].types.length > 0) {

				var typeList = [];
				for ( var typeIndex = 0; typeIndex < object.emails[index].types.length; typeIndex++) {
					if (typeof object.emails[index].types[typeIndex] != 'undefined') {
						typeList.push("'" + object.emails[index].types[typeIndex] + "'");
					}
				}
				tempString.push("'types':[" + typeList + "]");
			}

			if (typeof object.emails[index].email != 'undefined') {
				tempString.push("'email':'" + object.emails[index].email + "'");
			}

			emails.push("{" + tempString + "}");
		}
		ret.push("'emails':[" + emails + "]");
	}

	// phoneNumbers
	if (typeof object.phoneNumbers != 'undefined' && object.phoneNumbers.length > 0) {
		var phoneNumbers = [];
		for ( var index = 0; index < object.phoneNumbers.length; index++) {
			var tempString = [];
			if (typeof object.phoneNumbers[index].types != 'undefined' && object.phoneNumbers[index].types.length > 0) {
				var typeList = [];
				for ( var typeIndex = 0; typeIndex < object.phoneNumbers[index].types.length; typeIndex++) {
					if (typeof object.phoneNumbers[index].types[typeIndex] != 'undefined') {
						typeList.push("'" + object.phoneNumbers[index].types[typeIndex] + "'");
					}
				}
				tempString.push("'types':[" + typeList + "]");
			}

			if (typeof object.phoneNumbers[index].number != 'undefined') {
				tempString.push("'number':'" + object.phoneNumbers[index].number + "'");
			}
			phoneNumbers.push("{" + tempString + "}");
		}
		ret.push("'phoneNumbers':[" + phoneNumbers + "]");
	}

	// addresses
	if (typeof object.addresses != 'undefined' && object.addresses.length > 0) {
		var addresses = [];
		for ( var index = 0; index < object.addresses.length; index++) {
			var tempString = [];
			if (typeof object.addresses[index].types != 'undefined') {
				var typeList = [];
				for ( var typeIndex = 0; typeIndex < object.addresses[index].types.length; typeIndex++) {
					if (typeof object.addresses[index].types[typeIndex] != 'undefined') {
						typeList.push("'" + object.addresses[index].types[typeIndex] + "'");
					}
				}
				tempString.push("'types':[" + typeList + "]");
			}

			if (typeof object.addresses[index].country != 'undefined')
				tempString.push("'country':'" + object.addresses[index].country + "'");
			if (typeof object.addresses[index].region != 'undefined')
				tempString.push("'region':'" + object.addresses[index].region + "'");
			if (typeof object.addresses[index].county != 'undefined')
				tempString.push("'county':'" + object.addresses[index].county + "'");
			if (typeof object.addresses[index].city != 'undefined')
				tempString.push("'city':'" + object.addresses[index].city + "'");
			if (typeof object.addresses[index].street != 'undefined')
				tempString.push("'street':'" + object.addresses[index].street + "'");
			if (typeof object.addresses[index].streetNumber != 'undefined')
				tempString.push("'streetNumber':'" + object.addresses[index].streetNumber + "'");
			if (typeof object.addresses[index].premises != 'undefined')
				tempString.push("'premises':'" + object.addresses[index].premises + "'");
			if (typeof object.addresses[index].additionalInformation != 'undefined')
				tempString.push("'additionalInformation':'" + object.addresses[index].additionalInformation + "'");
			if (typeof object.addresses[index].postalCode != 'undefined')
				tempString.push("'postalCode':'" + object.addresses[index].postalCode + "'");

			addresses.push("{" + tempString + "}");
		}
		ret.push("'addresses':[" + addresses + "]");
	}

	// photoURI
	if (typeof object.photoURI != 'undefined') {
		ret.push("'photoURI':'" + object.photoURI + "'");
	}

	return "{" + ret + "}";

}