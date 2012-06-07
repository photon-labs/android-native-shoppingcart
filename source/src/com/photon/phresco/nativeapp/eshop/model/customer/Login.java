/**
 * Classname: Login
 * Version information: 1.0
 * Date: Jan 10, 2012
 * Copyright notice:
 *
 */
package com.photon.phresco.nativeapp.eshop.model.customer;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Class to hold the customer login detail
 *
 * @author chandankumar_r
 *
 */
public class Login implements Serializable {

	private static final String TAG = "Model: Login ***** ";
	private static final long serialVersionUID = 1L;
	private String message;
	private String successMessage;
	private int userId;
	private String userName;

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get the Login  GSON object
	 *
	 * @param loginJSONObj
	 */
	public Login getLoginGSONObject(String loginObjString) {
		Login loginObj=null;
		PhrescoLogger.info(TAG + "getLoginGSONObject() - JSON STRING : " + loginObjString);
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			loginObj = jsonObj.fromJson(loginObjString, Login.class);
			PhrescoLogger.info(TAG + "getLoginGSONObject() - JSON OBJECT : " + loginObj.toString());

		}  catch (JsonSyntaxException ex) {
			PhrescoLogger.info(TAG + "JsonSyntaxException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return loginObj;
	}
}
