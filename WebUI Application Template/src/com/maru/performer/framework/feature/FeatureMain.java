package com.maru.performer.framework.feature;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.BaseObject;
import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.DeviceAPIError;
import com.maru.performer.framework.base.callback.ErrorCallback;
import com.maru.performer.framework.base.callback.PendingOperation;
import com.maru.performer.framework.call.CallManager;
import com.maru.performer.framework.camera.CameraManager;
import com.maru.performer.framework.contact.ContactManager;
import com.maru.performer.framework.demo.Test;
import com.maru.performer.framework.feature.callback.FeatureCallback;
import com.maru.performer.framework.feature.callback.RequestFeatureSuccessCallback;
import com.maru.performer.framework.geolocation.Geolocation;
import com.maru.performer.framework.sensor.SensorManager;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class FeatureMain extends FeatureBase {

	public static final String		CLASS_NAME				= "Bondi";

	public static final int			FEATURE_APPCONFIG		= 0x00000001;
	public static final int			FEATURE_APPLAUNCHER		= 0x00000002;
	public static final int			FEATURE_CALENDAR		= 0x00000004;
	public static final int			FEATURE_CALL			= 0x00000008;
	public static final int			FEATURE_CAMERA			= 0x0000000F;
	public static final int			FEATURE_CONTACT			= 0x00000010;
	public static final int			FEATURE_DEVICESTATUS	= 0x00000020;
	public static final int			FEATURE_FILESYSTEM		= 0x00000040;
	public static final int			FEATURE_GALLERY			= 0x00000080;
	public static final int			FEATURE_GEOLOCATION		= 0x000000F0;
	public static final int			FEATURE_MESSAGING		= 0x00000100;
	public static final int			FEATURE_PIM				= 0x00000200;
	public static final int			FEATURE_SENSOR			= 0x00000400;
	public static final int			FEATURE_TASK			= 0x00000800;
	public static final int			FEATURE_TELEPHONY		= 0x00000F00;
	public static final int			FEATURE_UI				= 0x00001000;

	private final Handler			handler					= new Handler();

	private List<FeatureListener>	mFeatureListeners		= new ArrayList<FeatureListener>();

	private List<FeatureBase>		mFeatures				= new ArrayList<FeatureBase>();

	private Test					test					= null;

	/**
	 * @param context
	 * @param webView
	 */
	public FeatureMain(Context context, WebView webView) {
		super(context, webView);
	}

	/**
	 * @param watch
	 */
	public void clearWatch(final long watch) {
		for (FeatureListener fl : mFeatureListeners) {
			if (fl.getID() == watch) {
				mFeatureListeners.remove(fl);
			}
		}
	}


	/**
	 * @param obj feature
	 * @param status the current status of feature
	 * @param desc the description of the current status
	 */
	public void notifyFeatureChanged(FeatureBase obj, int status, String desc) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":notifyFeatureChanged()");
		//Log.d(Framework.CLASS_NAME, "\t\tFeature 		: " + obj.getClass());
		//Log.d(Framework.CLASS_NAME, "\t\tChanged code	: " + status);
		//Log.d(Framework.CLASS_NAME, "\t\tDescription 	: " + desc);

		for (FeatureListener fl : mFeatureListeners) {
			//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":" + fl.getFeatureCallback().getJavascriptMethodName());
		}
	}

	/**
	 * @param successCallback
	 * @param errorCallback
	 * @param feature
	 * @return
	 */
	public String requestFeature(final String successCallback, final String errorCallback, final int feature) {
		final RequestFeatureSuccessCallback scb = new RequestFeatureSuccessCallback(successCallback);
		final ErrorCallback ecb = new ErrorCallback(errorCallback);

		PendingOperation op = new PendingOperation(scb, ecb);

		handler.post(new Runnable() {

			public void run() {

				switch (feature) {
					case FEATURE_CONTACT: {
						ContactManager mContactManager = new ContactManager(mContext, mWebView);

						if (mContactManager != null) {

							Settings.addJavascriptInterface(mContactManager, "contact");

							if (scb.isActivated()) {
								scb.onSuccess(mContactManager);
								mFeatures.add(mContactManager);
								mContactManager.setStatusChangeListener(FeatureMain.this);
								mContactManager.createJavascriptInstance();
							}
						} else {
							if (ecb.isActivated()) {
								ecb.onError(DeviceAPIError.NOT_SUPPORTED_ERROR);
							}
						}
						break;
					}
					case FEATURE_CAMERA: {
						CameraManager mCameraManager = new CameraManager(mContext, mWebView);
						if (mCameraManager != null) {
							Settings.addJavascriptInterface(mCameraManager, "camera");
							if (scb.isActivated() == true) {
								scb.onSuccess(mCameraManager);
								mFeatures.add(mCameraManager);
								mCameraManager.setStatusChangeListener(FeatureMain.this);
								mCameraManager.createJavascriptInstance();
							}
						} else {
							if (ecb.isActivated()) {
								ecb.onError(DeviceAPIError.NOT_SUPPORTED_ERROR);
							}
						}
						break;
					}
					case FEATURE_GEOLOCATION: {
						Geolocation mGeolocation = new Geolocation(mContext, mWebView);
						if (mGeolocation != null) {
							Settings.addJavascriptInterface(mGeolocation, "geolocation");
							if (scb.isActivated() == true) {
								scb.onSuccess(mGeolocation);
								mFeatures.add(mGeolocation);
								mGeolocation.setStatusChangeListener(FeatureMain.this);
								mGeolocation.createJavascriptInstance();
							}
						} else {
							if (ecb.isActivated()) {
								ecb.onError(DeviceAPIError.NOT_SUPPORTED_ERROR);
							}
						}
						break;
					}
					case FEATURE_CALL: {
						CallManager mCallManager = new CallManager(mContext, mWebView);
						if (mCallManager != null) {
							Settings.addJavascriptInterface(mCallManager, "callmanager");
							if (scb.isActivated() == true) {
								scb.onSuccess(mCallManager);
								mFeatures.add(mCallManager);
								mCallManager.setStatusChangeListener(FeatureMain.this);
								mCallManager.createJavascriptInstance();
							}
						} else {
							if (ecb.isActivated()) {
								ecb.onError(DeviceAPIError.NOT_SUPPORTED_ERROR);
							}
						}
						break;
					}
					case FEATURE_SENSOR: {
						SensorManager mSensorManager = new SensorManager(mContext, mWebView);
						if (mSensorManager != null) {
							Settings.addJavascriptInterface(mSensorManager, "sensor");
							if (scb.isActivated() == true) {
								scb.onSuccess(mSensorManager);
								mFeatures.add(mSensorManager);
								mSensorManager.setStatusChangeListener(FeatureMain.this);
								mSensorManager.createJavascriptInstance();
							}
						} else {
							if (ecb.isActivated()) {
								ecb.onError(DeviceAPIError.NOT_SUPPORTED_ERROR);
							}
						}
						break;
					}
					default:
						break;
				}
			}
		});

		op.waiting();

		return Long.toString(op.getOperationID());

	}

	/**
	 * 
	 */
	public void test() {
		handler.post(new Runnable() {
			public void run() {
				//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":test()");
			}
		});
	}

	/**
	 * @param featureCallback
	 * @return
	 */
	public long watchFeatures(final String featureCallback) {
		FeatureListener fl = new FeatureListener(new FeatureCallback(featureCallback));
		mFeatureListeners.add(fl);
		return fl.getID();
	}

	@Override
	public String createJavascriptInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
