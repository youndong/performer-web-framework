package com.maru.performer.framework.base.callback;

import com.maru.performer.framework.Framework;

import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class PendingOperation {
	private static final String	CLASS_NAME			= "PendingOperation";
	private boolean				isCompleted			= false;

	private long				mOperationID		= 0;
	private ErrorCallback		mErrorCallback		= null;
	private SuccessCallback		mSuccessCallback	= null;

	/**
	 * @param scb
	 * @param ecb
	 */
	public PendingOperation(SuccessCallback scb, ErrorCallback ecb) {
		mOperationID = PendingOperationManager.generateID();
		PendingOperationManager.add(this);

		mSuccessCallback = scb;
		mErrorCallback = ecb;

		mSuccessCallback.addCompleteListener(this);
	}

	/**
	 * @return
	 */
	public boolean cancel() {

		finalize();

		return true; // this call is always successful
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() {

		PendingOperationManager.remove(this);

		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":finalize() is called.");

		mSuccessCallback.finalize();
		mErrorCallback.finalize();

		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 상위 클래스 종료자 호출
	}

	/**
	 * @return
	 */
	public long getOperationID() {
		return this.mOperationID;
	}

	/**
	 * @return
	 */
	public SuccessCallback getSuccessCallback() {
		// TODO Auto-generated method stub
		return mSuccessCallback;
	}

	/**
	 * @return
	 */
	public boolean isCompleted() {
		return isCompleted;
	}

	/**
	 * @param isCompleted
	 */
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;

		if (this.isCompleted)
			finalize();
	}

	/**
	 * 
	 */
	public void waiting() {
		while (true) {
			if (mSuccessCallback.isCalled() || mErrorCallback.isCalled()) {
				setCompleted(true);
				finalize();
				return;
			}
		}
	}
}
