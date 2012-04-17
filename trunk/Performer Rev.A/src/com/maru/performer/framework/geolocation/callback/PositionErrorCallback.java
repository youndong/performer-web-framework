package com.maru.performer.framework.geolocation.callback;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.ErrorCallback;

import android.util.Log;

public class PositionErrorCallback extends ErrorCallback {

	private final String	CLASS_NAME	= "PositionErrorCallback";

	public PositionErrorCallback(String errorCallback) {
		super(errorCallback);
		// TODO Auto-generated constructor stub
	}

	public void onError(PositionError positionError) {
		setCalled(true);
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":onError() named " + mJavascriptMethodName + " is called.");
		//Log.d(Framework.CLASS_NAME, "\t\tError code : " + positionError.getCode());

		Settings.loadUrl("javascript:" + mJavascriptMethodName + "(" + Integer.toString(positionError.getCode()) + ")");

	}

}
