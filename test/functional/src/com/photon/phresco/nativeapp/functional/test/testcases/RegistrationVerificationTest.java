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
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.functional.test.core.*;

public class RegistrationVerificationTest extends TestCase {

	private Solo soloRegister;
	private static final String TAG = "*******RegisterTestcase*********";
	private String activityName;
	private ImageView homeRegisterButton, registerButton, dialogBoxOK;
	private EditText emailField, passwordField, confPassField, firstNameField,
			lastNameField;

	private  UserInfoConstants info;
	private AndroidNativeData data;
	public RegistrationVerificationTest(Solo soloRegister) {
		this.soloRegister = soloRegister;
	}

	/**
	 * @throws TestException
	 */
	public void testRegisterScenario(Instrumentation instrumentation)
			throws TestException {

		try {

			Log.i(TAG, "------It is testRegisterScenario-----------");
			info = new UserInfoConstants();
			info.parser(instrumentation.getContext());
			data=new AndroidNativeData();
			data.parser(instrumentation.getContext());
			activityName = soloRegister.getCurrentActivity().getClass()
					.getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloRegister.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloRegister.getCurrentActivity().getClass()
							.getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop-----");
						break;
					}

					soloRegister.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ SplashActivity is not launched -----------");
				throw new TestException("****The Activity name****"
						+ soloRegister.getCurrentActivity().getClass()
								.getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				Log.i(TAG,
						" Activity name ---->"
								+ soloRegister.getCurrentActivity());
				ArrayList<View> al = soloRegister.getViews();
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
				throw new TestException(TAG
						+ soloRegister.getCurrentActivity().getClass()
								.getSimpleName() + "failed");
			}

			soloRegister.waitForActivity("HomeActivity", 2000);
			// click on Registerbutton
			homeRegisterButton = (ImageView) soloRegister
					.getView(R.id.home_register_btn);
			soloRegister.clickOnView(homeRegisterButton);
			// click on FirstName field and enter the value
			for (int i = 0; i < 5; i++) {
				if (soloRegister.waitForText("First Name")) {
					firstNameField = (EditText) soloRegister
							.getView(R.id.txt_fistName);
					soloRegister.clickOnView(firstNameField);
					soloRegister.clearEditText(firstNameField);
					soloRegister
							.enterText(firstNameField,data.FIRSTNAME);
					soloRegister.goBack();
					break;
				} else {
					soloRegister.waitForActivity("RegistrationActivity");
				}

			}
			// click on LastName field and enter the value
			lastNameField = (EditText) soloRegister.getView(R.id.txt_lastName);
			soloRegister.clickOnView(lastNameField);
			soloRegister.clearEditText(lastNameField);
			soloRegister.enterText(lastNameField,data.LASTNAME);
			soloRegister.goBack();

			// clears the text at first Editfield
			emailField = (EditText) soloRegister.getView(R.id.txt_email);
			soloRegister.clickOnView(emailField);
			soloRegister.clearEditText(emailField);
			// soloRegister.waitForActivity("SplashActivity", 2000);
			// it will type the text at first field which i give in method
			soloRegister.enterText(emailField,info.EMAIL);
			// soloRegister.sleep(1000);
			soloRegister.goBack();
			passwordField = (EditText) soloRegister.getView(R.id.txt_password);
			// click the password field based on EditText view object

			if (instrumentation != null) {
				instrumentation.runOnMainSync(new Runnable() {

					@Override
					public void run() {

						passwordField
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
						passwordField.setText("");
						passwordField.setText(info.PASSWORD);

					}
				});
			} else {
				System.out.println("Inst is null");
			}

			soloRegister.sleep(1000);
			soloRegister.goBack();
			
			// click the password field based on EditText view object
			confPassField = (EditText) soloRegister
					.getView(R.id.txt_confirm_password);

			if (instrumentation != null) {
				instrumentation.runOnMainSync(new Runnable() {

					@Override
					public void run() {

						confPassField
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
						confPassField.setText("");
						confPassField.setText(info.PASSWORD);

					}
				});
			} else {
				System.out.println("Inst is null");
			}

			soloRegister.goBack();
			soloRegister.scrollDown();

			// click on Register button
			registerButton = (ImageView) soloRegister
					.getView(R.id.register_btn);
			soloRegister.clickOnView(registerButton);
			for (int i = 0; i < 5; i++) {
				Log.i(TAG, "********Searching for dialog box*********");
				if (soloRegister.searchText(data.MESSAGE_EXIST)) {
					Log.i(TAG,
							"********Searching for Already Exist text*********");
					dialogBoxOK = (ImageView) soloRegister
							.getView(R.id.btn_dialog_ok);
					soloRegister.clickOnView(dialogBoxOK);
					soloRegister.waitForActivity("HomeActivity");
					/*
					 * If user already exists the login button will be enabled
					 */
					// new
					// LoginVerificationTestCase(soloRegister).testLoginScenario();
					break;
				} else if (soloRegister.searchText(data.MESSAGE_INSERTED)) {
					Log.i(TAG, "********Searching for Inserted text*********");
					dialogBoxOK = (ImageView) soloRegister
							.getView(R.id.btn_dialog_ok);
					soloRegister.clickOnView(dialogBoxOK);
					soloRegister.waitForActivity("HomeActivity", 2000);
					break;
				} else {
					soloRegister.waitForActivity("CustomDialogActivity", 5000);
				}
			}

		} catch (TestException e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}

	}
}
