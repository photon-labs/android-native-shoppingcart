
/*
 * Classname: OrderSubmitProduct
 * Version information: 1.0
 * Date: Jan 09, 2012
 * Copyright notice:
 */
 package com.photon.phresco.nativeapp.eshop.model.order;

import java.io.Serializable;

/**
 * @author viral_b
 *
 */
public class OrderSubmitProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	private int productId;
	private String name;
	private int quantity;
	private int price;
	private String imageURL;
	private String detailImageURL;
	private int totalPrice;

	public OrderSubmitProduct() {

	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	/**
	 * @return the detailImageURL
	 */
	public String getDetailImageURL() {
		return detailImageURL;
	}
	/**
	 * @param detailImageURL the detailImageURL to set
	 */
	public void setDetailImageURL(String detailImageURL) {
		this.detailImageURL = detailImageURL;
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
}
