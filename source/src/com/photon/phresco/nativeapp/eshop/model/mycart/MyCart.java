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
import java.util.ArrayList;
import java.util.List;

import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Class to hold the MyCart object
 *
 * @author viral_b
 *
 */
public class MyCart implements Serializable {

	private static final String TAG = "Model: MyCart ***** ";
	private static final long serialVersionUID = 1L;
	private static List<MyCartItem> myCart = null;
	private static int totalPrice;

	protected MyCart() {
	}

	/**
	 * Returns the cart object
	 *
	 * @return Vector<MyCartItem>
	 */

	public static List<MyCartItem> getMyCart() {
		return myCart;
	}

	/**
	 * Adds a product into cart
	 *
	 * @param object
	 *            MyCartItem object, containing product, and quantity to be
	 *            added into cart
	 */
	public static void add(MyCartItem object) {
		if (myCart == null) {
			myCart = new ArrayList<MyCartItem>();
		}
		myCart.add(object);

		// myCart.addElement(object);
	}


	/**
	 * Update product quantity in cart when the same product is added in to cart
	 *
	 * @param product
	 */
	public static void updateProductQuantityInCart(MyCartItem product) {
		try {

			product.setProductQuantity(product.getProductQuantity() + 1);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - update  - Exception : "+ ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Removes one item from cart at specified position
	 *
	 * @param position
	 */
	public static void removeFromList(int position) {
		PhrescoLogger.info(TAG
				+ " - remove MyCartItem FromList at  position : " + position);
		// myCart.removeElementAt(position);
		myCart.remove(position);
		if (myCart.size() == 0) {
			MyCart.clear();
		}
	}

	/**
	 * Returns the size of cart
	 *
	 * @return int Total number of items in cart
	 */
	public static int size() {
		return myCart.size();
	}

	/**
	 * Returns one item from cart at specified position
	 *
	 * @param position
	 * @return MyCartItem
	 */
	public static MyCartItem getMyCartItem(int position) {
		return myCart.get(position);
	}

	/**
	 * Removes all elements from cart, leaving it empty.
	 */
	public static void clear() {
		myCart.clear();
		myCart = null;

	}

	/**
	 * @return the totalPrice
	 */
	public static int getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice
	 *            the totalPrice to set
	 */
	public static void setTotalPrice(int totalPrice) {
		MyCart.totalPrice = totalPrice;
	}


	/**
	 * Update the cart total
	 *
	 * @return int
	 */
	public static int updateMyCartTotal() {
		int subTotalPrice = 0;
		try {
			int productPrice;
			int myCartItems = MyCart.size();

			for (int i = 0; i < myCartItems; i++) {
				productPrice = MyCart.getMyCartItem(i).getProduct().getPrice()
						* MyCart.getMyCartItem(i).getProductQuantity();
				PhrescoLogger.info(TAG + " - updateMyCart Product Price : "
						+ productPrice);
				PhrescoLogger.info(TAG + " - Product Quantity : "
						+ MyCart.getMyCartItem(i).getProductQuantity());
				subTotalPrice += productPrice;
				PhrescoLogger.info(TAG + " - updateMyCart subTotal Price : "
						+ subTotalPrice);
			}
			MyCart.setTotalPrice(subTotalPrice);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - updateMyCartTotal  - Exception : "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}
		return subTotalPrice;
	}

	/**
	 * Check if there are any products in cart
	 *
	 * @return boolean TRUE if there are no products in cart, FALSE otherwise
	 */
	public static boolean isEmpty() {
		boolean isCartEmpty = false;
		try {
			isCartEmpty = (MyCart.getMyCart() == null || MyCart.size() <= 0) ? true
					: false;
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - isEmpty  - Exception : "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}
		return isCartEmpty;
	}
}