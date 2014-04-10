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
package com.photon.phresco.nativeapp.eshop.model.product;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Class to hold the information regarding single product
 *
 * @author viral_b
 *
 */
public class Product implements Serializable {

	private static final String TAG = "Model: Product ***** ";
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	@SerializedName("listPrice")
	private int price;
	private String image;
	private float rating;
	private String manufacturer;
	private int category;
	private String model;
	private String specialProduct;
	private int sellPrice;

	public Product() {

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
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the specialProduct
	 */
	public String getSpecialProduct() {
		return specialProduct;
	}

	/**
	 * @param specialProduct
	 *            the specialProduct to set
	 */
	public void setSpecialProduct(String specialProduct) {
		this.specialProduct = specialProduct;
	}

	/**
	 * @return the listPrice
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param listPrice
	 *            the listPrice to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the sellPrice
	 */
	public int getSellPrice() {
		return sellPrice;
	}

	/**
	 * @param sellPrice
	 *            the sellPrice to set
	 */
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the productRating
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @param productRating
	 *            the productRating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}

	/**
	 * Get the product JSON object from web server
	 *
	 * @param sURL
	 * @return JSONObject
	 * @throws IOException
	 */
	public JSONObject getProductJSONObject(String sURL) throws IOException {

		PhrescoLogger.info(TAG + " - getProductJSONObject() :  ");
		JSONObject productJSONResponseObj = null;
		productJSONResponseObj = JSONHelper.getJSONObjectFromURL(sURL);
		return productJSONResponseObj;
	}

	/**
	 * Get the product list as GSON object
	 *
	 * @param productJSONObj
	 * @return List<Product>
	 */
	@SuppressWarnings("unchecked")
	public List<Product> getProductGSONObject(JSONObject productJSONObj) {
		List<Product> productList = null;
//		PhrescoLogger.info(TAG + "getCategoryGSONObject() - JSON STRING : " + productJSONObj.toString());
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			Type listType = new TypeToken<List<Product>>() {
			}.getType();
			productList = (List<Product>) jsonObj.fromJson(productJSONObj.getJSONArray("product").toString(), listType);
		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + "getProductGSONObject: JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return productList;
	}

	/**
	 * Get the product detail as GSON object
	 *
	 * @param productDetailJSONObj
	 * @return List<ProductDetail>
	 */
	@SuppressWarnings("unchecked")
	public List<ProductDetail> getProductDetailGSONObject(JSONObject productDetailJSONObj) {
		List<ProductDetail> productDetail = null;
		PhrescoLogger.info(TAG + "getCategoryGSONObject() - JSON STRING : " + productDetailJSONObj.toString());
		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			Type listType = new TypeToken<List<ProductDetail>>() {
			}.getType();
			productDetail = (List<ProductDetail>) jsonObj.fromJson(productDetailJSONObj.getJSONArray("product").toString(), listType);

		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + "getProductDetailGSONObject: JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return productDetail;
	}

	/**
	 * Download the product images from server, and store it on sd card
	 *
	 * @param productList
	 *            List of product objects, containing the product image
	 *            information
	 * @throws IOException
	 */
	public void downloadProductImages(List<Product> productList) throws IOException {
		PhrescoLogger.info(TAG + "downloadProductImages");
		ImageCacheManager imgCacheManager = new ImageCacheManager();

		// Download the product images from server
		for (Product productItem : productList) {
			imgCacheManager.downloadImage(Constants.getCurrentScreenResolution() + productItem.getImage(),
					Constants.PRODUCT_FOLDER_PATH + productItem.getImage().substring(productItem.getImage().lastIndexOf("/") + 1));

		}
	}

	/**
	 * Download the product detail images from server, and store it on sd card
	 *
	 * @param productList
	 *            List of product objects, containing the product image
	 *            information
	 * @throws IOException
	 */
	public void downloadProductDetailImages(List<ProductDetail> productDetail) throws IOException {
		PhrescoLogger.info(TAG + "downloadProductDetailImages");
		ImageCacheManager imgCacheManager = new ImageCacheManager();

		// Download the product images from server
		for (ProductDetail iProductDetail : productDetail) {
			imgCacheManager.downloadImage(Constants.getCurrentScreenResolution() + iProductDetail.getDetailImage(),
					Constants.PRODUCT_DETAIL_FOLDER_PATH + iProductDetail.getDetailImage().substring(iProductDetail.getDetailImage().lastIndexOf("/") + 1));
		}
	}
}