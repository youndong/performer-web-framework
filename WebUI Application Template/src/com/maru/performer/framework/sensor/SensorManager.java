package com.maru.performer.framework.sensor;

import com.maru.performer.framework.feature.FeatureBase;
import com.maru.performer.framework.sensor.callback.SensorError;
import com.maru.performer.framework.sensor.callback.SensorErrorCallback;
import com.maru.performer.framework.sensor.callback.SensorSuccessCallback;

import android.content.Context;
import android.hardware.SensorListener;
import android.os.Handler;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class SensorManager extends FeatureBase implements SensorListener {

	public static final String				CLASS_NAME				= "Sensor";
	public static final String				SENSOR_ACCELEROMETER	= "Sensor.SENSOR_ACCELEROMETER";
	public static final String				SENSOR_LIGHT			= "Sensor.SENSOR_LIGHT";
	public static final String				SENSOR_MAGNETIC_FIELD	= "Sensor.SENSOR_MAGNETIC_FIELD";
	public static final String				SENSOR_ORIENTATION		= "Sensor.SENSOR_ORIENTATION";
	public static final String				SENSOR_ORIENTATION_RAW	= "Sensor.SENSOR_ORIENTATION_RAW";
	public static final String				SENSOR_PROXIMITY		= "Sensor.SENSOR_PROXIMITY";
	public static final String				SENSOR_TEMPERATURE		= "Sensor.SENSOR_TEMPERATURE";
	public static final String				SENSOR_TRICORDER		= "Sensor.SENSOR_TRICORDER";
	private final Handler					handler					= new Handler();

	private SensorErrorCallback				mSensorErrorCallback	= null;
	private android.hardware.SensorManager	mSensorManager			= null;								;
	private int								mSensors				= 0;
	private SensorSuccessCallback			mSensorSuccessCallback	= null;

	public SensorManager(Context context, WebView webView) {
		super(context, webView);

		mSensorManager = (android.hardware.SensorManager) mContext.getSystemService(mContext.getApplicationContext().SENSOR_SERVICE);
	}

	public void onAccuracyChanged(int sensor, int value) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(int sensor, final float[] values) {
		if (mSensorSuccessCallback != null)
			mSensorSuccessCallback.onSuccess(sensor, values);
		else {
			if (mSensorErrorCallback != null) {
				SensorError sensorError = new SensorError();
				sensorError.setCode(SensorError.SENSOR_UNAVAILABLE);
				sensorError.setMessage("Can't find the SensorSuccessCallback");
				mSensorErrorCallback.onError(sensorError);
			}
		}
	}

	public void pause() {
		stop();
	}

	public void resume() {
		mSensorManager.registerListener(this, mSensors, android.hardware.SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void start(final int sensor, final String successCallback, final String errorCallback) {
		mSensors = sensor;

		mSensorManager.registerListener(this, sensor, android.hardware.SensorManager.SENSOR_DELAY_FASTEST);

		mSensorSuccessCallback = new SensorSuccessCallback(successCallback);
		mSensorErrorCallback = new SensorErrorCallback(errorCallback);
	}

	public void stop() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public String createJavascriptInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
