package com.maru.performer.framework.contact.callback;

import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.callback.SuccessCallback;

import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class ContactArraySuccessCallback extends SuccessCallback {

	private static final String	CLASS_NAME	= "ContactArraySuccessCallback";

	// private String mJavascriptMethodName = null;

	public ContactArraySuccessCallback(String successCallback) {
		super(successCallback);
	}

	public void onSuccess(List<String> contactIds) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":onSuccess");

		setCalled(true);
	}
}
