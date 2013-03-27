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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.functional.test.core.*;

public class OffersTest extends TestCase {

	private Solo specialOffers;
	private String activityName;
	private static final String TAG = "****SpecialOffersTestCase*****";
	private ImageView specialOfferButton, reviewButton, cancelButton;
	private ImageButton submitReviewButton;
	private RatingBar ratingBar;
	private EditText userComment;
	private ImageButton productSubmitReview, dialogOK;
	private AndroidNativeData data;
	public OffersTest(Solo specialOffers) {
		this.specialOffers = specialOffers;
	}

	public void testSpecialOffers(Instrumentation instrumentation) throws TestException {
		try {
			Log.i(TAG, "------It is testSpecialOffers-----------");
			 data = new AndroidNativeData();
	         data.parser(instrumentation.getContext());
			activityName = specialOffers.getCurrentActivity().getClass().getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				specialOffers.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = specialOffers.getCurrentActivity().getClass().getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "The activity name is---------->" + activityName);
						break;
					}

					specialOffers.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testSpecialOffers failed-----------");
				throw new TestException("Current Activity Failed----"
						+ specialOffers.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				Log.i(TAG," Activity name ---->"+specialOffers.getCurrentActivity());
				ArrayList<View> al = specialOffers.getViews();
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
				throw new TestException(TAG + specialOffers.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			specialOfferButton = (ImageView) specialOffers.getView(R.id.home_offers_btn);
			specialOffers.clickOnView(specialOfferButton);

			for (int i = 0; i < 30; i++) {
				Log.i(TAG, "Waiting for *****LG Electronics**** Menu button ");
				String name = specialOffers.getCurrentActivity().getClass().getSimpleName();
				Log.i(TAG, "The Activity name--------------->" + name);
				if (specialOffers.waitForText("LG Electronics", 1, 0, false, true)) {
					// specialOffers.sleep(1000);
					Log.i(TAG, "Clicking on *****LG Electronics**** Menu button ");
					specialOffers.clickOnMenuItem("LG Electronics");
					break;
				} else {
					Log.i(TAG, "Checking for *****LG Electronics**** Menu button ");
					specialOffers.waitForText("LG Electronics", 1, 2000, false, true);
				}

			}

			for (int i = 0; i < 30; i++) {
				Log.i(TAG, "checking the   ****REVIEW***button****");
				// if (soloBrowse.waitForView(this.addtocartButton)) {
				if (specialOffers.waitForText("LG Electronics", 1, 0, false, true)) {
					// if (soloBrowse.waitForText("LG Electronics")) {
					Log.i(TAG, "****if() condition and clicking on ****REVIEW**** button*******");
					this.reviewButton = (ImageView) specialOffers.getView(R.id.product_review);
					// soloBrowse.wait(20000);
					Log.i(TAG, "The ***REVIEW value---------->" + this.reviewButton);
					specialOffers.clickOnView(this.reviewButton);
					break;
				} else {
					Log.i(TAG, "Waiting for ****REVIEW***button****");
					specialOffers.waitForText("LG Electronics", 1, 2000);
				}
			}
			for (int i = 0; i < 30; i++) {
				Log.i(TAG, "Checking for ****SubmitReview*** button");
				if (specialOffers.waitForText("Average Customer Review", 1, 0, false, true)) {
					this.submitReviewButton = (ImageButton) specialOffers.getView(R.id.review_comment_btn);
					specialOffers.clickOnView(this.submitReviewButton);
					break;
				} else {
					Log.i(TAG, "Waiting for ****SubmitReview*** button");
					specialOffers.waitForText("Average Customer Review", 1, 2000, false, true);
				}

			}
			for (int i = 0; i < 5; i++) {
				Log.i(TAG, "***********Checking for User Comments Activity************");
				if (specialOffers.waitForActivity("ProductReviewCommentActivity", 3000)) {
					ratingBar = (RatingBar) specialOffers.getView(R.id.product_review_comment_rating);
					specialOffers.clickOnView(ratingBar);
					specialOffers.sleep(2000);
					specialOffers.clickOnView(ratingBar);
					specialOffers.clickOnView(ratingBar);
					specialOffers.sleep(2000);
					specialOffers.clickOnView(ratingBar);
					specialOffers.sleep(1000);
					Log.i(TAG, "*********This is UserComment Edit field***************");
					userComment = (EditText) specialOffers.getView(R.id.user_review_comment);
					specialOffers.clickOnView(userComment);
					specialOffers.clearEditText(userComment);
					specialOffers.enterText(userComment, data.USER_COMMENTS);
					specialOffers.sleep(1000);
					specialOffers.goBack();
					Log.i(TAG, "****************Clicking on PRODUCTSUBMITREVIEW ******button****");
					productSubmitReview = (ImageButton) specialOffers.getView(R.id.product_review_comment_btn);
					specialOffers.clickOnView(productSubmitReview);
					break;

				} else {
					Log.i(TAG, "***********Waiting for User Comments Activity************");
					specialOffers.waitForActivity("ProductReviewCommentActivity");
				}
			}
			for (int i = 0; i < 5; i++) {
				Log.i(TAG, "**********Checking for Dialog box **********");
				if (specialOffers.waitForText("You must login or register to post the review", 1, 0, false, true)) {
					if (specialOffers.searchText("You must login or register to post the review")) {
						dialogOK = (ImageButton) specialOffers.getView(R.id.btn_dialog_ok);
						specialOffers.clickOnView(dialogOK);
						break;

					} else {
						throw new TestException(specialOffers.getCurrentActivity().getClass().getSimpleName() + TAG
								+ "failed");
					}
				} else {
					Log.i(TAG, "**********Waiting for Dialog box **********");
					specialOffers.waitForText("You must login or register to post the review", 1, 0, false, true);
				}
			}
			for (int i = 0; i < 5; i++) {
				if (specialOffers.waitForActivity("LoginActivity", 2000)) {
					cancelButton = (ImageView) specialOffers.getView(R.id.cancel_btn);
					specialOffers.clickOnView(cancelButton);
					break;

				} else {
					specialOffers.waitForActivity("LoginActivity", 2000);
				}
			}

		} catch (TestException e) {
			Log.e(TAG,Log.getStackTraceString(e));
		}
	}
}
