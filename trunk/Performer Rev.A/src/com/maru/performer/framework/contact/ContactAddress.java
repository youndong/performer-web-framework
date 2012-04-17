package com.maru.performer.framework.contact;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class ContactAddress extends Address {

	private static final String	JSON_FIELD_TYPES	= "types";

	public static final String	TYPE_HOME			= "HOME";
	public static final String	TYPE_PREF			= "PREF";
	public static final String	TYPE_WORK			= "WORK";
	// Case insensitive list of address types At least the following values MUST
	// be supported:
	// WORK - Indicates a work address
	// PREF - Indicates preferred address
	// HOME - Indicates a home address
	private List<String>		mTypes				= new ArrayList<String>();

	public void addType(String type) {
		this.mTypes.add(type);
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = super.getJSON();

		try {
			JSONArray arrTypes = new JSONArray();
			for (String type : this.getTypes()) {
				arrTypes.put(type);
			}
			obj.put(ContactAddress.JSON_FIELD_TYPES, arrTypes);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}

	public List<String> getTypes() {
		return mTypes;
	}

	@Override
	public void setJSON(JSONObject obj) {

		try {
			// types
			JSONArray arrTypes = obj.getJSONArray(ContactAddress.JSON_FIELD_TYPES);

			for (int index = 0; index < arrTypes.length(); index++) {
				String type = arrTypes.getString(index);
				this.addType(type);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		super.setJSON(obj);

	}

	public void setTypes(List<String> types) {
		this.mTypes = types;
	}
}
