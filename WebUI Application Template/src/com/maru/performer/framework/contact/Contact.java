package com.maru.performer.framework.contact;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class Contact extends ContactProperties {
	public static final String	CREATED_CONTACT	= "created";

	private static final String	JSON_FIELD_ID	= "id";

	private String				mID				= null;

	public String getID() {
		return mID;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = super.getJSON();

		try {
			obj.put(JSON_FIELD_ID, this.getID());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}

	public void setID(String id) {
		this.mID = id;
	}

	@Override
	public void setJSON(JSONObject obj) {
		// 여기 수정할 것.
		try {
			// name
			this.setID(obj.getString(Contact.JSON_FIELD_ID));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		super.setJSON(obj);

	}
}
