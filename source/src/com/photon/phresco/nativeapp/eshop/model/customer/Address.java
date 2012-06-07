
/*
 * Classname: AddressInfo
 * Version information: 1.0
 * Date: Dec 02, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.customer;

import java.io.Serializable;

import com.photon.phresco.nativeapp.eshop.interfaces.IPhrescoValidator;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.util.StringUtils;

/**
 * Class to hold the information regarding customer Address info
 * @author viral_b
 *
 */
public class Address implements Serializable, IPhrescoValidator{
	private static final String TAG = "Model: AddressInfo ***** ";
	private static final long serialVersionUID = 1L;

	private String firstName;

	private String lastName;

	private String company;

	private String addressOne;

	private String addressTwo;

	private String city;

	private String state;

	private String country;

	private String zipCode;

	private String phoneNumber;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the addressOne
	 */
	public String getAddressOne() {
		return addressOne;
	}

	/**
	 * @param addressOne the addressOne to set
	 */
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	/**
	 * @return the addressTwo
	 */
	public String getAddressTwo() {
		return addressTwo;
	}

	/**
	 * @param addressTwo the addressTwo to set
	 */
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param conuntry the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Check if the required field is present, or left blank
	 *
	 * @param param
	 * @return boolean
	 */

	@Override
	public boolean isEmpty(String param) {
		StringUtils strUtilObj = new StringUtils();
		boolean isEmpty = false;
		try {
			isEmpty = strUtilObj.isEmpty(param);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - isEmpty  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
			isEmpty = true;
		}
		return isEmpty;
	}

}
