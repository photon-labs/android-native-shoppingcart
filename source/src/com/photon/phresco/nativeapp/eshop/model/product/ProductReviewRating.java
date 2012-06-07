/*
 * Classname: ProductReviewRating
 * Version information: 1.0
 * Date: Dec 07, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.product;

import java.io.Serializable;

/**
 * @author Viral
 *
 */
public class ProductReviewRating implements Serializable {

	private static final long serialVersionUID = 1L;

	private int key;

	private int value;

	public ProductReviewRating() {

	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
