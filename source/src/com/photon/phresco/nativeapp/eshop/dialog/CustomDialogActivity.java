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
 * Classname: CustomDialogActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.activity.PhrescoActivity;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.customer.Login;
import com.photon.phresco.nativeapp.eshop.model.customer.Registration;
import com.photon.phresco.nativeapp.eshop.model.product.Product;

/**
 * An activity that looks like a pop-up dialog with a custom theme using a
 * different background color and a button
 *
 * @author viral_b
 */
public class CustomDialogActivity extends PhrescoActivity {

	private static final String TAG = "CustomDialogActivity ***** ";

	private String previousActivity = null;
	private String currentActivity = null;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";
	private String product = "product";

	private Product productItem;
	private int productId = 0;
	private TextView txtMessage = null;
	private Login loginObj = null;
	private Registration registrationObj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ImageButton btnOK = null;
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.custom_dialog);

			btnOK = (ImageButton) findViewById(R.id.btn_dialog_ok);
			txtMessage = (TextView) findViewById(R.id.txt_message);

			Intent customDialogIntent = getIntent();

			if (customDialogIntent != null) {

				if ((Product) customDialogIntent.getSerializableExtra(product) != null) {
					productItem = (Product) customDialogIntent.getExtras().getSerializable(product);
					PhrescoLogger.info(TAG + "onCreate - product - Title : " + productItem.getName());

					productId = productItem.getId();
					PhrescoLogger.info(TAG + "onCreate - product Id == " + productId);
					PhrescoLogger.info(TAG + "onCreate - product Name == " + productItem.getName());
				}

				previousActivity = customDialogIntent.getExtras().getString(prevActivity);
				PhrescoLogger.info(TAG + "onCreate - previousActivity ===============	" + previousActivity);

				currentActivity = customDialogIntent.getExtras().getString(currActivity);
				PhrescoLogger.info(TAG + "onCreate - currentActivity ==============	" + currentActivity);

				if ((previousActivity != null) && previousActivity.length() > 0) {

					if (previousActivity.equalsIgnoreCase("RegistrationActivity")) {

						registrationObj = (Registration) customDialogIntent.getExtras().getSerializable("obj");

						// display the message in pop up dialog
						showMessage(registrationObj.getMessage());

					} else if (previousActivity.equalsIgnoreCase("LoginActivity")) {

						loginObj = (Login) customDialogIntent.getExtras().getSerializable("obj");

						// display the message in pop up dialog
						showMessage(loginObj.getSuccessMessage());
					} else if (previousActivity.equalsIgnoreCase("ProductReviewCommentActivity")) {
						PhrescoLogger.info(TAG + "onCreate - 12312312312312312312123");
						loginObj = (Login) customDialogIntent.getExtras().getSerializable("obj");
						// display the message in pop up dialog
						showMessage(getString(R.string.review_login_required));

					}
				}

			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreateException  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					if (previousActivity.equalsIgnoreCase("ProductReviewCommentActivity")) {
						setResultForProductReviewCommentActivity();
					} else if (previousActivity.equalsIgnoreCase("LoginActivity")) {
						setResultForLoginActivity();
					} else if (previousActivity.equalsIgnoreCase("RegistrationActivity")) {
						setResultForRegistrationActivity();
					}
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - btnOK  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
	}


	/**
	 *	set activity result for RegistrationActivity
	 */
	private void setResultForRegistrationActivity() {
		PhrescoLogger.info(TAG + " result set to RESULT_OK for Registration:  ");
		if (registrationObj.getSuccessMessage().equalsIgnoreCase("Success")) {
			Intent registrationIntent = new Intent();
			registrationIntent.putExtra("userId", registrationObj.getUserId());
			setResult(RESULT_OK, registrationIntent);
		} else {
			setResult(RESULT_OK, null);
		}
		finish();
	}

	/**
	 * set activity result for LoginActivity
	 */
	private void setResultForLoginActivity() {
		PhrescoLogger.info(TAG + " result set to RESULT_OK for Login :  ");
		setResult(RESULT_OK, null);
		finish();
	}

	/**
	 * set activity result for ProductReviewCommentActivity
	 */
	private void setResultForProductReviewCommentActivity() {
		try {
			if (currentActivity.equalsIgnoreCase("browse")) {
				PhrescoLogger.info(TAG + " result set to RESULT_OK for ProductReviewCommentActivity = browse :  ");
				Intent loginIntent = new Intent();
				loginIntent.putExtra(currActivity, currentActivity);
				loginIntent.putExtra(prevActivity, "CustomDialogActivity");
				loginIntent.putExtra(product, productItem);
				setResult(RESULT_OK, loginIntent);
				finish();
			} else if (currentActivity.equalsIgnoreCase("offers")) {
				PhrescoLogger.info(TAG + " result set to RESULT_OK for ProductReviewCommentActivity = offers :  ");
				Intent loginIntent = new Intent();
				loginIntent.putExtra(currActivity, currentActivity);
				loginIntent.putExtra(prevActivity, "CustomDialogActivity");
				loginIntent.putExtra(product, productItem);
				setResult(RESULT_OK, loginIntent);
				finish();
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - btnOK  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the success or failure message as per the response
	 *
	 * @param message
	 */
	private void showMessage(String message) {
		PhrescoLogger.info(TAG + " - showMessage() : " + message);
		try {
			txtMessage.setText(message);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - showMessage  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}
}
