package com.maru.performer.framework.contact;


import org.json.JSONException;
import org.json.JSONObject;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.base.BaseObject;
import com.maru.performer.framework.base.callback.ErrorCallback;
import com.maru.performer.framework.base.callback.PendingOperation;
import com.maru.performer.framework.base.callback.SuccessCallback;
import com.maru.performer.framework.contact.callback.AddressBookError;
import com.maru.performer.framework.contact.callback.ContactArraySuccessCallback;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.BaseTypes;
import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class AddressBook extends BaseObject {

	private static final String	CLASS_NAME			= "AddressBook";

	public static final int		DEVICE_ADDRESS_BOOK	= 1;
	private static final String	JSON_FIELD_ID		= "id";

	private static final String	JSON_FIELD_NAME		= "name";
	private static final String	JSON_FIELD_TYPE		= "type";
	public static final int		SIM_ADDRESS_BOOK	= 0;

	private final Handler		handler				= new Handler();
	private Context				mContext			= null;
	private String				mID					= null;

	private String				mName				= CLASS_NAME;
	private Cursor				mPeopleCursor		= null;
	private int					mType				= DEVICE_ADDRESS_BOOK;

	/**
	 * @param context
	 * @param type
	 */
	public AddressBook(Context context, int type) {
		this.mContext = context;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;
		mPeopleCursor = mContext.getContentResolver().query(People.CONTENT_URI, projection, selection, selectionArgs, sortOrder);

		this.setType(type);

		setID(Integer.toString(getType()));

		// mId = addressCount++;
	}

	/**
	 * @param successCallback
	 * @param errorCallback
	 * @param contact
	 * @return
	 */
	public String addContact(String successCallback, String errorCallback, final String contact) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact");

		final SuccessCallback scb = new SuccessCallback(successCallback);
		final ErrorCallback ecb = new ErrorCallback(errorCallback);

		PendingOperation op = new PendingOperation(scb, ecb);

		handler.post(new Runnable() {

			public void run() {

				Uri uri = null;
				Uri orgUri = null;
				Uri mobileUri = null;
				Uri faxUri = null;
				Uri emailUri = null;
				Uri addressUri = null;
				ContentValues values = new ContentValues();

				Contact _contact = new Contact();
				JSONObject obj = null;
				try {
					obj = new JSONObject(contact);
					_contact.setJSON(obj);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// values.put(ContactsContract.RawContacts.CONTACT_ID, 0);
				// values.put(ContactsContract.RawContacts.AGGREGATION_MODE,
				// ContactsContract.RawContacts.AGGREGATION_MODE_DISABLED);
				// Uri rawContactUri = mContext.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,
				// values);
				long rawContactId; // = ContentUris.parseId(rawContactUri);

				rawContactId = Long.parseLong(_contact.getID());

				// bondi에서는 family name과 given name을 구별하지 않고 한꺼번에 보낸다.
				values.clear();
				values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
				values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
				values.put(CommonDataKinds.StructuredName.GIVEN_NAME, _contact.getName());
				uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

				if (mPeopleCursor == null) {
					//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
					ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
				}

				for (String nickname : _contact.getNicknames()) {
					values.clear();
					values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
					values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);

					values.put(CommonDataKinds.Nickname.TYPE, CommonDataKinds.Nickname.TYPE_OTHER_NAME); //
					values.put(CommonDataKinds.Nickname.NAME, nickname);

					uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

					if (mPeopleCursor == null) {
						//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
						ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
					}
				}

				// add mobile number
				for (PhoneNumber phoneNumber : _contact.getPhoneNumbers()) {

					if (phoneNumber.getTypes().size() > 0) {
						for (String type : phoneNumber.getTypes()) {
							// mobileUri = Uri.withAppendedPath(uri,
							// ContactsContract.Pho.People.Phones.CONTENT_DIRECTORY);
							values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
							values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

							if (PhoneNumber.TYPE_WORK.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_WORK);
							} else if (PhoneNumber.TYPE_PREF.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, BaseTypes.TYPE_CUSTOM); //
							} else if (PhoneNumber.TYPE_HOME.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_HOME);
							} else if (PhoneNumber.TYPE_VOICE.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_OTHER); //
							} else if (PhoneNumber.TYPE_FAX.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_FAX_WORK); //
							} else if (PhoneNumber.TYPE_MSG.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_MMS); //
							} else if (PhoneNumber.TYPE_CELL.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE); //
							} else if (PhoneNumber.TYPE_PAGER.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_PAGER);
							} else if (PhoneNumber.TYPE_BBS.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_OTHER); //
							} else if (PhoneNumber.TYPE_MODEM.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_OTHER); //
							} else if (PhoneNumber.TYPE_CAR.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_CAR); //
							} else if (PhoneNumber.TYPE_ISDN.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_ISDN); //
							} else if (PhoneNumber.TYPE_VIDEO.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_OTHER); //
							} else if (PhoneNumber.TYPE_PCS.equals(type)) {
								values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE); //
							}

							values.put(CommonDataKinds.Phone.NUMBER, phoneNumber.getNumber());

							uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
							if (mPeopleCursor == null) {
								//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
								ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
							}
						}
					} else {
						values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
						values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

						values.put(CommonDataKinds.Phone.NUMBER, phoneNumber.getNumber());
						values.put(CommonDataKinds.Phone.TYPE, BaseTypes.TYPE_CUSTOM); //
						uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
						if (mPeopleCursor == null) {
							//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
							ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
						}
					}
				}
				// add email
				for (EmailAddress email : _contact.getEmails()) {

					if (email.getTypes().size() > 0) {
						for (String type : email.getTypes()) {
							values.clear();
							values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
							values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);

							if (EmailAddress.TYPE_HOME.equals(type)) {
								values.put(CommonDataKinds.Email.TYPE, CommonDataKinds.Email.TYPE_HOME);
							} else if (EmailAddress.TYPE_PREF.equals(type)) {
								values.put(CommonDataKinds.Email.TYPE, BaseTypes.TYPE_CUSTOM); //
							} else if (EmailAddress.TYPE_WORK.equals(type)) {
								values.put(CommonDataKinds.Email.TYPE, CommonDataKinds.Email.TYPE_WORK);
							}
							values.put(CommonDataKinds.Email.DATA1, email.getEmail());

							uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
							if (mPeopleCursor == null) {
								//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
								ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
							}
						}
					} else {
						values.clear();
						values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
						values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);

						values.put(CommonDataKinds.Email.TYPE, BaseTypes.TYPE_CUSTOM);

						uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
						if (mPeopleCursor == null) {
							//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
							ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
						}
					}
				}

				// add address
				for (ContactAddress address : _contact.getAddresses()) {

					if (address.getTypes().size() > 0) {
						for (String type : address.getTypes()) {
							values.clear();
							values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
							values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);

							if (ContactAddress.TYPE_HOME.equals(type)) {
								values.put(CommonDataKinds.StructuredPostal.TYPE, CommonDataKinds.StructuredPostal.TYPE_HOME);
							} else if (ContactAddress.TYPE_PREF.equals(type)) {
								values.put(CommonDataKinds.StructuredPostal.TYPE, BaseTypes.TYPE_CUSTOM);
							} else if (ContactAddress.TYPE_WORK.equals(type)) {
								values.put(CommonDataKinds.StructuredPostal.TYPE, CommonDataKinds.StructuredPostal.TYPE_WORK);
							}

							if (address.getCountry() != null)
								values.put(CommonDataKinds.StructuredPostal.COUNTRY, address.getCountry());
							if (address.getRegion() != null)
								values.put(CommonDataKinds.StructuredPostal.REGION, address.getRegion());

							String city = null;
							if (address.getCountry() != null)
								city = address.getCountry();
							if (address.getCounty() != null) {
								if (city != null)
									values.put(CommonDataKinds.StructuredPostal.CITY, city + ", " + address.getCounty());
								else
									values.put(CommonDataKinds.StructuredPostal.CITY, address.getCounty());
							}

							String street = null;

							if (address.getPremises() != null)
								street = address.getPremises();
							if (address.getStreet() != null) {
								if (street != null)
									street = street + ", " + address.getStreet();
								else
									street = address.getStreet();
							}
							if (address.getStreetNumber() != null) {
								if (street != null)
									values.put(CommonDataKinds.StructuredPostal.STREET, street + ", " + address.getStreetNumber());
								else
									values.put(CommonDataKinds.StructuredPostal.STREET, address.getStreetNumber());
							}

							// values.put(ContactsContract.CommonDataKinds.StructuredPostal.,
							// address.getAdditionalInformation());
							if (address.getPostalCode() != null)
								values.put(CommonDataKinds.StructuredPostal.POSTCODE, address.getPostalCode());

							uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
							if (mPeopleCursor == null) {
								//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
								ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
							}
						}
					} else {
						values.clear();
						values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
						values.put(ContactsContract.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);

						values.put(CommonDataKinds.Email.TYPE, BaseTypes.TYPE_CUSTOM);

						uri = mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

						mPeopleCursor = mContext.getContentResolver().query(uri, null, null, null, null);
						if (mPeopleCursor == null) {
							//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":addContact() - " + uri.toString());
							ecb.onError(AddressBookError.ADD_CONTACT_ERROR);
						}
					}
				}

				// InputStream is = null;
				// try {
				// is = mContext.getAssets().open("h_icon.png");
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// BufferedInputStream bis = new BufferedInputStream(is);
				// Bitmap source = BitmapFactory.decodeStream(bis);
				//
				// //Bitmap xbmp = source.createBitmap(64, 64, Bitmap.Config.RGB_565);
				//
				// ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// source.compress(CompressFormat.JPEG, 100, stream);
				//
				// byte[] image = stream.toByteArray();
				// String xx = Base64.encodeToString(image, Base64.NO_WRAP);
				//
				// Log.d(Framework.TAG, "<img src='data:image/jpg;base64," + xx + "'>");

				scb.onSuccess();
			}

		});

		op.waiting();

		return Long.toString(op.getOperationID());
	}

	/**
	 * @param contact
	 * @return
	 */
	public String createContact(String contact) {
		Uri uri = null;
		Uri orgUri = null;
		Uri mobileUri = null;
		Uri faxUri = null;
		Uri emailUri = null;
		Uri addressUri = null;
		ContentValues values = new ContentValues();

		Contact _contact = new Contact();
		JSONObject obj = null;
		try {
			obj = new JSONObject(contact);
			_contact.setJSON(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		values.put(ContactsContract.RawContacts.CONTACT_ID, 0);
		values.put(ContactsContract.RawContacts.AGGREGATION_MODE, ContactsContract.RawContacts.AGGREGATION_MODE_DISABLED);
		Uri rawContactUri = mContext.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);

		_contact.setID(Long.toString(rawContactId));

		return _contact.getJSON().toString();
	}

	/**
	 * @param successCallback
	 * @param errorCallback
	 * @param contact
	 * @return
	 */
	public String deleteContact(String successCallback, String errorCallback, String contact) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":deleteContact");

		final SuccessCallback scb = new SuccessCallback(successCallback);
		final ErrorCallback ecb = new ErrorCallback(errorCallback);

		PendingOperation op = new PendingOperation(scb, ecb);

		handler.post(new Runnable() {

			public void run() {
				scb.onSuccess();
			}
		});

		op.waiting();

		return Long.toString(op.getOperationID());
	}

	/**
	 * @param successCallback
	 * @param errorCallback
	 * @param filter
	 * @return
	 */
	public String findContacts(String successCallback, String errorCallback, String filter) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":findContacts");

		final ContactArraySuccessCallback scb = new ContactArraySuccessCallback(successCallback);
		final ErrorCallback ecb = new ErrorCallback(errorCallback);

		PendingOperation op = new PendingOperation(scb, ecb);

		handler.post(new Runnable() {

			public void run() {
				scb.onSuccess();
			}
		});

		op.waiting();

		return Long.toString(op.getOperationID());
	}

	/**
	 * @return
	 */
	public String getID() {
		return mID;
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub

		JSONObject obj = new JSONObject();
		try {
			obj.put(AddressBook.JSON_FIELD_NAME, this.getName());
			obj.put(AddressBook.JSON_FIELD_ID, this.getID());
			obj.put(AddressBook.JSON_FIELD_TYPE, this.getType());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}

	/**
	 * @return
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @return
	 */
	public int getType() {
		return mType;
	}

	/**
	 * @param id
	 */
	public void setID(String id) {
		this.mID = id;
	}

	@Override
	public void setJSON(JSONObject obj) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * @param type
	 */
	public void setType(int type) {
		this.mType = type;
	}

	/**
	 * @param successCallback
	 * @param errorCallback
	 * @param contact
	 * @return
	 */
	public String updateContact(String successCallback, String errorCallback, String contact) {
		//Log.d(Framework.CLASS_NAME, CLASS_NAME + ":updateContact");

		final SuccessCallback scb = new SuccessCallback(successCallback);
		final ErrorCallback ecb = new ErrorCallback(errorCallback);

		PendingOperation op = new PendingOperation(scb, ecb);

		handler.post(new Runnable() {

			public void run() {
				scb.onSuccess();
			}
		});

		op.waiting();

		return Long.toString(op.getOperationID());
	}
}
