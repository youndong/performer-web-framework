package com.maru.performer.framework.contact;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.maru.performer.framework.base.BaseObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class PhoneNumber extends BaseObject {

	public static final String	JSON_FIELD_NUMBER	= "number";
	public static final String	JSON_FIELD_TYPES	= "types";
	public static final String	TYPE_BBS			= "BBS";					// Indicates a bulletin board service
	public static final String	TYPE_CAR			= "CAR";					// Indicates a car-phone number
	// number
	public static final String	TYPE_CELL			= "CELL";					// Indicates a cellular number
	public static final String	TYPE_FAX			= "FAX";					// Indicates a facsimile number
	public static final String	TYPE_HOME			= "HOME";					// Indicates a home number
	public static final String	TYPE_ISDN			= "ISDN";					// Indicates an ISDN number
	// number
	public static final String	TYPE_MODEM			= "MODEM";					// Indicates a MODEM number
	public static final String	TYPE_MSG			= "MSG";					// Indicates a messaging service on the
	public static final String	TYPE_PAGER			= "PAGER";					// Indicates a pager number
	public static final String	TYPE_PCS			= "PCS";					// Personal CommunicationStandard
	public static final String	TYPE_PREF			= "PREF";					// Indicates preferred number
	public static final String	TYPE_VIDEO			= "VIDEO";					// Indicates a video-phone // number

	public static final String	TYPE_VOICE			= "VOICE";					// Indicates a voice number (Default)

	public static final String	TYPE_WORK			= "WORK";					// Indicates a work number

	// phone Number
	private String				mNumber				= null;
	// Case insensitive list of phone types as defined in RFC2426 At least the
	// enum type above values must be supported
	private List<String>		mTypes				= new ArrayList<String>();

	public void addType(String type) {
		this.mTypes.add(type);
	}

	@Override
	public JSONObject getJSON() {

		JSONObject obj = new JSONObject();

		try {
			obj.put(PhoneNumber.JSON_FIELD_NUMBER, this.getNumber());

			JSONArray arrTypes = new JSONArray();
			for (String type : this.getTypes()) {
				arrTypes.put(type);
			}

			obj.put(PhoneNumber.JSON_FIELD_TYPES, arrTypes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}

	public String getNumber() {
		return mNumber;
	}

	public List<String> getTypes() {
		return mTypes;
	}

	@Override
	public void setJSON(JSONObject obj) {
		try {
			// number
			this.setNumber(obj.getString(PhoneNumber.JSON_FIELD_NUMBER));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// types
			JSONArray arrTypes = obj.getJSONArray(PhoneNumber.JSON_FIELD_TYPES);

			for (int index = 0; index < arrTypes.length(); index++) {
				String type = arrTypes.getString(index);
				this.addType(type);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void setNumber(String number) {
		this.mNumber = number;
	}

	public void setTypes(List<String> types) {
		this.mTypes = types;
	}

}
