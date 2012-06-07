
/*
 * Classname: ProductReviewRatings
 * Version information: 1.0
 * Date: Dec 07, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.product;

import java.io.Serializable;
import java.util.List;

public class ProductReviewRatings implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ProductReviewRating> rating;

	public ProductReviewRatings() {

	}

	/**
	 * @return the ratings
	 */
	public List<ProductReviewRating> getRating() {
		return rating;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRating(List<ProductReviewRating> rating) {
		this.rating = rating;
	}

}
