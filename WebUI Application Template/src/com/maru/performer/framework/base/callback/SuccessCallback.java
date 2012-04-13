package com.maru.performer.framework.base.callback;

import java.util.ArrayList;
import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;

import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class SuccessCallback {

	private static final String		CLASS_NAME				= "SuccessCallback";

	private boolean					isActivated				= false;
	private boolean					isCalled				= false;
	protected String				mJavascriptMethodName	= null;

	private List<PendingOperation>	mListeners				= new ArrayList<PendingOperation>();

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

	/**
	 * @param successCallback
	 */
	public SuccessCallback(String successCallback) {
		mJavascriptMethodName = successCallback;
		setActivated(true);
	}

	// 내가 불려질 때 알려줘야할 listner 즉 PendingOperation을 등록한다.
	/**
	 * @param op
	 */
	public void addCompleteListener(PendingOperation op) {
		mListeners.add(op);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() {
		synchronized (this) {
			mListeners.clear();
			mJavascriptMethodName = null;

			// temporary
			setActivated(false);
		}
	}

	/**
	 * @return
	 */
	public boolean isActivated() {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":isActivated() = " + Boolean.toString(isActivated));
		return isActivated;
	}

	/**
	 * @return
	 */
	public boolean isCalled() {
		return isCalled;
	}

	// 내가 불려지면 나를 등록한 pendingOperation에게 알려준다.
	// 결과적으로 PendingOperationManager에서 나를 등록한 리스너를 제거하기 위한 목적이다.
	/**
	 * 
	 */
	public void notifyListeners() {
		for (PendingOperation op : mListeners) {
			op.setCompleted(isCalled());
		}
	}

	//
	/**
	 * 
	 */
	public void onSuccess() {
		if (isCalled())
			return;

		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":onSuccess() named " + mJavascriptMethodName + " is called.");

		Settings.loadUrl("javascript:" + mJavascriptMethodName + "()");

		setCalled(true);
	}

	/**
	 * @param isActivated
	 */
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	// 내가 불려졌는지 flag로 기록해 둔다.
	/**
	 * @param isCalled
	 */
	public void setCalled(boolean isCalled) {
		this.isCalled = isCalled;
		notifyListeners();
	}
}
