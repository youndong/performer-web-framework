package com.maru.performer.framework.geolocation;

import org.json.JSONObject;

import com.maru.performer.framework.base.BaseObject;


public class Coordinates extends BaseObject {
	private double	mAccuracy			= 0;
	private double	mAltitude			= 0;
	private double	mAltitudeAccuracy	= 0;
	private double	mHeading			= 0;
	private double	mLatitude			= 0;
	private double	mLongitude			= 0;
	private double	mSpeed				= 0;

	public double getAccuracy() {
		return mAccuracy;
	}

	public double getAltitude() {
		return mAltitude;
	}

	public double getAltitudeAccuracy() {
		return mAltitudeAccuracy;
	}

	public double getHeading() {
		return mHeading;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public double getSpeed() {
		return mSpeed;
	}

	public void setAccuracy(double accuracy) {
		this.mAccuracy = accuracy;
	}

	public void setAltitude(double altitude) {
		this.mAltitude = altitude;
	}

	public void setAltitudeAccuracy(double altitudeAccuracy) {
		this.mAltitudeAccuracy = altitudeAccuracy;
	}

	public void setHeading(double heading) {
		this.mHeading = heading;
	}

	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}

	public void setSpeed(double speed) {
		this.mSpeed = speed;
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
