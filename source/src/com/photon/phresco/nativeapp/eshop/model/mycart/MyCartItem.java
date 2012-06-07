
/*
 * Classname: MyCartItem
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.mycart;

import java.io.Serializable;

import com.photon.phresco.nativeapp.eshop.model.product.ProductDetail;

/**
 * Class to hold the information regarding single item in MyCart object
 * @author viral_b
 *
 */
public class MyCartItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private ProductDetail product;
	private int productQuantity;

	public MyCartItem() {

	}

	/**
	 * Returns information regarding a product from current cart item
	 * @return ProductDetail
	 */
	public ProductDetail getProduct() {
		return product;
	}

	/**
	 * Sets product information for current cart item
	 * @param product
	 */
	public void setProduct(ProductDetail product) {
		this.product = product;
	}

	/**
	 * Returns product quantity from current cart item
	 * @return int
	 */
	public int getProductQuantity() {
		return productQuantity;
	}

	/**
	 * Set product quantity for current cart item
	 * @param productQuantity
	 */
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

}