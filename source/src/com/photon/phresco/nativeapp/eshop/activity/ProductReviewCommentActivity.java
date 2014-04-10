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
/**
 *
 */
package com.photon.phresco.nativeapp.eshop.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.dialog.CustomDialogActivity;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.product.Product;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewComments;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewCommentsSubmit;

/**
 * @author chandankumar_r
 *
 */
public class ProductReviewCommentActivity extends PhrescoActivity {

	private static final String TAG = "ProductReviewCommentActivity ***** ";

	private Exception ioException = null;

	private ImageButton backButton, browseButton, offersButton, myCartButton;
	private ImageButton submitReview;
	private RatingBar productCommentRating;
	private EditText userComment;
	private Product productItem;
	private static int count = 0;
	private int productId = 0;
	private float userRatings;
	private Intent userCommentIntent;

	private String previousActivity = null;
	private String currentActivity = null;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	private String browse = "browse";
	private String offers = "offers";
	private String product = "product";
	private String productReviewCommentVar = "productReviewComment";

	private String date;
	private static ProductReviewCommentsSubmit productReviewCommentSubmitobj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.product_review_comment);
			PhrescoLogger.info(TAG + " - Constants.userId : " + Constants.getUserId());

			userCommentIntent = getIntent();

			if (userCommentIntent != null && ((Product) userCommentIntent.getSerializableExtra(product) != null)) {

				productItem = (Product) userCommentIntent.getExtras().getSerializable(product);
				PhrescoLogger.info(TAG + " - product - Title : " + productItem.getName());

				productId = productItem.getId();
				PhrescoLogger.info(TAG + " - product Id == " + productId);

				previousActivity = getExtrasPreviousActivity();

				previousActivity = userCommentIntent.getExtras().getString(prevActivity);
				currentActivity = userCommentIntent.getExtras().getString(currActivity);

				PhrescoLogger.info(TAG + " - previousActivity == " + previousActivity);
				PhrescoLogger.info(TAG + " - currentActivity == " + currentActivity);

			}
			initEnvironment();
			getPostingCommentTime();

			PhrescoLogger.info(TAG + " - productReviewCommentSubmitobj == " + productReviewCommentSubmitobj);
			if (productReviewCommentSubmitobj != null) {
				getProductReviewComments();
			}

			browseButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startCategoryListActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - browseButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			offersButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startOffersActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - offersButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			myCartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startMyCartActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - myCartButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			backButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						goBackToPreviousActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}

			});

			submitReview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					PhrescoLogger.info(TAG + " - submit Review button- Clicked : ");
					if (isValid()) {
						if (Constants.getUserId() == 0) {
							productReviewCommentSubmitobj = setProductReviewComments();
							// when user is not logged in
							showCustomDialogMessage();
						} else {
							// when user is logged in
							if (productReviewCommentSubmitobj != null) {
								productReviewCommentSubmitobj = null;
							}
							submitProductReviewComment();
						}
					}
				}
			});
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * method to get product rating and comment given by user
	 *
	 */
	private void getProductReviewComments() {
		PhrescoLogger.info(TAG + " - getProductReviewComments  start-  : ");
		try {
			((RatingBar) findViewById(R.id.product_review_comment_rating)).setRating(productReviewCommentSubmitobj.getRating());
			((EditText) findViewById(R.id.user_review_comment)).setText(productReviewCommentSubmitobj.getComment());

			PhrescoLogger.info(TAG + " - productReviewCommentSubmitobj.getRating()  : " + productReviewCommentSubmitobj.getRating());
			PhrescoLogger.info(TAG + " - productReviewCommentSubmitobj.getComment()  : " + productReviewCommentSubmitobj.getComment());
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getProductReviewComments  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * method to set the product rating and comments
	 *
	 */
	private ProductReviewCommentsSubmit setProductReviewComments() {
		PhrescoLogger.info(TAG + " - setProductReviewComments  start-  : ");
		ProductReviewCommentsSubmit obj = null;
		try {
			obj = new ProductReviewCommentsSubmit();
			obj.setComment(userComment.getText().toString());
			obj.setRating(productCommentRating.getRating());

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - setProductReviewComments  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return obj;
	}

	/**
	 * start my cart activity
	 */
	private void startMyCartActivity() {
		try {
			Intent myCartActivity = new Intent(getApplicationContext(), MyCartActivity.class);
			myCartActivity.putExtra(currActivity, "mycart");
			startActivity(myCartActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startMyCartActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start category list activity
	 */
	private void startCategoryListActivity() {
		try {
			Intent categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra(currActivity, browse);
			startActivity(categoryListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCategoryListActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start offers activity
	 */
	private void startOffersActivity() {
		try {
			Intent offersActivity = new Intent(getApplicationContext(), OffersActivity.class);
			offersActivity.putExtra(currActivity, offers);
			startActivity(offersActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startOffersActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/*
	 * method created to show pop up message when user is not logged in and
	 * trying to post review
	 */
	private void showCustomDialogMessage() {
		try {
			if (currentActivity.equalsIgnoreCase(browse)) {
				PhrescoLogger.info(TAG + " - showCustomDialogMessage  - ++++++++++++++++++++++++++");

				Intent customDialogActivity = new Intent(getApplication(), CustomDialogActivity.class);
				customDialogActivity.putExtra(currActivity, browse);
				customDialogActivity.putExtra(prevActivity, "ProductReviewCommentActivity");
				customDialogActivity.putExtra(product, productItem);
				customDialogActivity.putExtra("userRating", userRatings);
				startActivityForResult(customDialogActivity, 1);
			} else if (currentActivity.equalsIgnoreCase(offers)) {
				PhrescoLogger.info(TAG + " - showCustomDialogMessage  - ************************");
				Intent customDialogActivity = new Intent(getApplication(), CustomDialogActivity.class);
				customDialogActivity.putExtra(currActivity, offers);
				customDialogActivity.putExtra(prevActivity, "ProductReviewCommentActivity");
				customDialogActivity.putExtra(product, productItem);
				customDialogActivity.putExtra("userRating", userRatings);
				startActivityForResult(customDialogActivity, 1);
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - showCustomDialogMessage  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		PhrescoLogger.info(TAG + " - onActivityResult");

		try {
			if (resultCode == RESULT_OK && requestCode == 1) {
				PhrescoLogger.info(TAG + " - onActivityResult === OOOOOOOO == previousActivity : " + previousActivity);
				PhrescoLogger.info(TAG + " - onActivityResult === OOOOOOOO == currentActivity  : " + currentActivity);

				Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
				loginIntent.putExtra(currActivity, currentActivity);
				loginIntent.putExtra(prevActivity, data.getExtras().getString(prevActivity));
				loginIntent.putExtra(product, productItem);
				startActivity(loginIntent);
				finish();

			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * to post comment year date and time
	 *
	 */
	private void getPostingCommentTime() {
		try {
			SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdfDateTime.format(new Date(System.currentTimeMillis()));

			PhrescoLogger.info(TAG + " - txt Review Date : " + date);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getPostingCommentTime  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	private String getExtrasPreviousActivity() {
		return (userCommentIntent.getExtras().getString("prevActivity") != null && userCommentIntent.getExtras().getString("prevActivity").length() > 0) ? userCommentIntent.getExtras().getString(
				"prevActivity") : null;
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);
			browseButton = (ImageButton) findViewById(R.id.tab_browse);
			offersButton = (ImageButton) findViewById(R.id.tab_specialoffer);
			myCartButton = (ImageButton) findViewById(R.id.tab_mycart);
			submitReview = (ImageButton) findViewById(R.id.product_review_comment_btn);
			userComment = (EditText) findViewById(R.id.user_review_comment);
			productCommentRating = (RatingBar) findViewById(R.id.product_review_comment_rating);

			PhrescoLogger.info(TAG + " - initEnvironment  currentActivity- : " + currentActivity);

			if (currentActivity.equalsIgnoreCase(browse)) {
				browseButton.setBackgroundResource(R.drawable.browse_tab_selected);
				offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			} else if (currentActivity.equalsIgnoreCase(offers)) {
				browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
				offersButton.setBackgroundResource(R.drawable.specialoffer_tab_selected);
			}

			myCartButton.setBackgroundResource(R.drawable.mycart_tab_normal);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Submit your comment to the server
	 *
	 */
	private void submitProductReviewComment() {

		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(ProductReviewCommentActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.submit_product_comment));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					postProductReviewCommentRequestToServer();
				}

				@Override
				public void processOnComplete() {
					getProductReviewComment();
				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " submitProductReviewComment() - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * Get the product reviews from server and go back to activity
	 */

	private void getProductReviewComment() {
		try {
			if (ioException == null) {
				if (previousActivity == null) {
					previousActivity = productReviewCommentVar;
				}
				if (currentActivity.equalsIgnoreCase(browse)) {
					Intent postComment = new Intent(getApplicationContext(), ProductReviewActivity.class);
					PhrescoLogger.info(TAG + " - getProductReviewComment() currentActivity : " + currentActivity);
					postComment.putExtra(currActivity, browse);
					postComment.putExtra(product, productItem);
					postComment.putExtra(prevActivity, productReviewCommentVar);
					startActivity(postComment);
					finish();
				} else if (currentActivity.equalsIgnoreCase(offers)) {
					Intent postComment = new Intent(getApplicationContext(), ProductReviewActivity.class);
					PhrescoLogger.info(TAG + " - getProductReviewComment() currentActivity : " + currentActivity);
					postComment.putExtra(currActivity, offers);
					postComment.putExtra(product, productItem);
					postComment.putExtra(prevActivity, productReviewCommentVar);
					startActivity(postComment);
					finish();
				}

			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getProductReviewComment() - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * method to post the product review comment
	 *
	 */
	private void postProductReviewCommentRequestToServer() {
		try {
			postProductCommentDetails();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " postProductReviewCommentRequestToServer - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * send the required details to server
	 *
	 */
	private void postProductCommentDetails() throws IOException {

		try {
			ProductReviewComments productReviewCommentObj = new ProductReviewComments();
			JSONObject productReviewCommentJSONResponse = productReviewCommentObj.postCredentialDetails(productId, Constants.getUserId(), productCommentRating.getRating(), userComment.getText().toString(), date,
					this.getClass().getName());

			if (productReviewCommentJSONResponse != null) {
				ProductReviewComments productReviewComment = productReviewCommentObj.getProductReviewCommentGSONObject(productReviewCommentJSONResponse.toString());
				if (productReviewComment != null) {
					PhrescoLogger.info(TAG + " productReviewComment Obj " + productReviewComment);
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " postProductCommentDetails - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Move back to previous activity
	 */
	protected void goBackToPreviousActivity() {
		try {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - : " + previousActivity);
			PhrescoLogger.info(TAG + " - going Back ToPreviousActivity from current activity - : " + currentActivity);
			Intent activityIntent = null;

			if (previousActivity == null) {
				previousActivity = productReviewCommentVar;
			}
			/*if (currentActivity.equalsIgnoreCase(browse)) {
				activityIntent = new Intent(getApplicationContext(), ProductReviewActivity.class);
				// activityIntent.putExtra(prevActivity, "productReview");
			} else if (currentActivity.equalsIgnoreCase(offers)) {
				activityIntent = new Intent(getApplicationContext(), ProductReviewActivity.class);
				// activityIntent.putExtra(prevActivity, "productReview");
			}*/
			activityIntent = new Intent(getApplicationContext(), ProductReviewActivity.class);
			activityIntent.putExtra(currActivity, currentActivity);
			activityIntent.putExtra(prevActivity, "");
			activityIntent.putExtra(product, productItem);
			startActivity(activityIntent);
			finish();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Validate all the required fields on screen
	 *
	 * @return boolean
	 */
	private boolean isValid() {
		PhrescoLogger.info(TAG + " - isValid()");
		boolean isValidFlag = true;
		ProductReviewCommentsSubmit commentObj = new ProductReviewCommentsSubmit();
		try {
			if (productCommentRating.getRating() == 0) {
				PhrescoLogger.info(TAG + " - productCommentRating.getRating() : " + productCommentRating.getRating());
				if (count == 0) {
					toast(getString(R.string.empty_review_rating));
					count++;
				}
				isValidFlag = false;
			} else if (commentObj.isEmpty(userComment.getText().toString())) {
				userComment.setError(getString(R.string.empty_review_comment));
				isValidFlag = false;
			}
		} catch (Exception ex) {
			isValidFlag = false;
			PhrescoLogger.info(TAG + " - isValid  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return isValidFlag;
	}
}