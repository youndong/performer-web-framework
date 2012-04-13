package com.maru.performer.framework.feature.callback;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class FeatureCallback {
	private static final String	CLASS_NAME				= "FeatureCallback";
	private String				mJavascriptMethodName	= null;

	/**
	 * @param featureCallback
	 */
	public FeatureCallback(String featureCallback) {
		this.setJavascriptMethodName(featureCallback);
	}

	/**
	 * @return
	 */
	public String getJavascriptMethodName() {
		return mJavascriptMethodName;
	}

	/**
	 * @param javascriptMethodName
	 */
	public void setJavascriptMethodName(String javascriptMethodName) {
		this.mJavascriptMethodName = javascriptMethodName;
	}

}
