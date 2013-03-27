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

public class CategoryListValidationTest extends TestCase {

	private Solo soloBrowseValid;
	private static final String TAG = "****BrowseValidationTestCase******";

	private String activityName;
	private ImageView addtocartButton, updatecartButton, viewmycartButton, checkoutButton, browseButton,reviewOrderButton, orderComments;
	private TextView customerInfo, deliveryInfo, billingInfo, paymentMethod;
	private EditText quantityValue;
	private AndroidNativeData data;

	public CategoryListValidationTest(Solo soloBrowseValid) {
		this.soloBrowseValid = soloBrowseValid;
	}

	public void testBrowseValidation(Instrumentation instrumentation) throws TestException {

		try {
			// click on Browse button
			// soloBrowse.clickOnImageButton(1);
			Log.i(TAG, "------It is testBrowseValidation-----------");
            data =new AndroidNativeData();
            data.parser(instrumentation.getContext());
			activityName = soloBrowseValid.getCurrentActivity().getClass().getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloBrowseValid.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloBrowseValid.getCurrentActivity().getClass().getSimpleName();
					Log.i(TAG, "The activity name------>" + activityName);
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop at-----> HomeActivity-----");
						break;
					}

					soloBrowseValid.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testBrowseValidation failed-----------");
				throw new TestException("Current Activity Failed----"
						+ soloBrowseValid.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				Log.i(TAG," Activity name ---->"+soloBrowseValid.getCurrentActivity());
				ArrayList<View> al = soloBrowseValid.getViews();
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
				throw new TestException(TAG + soloBrowseValid.getCurrentActivity().getClass().getSimpleName()
						+ "failed");
			}
			
			// soloBrowse.waitForActivity("HomeActivity", 60000);
			Log.i(TAG, "*** clicking on Browse button*****");
			browseButton = (ImageView) soloBrowseValid.getView(R.id.home_browse_btn);
			soloBrowseValid.clickOnView(browseButton);
			for (int i = 0; i < 30; i++) {
				Log.i(TAG, "**** checking for Television item*****");
				this.activityName = soloBrowseValid.getCurrentActivity().getClass().getSimpleName();
				Log.i(TAG, "The activity name------------>" + this.activityName);
				if (soloBrowseValid.waitForText("Television")) {
					Log.i(TAG, "********Enter into if() condition and clicking on Television******");
					// click on Television
					soloBrowseValid.clickInList(1);
					break;
				}
				Log.i(TAG, "*****waiting for Television item*****");
				soloBrowseValid.waitForText("Television", 1, 2000);
			}

			for (int i = 0; i < 30; i++) {
				this.activityName = soloBrowseValid.getCurrentActivity().getClass().getSimpleName();
				Log.i(TAG, "checking for ***LG Electronics*** in ------------>" + this.activityName);
				if (soloBrowseValid.waitForText("LG Electronics")) {
					Log.i(TAG, "****if() condition and clicking on ****LG Electronics*****Item*****");
					soloBrowseValid.clickOnMenuItem("LG Electronics");
					break;
				} else {
					Log.i(TAG, "waiting for  *****LG Electronics**** item in ------------>" + this.activityName);
					soloBrowseValid.waitForText("LG Electronics", 1, 2000);
				}
			}

			ArrayList<ImageView> al = soloBrowseValid.getCurrentImageViews();
			Iterator<ImageView> it = al.iterator();
			while (it.hasNext()) {
				String imageView = it.next().getClass().getSimpleName();
				Log.i(TAG, "*********ImageView---------->" + imageView);
			}

			for (int i = 0; i < 30; i++) {
				Log.i(TAG, "checking the   ****ADD TO CART***button****");
				// if (soloBrowse.waitForView(this.addtocartButton)) {
				if (soloBrowseValid.waitForText("LG Electronics", 1, 0, false, true)) {
					// if (soloBrowse.waitForText("LG Electronics")) {
					Log.i(TAG, "****if() condition and clicking on ****ADD TO CART**** button*******");
					this.addtocartButton = (ImageView) soloBrowseValid.getView(R.id.add_to_cart);
					// soloBrowse.wait(20000);
					Log.i(TAG, "The ***ADD TO CART VIEW value---------->" + this.addtocartButton);
					soloBrowseValid.clickOnView(this.addtocartButton);
					break;
				} else {
					Log.i(TAG, "Waiting for ****ADD TO CART***button****");
					soloBrowseValid.waitForText("LG Electronics", 1, 2000);
				}
			}

			for (int i = 0; i < 5; i++) {
				Log.i(TAG, "checking the   ****PRODUCT QUANTITY FIELD*******");
				if (soloBrowseValid.waitForActivity("MyCartActivity", 5000)) {
					quantityValue = (EditText) soloBrowseValid.getView(R.id.product_quantity);
					soloBrowseValid.clickOnView(quantityValue);
					soloBrowseValid.clearEditText(quantityValue);
					soloBrowseValid.enterText(quantityValue, "2");
					soloBrowseValid.goBack();
					break;
				} else {
					soloBrowseValid.waitForActivity("MyCartActivity", 5000);
				}

			}
			// Click on UPDATE CART button
			updatecartButton = (ImageView) soloBrowseValid
					.getView(R.id.update_cart_btn);
			soloBrowseValid.waitForActivity("MyCartActivity", 1000);
			soloBrowseValid.clickOnView(updatecartButton);

			// Click on VIEW MY CART button
			viewmycartButton = (ImageView) soloBrowseValid
					.getView(R.id.view_cart_button);
			soloBrowseValid.waitForActivity("MyCartActivity");
			soloBrowseValid.clickOnView(viewmycartButton);
			soloBrowseValid.waitForActivity("ViewMyCartActivity");

			soloBrowseValid.scrollDown();
			// click on CHECKOUT button
			checkoutButton = (ImageView) soloBrowseValid.getView(R.id.checkout_btn);
			soloBrowseValid.clickOnView(checkoutButton);

			soloBrowseValid.waitForActivity("CheckOutActivity", 2000);
			customerInfo = (TextView) soloBrowseValid
					.getView(R.id.customer_info_title);
			soloBrowseValid.clickOnView(customerInfo);
			soloBrowseValid.waitForActivity("CheckOutActivity", 2000);

			deliveryInfo = (TextView) soloBrowseValid.getView(R.id.delivery_info_title);
			soloBrowseValid.clickOnView(deliveryInfo);
			soloBrowseValid.waitForActivity("CheckOutActivity", 2000);
			billingInfo = (TextView) soloBrowseValid.getView(R.id.billing_info_title);
			soloBrowseValid.clickOnView(billingInfo);
			soloBrowseValid.waitForActivity("CheckOutActivity", 2000);
			paymentMethod = (TextView) soloBrowseValid.getView(R.id.payment_method_title);
			soloBrowseValid.clickOnView(paymentMethod);
			soloBrowseValid.waitForActivity("CheckOutActivity", 2000);
			orderComments = (ImageView) soloBrowseValid.getView(R.id.order_comment_arrow_img);
			soloBrowseValid.clickOnView(orderComments);
			reviewOrderButton = (ImageView) soloBrowseValid.getView(R.id.review_order_btn);
			soloBrowseValid.clickOnView(reviewOrderButton);
			if (soloBrowseValid.searchText(data.CONFIRM_CATEGORY_DIALOG)) {
				soloBrowseValid.sleep(1000);
			} else {
				throw new TestException(soloBrowseValid.getCurrentActivity().getClass().getSimpleName() + TAG
						+ "failed");
			}

		} catch (TestException e) {
			Log.e(TAG,Log.getStackTraceString(e));

		}

	}
}