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
public class EmailAddress extends BaseObject {

	private static final String	JSON_FIELD_EMAIL	= "email";
	private static final String	JSON_FIELD_TYPES	= "types";

	public static final String	TYPE_HOME			= "HOME";
	public static final String	TYPE_PREF			= "PREF";
	public static final String	TYPE_WORK			= "WORK";

	private String				mEmail				= null;
	private List<String>		mTypes				= new ArrayList<String>();

	public void addType(String type) {
		this.mTypes.add(type);
	}

	public String getEmail() {
		return mEmail;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();

		try {
			// name
			obj.put(EmailAddress.JSON_FIELD_EMAIL, this.getEmail());

			// nicknames
			JSONArray arrTypes = new JSONArray();
			for (String type : this.getTypes()) {
				arrTypes.put(type);
			}
			obj.put(EmailAddress.JSON_FIELD_TYPES, arrTypes);
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

	public void setEmail(String email) {
		this.mEmail = email;
	}

	@Override
	public void setJSON(JSONObject obj) {
		try {
			// email
			this.setEmail(obj.getString(EmailAddress.JSON_FIELD_EMAIL));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// types
			JSONArray arrTypes = obj.getJSONArray(EmailAddress.JSON_FIELD_TYPES);

			for (int index = 0; index < arrTypes.length(); index++) {
				String type = arrTypes.getString(index);
				this.addType(type);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void setTypes(List<String> types) {
		this.mTypes = types;
	}
}
