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
 * Classname: OrderReviewActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCart;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCartItem;
import com.photon.phresco.nativeapp.eshop.model.order.Order;
import com.photon.phresco.nativeapp.eshop.model.order.OrderStatus;
import com.photon.phresco.nativeapp.eshop.model.order.OrderSubmit;
import com.photon.phresco.nativeapp.eshop.model.order.OrderSubmitProduct;

/**
 * Show order review screen
 *
 * @author viral_b
 *
 */
public class OrderReviewActivity extends PhrescoActivity {

	private static final String TAG = "OrderReviewActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton;
	private ImageButton orderSubmitButton, cancelButton;
	private Order orderInfo;
	private OrderStatus submitOrderJSONResponse = null;

	private String currActivity = "currentActivity";
	private String myCartActivity = "mycart";

	private Exception ioException = null;

	// @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.checkout_order_review);

		initEnvironment();

		Intent orderInfoIntent = getIntent();

		if (orderInfoIntent != null && (Order) orderInfoIntent.getSerializableExtra("orderObj") != null) {

			try {

				orderInfo = (Order) orderInfoIntent.getSerializableExtra("orderObj");

				displayOrderDetail();

			} catch (Exception ex) {
				PhrescoLogger.info(TAG + " - Getting Customer Details  - Exception : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
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

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startCheckOutActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - cancelButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		orderSubmitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					submitOrder();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - orderReviewButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
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
	 * start my cart activity
	 */
	private void startMyCartActivity() {
		try {
			Intent cartActivity = new Intent(getApplicationContext(), MyCartActivity.class);
			cartActivity.putExtra(currActivity, myCartActivity);
			startActivity(cartActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startMyCartActivity  - Exception : " + ex.toString());
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
	 * start check out activity
	 */
	private void startCheckOutActivity() {
		try {
			Intent checkOutActivity = new Intent(getApplicationContext(), CheckOutActivity.class);
			checkOutActivity.putExtra(currActivity, myCartActivity);
			startActivity(checkOutActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCheckOutActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Move back to previous activity depending upon the size of MyCart If, cart
	 * contains products, go back to check out screen, else go to category
	 * screen
	 */
	private void goBackToPreviousActivity() {
		Intent myActivity = null;
		if (MyCart.size() > 0) {
			myActivity = new Intent(getApplicationContext(), CheckOutActivity.class);
			myActivity.putExtra(currActivity, myCartActivity);
		} else {
			myActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			myActivity.putExtra(currActivity, "browse");
		}
		startActivity(myActivity);
		finish();
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

			orderSubmitButton = (ImageButton) findViewById(R.id.submit_order_btn);
			cancelButton = (ImageButton) findViewById(R.id.order_review_back_btn);

			browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			myCartButton.setBackgroundResource(R.drawable.mycart_tab_selected);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Submit the order details to server
	 */
	private void submitOrder() {
		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(OrderReviewActivity.this);

			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.order_submit));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					PhrescoLogger.info(TAG + " submitOrder - processOnStart() ");

					submitOrderDetailsToServer();
				}

				@Override
				public void processOnComplete() {
					PhrescoLogger.info(TAG + " submitOrder - processOnComplete()");

					showOrderStatusInformation();

				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " submitOrder - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Submit the order information to server
	 */
	private void submitOrderDetailsToServer() {
		try {
			postOrderDetails();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the order status information to user. If order submitted
	 * successfully, show the order number Else show the error message
	 */
	private void showOrderStatusInformation() {
		if (ioException == null) {
			try {
				PhrescoLogger.info(TAG + "showOrderStatusInformation() - Order submit response : " + submitOrderJSONResponse.toString());

				Intent orderStatusActivity = new Intent(getApplicationContext(), OrderStatusActivity.class);
				orderStatusActivity.putExtra(currActivity, myCartActivity);
				orderStatusActivity.putExtra("orderStatus", submitOrderJSONResponse);
				startActivity(orderStatusActivity);
				finish();
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + " - showOrderStatusInformation - Exception : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
		} else {
			// toast("Unable to fetch products reviews from server.. Please try again later");
			showErrorDialogWithCancel();
		}
	}

	/**
	 * Create JSON String for order details, and post it to the server
	 */
	private void postOrderDetails() throws IOException {
		Gson jsonObj = new Gson();
		// convert the orderInfo object to JSON String

		List<OrderSubmitProduct> products = null;
		OrderSubmit orderSubmit = new OrderSubmit();
		orderSubmit.setComments(orderInfo.getComments());
		orderSubmit.setCustomerInfo(orderInfo.getCustomerInfo());
		orderSubmit.setPaymentMethod(orderInfo.getPaymentMethod());
		orderSubmit.setTotalPrice(orderInfo.getTotalPrice());

		if (orderInfo.getProducts().size() > 0) {
			products = new ArrayList<OrderSubmitProduct>();

			for (MyCartItem product : orderInfo.getProducts()) {
				OrderSubmitProduct orderSubmitProduct = new OrderSubmitProduct();
				orderSubmitProduct.setProductId(product.getProduct().getId());
				orderSubmitProduct.setName(product.getProduct().getName());
				orderSubmitProduct.setPrice(product.getProduct().getPrice());
				orderSubmitProduct.setImageURL(product.getProduct().getImage());
				orderSubmitProduct.setDetailImageURL(product.getProduct().getDetailImage());
				orderSubmitProduct.setQuantity(product.getProductQuantity());
				orderSubmitProduct.setTotalPrice(product.getProduct().getPrice() * product.getProductQuantity());

				products.add(orderSubmitProduct);
			}
			orderSubmit.setProducts(products);
		}
		String orderDetail = jsonObj.toJson(orderSubmit);
		PhrescoLogger.info(TAG + "postOrderDetails() - JSON String : " + orderDetail);

		// Post the orderInfo JSON String to server, and receive the JSON response
		Order orderObj = new Order();
		JSONObject jObject = null;

//		submitOrderJSONResponse = orderObj.postOrderJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.ORDER_POST_URL, orderDetail);

		jObject = orderObj.postOrderJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.ORDER_POST_URL, orderDetail);
		if (jObject != null) {
			try {
				// Create an object for Gson (used to create the JSON object)
				submitOrderJSONResponse = orderObj.getOrderStatusGSONObject(jObject.toString());
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "getOrderStatusGSONObject() - Exception : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
		} else {
			submitOrderJSONResponse = null;
		}
	}

	/**
	 * Show the current order information
	 */
	private void displayOrderDetail() {
		PhrescoLogger.info(TAG + " - displayOrderDetail  ");
		try {
			((TextView) findViewById(R.id.email)).setText(orderInfo.getCustomerInfo().getEmailID());

			// Data For Delivery Address
			PhrescoLogger.info(TAG + " - Setting Delivery Address  ");

			((TextView) findViewById(R.id.delivery_info_first_name)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getFirstName());
			((TextView) findViewById(R.id.delivery_info_last_name)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getLastName());
			((TextView) findViewById(R.id.delivery_info_company)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getCompany());
			((TextView) findViewById(R.id.delivery_info_address1)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getAddressOne());
			((TextView) findViewById(R.id.delivery_info_address2)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getAddressTwo());
			((TextView) findViewById(R.id.delivery_info_city)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getCity());
			((TextView) findViewById(R.id.delivery_info_state)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getState());
			((TextView) findViewById(R.id.delivery_info_country)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getCountry());
			((TextView) findViewById(R.id.delivery_info_zipcode)).setText(String.valueOf(orderInfo.getCustomerInfo().getDeliveryAddress().getZipCode()));
			((TextView) findViewById(R.id.delivery_info_phone)).setText(String.valueOf(orderInfo.getCustomerInfo().getDeliveryAddress().getPhoneNumber()));

			// Data For Billing Address
			PhrescoLogger.info(TAG + " - Setting Billing Address  ");

			((TextView) findViewById(R.id.billing_info_first_name)).setText(orderInfo.getCustomerInfo().getBillingAddress().getFirstName());
			((TextView) findViewById(R.id.billing_info_last_name)).setText(orderInfo.getCustomerInfo().getBillingAddress().getLastName());
			((TextView) findViewById(R.id.billing_info_company)).setText(orderInfo.getCustomerInfo().getBillingAddress().getCompany());
			((TextView) findViewById(R.id.billing_info_address1)).setText(orderInfo.getCustomerInfo().getBillingAddress().getAddressOne());
			((TextView) findViewById(R.id.billing_info_address2)).setText(orderInfo.getCustomerInfo().getBillingAddress().getAddressTwo());
			((TextView) findViewById(R.id.billing_info_city)).setText(orderInfo.getCustomerInfo().getBillingAddress().getCity());
			((TextView) findViewById(R.id.billing_info_state)).setText(orderInfo.getCustomerInfo().getBillingAddress().getState());
			((TextView) findViewById(R.id.billing_info_country)).setText(orderInfo.getCustomerInfo().getBillingAddress().getCountry());
			((TextView) findViewById(R.id.billing_info_zipcode)).setText(String.valueOf(orderInfo.getCustomerInfo().getBillingAddress().getZipCode()));
			((TextView) findViewById(R.id.billing_info_phone)).setText(String.valueOf(orderInfo.getCustomerInfo().getBillingAddress().getPhoneNumber()));

			// Payment method
			((TextView) findViewById(R.id.lbl_currency_subtotal)).setText(Constants.getCurrency());
			((TextView) findViewById(R.id.sub_total)).setText(String.valueOf(MyCart.getTotalPrice()));

			((TextView) findViewById(R.id.lbl_currency_ordertotal)).setText(Constants.getCurrency());
			((TextView) findViewById(R.id.order_total)).setText(String.valueOf(MyCart.getTotalPrice()));

			((TextView) findViewById(R.id.payment_by)).setText(orderInfo.getPaymentMethod());

			// Order comments
			((TextView) findViewById(R.id.order_comments)).setText(orderInfo.getComments());
		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - displayOrderDetail  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
