package com.maru.performer.framework.feature;

import java.util.Calendar;

import com.maru.performer.framework.feature.callback.FeatureCallback;


/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class FeatureListener {
	private static final String	CLASS_NAME			= "FeatureListener";
	private FeatureCallback		mFeatureCallback	= null;
	private long				mID					= 0;

	/**
	 * @param featureCallback
	 */
	public FeatureListener(FeatureCallback featureCallback) {
		this.setID(Calendar.getInstance().getTimeInMillis());
		this.setFeatureCallback(featureCallback);
	}

	/**
	 * @return
	 */
	public FeatureCallback getFeatureCallback() {
		return mFeatureCallback;
	}

	/**
	 * @return
	 */
	public long getID() {
		return mID;
	}

	/**
	 * @param mFeatureCallback
	 */
	public void setFeatureCallback(FeatureCallback mFeatureCallback) {
		this.mFeatureCallback = mFeatureCallback;
	}

	/**
	 * @param id
	 */
	public void setID(long id) {
		this.mID = id;
	}
}
