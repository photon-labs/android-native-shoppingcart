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
package com.photon.phresco.nativeapp.eshop.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.product.Product;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewComments;

/**
 * Show product review screen
 *
 * @author viral_b
 *
 */
public class ProductReviewDetailActivity extends PhrescoActivity {

	private static final String TAG = "ProductReviewDetailActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton;

	private TextView txtReviewComment, txtReviewUser, txtReviewDate;
	private RatingBar rbReviewRating;

	private ProductReviewComments productReviewComment;

	private String currentActivity = null;

	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	private static Product productItem;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_review_detail);

		Intent productReviewCommentIntent = getIntent();
		if (productReviewCommentIntent != null && productReviewCommentIntent.getExtras() != null) {
			productReviewComment = (ProductReviewComments) productReviewCommentIntent.getSerializableExtra("reviewComment");
			productItem = (Product) productReviewCommentIntent.getExtras().getSerializable("product");
			currentActivity = productReviewCommentIntent.getExtras().getString(currActivity);
		}
		PhrescoLogger.info(TAG + " - currentActivity   from Intent: " + currentActivity);
		initEnvironment();
		txtReviewComment.setMovementMethod(new ScrollingMovementMethod());
		showProductReviewDetails();

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
	}

	/**
	 * Move back to previous activity
	 */
	private void goBackToPreviousActivity() {
		try {
			Intent productReviewActivity = new Intent(getApplicationContext(), ProductReviewActivity.class);
			productReviewActivity.putExtra(currActivity, currentActivity);
			productReviewActivity.putExtra(prevActivity, "");
			productReviewActivity.putExtra("product", productItem);
			startActivity(productReviewActivity);
			finish();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start category list activity
	 */
	private void startCategoryListActivity() {
		try {
			Intent categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra(currActivity, "browse");
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
			offersActivity.putExtra(currActivity, "offers");
			startActivity(offersActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startOffersActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
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
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);

			browseButton = (ImageButton) findViewById(R.id.tab_browse);
			offersButton = (ImageButton) findViewById(R.id.tab_specialoffer);
			myCartButton = (ImageButton) findViewById(R.id.tab_mycart);

			txtReviewComment = (TextView) findViewById(R.id.review_comment);
			txtReviewUser = (TextView) findViewById(R.id.review_user);
			txtReviewDate = (TextView) findViewById(R.id.review_date);
			rbReviewRating = (RatingBar) findViewById(R.id.review_rating);

			if (currentActivity.equalsIgnoreCase("browse")){
				browseButton.setBackgroundResource(R.drawable.browse_tab_selected);
				offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			} else if (currentActivity.equalsIgnoreCase("offers")) {
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
	 * Show the product review details
	 */
	private void showProductReviewDetails() {
		try {
			txtReviewComment.setText(productReviewComment.getComment());
			txtReviewUser.setText(productReviewComment.getUser());
			rbReviewRating.setRating(productReviewComment.getRating());

			SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date dtSource = sdfSource.parse(productReviewComment.getCommentDate());
			SimpleDateFormat sdfCommentDate = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			txtReviewDate.setText(sdfCommentDate.format(dtSource));
		} catch (ParseException ex) {
			PhrescoLogger.info(TAG + " - showProductReviewDetails  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
