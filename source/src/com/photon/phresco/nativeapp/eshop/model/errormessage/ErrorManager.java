/*
 * Classname: ErrorManager
 * Version information: 1.0
 * Date: Jan 19, 2012
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.errormessage;

import java.io.Serializable;

/**
 *
 * Class to hold the error message returned from server
 *
 * @author viral_b
 *
 */
public class ErrorManager implements Serializable {
//	private static final String TAG = "Model: ErrorManager ***** ";
	private static final long serialVersionUID = 1L;
	private String type;
	private String message;

	public ErrorManager() {

	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
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
}
