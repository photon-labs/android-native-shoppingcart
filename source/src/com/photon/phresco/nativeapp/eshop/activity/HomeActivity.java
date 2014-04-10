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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Show the home screen for the application
 *
 * @author viral_b
 *
 */
public class HomeActivity extends PhrescoActivity {

	private static final String TAG = "HomeActivity ***** ";

	private ImageButton backButton, searchButton, browseButton, offersButton, loginButton;
	private ImageButton registerButton, registerDisableButton, loginDisabledButton;

	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	private String home = "home";
	private String browse = "browse";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		initEnvironment();
		PhrescoLogger.info(TAG + " - Constants.USER_ID : " + Constants.getUserId());
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// System.runFinalizersOnExit(true);
					android.os.Process.killProcess(android.os.Process.myPid());
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startProductListActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - searchButton  - Exception : " + ex.toString());
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

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startLoginActivity();

				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - loginButton  - Exception : " + ex.toString());
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

	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);
			searchButton = (ImageButton) findViewById(R.id.home_search_btn);
			browseButton = (ImageButton) findViewById(R.id.home_browse_btn);
			offersButton = (ImageButton) findViewById(R.id.home_offers_btn);
			loginButton = (ImageButton) findViewById(R.id.home_login_btn);
			loginDisabledButton = (ImageButton) findViewById(R.id.home_login_disable_btn);
			registerButton = (ImageButton) findViewById(R.id.home_register_btn);
			registerDisableButton = (ImageButton) findViewById(R.id.home_register_disable_btn);

			if (Constants.getUserId() == 0) {

				// when user is not logged in
				loginButton.setVisibility(View.VISIBLE);
				loginDisabledButton.setVisibility(View.INVISIBLE);

				registerButton.setVisibility(View.VISIBLE);
				registerDisableButton.setVisibility(View.INVISIBLE);
			} else {

				// when user is logged in
				loginButton.setVisibility(View.INVISIBLE);
				loginDisabledButton.setVisibility(View.VISIBLE);

				registerButton.setVisibility(View.INVISIBLE);
				registerDisableButton.setVisibility(View.VISIBLE);
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Start the product list activity
	 */
	private void startProductListActivity() {
		try {
			Intent productListActivity = new Intent(getApplicationContext(), ProductListActivity.class);
			productListActivity.putExtra(currActivity, browse);
			productListActivity.putExtra(prevActivity, home);
			if (((EditText) findViewById(R.id.txt_search)).getText().toString().length() > 0) {
				productListActivity.putExtra("search", ((EditText) findViewById(R.id.txt_search)).getText().toString());
			}
			startActivity(productListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductListActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Start the category list activity
	 */
	private void startCategoryListActivity() {
		try {
			Intent categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra(currActivity, browse);
			categoryListActivity.putExtra(prevActivity, home);
			startActivity(categoryListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCategoryListActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Start the offers activity
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
	 * Start the login activity
	 */
	private void startLoginActivity() {
		try {
			Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
			loginActivity.putExtra(currActivity, home);
			startActivity(loginActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startLoginActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Start the registration activity
	 */
	private void startRegistrationActivity() {
		try {
			Intent registrationActivity = new Intent(getApplicationContext(), RegistrationActivity.class);
			registrationActivity.putExtra(currActivity, home);
			startActivity(registrationActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startRegistrationActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}	
}
