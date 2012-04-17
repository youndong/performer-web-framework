package com.maru.performer.framework.sensor.callback;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.ErrorCallback;

import android.util.Log;

public class SensorErrorCallback extends ErrorCallback {

	private static final String	CLASS_NAME	= "SensorErrorCallback";

	public SensorErrorCallback(String errorCallback) {
		super(errorCallback);
		// TODO Auto-generated constructor stub
	}

	public void onError(SensorError sensorError) {
		setCalled(true);
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":onError() named " + mJavascriptMethodName + " is called.");
		//Log.d(Framework.CLASS_NAME, "\t\tError code : " + sensorError.getCode());

		Settings.loadUrl("javascript:" + mJavascriptMethodName + "(" + Integer.toString(sensorError.getCode()) + ")");

	}
}
