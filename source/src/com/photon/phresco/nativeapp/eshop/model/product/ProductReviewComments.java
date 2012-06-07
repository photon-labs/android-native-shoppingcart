
/*
 * Classname: ProductReviewComments
 * Version information: 1.0
 * Date: Dec 01, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.product;

import java.io.IOException;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Class to hold the information regarding product review comments
 * @author viral_b
 *
 */
public class ProductReviewComments implements Serializable {

	private static final String TAG = "Model: ProductReviewComments ***** ";
	private static final long serialVersionUID = 1L;
	private int rating;
	private String comment;
	private String user;
	private String commentDate;
	private String successMessage;

	public ProductReviewComments() {

	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the commentDate
	 */
	public String getCommentDate() {
		return commentDate;
	}

	/**
	 * @param commentDate the commentDate to set
	 */
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}


	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @param successMessage the successMessage to set
	 */
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}


	/**
	 * Post the Product Review comment  JSON object
	 *
	 * @param productReviewCommentObj
	 */
	public JSONObject postCredentialDetails(int productId, int userId, float rating, String userComment, String commentDate, String operation) throws IOException{
		JSONObject responseJSON = null;

		// Post the comment to JSON to server, and receive the JSON response
				JSONObject jObjMain = new JSONObject();
				JSONObject jObj = new JSONObject();

				try {
					if (operation.contains("ProductReviewCommentActivity")) {
						jObj.put("productId", productId);
						jObj.put("userId", userId);
						jObj.put("rating", rating);
						jObj.put("comment", userComment);
						jObj.put("commentDate", commentDate);
					}

					if (operation.contains("ProductReviewCommentActivity")) {
						jObjMain.put("review", jObj);
					}

				} catch (JSONException ex) {
					PhrescoLogger.info(TAG + " - postLoginDetails  - JSONException : " + ex.toString());
					PhrescoLogger.warning(ex);
				}

				responseJSON = JSONHelper.postJSONObjectToURL(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.PRODUCT_REVIEW_POST_URL, jObjMain.toString());
				return responseJSON;

	}

	/**
	 * Get the Product Review comment  GSON object
	 *
	 * @param productReviewCommentObj
	 */

	public ProductReviewComments getProductReviewCommentGSONObject(String reviewCommentObjString) {
		ProductReviewComments productReviewCommentObj=null;

		PhrescoLogger.info(TAG + "getProductReviewCommentGSONObject() - JSON STRING : " + reviewCommentObjString);

		try {
			// Create an object for Gson (used to create the JSON object)
			Gson jsonObj = new Gson();
			productReviewCommentObj = jsonObj.fromJson(reviewCommentObjString, ProductReviewComments.class);
			PhrescoLogger.info(TAG + "getProductReviewCommentGSONObject() - JSON OBJECT : " + productReviewCommentObj.toString());

		}  catch (JsonSyntaxException ex) {
			PhrescoLogger.info(TAG + "JsonSyntaxException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		return productReviewCommentObj;
	}

}
