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