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
package com.photon.phresco.nativeapp.unit.test.core;

/**
 * Constants that will be used through out the device application
 * @author viral_b
 *
 */
public class Constants {
	// Constant variables

	protected Constants() {}

	public static final String REST_API = "rest/api/";
	public static final String CONFIG_URL = "config/";
	public static final String CATEGORIES_URL = "categories/";
	public static final String PRODUCTS_URL = "products/";
	public static final String PRODUCT_REVIEW_URL = "reviews/";
	public static final String SEARCH_URL = "search/";
	public static final String NEW_PRODUCTS_URL = "newproducts/";
	public static final String SPECIAL_PRODUCTS_URL = "specialproducts/";

	public static final String ORDER_POST_URL = "product/post/orderdetail/";
	public static final String PRODUCT_REVIEW_POST_URL = "product/post/review/";
	public static final String LOGIN_POST_URL = "post/login/";
	public static final String REGISTER_POST_URL = "post/register/";
	public static final String PHRESCO_ENV_CONFIG = "phresco-env-config.xml";


	private static String webContextURL = "";

	private static String restAPI = "";



	/**
	 * @return the webContextURL
	 */
	public static String getWebContextURL() {
		return webContextURL;
	}
	/**
	 * @param webContextURL the webContextURL to set
	 */
	public static void setWebContextURL(String webContextURL) {
		Constants.webContextURL = webContextURL;
	}

	/**
	 * @return the restAPI
	 */
	public static String getRestAPI() {
		return restAPI;
	}
	/**
	 * @param restAPI the restAPI to set
	 */
	public static void setRestAPI(String restAPI) {
		Constants.restAPI = restAPI;
	}

}