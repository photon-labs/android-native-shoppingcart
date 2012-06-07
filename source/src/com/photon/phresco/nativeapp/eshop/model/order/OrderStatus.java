
/*
 * Classname: OrderStatus
 * Version information: 1.0
 * Date: Jan 11, 2012
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.order;

import java.io.Serializable;

/**
 * @author viral_b
 *
 */
public class OrderStatus implements Serializable {

//	private static final String TAG = "Model: OrderStatus ***** ";
	private static final long serialVersionUID = 1L;


	private String message;
	private int orderId;
	private String successMessage;

	public OrderStatus() {

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

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

}
