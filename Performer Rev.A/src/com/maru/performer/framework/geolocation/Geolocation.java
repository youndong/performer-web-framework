package com.maru.performer.framework.geolocation;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.feature.FeatureBase;
import com.maru.performer.framework.geolocation.callback.PositionErrorCallback;
import com.maru.performer.framework.geolocation.callback.PositionSuccessCallback;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class Geolocation extends FeatureBase {

	private class GetCurrentPosition_LocationListener implements LocationListener {

		public void onLocationChanged(Location loc) {
			if (loc == null) {
				//Log.e(Framework.CLASS_NAME, CLASS_NAME + ":onLocationChanged() : loc is null");
			} else {

				Position position = new Position(loc);

				if (mLocationListener != null) {
					mPositionSuccessCallback.onSuccess(position);

					mLocationManager.removeUpdates(mLocationListener);
					mLocationListener = null;
				}
			}
		}

		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	public static final String		CLASS_NAME					= "Geolocation";

	private final Handler			handler						= new Handler();

	private LocationListener		mLocationListener;
	// getCurrentPosition
	private LocationManager			mLocationManager;
	private List<String>			mLocationProviders;
	private PositionErrorCallback	mPositionErrorCallback;

	private PositionSuccessCallback	mPositionSuccessCallback	= null;

	// watchPosition
	private List<WatchPosition>		mWatchPositions				= new ArrayList<WatchPosition>();

	public Geolocation(Context context, WebView webView) {
		super(context, webView);
		changeStatus(FeatureBase.STATUS_ENABLED, "Enabled");
	}

	public void clearWatch(int id) {
		WatchPosition wpc = mWatchPositions.get(id);

		if (wpc != null) {
			if (wpc.getLocationListener() != null)
				wpc.getLocationManager().removeUpdates(wpc.getLocationListener());

			if (wpc.getLocationProviders() != null)
				wpc.getLocationProviders().clear();

			mWatchPositions.set(id, null);
			wpc.setLocationManager(null);
			wpc.setLocationListener(null);
			wpc.setSuccessCallback(null);
			wpc.setErrorCallback(null);
			wpc.setPositionOption(null);
		}
	}

	@Override
	public String createJavascriptInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getCurrentPosition(final String successCallback, final String errorCallback, final String options) throws JSONException {

		mPositionSuccessCallback = new PositionSuccessCallback(successCallback);
		mPositionErrorCallback = new PositionErrorCallback(errorCallback);

		PositionOption positionOptions = new PositionOption(options);

		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		mLocationListener = new GetCurrentPosition_LocationListener();
		mLocationProviders = mLocationManager.getProviders(true);

		for (int i = 0; i < mLocationProviders.size(); i++) {
			mLocationManager.requestLocationUpdates(mLocationProviders.get(i), positionOptions.getTimeout(), 0, mLocationListener);
		}

	}

	public PositionErrorCallback getErrorCallback_GetCurrentPosition() {
		return mPositionErrorCallback;
	}

	public JSONObject getJSON() {
		return null;
	}

	public PositionSuccessCallback getPositionSuccessCallback() {
		return mPositionSuccessCallback;
	}

	public void setJSON(JSONObject obj) {
		// TODO Auto-generated method stub

	}

	public void setPositionErrorCallback(PositionErrorCallback positionErrorCallback) {
		this.mPositionErrorCallback = positionErrorCallback;
	}

	public void setPositionSuccessCallback(PositionSuccessCallback positionSuccessCallback) {
		this.mPositionSuccessCallback = positionSuccessCallback;
	}

	public void terminate() {

		//

		if (mLocationListener != null)
			mLocationManager.removeUpdates(mLocationListener);

		if (mLocationProviders != null)
			mLocationProviders.clear();

		mLocationManager = null;
		mLocationListener = null;
		mPositionSuccessCallback = null;
		mPositionErrorCallback = null;

		for (int location = 0; location < mWatchPositions.size(); location++) {
			clearWatch(location);
		}

		mWatchPositions.clear();
	}

	public int watchPosition(final String successCallback, final String errorCallback, final String options) {

		PositionOption positionOptions = null;
		try {
			positionOptions = new PositionOption(options);
		} catch (JSONException e) {
			return WatchPosition.INVALID_ID;
		}

		WatchPosition wpc = new WatchPosition(mContext, mWebView, successCallback, errorCallback, positionOptions);
		wpc.setID(mWatchPositions.size());
		mWatchPositions.add(wpc);

		return wpc.getID();
	}
}
