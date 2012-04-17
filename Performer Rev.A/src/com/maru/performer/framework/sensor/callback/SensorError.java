package com.maru.performer.framework.sensor.callback;

public class SensorError {
	static public final int	PERMISSION_DENIED	= 1;

	static public final int	SENSOR_UNAVAILABLE	= 2;

	static public final int	TIMEOUT				= 3;

	private int				mCode				= 0;

	private String			mMessage			= null;

	public int getCode() {
		return mCode;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setCode(int code) {
		this.mCode = code;
	}

	public void setMessage(String message) {
		this.mMessage = message;
	}
}
