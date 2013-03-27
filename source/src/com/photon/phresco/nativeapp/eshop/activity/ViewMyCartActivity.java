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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.adapter.ViewMyCartListViewCustomAdapter;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCart;

/**
 * Show cart once ready to checkout
 *
 * @author viral_b
 *
 */
public class ViewMyCartActivity extends PhrescoActivity {

	private static final String TAG = "ViewMyCartActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton;
	private ImageButton checkoutButton;
	private ListView myCartListview;
	private ViewMyCartListViewCustomAdapter adapter;
	private TextView currency, subTotal;
	private String currActivity = "currentActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_mycart);

		initEnvironment();

		currency.setText(Constants.getCurrency());
		try {
			if (!MyCart.isEmpty()) {
				adapter = new ViewMyCartListViewCustomAdapter(this);
				myCartListview.setAdapter(adapter);
				// updateMyCartSubTotal();
				PhrescoLogger.info(TAG + " - adapter.getCount() : " + adapter.getCount());
			} else {
				toast(getString(R.string.empty_cart_message));
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

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

		checkoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startCheckOutActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - checkoutButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
	}

	/**
	 * Move back to previous activity depending upon the size of MyCart. If cart
	 * contains products, go back to MyCart screen, else go to category screen
	 */
	private void goBackToPreviousActivity() {
		try {
			Intent myActivity = null;
			if (MyCart.size() > 0) {
				myActivity = new Intent(getApplicationContext(), MyCartActivity.class);
				myActivity.putExtra(currActivity, "mycart");
			} else {
				myActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
				myActivity.putExtra(currActivity, "browse");
			}
			startActivity(myActivity);
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
	 * Show the checkout screen, if cart contains products, else show message
	 */
	private void startCheckOutActivity() {
		try {
			if (MyCart.size() > 0) {
				Intent checkOutActivity = new Intent(getApplicationContext(), CheckOutActivity.class);
				checkOutActivity.putExtra(currActivity, "mycart");
				startActivity(checkOutActivity);
				finish();
			} else {
				subTotal.setText(String.valueOf(0));
				toast(getString(R.string.empty_cart_message));
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCheckOutActivity  - Exception : " + ex.toString());
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

			subTotal = (TextView) findViewById(R.id.lbl_amount);
			currency = (TextView) findViewById(R.id.lbl_currency);

			checkoutButton = (ImageButton) findViewById(R.id.checkout_btn);
			myCartListview = (ListView) findViewById(R.id.mycart_listview);

			browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			myCartButton.setBackgroundResource(R.drawable.mycart_tab_selected);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		PhrescoLogger.info(TAG + " - onStart()********** ");
		updateMyCartSubTotalOnCreate();
	}

	/**
	 * Show the total amount of cart, when user comes to this screen from other
	 * screen
	 */
	public void updateMyCartSubTotalOnCreate() {
		try {
			subTotal.setText(String.valueOf(MyCart.getTotalPrice()));
			PhrescoLogger.info(TAG + " - updateMyCartSubTotalOnCreate  - : " + MyCart.getTotalPrice());
		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - updateMyCartSubTotalOnCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

}
