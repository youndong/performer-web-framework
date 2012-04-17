package com.maru.performer.framework.contact.callback;

import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.Settings;
import com.maru.performer.framework.base.callback.SuccessCallback;
import com.maru.performer.framework.contact.AddressBook;

import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class AddressBookArraySuccessCallback extends SuccessCallback {

	private static final String	CLASS_NAME	= "AddressBookArraySuccessCallback";

	public AddressBookArraySuccessCallback(String successCallback) {
		super(successCallback);
		// TODO Auto-generated constructor stub
	}

	public void onSuccess(List<AddressBook> addressBooks) {

		// String url = "javascript:" + mJavascriptMethodName + "(";
		//
		// JSONArray arr = new JSONArray();
		//
		// for (int location = 0; location < addressBooks.size(); location++) {
		// AddressBook ab = addressBooks.get(location);
		// if (ab != null) {
		// JSONObject obj = ab.getJSONObject();
		// if (obj != null)
		// {
		// arr.put(obj);
		// }
		// }
		// }
		//
		// url = url+ arr.toString() + ")";

		String url = "javascript:" + mJavascriptMethodName + "(new Array(";

		for (int location = 0; location < addressBooks.size(); location++) {
			AddressBook ab = addressBooks.get(location);
			if (ab != null) {
				url = url + "new AddressBook(" + ab.getID() + ", '" + ab.getName() + "', " + ab.getType() + ")";
			}

			if (location < addressBooks.size() - 1) {
				url = url + ",";
			}
		}

		url = url + "))";

		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":\n\t\t" + url);

		Settings.loadUrl(url);
	}
}
