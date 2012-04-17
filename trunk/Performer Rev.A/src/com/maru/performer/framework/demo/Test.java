package com.maru.performer.framework.demo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.maru.performer.framework.Framework;
import com.maru.performer.framework.feature.FeatureMain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class Test {

	FeatureMain		mBondi		= null;
	private Context	mContext	= null;

	public Test(Context context, FeatureMain bondi) {
		this.mBondi = bondi;
		this.mContext = context;
	}

	public void run() {

		/*
		 * String id = null; PendingOperation op = null; id = mBondi.requestFeature("successCallback", "errorCallback",
		 * Bondi.FEATURE_CONTACT); op = PendingOperationManager.find(id);
		 * 
		 * id = mBondi.requestFeature("successCallback", "errorCallback", Bondi.FEATURE_CAMERA); op =
		 * PendingOperationManager.find(id);
		 */

		/*
		 * Contact contact = new Contact();
		 * 
		 * // id contact.setID("contact");
		 * 
		 * // addresses ContactAddress address1 = new ContactAddress(); address1.addType(ContactAddress.TYPE_PREF);
		 * address1.addType(ContactAddress.TYPE_WORK); address1.setCountry("Korea"); address1.setCity("Seoul");
		 * contact.addAddress(address1);
		 * 
		 * ContactAddress address2 = new ContactAddress(); address2.addType(ContactAddress.TYPE_HOME);
		 * address2.setCountry("USA"); address2.setCity("L.A"); contact.addAddress(address2);
		 * 
		 * // nicknames contact.addNickname("babo"); contact.addNickname("nice guy"); contact.addNickname("good boy");
		 * 
		 * // emails EmailAddress email1 = new EmailAddress(); email1.setEmail("me@gmail.com");
		 * email1.addType(EmailAddress.TYPE_HOME); email1.addType(EmailAddress.TYPE_PREF); contact.addEmail(email1);
		 * 
		 * EmailAddress email2 = new EmailAddress(); email2.setEmail("you@gmail.com");
		 * email2.addType(EmailAddress.TYPE_WORK); contact.addEmail(email2);
		 * 
		 * PhoneNumber phoneNumber1 = new PhoneNumber(); phoneNumber1.addType(PhoneNumber.TYPE_HOME);
		 * phoneNumber1.addType(PhoneNumber.TYPE_PREF); phoneNumber1.addType(PhoneNumber.TYPE_VOICE);
		 * phoneNumber1.setNumber("111-111-1111"); contact.addPhoneNumber(phoneNumber1);
		 * 
		 * PhoneNumber phoneNumber2 = new PhoneNumber(); phoneNumber2.addType(PhoneNumber.TYPE_CAR);
		 * phoneNumber2.setNumber("222-222-2222"); contact.addPhoneNumber(phoneNumber2);
		 * 
		 * PhoneNumber phoneNumber3 = new PhoneNumber(); phoneNumber3.addType(PhoneNumber.TYPE_WORK);
		 * phoneNumber3.setNumber("333-333-3333"); contact.addPhoneNumber(phoneNumber3);
		 * 
		 * Log.d(Framework.TAG, contact.getJSON().toString());
		 * 
		 * 
		 * Contact contact2 = new Contact(); JSONObject obj = null; try {
		 * 
		 * //obj = new JSONObject(
		 * "{'name': 'Pedro Fraca', 'nicknames': [{'0': 'peter'}], 'emails': [{'0': [{'email': '0pedro@gmail.com'}], '1': [{'email': '1pedro@gmail.com'}]}], 'phoneNumbers': [{'0': [{'number': '6666666666'}]}], 'photoURI': 'http://foo.com/images/pedro.jpg'}"
		 * ); obj = new JSONObject(
		 * "{'name':'contact','nicknames':['babo','nice guy','good boy'],'emails':[{'types':['HOME','PREF'],'email':'me@gmail.com'},{'types':['WORK'],'email':'you@gmail.com'}],'phoneNumbers':[{'types':['HOME','PREF','VOICE'],'number':'111-111-1111'},{'types':['CAR'],'number':'222-222-2222'},{'types':['WORK'],'number':'333-333-3333'}],'addresses':[{'types':['PREF','WORK'],'city':'Seoul','country':'Korea'},{'types':['HOME'],'city':'L.A','country':'USA'}],'photoURI':'http://foo.com/images/pedro.jpg'}"
		 * ); } catch (JSONException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * contact2.setJSON(obj);
		 * 
		 * Log.d(Framework.TAG, contact2.getJSON().toString());
		 */
		InputStream is = null;
		try {
			is = mContext.getAssets().open("h_icon.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		Bitmap source = BitmapFactory.decodeStream(bis);

		// Bitmap xbmp = source.createBitmap(64, 64, Bitmap.Config.RGB_565);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		source.compress(CompressFormat.JPEG, 100, stream);

		byte[] image = stream.toByteArray();
		String xx = Base64.encodeToString(image, Base64.NO_WRAP);

		//Log.d(Framework.CLASS_NAME, "<img src='data:image/jpg;base64," + xx + "'>");

	}
}
