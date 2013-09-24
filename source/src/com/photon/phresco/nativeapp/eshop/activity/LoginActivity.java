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
package com.photon.phresco.nativeapp.eshop.activity;

import java.io.IOException;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.dialog.CustomDialogActivity;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.customer.Customer;
import com.photon.phresco.nativeapp.eshop.model.customer.Login;
import com.photon.phresco.nativeapp.eshop.model.product.Product;

/**
 * Show the login screen
 *
 * @author viral_b
 *
 */
public class LoginActivity extends PhrescoActivity {

	private static final String TAG = "LoginActivity ***** ";
	private ImageButton loginButton, cancelButton, registerButton, backButton;
	private EditText loginEmailId, loginPassword;

	private Exception ioException = null;

	private String previousActivity = null;
	private String currentActivity = null;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";
	private String product = "product";

	private Login loginResponseObj = null;
	private Product productItem;
	private int productId = 0;

	private Customer customerObj = new Customer();
	private static final int REQUEST_CODE_ONE = 1;
	private static final int REQUEST_CODE_TWO = 2;
	private static final int REQUEST_CODE_THREE = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);

			Intent loginIntent = getIntent();

			if (loginIntent != null) {

				if ((Product) loginIntent.getSerializableExtra(product) != null) {
					productItem = (Product) loginIntent.getExtras().getSerializable(product);
					PhrescoLogger.info(TAG + "onCreate ---> - product - Title : " + productItem.getName());

					productId = productItem.getId();
					PhrescoLogger.info(TAG + "onCreate ---> - product Id == " + productId);
				}

				previousActivity = loginIntent.getExtras().getString(prevActivity);
				PhrescoLogger.info(TAG + "prevActivity from intent is ====	" + previousActivity);

				currentActivity = loginIntent.getExtras().getString(currActivity);
				PhrescoLogger.info(TAG + "currentActivity from intent is ====	" + currentActivity);
		}

			initEnvironment();

			loginButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (isValid()) {
							submitLoginDetails();
						}
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - LoginButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startHomeActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - cancelButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			registerButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						startRegistrationActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - registerButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			backButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						startHomeActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start home activity
	 */
	private void startHomeActivity() {
		try {
			Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
			homeActivity.putExtra(currActivity, "home");
			startActivity(homeActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startHomeActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start registration activity
	 */
	private void startRegistrationActivity() {
		try {
			Intent registrationActivity = new Intent(getApplicationContext(), RegistrationActivity.class);
			registrationActivity.putExtra(currActivity, "home");
			startActivity(registrationActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startRegistrationActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			loginButton = (ImageButton) findViewById(R.id.login_btn);
			cancelButton = (ImageButton) findViewById(R.id.cancel_btn);
			registerButton = (ImageButton) findViewById(R.id.register_btn);
			backButton = (ImageButton) findViewById(R.id.back_btn);

			loginEmailId = (EditText) findViewById(R.id.txt_email);
			loginPassword = (EditText) findViewById(R.id.txt_password);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	private void submitLoginDetails() {
		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(LoginActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.login_auth));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					postLoginRequestToServer();
				}

				@Override
				public void processOnComplete() {
					getLoginResponseFromServer();
				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " submit Login Details - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the custom dialog with message
	 */
	private void showCustomDialogMessage() {
		try {
			PhrescoLogger.info(TAG + " - Entered After Login : ");

			PhrescoLogger.info(TAG + " showCustomDialogMessage currentActivity -  " + currentActivity);
			PhrescoLogger.info(TAG + " showCustomDialogMessage previousActivity -  " + previousActivity);
			if (currentActivity.equalsIgnoreCase("browse")) {

				PhrescoLogger.info(TAG + " showCustomDialogMessage -- !!!!!!!!!!!!!!!!!!!!!!!!!");

				if (previousActivity.equalsIgnoreCase("CustomDialogActivity")) {

					PhrescoLogger.info(TAG + " showCustomDialogMessage -- @@@@@@@@@@@@@@@@@@@@@@@@@@");
					Intent customDialogActivity = new Intent(getApplicationContext(), CustomDialogActivity.class);
					customDialogActivity.putExtra(currActivity, currentActivity);
					customDialogActivity.putExtra(prevActivity, "LoginActivity");
					customDialogActivity.putExtra("obj", loginResponseObj);
					customDialogActivity.putExtra(product, productItem);
					startActivityForResult(customDialogActivity, REQUEST_CODE_TWO);

				}

			} else if (currentActivity.equalsIgnoreCase("offers")) {
				PhrescoLogger.info(TAG + " showCustomDialogMessage -- ##########################");
				if (previousActivity.equalsIgnoreCase("CustomDialogActivity")) {

					PhrescoLogger.info(TAG + " showCustomDialogMessage -- $$$$$$$$$$$$$$$$$$$$$$");
					Intent customDialogActivity = new Intent(getApplicationContext(), CustomDialogActivity.class);
					customDialogActivity.putExtra(currActivity, currentActivity);
					customDialogActivity.putExtra(prevActivity, "LoginActivity");
					customDialogActivity.putExtra("obj", loginResponseObj);
					customDialogActivity.putExtra(product, productItem);

					PhrescoLogger.info(TAG + "showCustomDialogMessage() ---> productItem ======= " + productItem.getName());

					startActivityForResult(customDialogActivity, REQUEST_CODE_THREE);

				}
			} else {
				PhrescoLogger.info(TAG + " showCustomDialogMessage -- %%%%%%%%%%%%%%%%%%%%%%%%%");
				Intent customDialogActivity = new Intent(getApplicationContext(), CustomDialogActivity.class);
				customDialogActivity.putExtra(prevActivity, "LoginActivity");
				customDialogActivity.putExtra("obj", loginResponseObj);
				startActivityForResult(customDialogActivity, REQUEST_CODE_ONE);
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " showCustomDialogMessage - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Wrapper method to post the login details to server for authentication
	 */
	private void postLoginRequestToServer() {
		try {
			postLoginDetails();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " postLoginRequestToServer - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Post the login details to server for authentication
	 * @throws Exception 
	 */
	protected void postLoginDetails() throws Exception {
		Login logObj = new Login();
		JSONObject loginJSONResponse = customerObj.postCredentialDetails(loginEmailId.getText().toString(), loginPassword.getText().toString());

		if (loginJSONResponse != null) {
			loginResponseObj = logObj.getLoginGSONObject(loginJSONResponse.toString());
		}
	}

	/**
	 * Wrapper method to start the next activity when login response is
	 * available from server
	 */
	private void getLoginResponseFromServer() {
		try {
			if (ioException == null) {
				Constants.setUserId(loginResponseObj.getUserId());
				PhrescoLogger.info(TAG + " - Constants.USER_ID : " + Constants.getUserId());
				showCustomDialogMessage();
			} else {
				showErrorDialogWithCancel();
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - submitLoginDetails - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode == RESULT_OK) {
				Intent intentObj = null;
				PhrescoLogger.info(TAG + " - onActivityResult - requestCode === " + requestCode);
				PhrescoLogger.info(TAG + " - onActivityResult - Intent from ActivityResult - data === " + data);
				PhrescoLogger.info(TAG + " - onActivityResult - product Item from main intent === " + productItem);

				switch (requestCode) {
					case REQUEST_CODE_ONE:
						intentObj = new Intent(getApplicationContext(), HomeActivity.class);
						intentObj.putExtra(currActivity, "home");
						startActivity(intentObj);
						finish();
						break;
					case REQUEST_CODE_TWO:
						intentObj = new Intent(getApplicationContext(), ProductReviewCommentActivity.class);
						intentObj.putExtra(currActivity, currentActivity);
						intentObj.putExtra(prevActivity, previousActivity);
						intentObj.putExtra(product, productItem);
						startActivity(intentObj);
						finish();
						break;
					case REQUEST_CODE_THREE:
						intentObj = new Intent(getApplicationContext(), ProductReviewCommentActivity.class);
						intentObj.putExtra(currActivity, currentActivity);
						intentObj.putExtra(prevActivity, previousActivity);
						intentObj.putExtra(product, productItem);
						startActivity(intentObj);
						finish();
						break;
					default:
						break;
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onActivityResult  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Validate all the required fields on screen
	 *
	 * @return boolean
	 */
	private boolean isValid() {
		boolean isValidFlag = true;
		try {
			if (customerObj.isEmpty(loginEmailId.getText().toString())) {
				loginEmailId.setError(getString(R.string.email_required));
				isValidFlag = false;
			} else if (customerObj.isEmpty(loginPassword.getText().toString())) {
				loginPassword.setError(getString(R.string.password_required));
				isValidFlag = false;
			} else if (!customerObj.isValidEmailId(loginEmailId.getText().toString())) {
				loginEmailId.setError(getString(R.string.invalid_email));
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
