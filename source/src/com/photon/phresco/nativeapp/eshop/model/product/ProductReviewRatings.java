/*
 * ###
 * PHR_AndroidNative
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */

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
