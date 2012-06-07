
/*
 * Classname: ProductReview
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.product;

import java.io.Serializable;
import java.util.List;

/**
 * Class to hold the information regarding product reviews
 * @author viral_b
 *
 */
public class ProductReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productId;
	private int userId;
	private ProductReviewRatings ratings;
	private int average;
	private List<ProductReviewComments> comments;

	public ProductReview() {

	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the ratings
	 */
	public ProductReviewRatings getRatings() {
		return ratings;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(ProductReviewRatings ratings) {
		this.ratings = ratings;
	}

	/**
	 * @return the average
	 */
	public int getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(int average) {
		this.average = average;
	}

	/**
	 * @return the comments
	 */
	public List<ProductReviewComments> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<ProductReviewComments> comments) {
		this.comments = comments;
	}



}
