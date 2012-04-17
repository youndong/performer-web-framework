package com.maru.performer.framework.geolocation;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.BaseObject;
import com.maru.performer.framework.geolocation.callback.PositionErrorCallback;
import com.maru.performer.framework.geolocation.callback.PositionSuccessCallback;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class WatchPosition extends BaseObject {
	private class WatchPosition_LocationListener implements LocationListener {

		public void onLocationChanged(Location loc) {
			if (loc == null) {
				//Log.d(Framework.CLASS_NAME, CLASS_NAME + "location changed to null");
			} else {
				Position position = new Position(loc);
				if (getLocationListener() != null) {
					//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":WatchPosition Id : " + Integer.toString(mID));

					mPositionSuccessCallback.onSuccess(position);
					mPositionSuccessCallback.setCalled(false);
				}
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	private static final String		CLASS_NAME					= "WatchPosition";

	public static final int			INVALID_ID					= -1;
	private Context					mContext					= null;
	public int						mID							= INVALID_ID;
	private LocationListener		mLocationListener			= null;
	private LocationManager			mLocationManager			= null;
	private List<String>			mLocationProviders			= new ArrayList<String>();

	private PositionErrorCallback	mPositionErrorCallback		= null;
	private PositionOption			mPositionOption				= null;

	private PositionSuccessCallback	mPositionSuccessCallback	= null;

	private WebView					mWebView					= null;

	public WatchPosition(Context context, WebView webView) {
		this.mContext = context;
		this.mWebView = webView;
	}

	public WatchPosition(Context context, WebView webView, final String successCallback, final String errorCallback, final PositionOption options) {
		this(context, webView);

		setSuccessCallback(new PositionSuccessCallback(successCallback));
		setErrorCallback(new PositionErrorCallback(errorCallback));

		setLocationManager((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE));

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		setLocationListener(new WatchPosition_LocationListener());
		setLocationProviders(getLocationManager().getProviders(true));

		for (int i = 0; i < getLocationProviders().size(); i++) {
			getLocationManager().requestLocationUpdates(getLocationProviders().get(i), options.getTimeout(), 0, getLocationListener());
		}
	}

	public PositionErrorCallback getErrorCallback() {
		return mPositionErrorCallback;
	}

	public int getID() {
		return this.mID;
	}

	@Override
	public JSONObject getJSON() {
		return null;
	}

	public LocationListener getLocationListener() {
		return mLocationListener;
	}

	public LocationManager getLocationManager() {
		return mLocationManager;
	}

	public List<String> getLocationProviders() {
		return mLocationProviders;
	}

	public PositionOption getPositionOption() {
		return mPositionOption;
	}

	public PositionSuccessCallback getSuccessCallback() {
		return mPositionSuccessCallback;
	}

	public void setErrorCallback(PositionErrorCallback positionErrorCallback) {
		this.mPositionErrorCallback = positionErrorCallback;
	}

	public void setID(int id) {
		this.mID = id;
	}

	@Override
	public void setJSON(JSONObject obj) {
		// TODO Auto-generated method stub
	}

	public void setLocationListener(LocationListener locationListener) {
		this.mLocationListener = locationListener;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.mLocationManager = locationManager;
	}

	public void setLocationProviders(List<String> locationProviders) {
		this.mLocationProviders = locationProviders;
	}

	public void setPositionOption(PositionOption positionOption) {
		this.mPositionOption = positionOption;
	}

	public void setSuccessCallback(PositionSuccessCallback positionSuccessCallback) {
		this.mPositionSuccessCallback = positionSuccessCallback;
	}
}
