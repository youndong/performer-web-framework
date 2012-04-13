package com.maru.performer.framework.geolocation.callback;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.SuccessCallback;
import com.maru.performer.framework.geolocation.Position;

import android.util.Log;

public class PositionSuccessCallback extends SuccessCallback {

	private final String	CLASS_NAME	= "PositionSuccessCallback";

	public PositionSuccessCallback(String successCallback) {
		super(successCallback);
		// TODO Auto-generated constructor stub
	}

	public void onSuccess(Position position) {
		if (isCalled())
			return;

		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":onSuccess() named " + mJavascriptMethodName + " is called.");
		String urlCallback = "javascript:" //
				+ mJavascriptMethodName //
				+ "(" //
				+ "new Position(" //
				+ position.getTimeStamp() //
				+ ", new Coordinates(" //
				+ position.getCoords().getLatitude() // //
				+ ", " //
				+ position.getCoords().getLongitude() // Double.toString(loc.getLongitude()) //
				+ ", " //
				+ position.getCoords().getAltitude() // Double.toString(loc.getAltitude()) //
				+ ", " //
				+ position.getCoords().getAccuracy() //
				+ ", " //
				+ position.getCoords().getAltitudeAccuracy() // "0.0"
				+ ", " //
				+ position.getCoords().getHeading() //
				+ ", " //
				+ position.getCoords().getSpeed() //
				+ ")" //
				+ ")" //
				+ ")";

		Settings.loadUrl(urlCallback);

		setCalled(true);
	}

}
