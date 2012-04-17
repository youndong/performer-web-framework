package com.maru.performer.framework.geolocation;

import org.json.JSONObject;

import com.maru.performer.framework.base.BaseObject;

import android.location.Location;

public class Position extends BaseObject {
	public static Position convertToPosition(Location loc) {
		if (loc == null)
			return null;

		Position position = new Position();
		Coordinates _coord = new Coordinates();

		position.setTimeStamp(loc.getTime());

		Coordinates coord = new Coordinates();
		coord.setAccuracy(loc.getAccuracy());
		coord.setAltitude(loc.getAltitude());
		// coord.setAltitudeAccuracy(); // not supported on Android
		coord.setHeading(loc.getBearing());
		coord.setLatitude(loc.getLatitude());
		coord.setLongitude(loc.getLongitude());
		coord.setSpeed(loc.getSpeed());

		position.setCoords(coord);
		return position;
	}

	private Coordinates	mCoords		= null;

	private long		mTimeStamp	= 0;

	public Position() {
		this.mCoords = new Coordinates();
		this.mTimeStamp = 0;
	}

	public Position(Location loc) {
		this.mCoords = new Coordinates();
		this.mTimeStamp = 0;

		setTimeStamp(loc.getTime());

		this.mCoords.setAccuracy(loc.getAccuracy());
		this.mCoords.setAltitude(loc.getAltitude());
		// this.mCoords.setAltitudeAccuracy(); // not supported on Android
		this.mCoords.setHeading(loc.getBearing());
		this.mCoords.setLatitude(loc.getLatitude());
		this.mCoords.setLongitude(loc.getLongitude());
		this.mCoords.setSpeed(loc.getSpeed());
	}

	public Coordinates getCoords() {
		return mCoords;
	}

	public long getTimeStamp() {
		return mTimeStamp;
	}

	public void setCoords(Coordinates coords) {
		this.mCoords = coords;
	}

	public void setTimeStamp(long timeStamp) {
		this.mTimeStamp = timeStamp;
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
