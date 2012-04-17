package com.maru.performer.framework.contact;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.maru.performer.framework.base.BaseObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class ContactProperties extends BaseObject {

	private static final String		JSON_FIELD_ADDRESSES		= "addresses";
	private static final String		JSON_FIELD_NAME				= "name";
	private static final String		JSON_FIELD_NICKNAMES		= "nicknames";
	private static final String		JSON_FIELD_PHOTO_URI		= "photoURI";
	private static final String		JSON_FILED_EMAILS			= "emails";
	private static final String		JSON_FILED_PHONE_NUMBERS	= "phoneNumbers";

	private List<ContactAddress>	mAddresses					= new ArrayList<ContactAddress>();
	private List<EmailAddress>		mEmails						= new ArrayList<EmailAddress>();
	private String					mName						= null;
	private List<String>			mNicknames					= new ArrayList<String>();
	private List<PhoneNumber>		mPhoneNumbers				= new ArrayList<PhoneNumber>();
	private String					mPhotoUri					= null;

	public void addAddress(ContactAddress address) {
		this.mAddresses.add(address);
	}

	public void addEmail(EmailAddress email) {
		this.mEmails.add(email);
	}

	public void addNickname(String nickname) {
		this.mNicknames.add(nickname);
	}

	public void addPhoneNumber(PhoneNumber phoneNumber) {
		this.mPhoneNumbers.add(phoneNumber);
	}

	public List<ContactAddress> getAddresses() {
		return mAddresses;
	}

	public List<EmailAddress> getEmails() {
		return mEmails;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();

		// 여기 수정할 것.
		try {
			// name
			obj.put(ContactProperties.JSON_FIELD_NAME, this.getName());

			// nicknames
			JSONArray arrNicknames = new JSONArray();
			for (String nickname : this.getNicknames()) {
				arrNicknames.put(nickname);
			}
			obj.put(ContactProperties.JSON_FIELD_NICKNAMES, arrNicknames);

			// addresses
			JSONArray arrAddresses = new JSONArray();
			for (Address address : this.getAddresses()) {
				arrAddresses.put(address.getJSON());
			}
			obj.put(ContactProperties.JSON_FIELD_ADDRESSES, arrAddresses);

			// photo URI
			obj.put(ContactProperties.JSON_FIELD_PHOTO_URI, this.getPhotoUri());

			// phone numbers
			JSONArray arrPhoneNumbers = new JSONArray();
			for (PhoneNumber phoneNumber : this.getPhoneNumbers()) {
				arrPhoneNumbers.put(phoneNumber.getJSON());
			}
			obj.put(ContactProperties.JSON_FILED_PHONE_NUMBERS, arrPhoneNumbers);

			// emails
			JSONArray arrEmails = new JSONArray();
			for (EmailAddress email : this.getEmails()) {
				arrEmails.put(email.getJSON());
			}
			obj.put(ContactProperties.JSON_FILED_EMAILS, arrEmails);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return obj;
	}

	public String getName() {
		return mName;
	}

	public List<String> getNicknames() {
		return mNicknames;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return mPhoneNumbers;
	}

	public String getPhotoUri() {
		return mPhotoUri;
	}

	public void setAddresses(List<ContactAddress> addresses) {
		this.mAddresses = addresses;
	}

	public void setEmails(List<EmailAddress> emails) {
		this.mEmails = emails;
	}

	@Override
	public void setJSON(JSONObject obj) {
		// 여기 수정할 것.
		try {
			// name
			this.setName(obj.getString(ContactProperties.JSON_FIELD_NAME));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// nicknames
			JSONArray arrNicknames = obj.getJSONArray(ContactProperties.JSON_FIELD_NICKNAMES);

			for (int index = 0; index < arrNicknames.length(); index++) {
				String nickname = arrNicknames.getString(index);
				this.addNickname(nickname);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// addresses
			JSONArray arrAddresses = obj.getJSONArray(ContactProperties.JSON_FIELD_ADDRESSES);

			for (int index = 0; index < arrAddresses.length(); index++) {
				JSONObject addressObj = arrAddresses.getJSONObject(index);
				ContactAddress address = new ContactAddress();
				address.setJSON(addressObj);
				this.addAddress(address);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// photo URI
			this.setPhotoUri(obj.getString(ContactProperties.JSON_FIELD_PHOTO_URI));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// phone numbers
			JSONArray arrPhoneNumbers = obj.getJSONArray(ContactProperties.JSON_FILED_PHONE_NUMBERS);

			for (int index = 0; index < arrPhoneNumbers.length(); index++) {
				JSONObject phoneNumberObj = arrPhoneNumbers.getJSONObject(index);
				PhoneNumber phoneNumber = new PhoneNumber();
				phoneNumber.setJSON(phoneNumberObj);
				this.addPhoneNumber(phoneNumber);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// emails
			JSONArray arrEmails = obj.getJSONArray(ContactProperties.JSON_FILED_EMAILS);

			for (int index = 0; index < arrEmails.length(); index++) {
				JSONObject emailObj = arrEmails.getJSONObject(index);
				EmailAddress email = new EmailAddress();
				email.setJSON(emailObj);
				this.addEmail(email);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setName(String name) {
		this.mName = name;
	}

	public void setNicknames(List<String> nicknames) {
		this.mNicknames = nicknames;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.mPhoneNumbers = phoneNumbers;
	}

	public void setPhotoUri(String mPhotoUri) {
		this.mPhotoUri = mPhotoUri;
	}

}
