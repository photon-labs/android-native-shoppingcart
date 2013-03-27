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
package com.photon.phresco.nativeapp.eshop.model.category;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Class to hold the category object
 *
 * @author viral_b
 *
 */

public class Category implements Serializable {

	private static final String TAG = "Model: Category ***** ";
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private int productCount;

	private String image;

	public Category() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the productCount
	 */
	public int getProductCount() {
		return productCount;
	}

	/**
	 * @param productCount
	 *            the productCount to set
	 */
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Get the category JSON object from web server
	 * @param 	sURL
	 * @return	JSONObject
	 * @throws 	IOException
	 */
	public JSONObject getCategoryJSONObject(String sURL) throws IOException {
		PhrescoLogger.info(TAG + " - getCategoryJSONObject() :  ");
		JSONObject categoryJSONResponseObj = null;
		categoryJSONResponseObj = JSONHelper.getJSONObjectFromURL(sURL);
		return categoryJSONResponseObj;
	}

	/**
	 * Get the category list as GSON object
	 *
	 * @param categoryJSONObj
	 * @return List<Category>
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getCategoryGSONObject(JSONObject categoryJSONObj) {
		List<Category> categoryList = null;
		PhrescoLogger.info(TAG + "getCategoryGSONObject() - JSON STRING : " + categoryJSONObj.toString());
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			Type listType = new TypeToken<List<Category>>() {
			}.getType();
			categoryList = (List<Category>) jsonObj.fromJson(categoryJSONObj.getJSONArray("category").toString(), listType);

		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + "getCategoryGSONObject: JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return categoryList;
	}

	/**
	 * Download the category images from server, and store it on sd card
	 * @param categoryList
	 * 		List of category objects, containing the category image information
	 * @throws IOException
	 */
	public void downloadCategoryImages(List<Category> categoryList) throws IOException {
		PhrescoLogger.info(TAG + "downloadCategoryImages");
		ImageCacheManager imgCacheManager = new ImageCacheManager();
		// Download the category images from server
		for (Category categoryItem : categoryList) {
			imgCacheManager.downloadImage(Constants.getCurrentScreenResolution() + categoryItem.getImage(),
					Constants.CATEGORIES_FOLDER_PATH + categoryItem.getImage().substring(categoryItem.getImage().lastIndexOf("/") + 1));

		}
	}
}