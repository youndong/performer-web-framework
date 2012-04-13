package com.maru.performer.framework.contact;

import java.util.ArrayList;
import java.util.List;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.callback.DeviceAPIError;
import com.maru.performer.framework.base.callback.ErrorCallback;
import com.maru.performer.framework.base.callback.PendingOperation;
import com.maru.performer.framework.contact.callback.AddressBookArraySuccessCallback;
import com.maru.performer.framework.feature.FeatureBase;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class ContactManager extends FeatureBase {

	private static final String		CLASS_NAME		= "ContactManager";

	private static final Handler	handler			= new Handler();
	private List<AddressBook>		mAddressBooks	= new ArrayList<AddressBook>();

	public ContactManager(Context context, WebView webView) {
		super(context, webView);

		AddressBook addressbook = new AddressBook(this.mContext, AddressBook.DEVICE_ADDRESS_BOOK);

		mAddressBooks.add(addressbook);
	}

	/**
	 * @param id
	 * @param successCallback
	 * @param errorCallback
	 * @param contact
	 * @return
	 */
	public String addContact(String id, String successCallback, String errorCallback, String contact) {
		AddressBook ab = findAddressBook(id);
		if (ab != null)
			return ab.addContact(successCallback, errorCallback, contact);

		return null;
	}

	public String createContact(String id, String contactProperties) {

		AddressBook ab = findAddressBook(id);

		if (ab != null) {
			String ret = ab.createContact(contactProperties);
			//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":createContact(), " + ret);
			return ret;
		}

		return null;
	}

	@Override
	public String createJavascriptInstance() {
		String url = "new Contact('contact')";
		return url;
	}

	/**
	 * @param id
	 * @param successCallback
	 * @param errorCallback
	 * @param contact
	 * @return
	 */
	public String deleteContact(String id, String successCallback, String errorCallback, String contact) {
		AddressBook ab = findAddressBook(id);
		if (ab != null)
			return ab.deleteContact(successCallback, errorCallback, contact);

		return null;
	}

	/**
	 * @param id
	 * @return
	 */
	private AddressBook findAddressBook(String id) {
		for (AddressBook ab : mAddressBooks) {
			if (ab.getID().equals(id)) {
				return ab;
			}
		}
		return null;
	}

	/**
	 * @param id
	 * @param successCallback
	 * @param errorCallback
	 * @param filter
	 * @return
	 */
	public String findContacts(String id, String successCallback, String errorCallback, String filter) {
		AddressBook ab = findAddressBook(id);
		if (ab != null)
			return ab.findContacts(successCallback, errorCallback, filter);

		return null;
	}

	public String getAddressBooks(final String successCallback, final String errorCallback) {

		final AddressBookArraySuccessCallback scb = new AddressBookArraySuccessCallback(successCallback);
		final ErrorCallback ecb = new ErrorCallback(errorCallback);

		PendingOperation op = new PendingOperation(scb, ecb);

		handler.post(new Runnable() {

			public void run() {
				if (mAddressBooks.size() > 0)
					scb.onSuccess(mAddressBooks);
				else
					ecb.onError(DeviceAPIError.NOT_FOUND_ERROR);
			}
		});

		return Long.toString(op.getOperationID());
	}

	/**
	 * @param id
	 * @param successCallback
	 * @param errorCallback
	 * @param contact
	 * @return
	 */
	public String updateContact(String id, String successCallback, String errorCallback, String contact) {
		AddressBook ab = findAddressBook(id);
		if (ab != null)
			return ab.updateContact(successCallback, errorCallback, contact);

		return null;
	}

	public void xxx(String a) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":test()");
	}

}
