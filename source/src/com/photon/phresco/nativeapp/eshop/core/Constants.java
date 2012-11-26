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
 * Classname: Constants
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.core;


/**
 * Constants that will be used through out the device application
 *
 * @author viral_b
 *
 */
public class Constants {
	// Constant variables

	protected Constants() {
	}

	public static final String APP_FOLDER_PATH = "/sdcard/phresco/";
	public static final String LOG_FOLDER_PATH = Constants.APP_FOLDER_PATH + "log/";
	public static final String LOG_FILE = "Phresco.log";

	public static final String IMAGE_FOLDER_PATH = Constants.APP_FOLDER_PATH + "images/";
	public static final String MENU_FOLDER_PATH = Constants.IMAGE_FOLDER_PATH + "menu/";
	public static final String CATEGORIES_FOLDER_PATH = Constants.IMAGE_FOLDER_PATH + "categories/";
	public static final String PRODUCT_FOLDER_PATH = Constants.IMAGE_FOLDER_PATH + "product/";
	public static final String PRODUCT_DETAIL_FOLDER_PATH = Constants.PRODUCT_FOLDER_PATH + "details/";

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

	private static String currentScreenResolution = "";
	private static String webContextURL = "";
	private static String homeURL = "";
	private static String currency = "";
	private static String restAPI = "";
	private static int userId = 0;
	
	/**
	 * @return the currentScreenResolution
	 */
	public static String getCurrentScreenResolution() {
		return currentScreenResolution;
	}
	/**
	 * @param currentScreenResolution the currentScreenResolution to set
	 */
	public static void setCurrentScreenResolution(String currentScreenResolution) {
		Constants.currentScreenResolution = currentScreenResolution;
	}
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
	 * @return the homeURL
	 */
	public static String getHomeURL() {
		return homeURL;
	}
	/**
	 * @param homeURL the homeURL to set
	 */
	public static void setHomeURL(String homeURL) {
		Constants.homeURL = homeURL;
	}
	/**
	 * @return the currency
	 */
	public static String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public static void setCurrency(String currency) {
		Constants.currency = currency;
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
	/**
	 * @return the userId
	 */
	public static int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public static void setUserId(int userId) {
		Constants.userId = userId;
	}

}