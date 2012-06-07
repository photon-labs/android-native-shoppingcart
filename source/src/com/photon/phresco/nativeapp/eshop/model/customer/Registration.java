/**
 * Classname: Registration
 * Version information: 1.0
 * Date: Jan 11, 2012
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.customer;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Class to hold the Registered Customer details
 *
 * @author chandankumar_r
 *
 */
public class Registration implements Serializable {

	private static final String TAG = "Model: Registration ***** ";
	private static final long serialVersionUID = 1L;

	private String message;
	private String successMessage;
	private int userId;
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * Get the Registration as GSON object
	 *
	 * @param registeredJSONObj
	 *
	 */
	public Registration getRegistrationGSONObject(String registrationObjString) {
		Registration registerObj=null;
		PhrescoLogger.info(TAG + "getRegistrationGSONObject() - JSON STRING : " + registrationObjString);
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			registerObj = jsonObj.fromJson(registrationObjString, Registration.class);
			PhrescoLogger.info(TAG + "getRegistrationGSONObject() - JSON OBJECT : " + registerObj.toString());

		}  catch (JsonSyntaxException ex) {
			PhrescoLogger.info(TAG + "JsonSyntaxException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return registerObj;
	}

}
