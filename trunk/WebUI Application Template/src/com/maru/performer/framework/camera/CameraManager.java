package com.maru.performer.framework.camera;

import java.io.IOException;
import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.feature.FeatureBase;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class CameraManager extends FeatureBase {
	// private class Preview

	public static final String	CLASS_NAME		= "CameraManager";

	private final Handler		handler			= new Handler();

	private CameraPreview		mPreview	= null;

	private Camera mCamera;

	private int	numberOfCameras;

	private int	defaultCameraId;
	
	private static int					mOrientation	= Configuration.ORIENTATION_UNDEFINED;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param webView
	 */
	public CameraManager(Context context, WebView webView) {
		super(context, webView);
		changeStatus(FeatureBase.STATUS_ENABLED, "Enabled");
	}

	@Override
	public String createJavascriptInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Camera preview is started by camera.startPreview in JavaScript
	 * 
	 * @param name
	 *            camera identifier
	 * @param left
	 *            the left of bounding rectangle of preview
	 * @param top
	 *            the top of bounding rectangle of preview
	 * @param right
	 *            the right of bounding rectangle of preview
	 * @param bottom
	 *            the bottom of bounding rectangle of preview
	 */
	public void createPreview(final String name, final int left, final int top, final int right, final int bottom) {

		((Activity) mContext).runOnUiThread(new Runnable() {

			private int	cameraCurrentlyLocked;

			public void run() {
				mPreview = new CameraPreview(mContext, name);
				
				if ( mPreview == null )
					return;
				
				mPreview.setBoundingRect(left, top, right, bottom);
				mPreview.applyBoundingRect();
				
				mWebView.addView(mPreview);

				// Find the total number of cameras available
				numberOfCameras = Camera.getNumberOfCameras();

				// Find the ID of the default camera
				CameraInfo cameraInfo = new CameraInfo();
				for (int i = 0; i < numberOfCameras; i++) {
					Camera.getCameraInfo(i, cameraInfo);
					if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
						defaultCameraId = i;
					}
				}

		        mCamera = Camera.open();
		        
		        cameraCurrentlyLocked = defaultCameraId;
		        mPreview.setCamera(mCamera);
			}
		}
		);
		/*
		getOrientation();

		((Activity) mContext).runOnUiThread(new Runnable() {

			public void run() {

				if (mCameraPreview == null) {
					mCameraPreview = new CameraPreview(mContext, name);

					if (mCameraPreview != null) {

						mCameraPreview.setBackgroundColor(Color.TRANSPARENT);

						mCameraPreview.setBoundingRect(left, top, right, bottom);
						mCameraPreview.applyBoundingRect();

						mWebView.addView(mCameraPreview);
					}
				} else {
					setClientRect(name, left, top, right, bottom);
				}

			}
		});
		
		*/

	}

	/**
	 * Return the current orientation of camera
	 * 
	 * @return the current orientation of camera
	 */
	public static int getOrientation() {
		// begin of debug
		if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":Orientation : ORIENTATION_LANDSCAPE");
		} else if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
			//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":Orientation : ORIENTATION_PORTRAIT");
		} // end of debug

		return mOrientation;
	}

	/**
	 * Set the new configuration to change the orientation
	 * 
	 * @param newConfig
	 *            the changed device configuration information
	 */
	public void onConfigurationChanged(Configuration newConfig) {
		mOrientation = newConfig.orientation;
	}

	/**
	 * When HTML page is scrolled or zoom in/out, the coordinates of the bounding rectangle is changed. So, it should be
	 * transfered to the native camera module to apply changed bounding rectangle.
	 * 
	 * @param name
	 *            camera identifier
	 * @param left
	 *            the left of the changed bounding rectangle
	 * @param top
	 *            the top of the changed bounding rectangle
	 * @param right
	 *            the right of the changed bounding rectangle
	 * @param bottom
	 *            the bottom of the changed bounding rectangle
	 */
	public void setClientRect(final String name, final int left, final int top, final int right, final int bottom) {

		((Activity) mContext).runOnUiThread(new Runnable() {

			public void run() {

				if (mPreview != null) {

					mPreview.setBoundingRect(left, top, right, bottom);
					mPreview.applyBoundingRect();
				}
			}
		});
	}

	public void terminate() {

		((Activity) mContext).runOnUiThread(new Runnable() {

			public void run() {

				if (mPreview != null) {
					mWebView.removeView(mPreview);
					mCamera.release();
					mPreview = null;
				}

				changeStatus(FeatureBase.STATUS_TERMINATED, "Terminated");
			}
		});
	}
}


