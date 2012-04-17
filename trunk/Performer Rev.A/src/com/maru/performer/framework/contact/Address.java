package com.maru.performer.framework.contact;


import org.json.JSONException;
import org.json.JSONObject;

import com.maru.performer.framework.base.BaseObject;

/**
 * @version 0.1 20 Oct 2010
 * @author Youndong Park
 */
public class Address extends BaseObject {
	public static final String	JSON_FIELD_ADDITIONAL_INFORMATION	= "additionalInformation";

	public static final String	JSON_FIELD_CITY						= "city";

	public static final String	JSON_FIELD_COUNTRY					= "country";

	public static final String	JSON_FIELD_COUNTY					= "county";

	public static final String	JSON_FIELD_POSTAL_CODE				= "postalCode";

	public static final String	JSON_FIELD_PREMISES					= "premises";

	public static final String	JSON_FIELD_REGION					= "region";

	public static final String	JSON_FIELD_STREE_TNUMBER			= "streetNumber";

	public static final String	JSON_FIELD_STREET					= "street";

	// Contains other address details that are not captured by the rest of the
	// attributes in this interface.
	// Examples include a floor number in a building, an apartment number, the
	// name of an office occupant, etc..
	private String				mAdditionalInformation				= null;

	// Reflects the name of the city.
	private String				mCity								= null;

	// Country specified using the two-letter [ISO 3166-1] code.
	private String				mCountry							= null;

	// Name of a land area within the larger region.
	private String				mCounty								= null;

	// Reflects the postal code of the location (e.g. the zip code in the US
	private String				mPostalCode							= null;

	// Denotes the details of the premises, such as a building name, block of
	// flats, etc.
	private String				mPremises							= null;

	// Name of a country subdivision (e.g. the state name in the US).
	private String				mRegion								= null;

	// Reflects the name of the street.
	private String				mStreet								= null;

	// Reflects the location's street number.
	private String				mStreetNumber						= null;

	/**
	 * @return
	 */
	public String getAdditionalInformation() {
		return mAdditionalInformation;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return mCity;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return mCountry;
	}

	/**
	 * @return
	 */
	public String getCounty() {
		return mCounty;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();

		try {
			obj.put(Address.JSON_FIELD_COUNTRY, this.getCountry());
			obj.put(Address.JSON_FIELD_REGION, this.getRegion());
			obj.put(Address.JSON_FIELD_COUNTY, this.getCounty());
			obj.put(Address.JSON_FIELD_CITY, this.getCity());
			obj.put(Address.JSON_FIELD_STREET, this.getStreet());
			obj.put(Address.JSON_FIELD_STREE_TNUMBER, this.getStreetNumber());
			obj.put(Address.JSON_FIELD_PREMISES, this.getPremises());
			obj.put(Address.JSON_FIELD_ADDITIONAL_INFORMATION, this.getAdditionalInformation());
			obj.put(Address.JSON_FIELD_POSTAL_CODE, this.getPostalCode());

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
	public String getPostalCode() {
		return mPostalCode;
	}

	/**
	 * @return
	 */
	public String getPremises() {
		return mPremises;
	}

	/**
	 * @return
	 */
	public String getRegion() {
		return mRegion;
	}

	/**
	 * @return
	 */
	public String getStreet() {
		return mStreet;
	}

	/**
	 * @return
	 */
	public String getStreetNumber() {
		return mStreetNumber;
	}

	/**
	 * @param mAdditionalInformation
	 */
	public void setAdditionalInformation(String mAdditionalInformation) {
		this.mAdditionalInformation = mAdditionalInformation;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.mCity = city;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		this.mCountry = country;
	}

	/**
	 * @param county
	 */
	public void setCounty(String county) {
		this.mCounty = county;
	}


	@Override
	public void setJSON(JSONObject obj) {

		try {
			this.setCountry(obj.getString(Address.JSON_FIELD_COUNTRY));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setRegion(obj.getString(Address.JSON_FIELD_REGION));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setCounty(obj.getString(Address.JSON_FIELD_COUNTY));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setCity(obj.getString(Address.JSON_FIELD_CITY));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setStreet(obj.getString(Address.JSON_FIELD_STREET));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setStreetNumber(obj.getString(Address.JSON_FIELD_STREE_TNUMBER));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setPremises(obj.getString(Address.JSON_FIELD_PREMISES));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setAdditionalInformation(obj.getString(Address.JSON_FIELD_ADDITIONAL_INFORMATION));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.setPostalCode(obj.getString(Address.JSON_FIELD_POSTAL_CODE));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.mPostalCode = postalCode;
	}

	/**
	 * @param premises
	 */
	public void setPremises(String premises) {
		this.mPremises = premises;
	}

	/**
	 * @param region
	 */
	public void setRegion(String region) {
		this.mRegion = region;
	}

	/**
	 * @param street
	 */
	public void setStreet(String street) {
		this.mStreet = street;
	}

	/**
	 * @param streetNumber
	 */
	public void setStreetNumber(String streetNumber) {
		this.mStreetNumber = streetNumber;
	}
}
