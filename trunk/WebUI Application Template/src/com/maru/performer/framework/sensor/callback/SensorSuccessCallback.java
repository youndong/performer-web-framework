package com.maru.performer.framework.sensor.callback;

import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.SuccessCallback;
import com.maru.performer.framework.sensor.SensorManager;


public class SensorSuccessCallback extends SuccessCallback {

	private static final String	CLASS_NAME	= "SensorSuccessCallback";

	public SensorSuccessCallback(String successCallback) {
		super(successCallback);
		// TODO Auto-generated constructor stub
	}

	public void onSuccess(int sensor, final float[] values) {
		// if (isCalled())
		// return;

		synchronized (this) {

			switch (sensor) {
				case android.hardware.SensorManager.SENSOR_ORIENTATION:
					if (mJavascriptMethodName != null) {
						Settings.loadUrl("javascript:" + mJavascriptMethodName + "(new Sensor(" + SensorManager.SENSOR_ORIENTATION + ", new Orientation("
								+ Float.toString(values[0]) + "," + Float.toString(values[1]) + "," + Float.toString(values[2]) + ")" + "))");
					}
					break;
				case android.hardware.SensorManager.SENSOR_ACCELEROMETER:
					break;
				case android.hardware.SensorManager.SENSOR_TEMPERATURE:
					break;
				case android.hardware.SensorManager.SENSOR_MAGNETIC_FIELD:

					if (mJavascriptMethodName != null) {
						Settings.loadUrl("javascript:" + mJavascriptMethodName + "(new Sensor(" + SensorManager.SENSOR_MAGNETIC_FIELD + ", new MagneticField("
								+ Float.toString(values[0]) + "," + Float.toString(values[1]) + "," + Float.toString(values[2]) + ")" + "))");
					}
					break;
				case android.hardware.SensorManager.SENSOR_LIGHT:
					break;
				case android.hardware.SensorManager.SENSOR_PROXIMITY:
					break;
				case android.hardware.SensorManager.SENSOR_TRICORDER:
					break;
				case android.hardware.SensorManager.SENSOR_ORIENTATION_RAW:
					break;
				default:
					break;
			}

		}

		// setCalled(true);
	}

}
