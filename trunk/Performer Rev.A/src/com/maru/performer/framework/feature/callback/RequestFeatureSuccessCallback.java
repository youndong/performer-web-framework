package com.maru.performer.framework.feature.callback;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.SuccessCallback;
import com.maru.performer.framework.feature.FeatureBase;

import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class RequestFeatureSuccessCallback extends SuccessCallback {

	private static final String	CLASS_NAME	= "RequestFeatureSuccessCallback";

	// private String mJavascriptMethodName = null;

	/**
	 * @param successCallback
	 */
	public RequestFeatureSuccessCallback(String successCallback) {
		super(successCallback);
	}

	/**
	 * @param feature
	 */
	public void onSuccess(FeatureBase feature) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":" + feature.getClass());

		String url = "javascript:" + mJavascriptMethodName + "(" + feature.createJavascriptInstance() + ")";
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":\n\t\t" + url);

		Settings.loadUrl(url);

		if (isCalled())
			return;

		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":onSuccess() named " + mJavascriptMethodName + " is called.");

		setCalled(true);
	}

}
