/*
 * ###
 * PHR_AndroidNative
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
/*
 * Classname: CustomerInfo
 * Version information: 1.0
 * Date: Dec 02, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.customer;

import java.io.IOException;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.interfaces.IPhrescoValidator;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.util.StringUtils;
import com.photon.phresco.nativeapp.eshop.util.Utility;

/**
 * Class to hold the customer detail
 *
 * @author viral_b
 *
 */
public class Customer implements Serializable, IPhrescoValidator {

	private static final String TAG = "Model: Customer ***** ";
	private static final long serialVersionUID = 1L;

	private String emailID;

	private Address deliveryAddress;

	private Address billingAddress;

	public Customer() {

	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param emailID
	 *            the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * @return the deliveryAddress
	 */
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress
	 *            the deliveryAddress to set
	 */
	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the billingAddress
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress
	 *            the billingAddress to set
	 */
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Post the login details to server for authentication
	 * @param firstName
	 * @param lastName
	 */
	public JSONObject postCredentialDetails(String emailId, String password) throws IOException {
		JSONObject responseJSON = null;

		// Post the login JSON to server, and receive the JSON response
		JSONObject jObjMain = new JSONObject();
		JSONObject jObj = new JSONObject();

		try {
				jObj.put("loginEmail", emailId);
				jObj.put("password", password);

				jObjMain.put("login", jObj);

		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + " - postLoginDetails  - JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		responseJSON = JSONHelper.postJSONObjectToURL(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.LOGIN_POST_URL, jObjMain.toString());
		return responseJSON;
	}

	/**
	 * Post the registration details to server for authentication
	 * @param  firstName
	 * @param  lastName
	 * @param  emailId
	 * @param  password
	 */
	public JSONObject postCredentialDetails(String firstName,String lastName,String emailId, String password) throws IOException {
		JSONObject responseJSON = null;

		// Post the register JSON to server, and receive the JSON response
		JSONObject jObjMain = new JSONObject();
		JSONObject jObj = new JSONObject();

		try {

				jObj.put("firstName", firstName);
				jObj.put("lastName", lastName);
				jObj.put("email", emailId);
				jObj.put("phoneNumber", "");
				jObj.put("password", password);

				jObjMain.put("register", jObj);


		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + " - postRegistrationDetails  - JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		responseJSON = JSONHelper.postJSONObjectToURL(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.REGISTER_POST_URL, jObjMain.toString());
		return responseJSON;
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

	/**
	 * Check if the email id is valid or not
	 *
	 * @param emailId
	 * @return
	 */
	public boolean isValidEmailId(String emailId) {
		boolean isValidEmail = false;
		try {
			isValidEmail = Utility.checkEmail(emailId);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - validateEmailId  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
			isValidEmail = false;
		}
		return isValidEmail;
	}

	/*
	 * Check if password and confirm password is similar or not
	 */
	public boolean isConfirmPasswordCorrect(String password, String confirmPassword) {
		boolean isConfirmPasswordMatching = false;
		try {
			isConfirmPasswordMatching = password.equalsIgnoreCase(confirmPassword);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - validatePassword  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
			isConfirmPasswordMatching = false;
		}
		return isConfirmPasswordMatching;
	}

}
