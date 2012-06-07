
/*
 * Classname: OrderSubmit
 * Version information: 1.0
 * Date: Jan 09, 2012
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.order;

import java.io.Serializable;
import java.util.List;

import com.photon.phresco.nativeapp.eshop.model.customer.Customer;

/**
 * Class to hold the order details that will be submitted to server
 * @author viral_b
 *
 */
public class OrderSubmit implements Serializable {

	private static final long serialVersionUID = 1L;

	private Customer customerInfo;
	private String paymentMethod;
	private int totalPrice;
	private String comments;
	private List<OrderSubmitProduct> products;
	public OrderSubmit() {

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
	public List<OrderSubmitProduct> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<OrderSubmitProduct> products) {
		this.products = products;
	}

}
