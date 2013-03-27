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
/**
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.nativeapp.functional.test.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import android.app.Instrumentation;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.functional.test.core.*;


/**
 *
 * This testcase for phresco shopping scenario.Once enter into Browse will see
 * list of categories and once we choosed it will take you for different models
 * in the screen then will proceed the order for any thing
 *
 *
 */
public class CategoryListVerificationTest extends TestCase {

	private Solo soloBrowse;
	private String activityName;
	private AndroidNativeData data;
	private  UserInfoConstants info;
	private ImageView backButton, addtocartButton, updatecartButton, viewmycartButton, checkoutButton, browseButton,
			submitorderButton, reviewOrderButton;
	private EditText firstName, address1, cityName, zipcode, phoneNumber, billingFirst, billingCompany,
			billingAddress1, billingCity, billingZip, quantityValue, billingLast;
	private TextView customerInfo;
	private static final String TAG = "*******BrowseVerificationTestCase****";

	public CategoryListVerificationTest(Solo soloTest) {
		this.soloBrowse = soloTest;
	}

	/**
	 * Once the setUp() method executed then this method will be called from
	 * MainTestCase class
	 *
	 * @throws TestException
	 *
	 */
	public void testBrowseVerification(Instrumentation instrumentation) throws TestException {

		try {
			// click on Browse button
			// soloBrowse.clickOnImageButton(1);
			Log.i(TAG, "------It is testBrowseVerification()-----------");
			data =new AndroidNativeData();
            data.parser(instrumentation.getContext());
            info = new UserInfoConstants();
//            info.parser(instrumentation);
            info.parser(instrumentation.getContext());
			this.activityName = soloBrowse.getCurrentActivity().getClass().getSimpleName();

			if (this.activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------Activity name----------->" + this.activityName);
				soloBrowse.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloBrowse.getCurrentActivity().getClass().getSimpleName();
					Log.i(TAG, "------Activity name----------->" + this.activityName);
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------if()-- condition-----");

						break;
					}
					Log.i(TAG, "------Activity name----------->" + this.activityName);
					soloBrowse.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testBrowseVerification failed-----------");
				throw new TestException("Current Activity Failed----"
						+ soloBrowse.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				Log.i(TAG," Activity name ---->"+soloBrowse.getCurrentActivity());
				ArrayList<View> al = soloBrowse.getViews();
				Iterator<View> it = al.iterator();
				while (it.hasNext()) {
					String viewName = it.next().getClass().getSimpleName();
					if (viewName.equalsIgnoreCase("ImageView")) {
						Log.i(TAG, "------ImageView found-----------");
						break;
					}
					continue;
				}

			} else {
				Log.i(TAG, "------HomeActivity not  found-----------");
				throw new TestException(TAG + soloBrowse.getCurrentActivity().getClass().getSimpleName() + "failed");
			}
			// soloBrowse.waitForActivity("HomeActivity", 60000);
			Log.i(TAG, "*** clicking on Browse button*****");
			browseButton = (ImageView) soloBrowse.getView(R.id.home_browse_btn);
			soloBrowse.clickOnView(browseButton);
			for (int i = 0; i < 20; i++) {
				Log.i(TAG, "**** checking for Television item*****");
				this.activityName = soloBrowse.getCurrentActivity().getClass().getSimpleName();
				Log.i(TAG, "The activity name------------>" + this.activityName);
				if (soloBrowse.waitForText("Television")) {
					Log.i(TAG, "********Enter into if() condition and clicking on Television******");
					// click on Television
					soloBrowse.clickInList(1);
					break;
				}
				Log.i(TAG, "*****waiting for Television item*****");
				soloBrowse.waitForText("Television", 1, 2000);
			}

			for (int i = 0; i < 20; i++) {
				this.activityName = soloBrowse.getCurrentActivity().getClass().getSimpleName();
				Log.i(TAG, "checking for ***LG Electronics*** in ------------>" + this.activityName);
				if (soloBrowse.waitForText("LG Electronics")) {
					Log.i(TAG, "****if() condition and clicking on ****LG Electronics*****Item*****");
					soloBrowse.clickOnMenuItem("LG Electronics");
					break;
				} else {
					Log.i(TAG, "waiting for  *****LG Electronics**** item in ------------>" + this.activityName);
					soloBrowse.waitForText("LG Electronics", 1, 2000);
				}
			}

			ArrayList<ImageView> al = soloBrowse.getCurrentImageViews();
			Iterator<ImageView> it = al.iterator();
			while (it.hasNext()) {
				String imageView = it.next().getClass().getSimpleName();
				Log.i(TAG, "*********ImageView---------->" + imageView);
			}

			for (int i = 0; i < 30; i++) {
				Log.i(TAG, "checking the   ****ADD TO CART***button****");
				// if (soloBrowse.waitForView(this.addtocartButton)) {
				if (soloBrowse.waitForText("LG Electronics", 1, 0, false, true)) {
					// if (soloBrowse.waitForText("LG Electronics")) {
					Log.i(TAG, "****if() condition and clicking on ****ADD TO CART**** button*******");
					this.addtocartButton = (ImageView) soloBrowse.getView(R.id.add_to_cart);
					// soloBrowse.wait(20000);
					Log.i(TAG, "The ***ADD TO CART VIEW value---------->" + this.addtocartButton);
					soloBrowse.clickOnView(this.addtocartButton);
					break;
				} else {
					Log.i(TAG, "Waiting for ****ADD TO CART***button****");
					soloBrowse.waitForText("LG Electronics", 1, 2000);
				}
			}

			for (int i = 0; i < 5; i++) {
				Log.i(TAG, "checking the   ****PRODUCT QUANTITY FIELD*******");
				if (soloBrowse.waitForActivity("MyCartActivity", 5000)) {
					quantityValue = (EditText) soloBrowse.getView(R.id.product_quantity);
					soloBrowse.clickOnView(quantityValue);
					soloBrowse.clearEditText(quantityValue);
					soloBrowse.enterText(quantityValue, "2");
					soloBrowse.goBack();
					break;
				} else {
					soloBrowse.waitForActivity("MyCartActivity", 5000);
				}

			}
			// Click on UPDATE CART button
			updatecartButton = (ImageView) soloBrowse.getView(R.id.update_cart_btn);
			soloBrowse.waitForActivity("MyCartActivity", 1000);
			soloBrowse.clickOnView(updatecartButton);

			// Click on VIEW MY CART button
			viewmycartButton = (ImageView) soloBrowse.getView(R.id.view_cart_button);
			soloBrowse.waitForActivity("MyCartActivity");
			soloBrowse.clickOnView(viewmycartButton);
			soloBrowse.waitForActivity("ViewMyCartActivity");

			soloBrowse.scrollDown();
			// click on CHECKOUT button
			checkoutButton = (ImageView) soloBrowse.getView(R.id.checkout_btn);
			soloBrowse.clickOnView(checkoutButton);

			soloBrowse.waitForActivity("CheckOutActivity");
			customerInfo = (TextView) soloBrowse.getView(R.id.customer_info_title);
			soloBrowse.clickOnView(customerInfo);
			soloBrowse.waitForActivity("CheckOutActivity", 2000);

			firstName = (EditText) soloBrowse.getView(R.id.delivery_first_name);
			soloBrowse.clickOnView(firstName);
			soloBrowse.clearEditText(firstName);
			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			soloBrowse.enterText(firstName, data.DELIVERY_FIRSTNAME);
			soloBrowse.goBack();
			soloBrowse.waitForActivity("MainActivity", 2000);


			address1 = (EditText) soloBrowse.getView(R.id.delivery_address1);
			soloBrowse.clickOnView(address1);
			soloBrowse.clearEditText(address1);
			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			soloBrowse.enterText(address1, data.DELIVERY_ADDRESS1);
			soloBrowse.goBack();
			soloBrowse.waitForActivity("CheckOutActivity", 1000);


			cityName = (EditText) soloBrowse.getView(R.id.delivery_city);
			soloBrowse.clickOnView(cityName);
			soloBrowse.clearEditText(cityName);
			soloBrowse.waitForActivity("SplashActivity", 1000);
			soloBrowse.enterText(cityName, data.DELIVERY_CITY);
			soloBrowse.goBack();
			soloBrowse.waitForActivity("CheckOutActivity", 2000);

			
			 soloBrowse.pressSpinnerItem(0, 2);
			 soloBrowse.waitForActivity("CheckOutActivity", 4000);
			 soloBrowse.pressSpinnerItem(1, 0);
			 

			
			/* * deliveryState = (Spinner)
			 * soloBrowse.getView(R.id.delivery_state);
			 * soloBrowse.clickOnView(deliveryState); soloBrowse.sleep(1000);
			 * soloBrowse.clickInList(3); soloBrowse.sleep(1000);
			 */

			zipcode = (EditText) soloBrowse.getView(R.id.delivery_zipcode);
			soloBrowse.clickOnView(zipcode);
			soloBrowse.clearEditText(zipcode);
			soloBrowse.enterText(zipcode, data.DELIVERY_ZIPCODE);
			soloBrowse.goBack();
			soloBrowse.waitForActivity("CheckOutActivity", 1000);

			phoneNumber = (EditText) soloBrowse.getView(R.id.delivery_phone);
			soloBrowse.clickOnView(phoneNumber);
			soloBrowse.clearEditText(phoneNumber);
			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			soloBrowse.enterText(phoneNumber, data.DELIVERY_PHONENUMBER);
			soloBrowse.goBack();
			soloBrowse.waitForActivity("CheckOutActivity", 1000);

			TextView title = (TextView) soloBrowse.getView(R.id.billing_info_title);
			soloBrowse.clickOnView(title);
			soloBrowse.waitForActivity("CheckOutActivity", 2000);
			soloBrowse.clickOnView(title);

			// soloBrowse.scrollDown();

			// click on REVIEWORDER

			billingFirst = (EditText) soloBrowse.getView(R.id.billing_first_name);
			soloBrowse.clickOnView(billingFirst);
			soloBrowse.clearEditText(billingFirst);
			soloBrowse.enterText(billingFirst, data.BILLING_FIRSTNAME);
			soloBrowse.goBack();
			soloBrowse.waitForActivity("MyCartActivity", 1000);

			billingLast = (EditText) soloBrowse.getView(R.id.billing_last_name);
			soloBrowse.clickOnView(billingLast);
			soloBrowse.clearEditText(billingLast);
			soloBrowse.enterText(billingLast,data.BILLING_LASTNAME);
			soloBrowse.goBack();

			billingCompany = (EditText) soloBrowse.getView(R.id.billing_company);
			soloBrowse.clickOnView(billingCompany);
			soloBrowse.clearEditText(billingCompany);
			soloBrowse.enterText(billingCompany, data.BILLING_COMPANY);
			soloBrowse.goBack();

			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			billingAddress1 = (EditText) soloBrowse.getView(R.id.billing_address1);
			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			soloBrowse.clickOnView(billingAddress1);
			soloBrowse.clearEditText(billingAddress1);
			soloBrowse.enterText(billingAddress1, data.BILLING_ADDRESS1);
			soloBrowse.goBack();

			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			billingCity = (EditText) soloBrowse.getView(R.id.billing_city);
			soloBrowse.clickOnView(billingCity);
			soloBrowse.clearEditText(billingCity);
			soloBrowse.enterText(billingCity, data.BILLING_CITY);
			soloBrowse.goBack();

			billingZip = (EditText) soloBrowse.getView(R.id.billing_zipcode);
			soloBrowse.clickOnView(billingZip);
			soloBrowse.clearEditText(billingZip);
			soloBrowse.enterText(billingZip, data.BILLING_ZIP);
			soloBrowse.goBack();

			soloBrowse.scrollDown();
			soloBrowse.sleep(1000);
			soloBrowse.scrollDown();
			soloBrowse.sleep(1000);

			reviewOrderButton = (ImageView) soloBrowse
					.getView(R.id.review_order_btn);
			soloBrowse.waitForActivity("CheckOutActivity", 1000);
			soloBrowse.clickOnView(reviewOrderButton);
			soloBrowse.waitForActivity("CheckOutActivity", 5000);
			// soloBrowse.clickOnImage(9);
			soloBrowse.scrollDown();
			soloBrowse.scrollDown();
			soloBrowse.scrollDown();
			soloBrowse.scrollDown();
			soloBrowse.waitForActivity("CheckOutActivity", 2000);
			// click on SUBMIT ORDER;
			submitorderButton = (ImageView) soloBrowse
					.getView(R.id.submit_order_btn);
			soloBrowse.clickOnView(submitorderButton);

			// check the text present
			for (int i = 0; i < 20; i++) {
				Log.i(TAG, "---for()---- Loop");
				if (soloBrowse.searchText("Order Status")) {
					// soloBrowse.wait(3000);

					backButton = (ImageView) soloBrowse.getView(R.id.back_btn);
					soloBrowse.clickOnView(backButton);
					Log.i(TAG, "*******clicking on Back button*********");
					soloBrowse.waitForActivity("CategoryListActivity", 2000);
					// soloBrowse.wait(1000);
					soloBrowse.clickOnView(backButton);
					soloBrowse.waitForActivity("HomeActivity");
					break;
				}
				soloBrowse.waitForActivity("OrderStatusActivity", 1000);
			}


		}

		catch (TestException e) {
			Log.e(TAG,Log.getStackTraceString(e));

		}

	}
}
