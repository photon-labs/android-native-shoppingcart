/**
 * PHR_AndroidNative
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.nativeapp.eshop.model.order;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.customer.Customer;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCartItem;

/**
 * Class to hold the order information
 * @author viral_b
 *
 */
public class Order implements Serializable {

	private static final String TAG = "Model: Order ***** ";
	private static final long serialVersionUID = 1L;

	private Customer customerInfo;
	private String paymentMethod;
	private int totalPrice;
	private String comments;
	private List<MyCartItem> products;
	public Order() {

	}

	/**
	 * @return the customerInfo
	 */
	public Customer getCustomerInfo() {
		return customerInfo;
	}

	/**
	 * @param customerInfo the customerInfo to set
	 */
	public void setCustomerInfo(Customer customerInfo) {
		this.customerInfo = customerInfo;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the totalPrice
	 */
	public int getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the products
	 */
	public List<MyCartItem> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<MyCartItem> products) {
		this.products = products;
	}


	/**
	 * Post the order object to server
	 * @param 	sURL
	 * @param 	strJSON
	 * @return	JSONObject
	 * @throws Exception 
	 */
	public JSONObject postOrderJSONObject(String sURL, String strJSON) throws Exception {
		PhrescoLogger.info(TAG + " - postOrderJSONObject() :  ");
		JSONObject submitOrderJSONResponse = null;
		submitOrderJSONResponse = JSONHelper.postJSONObjectToURL(sURL, strJSON);
		return submitOrderJSONResponse;
	}


	/**
	 * Returns order post response object serialized using GSON object
	 *
	 * @param orderObjString
	 * @return Order post response object
	 */
	public OrderStatus getOrderStatusGSONObject(String orderObjString) {
		OrderStatus orderStatusObj = null;
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			orderStatusObj = jsonObj.fromJson(orderObjString, OrderStatus.class);
			PhrescoLogger.info(TAG + "getOrderGSONObject() - JSON OBJECT : " + orderStatusObj.toString());
		} catch (JsonSyntaxException ex) {
			PhrescoLogger.info(TAG + "getOrderGSONObject - JsonSyntaxException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return orderStatusObj;
	}
}
