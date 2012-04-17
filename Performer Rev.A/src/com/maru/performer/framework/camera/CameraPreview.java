package com.maru.performer.framework.camera;

import java.io.IOException;
import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;

public class CameraPreview extends ViewGroup  implements SurfaceHolder.Callback {

	private static final String	CLASS_NAME		= "CameraPreview";
	private static Rect				mBoundingRect	= new Rect();

    private final String TAG = "Preview";

    SurfaceView mSurfaceView;
    private SurfaceHolder mHolder = null;
    public SurfaceHolder getHolder() {
		return mHolder;
	}

	public void setHolder(SurfaceHolder holder) {
		this.mHolder = holder;
	}

	Size mPreviewSize;
    List<Size> mSupportedPreviewSizes;
    Camera mCamera;

	private Context	mContext;
	private int	mLstLotation = -1;
	private android.widget.AbsoluteLayout.LayoutParams	al;
	private String	mUrl = null;

	CameraPreview(Context context) {
        super(context);
      
        mContext = context;
        
        mSurfaceView = new SurfaceView(context);
        addView(mSurfaceView);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        al = (android.widget.AbsoluteLayout.LayoutParams)this.getLayoutParams();
    }
	
	CameraPreview(Context context, String name) {
		this(context);
		
		mUrl  = "javascript:bondi.camera.requestTop('" + name + "')";
	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation") void applyBoundingRect() {

		/*
		if (Configuration.ORIENTATION_PORTRAIT == CameraManager.getOrientation()) {
			if (al == null) {
				al = new AbsoluteLayout.LayoutParams(mBoundingRect.height(), mBoundingRect.width(), mBoundingRect.left, mBoundingRect.top);
			} else {
				al.y = mBoundingRect.top;
				al.x = mBoundingRect.left;
				al.height = mBoundingRect.width();
				al.width = mBoundingRect.height();
			}
		} else {
		*/ 
			if (al != null) {
				al.y = mBoundingRect.top;
				al.x = mBoundingRect.left;
				al.height = mBoundingRect.height();
				al.width = mBoundingRect.width();
				requestLayout();
			} else {
				al = new AbsoluteLayout.LayoutParams(mBoundingRect.width(), mBoundingRect.height(), mBoundingRect.left, mBoundingRect.top);
				setLayoutParams(al);
			}
		// }
		
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//applyBoundingRect();
		Settings.loadUrl(mUrl);
		
		setRotation(mCamera);
		
		super.dispatchDraw(canvas);
	}

	/**
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setBoundingRect(int left, int top, int right, int bottom) {
		mBoundingRect.set(left, top, right, bottom);
	}

    public void setCamera(Camera camera) {
        mCamera = camera;
        if (mCamera != null) {
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
            requestLayout();
        }
    }

    public void switchCamera(Camera camera) {
       setCamera(camera);
       
       try {
           camera.setPreviewDisplay(mHolder);
       } catch (IOException exception) {
           Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
       }
       Camera.Parameters parameters = camera.getParameters();
       parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
       requestLayout();

       camera.setParameters(parameters);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // We purposely disregard child measurements because act as a
        // wrapper to a SurfaceView that centers the camera preview instead
        // of stretching it.
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width/*+mBoundingRect.width()*/, height/*+mBoundingRect.height()*/);
        
       
        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed && getChildCount() > 0) {
            final View child = getChildAt(0);

            final int width = r - l;
            final int height = b - t;

            int previewWidth = width;
            int previewHeight = height;
            if (mPreviewSize != null) {
                previewWidth = mPreviewSize.width;
                previewHeight = mPreviewSize.height;
            }

            // Center the child SurfaceView within the parent.
            if (width * previewHeight > height * previewWidth) {
                final int scaledChildWidth = previewWidth * height / previewHeight;
                child.layout((width - scaledChildWidth) / 2, 0,
                        (width + scaledChildWidth) / 2, height);
            } else {
                final int scaledChildHeight = previewHeight * width / previewWidth;
                child.layout(0, (height - scaledChildHeight) / 2,
                        width, (height + scaledChildHeight) / 2);
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }


    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = mCamera.getParameters();
        
        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        
        requestLayout();

        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

	private void setRotation(Camera camera) {
		
		if (camera == null)
			return;
		
		int rotation = ((Activity) mContext).getWindowManager().getDefaultDisplay().getRotation();
		
		if (rotation == mLstLotation )
			return;
		
		mLstLotation = rotation;
		
		int degrees = 0;
		switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
		}
		int result = (90 - degrees + 360) % 360;
		
		camera.stopPreview();
		camera.setDisplayOrientation(result);
		camera.startPreview();
	}
}