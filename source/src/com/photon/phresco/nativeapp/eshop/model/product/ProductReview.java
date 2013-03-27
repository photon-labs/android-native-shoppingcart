/**
 * PHR_AndroidNative
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
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
