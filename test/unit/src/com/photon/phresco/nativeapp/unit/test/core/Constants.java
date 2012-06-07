
/*
 * Classname: Constants
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
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