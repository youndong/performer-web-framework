package com.maru.performer.framework.geolocation;


import org.json.JSONException;
import org.json.JSONObject;

import com.maru.performer.framework.base.BaseObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class PositionOption extends BaseObject {
	private static final String	CLASS_NAME			= "PositionOption";

	private boolean				enableHighAccuracy	= false;
	private int					maximumAge			= 0;
	private int					timeout				= 0;

	public PositionOption() {
		this.setTimeout(0);
		this.maximumAge = 0;
		this.enableHighAccuracy = false;
	}

	public PositionOption(int timeout, int maximumAge, boolean enableHighAccuracy) {
		super();
		this.setTimeout(timeout);
		this.maximumAge = maximumAge;
		this.enableHighAccuracy = enableHighAccuracy;
	}

	public PositionOption(String options) throws JSONException {
		JSONObject jsobjOptions = new JSONObject(options);

		this.setTimeout((Integer) jsobjOptions.get("timeout"));
		this.maximumAge = (Integer) jsobjOptions.get("maximumAge");
		this.enableHighAccuracy = (Boolean) jsobjOptions.get("enableHighAccuracy");
	}

	public int getMaximumAge() {
		return maximumAge;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean isEnableHighAccuracy() {
		return enableHighAccuracy;
	}

	public void setEnableHighAccuracy(boolean enableHighAccuracy) {
		this.enableHighAccuracy = enableHighAccuracy;
	}

	public void setMaximumAge(int maximumAge) {
		this.maximumAge = maximumAge;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJSON(JSONObject obj) {
		// TODO Auto-generated method stub
		
	}

}
