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
package com.photon.phresco.nativeapp.eshop.model.appconfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Holds the Application configuration JSON object read from web server
 *
 * @author viral_b
 *
 */
public class AppConfig {

	private static final String TAG = "Model: AppConfig ***** ";
	// private static AppConfig instance;

	private String[] featureLayout;
	private AndroidFeatureImagePaths androidFeatureImagePaths;
	private List<FeatureConfig> featureConfig;
	private List<HeaderAssets> headerAssets;
	private ServerEnvironmentUrls serverEnvironmentUrls;
	private AppVersionInfo appVersionInfo;
	private String defaultCurrency;
	private String[] supportedCurrencies;

	/**
	 * @return the featureLayout
	 */
	public String[] getFeatureLayout() {
		return featureLayout;
	}

	/**
	 * @param featureLayout
	 *            the featureLayout to set
	 */
	public void setFeatureLayout(String[] featureLayout) {
		this.featureLayout = featureLayout.clone();
	}

	/**
	 * @return the androidFeatureImagePaths
	 */
	public AndroidFeatureImagePaths getAndroidFeatureImagePaths() {
		return androidFeatureImagePaths;
	}

	/**
	 * @param androidFeatureImagePaths
	 *            the androidFeatureImagePaths to set
	 */
	public void setAndroidFeatureImagePaths(AndroidFeatureImagePaths androidFeatureImagePaths) {
		this.androidFeatureImagePaths = androidFeatureImagePaths;
	}

	/**
	 * @return the featureConfig
	 */
	public List<FeatureConfig> getFeatureConfig() {
		return featureConfig;
	}

	/**
	 * @param featureConfig
	 *            the featureConfig to set
	 */
	public void setFeatureConfig(List<FeatureConfig> featureConfig) {
		this.featureConfig = featureConfig;
	}

	/**
	 * @return the headerAssets
	 */
	public List<HeaderAssets> getHeaderAssets() {
		return headerAssets;
	}

	/**
	 * @param headerAssets
	 *            the headerAssets to set
	 */
	public void setHeaderAssets(List<HeaderAssets> headerAssets) {
		this.headerAssets = headerAssets;
	}

	/**
	 * @return the serverEnvironmentUrls
	 */
	public ServerEnvironmentUrls getServerEnvironmentUrls() {
		return serverEnvironmentUrls;
	}

	/**
	 * @param serverEnvironmentUrls
	 *            the serverEnvironmentUrls to set
	 */
	public void setServerEnvironmentUrls(ServerEnvironmentUrls serverEnvironmentUrls) {
		this.serverEnvironmentUrls = serverEnvironmentUrls;
	}

	/**
	 * @return the appVersionInfo
	 */
	public AppVersionInfo getAppVersionInfo() {
		return appVersionInfo;
	}

	/**
	 * @param appVersionInfo
	 *            the appVersionInfo to set
	 */
	public void setAppVersionInfo(AppVersionInfo appVersionInfo) {
		this.appVersionInfo = appVersionInfo;
	}

	/**
	 * @return the defaultCurrency
	 */
	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	/**
	 * @param defaultCurrency
	 *            the defaultCurrency to set
	 */
	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	/**
	 * @return the supportedCurrencies
	 */
	public String[] getSupportedCurrencies() {
		return supportedCurrencies;
	}

	/**
	 * @param supportedCurrencies
	 *            the supportedCurrencies to set
	 */
	public void setSupportedCurrencies(String[] supportedCurrencies) {
		this.supportedCurrencies = supportedCurrencies.clone();
	}

	/**
	 * Method used to create the single ton object of the AppConfig class
	 *
	 * @return AppConfig An instance of the AppConfig class
	 */
	/*
	 * public static AppConfig getInstance() { try {
	 * PhrescoLogger.info(TAG+" - Inside getInstance() *********** ");
	 * if(instance == null) { instance = new AppConfig(); } } catch (Exception
	 * ex) { PhrescoLogger.info(TAG+" - getInstance() - Exception :  " +
	 * ex.toString()); PhrescoLogger.warning(ex); } return instance; }
	 */

	/**
	 * Create the AppConfig JSON object, by reading the configuration JSON from
	 * web server
	 *
	 * @param sURL
	 * @return JSONObject
	 * @throws IOException
	 */
	public static JSONObject getAppConfigJSONObject(String sURL) throws IOException {
		PhrescoLogger.info(TAG + " - getAppConfigJSONObject() :  ");
		JSONObject appConfigJSONResponseObj = null;
		appConfigJSONResponseObj = JSONHelper.getJSONObjectFromURL(sURL);
		return appConfigJSONResponseObj;
	}

	/**
	 * Returns AppConfig object serialized using GSON object
	 *
	 * @param appConfigObjString
	 * @return AppConfig object
	 */
	public static AppConfig getAppConfigGSONObject(String appConfigObjString) {
		AppConfig appConfigObj = null;
		PhrescoLogger.info(TAG + "appConfigJSONResponseObj() - JSON STRING : " + appConfigObjString);
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			appConfigObj = jsonObj.fromJson(appConfigObjString, AppConfig.class);
			PhrescoLogger.info(TAG + "getAppConfigJSONObject() - JSON OBJECT : " + appConfigObj.toString());
		} catch (JsonSyntaxException ex) {
			PhrescoLogger.info(TAG + "JsonSyntaxException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return appConfigObj;
	}

	/**
	 * Download the menu images from server, and store it on sd card
	 * @throws IOException
	 */
	public static void downloadMenuImages(List<FeatureConfig> featureConfigObj) throws IOException {
		PhrescoLogger.info(TAG + "downloadMenuImages()");
		ImageCacheManager imgCacheManager = new ImageCacheManager();
		for (FeatureConfig f : featureConfigObj) {
			imgCacheManager.downloadImage(Constants.getCurrentScreenResolution() + f.getFeatureIcon().getDefaultTab(),
					Constants.MENU_FOLDER_PATH + f.getFeatureIcon().getDefaultTab().substring(f.getFeatureIcon().getDefaultTab().lastIndexOf("/") + 1));
			imgCacheManager.downloadImage(Constants.getCurrentScreenResolution() + f.getFeatureIcon().getHighlightedTab(),
					Constants.MENU_FOLDER_PATH + f.getFeatureIcon().getHighlightedTab().substring(f.getFeatureIcon().getHighlightedTab().lastIndexOf("/") + 1));

		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppConfig [featureLayout=");
		builder.append(Arrays.toString(featureLayout));
		builder.append(", androidFeatureImagePaths=");
		builder.append(androidFeatureImagePaths);
		builder.append(", featureConfig=");
		builder.append(featureConfig);
		builder.append(", headerAssets=");
		builder.append(headerAssets);
		builder.append(", serverEnvironmentUrls=");
		builder.append(serverEnvironmentUrls);
		builder.append(", appVersionInfo=");
		builder.append(appVersionInfo);
		builder.append(", defaultCurrency=");
		builder.append(defaultCurrency);
		builder.append(", supportedCurrencies=");
		builder.append(Arrays.toString(supportedCurrencies));
		builder.append("]");
		return builder.toString();
	}

}