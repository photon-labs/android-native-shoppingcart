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
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.dialog.CustomEmailDialogActivity;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.customer.Address;
import com.photon.phresco.nativeapp.eshop.model.customer.Customer;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCart;
import com.photon.phresco.nativeapp.eshop.model.order.Order;

/**
 * Show check out screen
 *
 * @author viral_b
 *
 */
public class CheckOutActivity extends PhrescoActivity {

	private static final String TAG = "CheckOutActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton;
	private ImageButton orderReviewButton, cancelButton;

	private RelativeLayout customerInfoTab, deliveryInfoTab, billingInfoTab, paymentMethodTab, orderCommentsTab;
	private ViewStub customerInfoStub, deliveryInfoStub, billingInfoStub, paymentMethodStub, orderCommentsStub;

	private ImageView customerInfoArrowImage, deliveryInfoArrowImage;
	private ImageView billingInfoArrowImage, paymentMethodArrowImage, orderCommentsArrowImage;

	private ImageButton editEmailButton;
	private TextView email;
	private CheckBox chkBillingAddress;

	/*
	 * boolean variables to hold the visibility value for different sections
	 */
	private boolean isCustomerInfoVisible = true;
	private boolean isDeliveryInfoVisible = true;
	private boolean isBillingInfoVisible = true;
	private boolean isPaymentMethodVisible = true;
	private boolean isorderCommentsVisible = true;

	private String currActivity = "currentActivity";
	private String myCartActivity = "mycart";

	private static Order orderInfo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout_home);

		// Init all the controls
		initEnvironment();

		// If we are coming back from order review screen, keep all the
		// values filled in different sections
		if (orderInfo != null) {
			displayOrderDetail();
		} else {
			displayOrderTotalPrice();
		}

		editEmailButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startCustomEmailDialogActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - editEmailButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		chkBillingAddress.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					copyDeliveryAddressToBillingAddress();
				} else {
					clearBillingAddress();
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

		customerInfoTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					toggleCustomerInfo();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - customerInfoTab  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		deliveryInfoTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					toggleDeliveryInfo();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - deliveryInfoTab  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		billingInfoTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					toggleBillingInfo();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - billingInfoTab  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		paymentMethodTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					togglePaymentMethodInfo();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - paymentMethodTab  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}

		});

		orderCommentsTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					toggleOrderCommentsInfo();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - orderCommentsTab  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startMyCartActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - cancelButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		orderReviewButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					orderInfo = createOrderObject();
					if (isValid()) {
						showOrderReviewActivity();
					} else {
						toast("Please fill required fields");
					}
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - orderReviewButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
	}

	/**
	 * start custom email dialog activity
	 */
	private void startCustomEmailDialogActivity() {
		try {
			Intent customEmailDialogActivity = new Intent(getApplicationContext(), CustomEmailDialogActivity.class);
			customEmailDialogActivity.putExtra("currentEmailId", email.getText().toString());
			startActivityForResult(customEmailDialogActivity, 1);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCustomEmailDialogActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start the category list activity
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
	 * Move back to previous activity depending upon the size of MyCart If, cart
	 * contains products, go back to my cart screen, else go to category screen
	 */
	private void goBackToPreviousActivity() {
		try {
			Intent myActivity = null;
			if (MyCart.size() > 0) {
				myActivity = new Intent(getApplicationContext(), MyCartActivity.class);
				myActivity.putExtra(currActivity, myCartActivity);
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
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);

			browseButton = (ImageButton) findViewById(R.id.tab_browse);
			offersButton = (ImageButton) findViewById(R.id.tab_specialoffer);
			myCartButton = (ImageButton) findViewById(R.id.tab_mycart);

			orderReviewButton = (ImageButton) findViewById(R.id.review_order_btn);
			cancelButton = (ImageButton) findViewById(R.id.cancel_btn);

			customerInfoTab = (RelativeLayout) findViewById(R.id.customer_info_tab);
			customerInfoStub = (ViewStub) findViewById(R.id.customer_info);
			customerInfoArrowImage = (ImageView) findViewById(R.id.customer_info_arrow_img);

			deliveryInfoTab = (RelativeLayout) findViewById(R.id.delivery_info_tab);
			deliveryInfoStub = (ViewStub) findViewById(R.id.delivery_info);
			deliveryInfoArrowImage = (ImageView) findViewById(R.id.delivery_info_arrow_img);

			billingInfoTab = (RelativeLayout) findViewById(R.id.billing_info_tab);
			billingInfoStub = (ViewStub) findViewById(R.id.billing_info);
			billingInfoArrowImage = (ImageView) findViewById(R.id.billing_info_arrow_img);

			paymentMethodTab = (RelativeLayout) findViewById(R.id.payment_method_tab);
			paymentMethodStub = (ViewStub) findViewById(R.id.payment_method);
			paymentMethodArrowImage = (ImageView) findViewById(R.id.payment_method_arrow_img);

			orderCommentsTab = (RelativeLayout) findViewById(R.id.order_comment_tab);
			orderCommentsStub = (ViewStub) findViewById(R.id.order_comment);
			orderCommentsArrowImage = (ImageView) findViewById(R.id.order_comment_arrow_img);

			browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			myCartButton.setBackgroundResource(R.drawable.mycart_tab_selected);

			customerInfoStub.setVisibility(View.VISIBLE);
			deliveryInfoStub.setVisibility(View.VISIBLE);
			billingInfoStub.setVisibility(View.VISIBLE);
			paymentMethodStub.setVisibility(View.VISIBLE);
			orderCommentsStub.setVisibility(View.VISIBLE);

			editEmailButton = (ImageButton) findViewById(R.id.edit_email_btn);
			email = (TextView) findViewById(R.id.txt_email);

			chkBillingAddress = (CheckBox) findViewById(R.id.chk_billing_adderss);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Toggle the customer information tab
	 */
	private void toggleCustomerInfo() {
		if (!isCustomerInfoVisible) {
			customerInfoStub.setVisibility(View.VISIBLE);
			customerInfoArrowImage.setBackgroundResource(R.drawable.down_arrow);
			editEmailButton = (ImageButton) findViewById(R.id.edit_email_btn);
			isCustomerInfoVisible = true;
			editEmailButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					openCustomEmailDialog();
				}
			});

		} else {
			customerInfoStub.setVisibility(View.GONE);
			customerInfoArrowImage.setBackgroundResource(R.drawable.arrow_icon);
			isCustomerInfoVisible = false;
		}
	}

	/**
	 * Toggle the delivery information tab
	 */
	private void toggleDeliveryInfo() {
		if (!isDeliveryInfoVisible) {
			deliveryInfoStub.setVisibility(View.VISIBLE);
			deliveryInfoArrowImage.setBackgroundResource(R.drawable.down_arrow);
			isDeliveryInfoVisible = true;
		} else {
			deliveryInfoStub.setVisibility(View.GONE);
			deliveryInfoArrowImage.setBackgroundResource(R.drawable.arrow_icon);
			isDeliveryInfoVisible = false;
		}
	}

	/**
	 * Toggle the billing information tab
	 */
	private void toggleBillingInfo() {
		if (!isBillingInfoVisible) {
			billingInfoStub.setVisibility(View.VISIBLE);
			billingInfoArrowImage.setBackgroundResource(R.drawable.down_arrow);
			isBillingInfoVisible = true;
			chkBillingAddress = (CheckBox) findViewById(R.id.chk_billing_adderss);
			chkBillingAddress.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						copyDeliveryAddressToBillingAddress();
					} else {
						clearBillingAddress();
					}
				}
			});

		} else {
			billingInfoStub.setVisibility(View.GONE);
			billingInfoArrowImage.setBackgroundResource(R.drawable.arrow_icon);
			isBillingInfoVisible = false;
		}
	}

	/**
	 * Toggle the payment method information tab
	 */
	private void togglePaymentMethodInfo() {
		if (!isPaymentMethodVisible) {
			paymentMethodStub.setVisibility(View.VISIBLE);
			paymentMethodArrowImage.setBackgroundResource(R.drawable.down_arrow);
			isPaymentMethodVisible = true;
		} else {
			paymentMethodStub.setVisibility(View.GONE);
			paymentMethodArrowImage.setBackgroundResource(R.drawable.arrow_icon);
			isPaymentMethodVisible = false;
		}
	}

	/**
	 * Toggle the order comments tab
	 */
	private void toggleOrderCommentsInfo() {
		if (!isorderCommentsVisible) {
			orderCommentsStub.setVisibility(View.VISIBLE);
			orderCommentsArrowImage.setBackgroundResource(R.drawable.down_arrow);
			isorderCommentsVisible = true;
		} else {
			orderCommentsStub.setVisibility(View.GONE);
			orderCommentsArrowImage.setBackgroundResource(R.drawable.arrow_icon);
			isorderCommentsVisible = false;
		}
	}

	/**
	 * Open the custom email dialog box, when Email button is clicked under
	 * Customer Information section
	 */
	private void openCustomEmailDialog() {
		try {
			Intent customEmailDialogActivity = new Intent(getApplicationContext(), CustomEmailDialogActivity.class);
			customEmailDialogActivity.putExtra("currentEmailId", ((TextView) findViewById(R.id.txt_email)).getText().toString());
			startActivityForResult(customEmailDialogActivity, 1);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - openCustomEmailDialog  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the order review screen, when the Order Review button is clicked
	 */
	private void showOrderReviewActivity() {
		try {
			Intent orderReviewActivity = new Intent(getApplicationContext(), OrderReviewActivity.class);
			orderReviewActivity.putExtra(currActivity, myCartActivity);
			orderReviewActivity.putExtra("orderObj", orderInfo);
			startActivity(orderReviewActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - showOrderReviewActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the order object with all the details filled up on checkout screen
	 *
	 * @return
	 */
	private Order createOrderObject() {
		Order order = null;
		try {

			// PhrescoLogger.info(TAG + " - createOrderObject - START : ");
			order = new Order();
			order.setCustomerInfo(getCustomerInfo());
			order.setComments(((EditText) findViewById(R.id.txt_order_comments)).getText().toString());
			RadioGroup rgPayment = (RadioGroup) findViewById(R.id.radio_grp_payment_method);
			RadioButton rbPayment = (RadioButton) findViewById(rgPayment.getCheckedRadioButtonId());
			order.setPaymentMethod(rbPayment.getText().toString());
			order.setTotalPrice(MyCart.getTotalPrice());
			order.setProducts(MyCart.getMyCart());
			// PhrescoLogger.info(TAG + " - createOrderObject -  END : ");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - createOrderObject  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return order;
	}

	/**
	 * Create the customer object with all the details filled up on checkout
	 * screen
	 *
	 * @return
	 */
	private Customer getCustomerInfo() {
		Customer customer = null;
		try {
			// PhrescoLogger.info(TAG + " - getCustomerInfo - START : ");
			customer = new Customer();
			customer.setEmailID(((TextView) findViewById(R.id.txt_email)).getText().toString());

			PhrescoLogger.info(TAG + "Email Id = " + ((TextView) findViewById(R.id.txt_email)).getText().toString());

			customer.setDeliveryAddress(getDeliveryAddress());
			customer.setBillingAddress(getBillingAddress());
			// PhrescoLogger.info(TAG + " - getCustomerInfo - END : ");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getCustomerInfo  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return customer;
	}

	/**
	 * Get the delivery address information
	 *
	 * @return
	 */
	private Address getDeliveryAddress() {
		Address address = null;

		try {
			// PhrescoLogger.info(TAG + " - getDeliveryAddress - START : ");
			address = new Address();

			address.setFirstName(((EditText) findViewById(R.id.delivery_first_name)).getText().toString());

			address.setLastName(((EditText) findViewById(R.id.delivery_last_name)).getText().toString());
			address.setCompany(((EditText) findViewById(R.id.delivery_company)).getText().toString());

			address.setAddressOne(((EditText) findViewById(R.id.delivery_address1)).getText().toString());

			address.setAddressTwo(((EditText) findViewById(R.id.delivery_address2)).getText().toString());

			address.setCity(((EditText) findViewById(R.id.delivery_city)).getText().toString());

			address.setState(((Spinner) findViewById(R.id.delivery_state)).getSelectedItem().toString());

			address.setCountry(((Spinner) findViewById(R.id.delivery_country)).getSelectedItem().toString());

			address.setZipCode(((EditText) findViewById(R.id.delivery_zipcode)).getText().toString());

			address.setPhoneNumber(((EditText) findViewById(R.id.delivery_phone)).getText().toString());

			// PhrescoLogger.info(TAG + " - getDeliveryAddress - END : ");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getDeliveryAddress  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		return address;
	}

	/**
	 * Get the billing address information
	 *
	 * @return
	 */
	private Address getBillingAddress() {
		Address address = null;

		try {
			// PhrescoLogger.info(TAG + " - getBillingAddress - START : ");

			address = new Address();

			address.setFirstName(((EditText) findViewById(R.id.billing_first_name)).getText().toString());

			address.setLastName(((EditText) findViewById(R.id.billing_last_name)).getText().toString());
			address.setCompany(((EditText) findViewById(R.id.billing_company)).getText().toString());

			address.setAddressOne(((EditText) findViewById(R.id.billing_address1)).getText().toString());

			address.setAddressTwo(((EditText) findViewById(R.id.billing_address2)).getText().toString());
			address.setCity(((EditText) findViewById(R.id.billing_city)).getText().toString());

			address.setState(((Spinner) findViewById(R.id.billing_state)).getSelectedItem().toString());

			address.setCountry(((Spinner) findViewById(R.id.billing_country)).getSelectedItem().toString());

			address.setZipCode(((EditText) findViewById(R.id.billing_zipcode)).getText().toString());

			address.setPhoneNumber(((EditText) findViewById(R.id.billing_phone)).getText().toString());

			// PhrescoLogger.info(TAG + " - getBillingAddress - END : ");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getBillingAddress  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		return address;
	}

	/**
	 * Validate all the required fields on screen
	 *
	 * @return boolean
	 */
	private boolean isValid() {
		boolean isValidFlag = true;

		try {
			// Validation For Delivery Address and Billing Address
			if (isDeliveryInfoValid() && isBillingInfoValid()) {
				isValidFlag = true;
			} else {
				isValidFlag = false;
			}

		} catch (Exception ex) {
			isValidFlag = false;
			PhrescoLogger.info(TAG + " - isValid  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return isValidFlag;
	}

	/**
	 * Check if all the mandatory delivery informations are filled up
	 *
	 * @param isValidFlag
	 * @return boolean
	 */
	private boolean isDeliveryInfoValid() {
		boolean isValidFlag = true;
		Address addressInfo = new Address();

		if (addressInfo.isEmpty(((EditText) findViewById(R.id.delivery_first_name)).getText().toString())) {
			((EditText) findViewById(R.id.delivery_first_name)).setError(getString(R.string.first_name_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((EditText) findViewById(R.id.delivery_address1)).getText().toString())) {
			((EditText) findViewById(R.id.delivery_address1)).setError(getString(R.string.address_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((EditText) findViewById(R.id.delivery_city)).getText().toString())) {
			((EditText) findViewById(R.id.delivery_city)).setError(getString(R.string.city_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((Spinner) findViewById(R.id.delivery_state)).getSelectedItem().toString())) {
			toast(getString(R.string.state_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((Spinner) findViewById(R.id.delivery_country)).getSelectedItem().toString())) {
			toast(getString(R.string.country_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((EditText) findViewById(R.id.delivery_zipcode)).getText().toString())) {
			((EditText) findViewById(R.id.delivery_zipcode)).setError(getString(R.string.zipcode_required));
			isValidFlag = false;
		}
		return isValidFlag;
	}

	/**
	 * Check if all the mandatory billing informations are filled up
	 *
	 * @param isValidFlag
	 * @return boolean
	 */
	private boolean isBillingInfoValid() {
		boolean isValidFlag = true;
		Address addressInfo = new Address();
		if (addressInfo.isEmpty(((EditText) findViewById(R.id.billing_first_name)).getText().toString())) {
			((EditText) findViewById(R.id.billing_first_name)).setError(getString(R.string.first_name_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((EditText) findViewById(R.id.billing_address1)).getText().toString())) {
			((EditText) findViewById(R.id.billing_address1)).setError(getString(R.string.address_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((EditText) findViewById(R.id.billing_city)).getText().toString())) {
			((EditText) findViewById(R.id.billing_city)).setError(getString(R.string.city_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((Spinner) findViewById(R.id.billing_state)).getSelectedItem().toString())) {
			toast(getString(R.string.state_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((Spinner) findViewById(R.id.billing_country)).getSelectedItem().toString())) {
			toast(getString(R.string.country_required));
			isValidFlag = false;
		} else if (addressInfo.isEmpty(((EditText) findViewById(R.id.billing_zipcode)).getText().toString())) {
			((EditText) findViewById(R.id.billing_zipcode)).setError(getString(R.string.zipcode_required));
			isValidFlag = false;
		}
		return isValidFlag;
	}

	/**
	 * Show the total amount of cart, when Payment Method tab is tapped on
	 * screen
	 */
	public void displayOrderTotalPrice() {
		try {

			((TextView) findViewById(R.id.lbl_currency_subtotal)).setText(Constants.getCurrency());
			((TextView) findViewById(R.id.sub_total)).setText(String.valueOf(MyCart.getTotalPrice()));

			((TextView) findViewById(R.id.lbl_currency_ordertotal)).setText(Constants.getCurrency());
			((TextView) findViewById(R.id.order_total)).setText(String.valueOf(MyCart.getTotalPrice()));

			PhrescoLogger.info(TAG + " - displayOrderTotalPrice  - : " + MyCart.getTotalPrice());

		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - displayOrderTotalPrice  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Copy the delivery address information into billing address fields
	 */
	private void copyDeliveryAddressToBillingAddress() {

		PhrescoLogger.info(TAG + " - copyDeliveryAddressToBillingAddress()  ");

		try {
			((EditText) findViewById(R.id.billing_first_name)).setText(((EditText) findViewById(R.id.delivery_first_name)).getText().toString());

			((EditText) findViewById(R.id.billing_last_name)).setText(((EditText) findViewById(R.id.delivery_last_name)).getText().toString());

			((EditText) findViewById(R.id.billing_company)).setText(((EditText) findViewById(R.id.delivery_company)).getText().toString());

			((EditText) findViewById(R.id.billing_address1)).setText(((EditText) findViewById(R.id.delivery_address1)).getText().toString());

			((EditText) findViewById(R.id.billing_address2)).setText(((EditText) findViewById(R.id.delivery_address2)).getText().toString());

			((EditText) findViewById(R.id.billing_city)).setText(((EditText) findViewById(R.id.delivery_city)).getText().toString());

			((Spinner) findViewById(R.id.billing_state)).setSelection(((Spinner) findViewById(R.id.delivery_state)).getSelectedItemPosition());

			((Spinner) findViewById(R.id.billing_country)).setSelection(((Spinner) findViewById(R.id.delivery_country)).getSelectedItemPosition());

			((EditText) findViewById(R.id.billing_zipcode)).setText(((EditText) findViewById(R.id.delivery_zipcode)).getText().toString());

			((EditText) findViewById(R.id.billing_phone)).setText(((EditText) findViewById(R.id.delivery_phone)).getText().toString());

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - copyDeliveryAddressToBillingAddress  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Clear all the values from billing address fields
	 */
	private void clearBillingAddress() {
		PhrescoLogger.info(TAG + " - clearBillingAddress()  ");
		try {
			//((Spinner) findViewById(R.id.billing_saved_address)).setSelection(0);
			((EditText) findViewById(R.id.billing_first_name)).setText("");
			((EditText) findViewById(R.id.billing_last_name)).setText("");
			((EditText) findViewById(R.id.billing_company)).setText("");
			((EditText) findViewById(R.id.billing_address1)).setText("");
			((EditText) findViewById(R.id.billing_address2)).setText("");
			((EditText) findViewById(R.id.billing_city)).setText("");
			((Spinner) findViewById(R.id.billing_state)).setSelection(0);
			((Spinner) findViewById(R.id.billing_country)).setSelection(0);
			((EditText) findViewById(R.id.billing_zipcode)).setText("");
			((EditText) findViewById(R.id.billing_phone)).setText("");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - clearBillingAddress  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			PhrescoLogger.info(TAG + " requestCode: " + requestCode);
			PhrescoLogger.info(TAG + " resultCode: " + resultCode);
			String emailId = null;
			if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
				emailId = data.getStringExtra("updatedEmailId");
				PhrescoLogger.info(TAG + " currentEmailId: " + emailId);
				((TextView) findViewById(R.id.txt_email)).setText(emailId);
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onActivityResult  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the current order information
	 */
	private void displayOrderDetail() {
		PhrescoLogger.info(TAG + " - displayOrderDetail  ");
		try {
			((TextView) findViewById(R.id.txt_email)).setText(orderInfo.getCustomerInfo().getEmailID());

			// Data For Delivery Address
			PhrescoLogger.info(TAG + " - Setting Delivery Address  ");

			((EditText) findViewById(R.id.delivery_first_name)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getFirstName());
			((EditText) findViewById(R.id.delivery_last_name)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getLastName());
			((EditText) findViewById(R.id.delivery_company)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getCompany());
			((EditText) findViewById(R.id.delivery_address1)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getAddressOne());
			((EditText) findViewById(R.id.delivery_address2)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getAddressTwo());
			((EditText) findViewById(R.id.delivery_city)).setText(orderInfo.getCustomerInfo().getDeliveryAddress().getCity());

			// ((Spinner)
			// findViewById(R.id.delivery_state)).setSelection(orderInfo.getCustomerInfo().getDeliveryAddress().getState());
			// ((Spinner)
			// findViewById(R.id.delivery_country)).setSelection(orderInfo.getCustomerInfo().getDeliveryAddress().getConuntry());

			((EditText) findViewById(R.id.delivery_zipcode)).setText(String.valueOf(orderInfo.getCustomerInfo().getDeliveryAddress().getZipCode()));
			((EditText) findViewById(R.id.delivery_phone)).setText(String.valueOf(orderInfo.getCustomerInfo().getDeliveryAddress().getPhoneNumber()));

			// Data For Billing Address
			PhrescoLogger.info(TAG + " - Setting Billing Address  ");

			((EditText) findViewById(R.id.billing_first_name)).setText(orderInfo.getCustomerInfo().getBillingAddress().getFirstName());
			((EditText) findViewById(R.id.billing_last_name)).setText(orderInfo.getCustomerInfo().getBillingAddress().getLastName());
			((EditText) findViewById(R.id.billing_company)).setText(orderInfo.getCustomerInfo().getBillingAddress().getCompany());
			((EditText) findViewById(R.id.billing_address1)).setText(orderInfo.getCustomerInfo().getBillingAddress().getAddressOne());
			((EditText) findViewById(R.id.billing_address2)).setText(orderInfo.getCustomerInfo().getBillingAddress().getAddressTwo());
			((EditText) findViewById(R.id.billing_city)).setText(orderInfo.getCustomerInfo().getBillingAddress().getCity());

			// ((Spinner)
			// findViewById(R.id.billing_state)).setText(orderInfo.getCustomerInfo().getBillingAddress().getState());
			// ((Spinner)
			// findViewById(R.id.billing_country)).setText(orderInfo.getCustomerInfo().getBillingAddress().getConuntry());

			((EditText) findViewById(R.id.billing_zipcode)).setText(String.valueOf(orderInfo.getCustomerInfo().getBillingAddress().getZipCode()));
			((EditText) findViewById(R.id.billing_phone)).setText(String.valueOf(orderInfo.getCustomerInfo().getBillingAddress().getPhoneNumber()));

			// Payment method
			((TextView) findViewById(R.id.lbl_currency_subtotal)).setText(Constants.getCurrency());
			((TextView) findViewById(R.id.sub_total)).setText(String.valueOf(MyCart.getTotalPrice()));

			((TextView) findViewById(R.id.lbl_currency_ordertotal)).setText(Constants.getCurrency());
			((TextView) findViewById(R.id.order_total)).setText(String.valueOf(MyCart.getTotalPrice()));

			// Order comments
			((EditText) findViewById(R.id.txt_order_comments)).setText(orderInfo.getComments());
		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - displayOrderDetail  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

}
