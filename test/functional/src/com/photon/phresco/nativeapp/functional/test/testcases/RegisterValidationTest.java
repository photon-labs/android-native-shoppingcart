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
public class RegisterValidationTest extends TestCase {

	private Solo soloRegisterValid;

	private String activityName;
	private ImageView homeRegisterButton, registerButton, backButton;
	private EditText emailField, passwordField, confPassField, firstNameField,lastNameField;

	public static final String TAG = "********RegisterVerification testcase*******";
	private  UserInfoConstants info;
	private AndroidNativeData data;
	
	
	public RegisterValidationTest(Solo soloRegister) {
		Log.i(TAG, "Enter into Constructor");
		this.soloRegisterValid = soloRegister;
	}

	public void testRegisterValidation(Instrumentation instrumentation)
			throws TestException {

		try {
			Log.i(TAG, "------It is testRegisterValidation()-----------");
			info = new UserInfoConstants();
			data =new AndroidNativeData();
			data.parser(instrumentation.getContext());
			info.parser(instrumentation.getContext());
			activityName = soloRegisterValid.getCurrentActivity().getClass()
					.getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloRegisterValid.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloRegisterValid.getCurrentActivity()
							.getClass().getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop-----");
						break;
					}

					soloRegisterValid.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testRegisterValidation failed-----------");
				throw new TestException("Current Activity Failed----"
						+ soloRegisterValid.getCurrentActivity().getClass()
								.getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				Log.i(TAG,
						" Activity name ---->"
								+ soloRegisterValid.getCurrentActivity());
				ArrayList<View> al = soloRegisterValid.getViews();
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
						+ soloRegisterValid.getCurrentActivity().getClass()
								.getSimpleName() + "failed");
			}
			Log.i(TAG, "Enter into try{} block");
			soloRegisterValid.waitForActivity("HomeActivity", 2000);
			// click on Registerbutton
			homeRegisterButton = (ImageView) soloRegisterValid
					.getView(R.id.home_register_btn);
			soloRegisterValid.clickOnView(homeRegisterButton);
			// click on FirstName field and enter the value
			for (int i = 0; i < 5; i++) {
				if (soloRegisterValid.waitForText("First Name")) {
					firstNameField = (EditText) soloRegisterValid.getView(R.id.txt_fistName);
					soloRegisterValid.clickOnView(firstNameField);
					soloRegisterValid.clearEditText(firstNameField);
					soloRegisterValid.enterText(firstNameField,data.FIRSTNAME);
					soloRegisterValid.goBack();
					break;
				} else {
					soloRegisterValid.waitForActivity("RegistrationActivity");
				}

			}
			// click on LastName field and enter the value
			lastNameField = (EditText) soloRegisterValid.getView(R.id.txt_lastName);
			soloRegisterValid.clickOnView(lastNameField);
			soloRegisterValid.clearEditText(lastNameField);
			soloRegisterValid.enterText(lastNameField,data.LASTNAME);
			soloRegisterValid.goBack();

			// clears the text at first Editfield
			emailField = (EditText) soloRegisterValid.getView(R.id.txt_email);
			soloRegisterValid.clickOnView(emailField);
			soloRegisterValid.clearEditText(emailField);
			// soloRegister.waitForActivity("SplashActivity", 2000);
			// it will type the text at first field which i give in method
			soloRegisterValid.enterText(emailField, info.EMAIL);
			// soloRegister.sleep(1000);
			soloRegisterValid.goBack();
			passwordField = (EditText) soloRegisterValid.getView(R.id.txt_password);
			// click the password field based on EditText view object

			if (instrumentation != null) {
				instrumentation.runOnMainSync(new Runnable() {

					@Override
					public void run() {

						passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
						passwordField.setText("");
						passwordField.setText(info.PASSWORD);

					}
				});
			} else {
				System.out.println("Inst is null");
			}

			soloRegisterValid.sleep(1000);
			soloRegisterValid.goBack();
			confPassField = (EditText) soloRegisterValid.getView(R.id.txt_confirm_password);

			// click the password field based on EditText view object

			if (instrumentation != null) {
				instrumentation.runOnMainSync(new Runnable() {

					@Override
					public void run() {

						confPassField
								.setTransformationMethod(PasswordTransformationMethod.getInstance());
						confPassField.setText("");
						confPassField.setText(info.WRONG_PASSWORD);

					}
				});
			} else {
				System.out.println("Inst is null");
			}

			soloRegisterValid.goBack();
			soloRegisterValid.scrollDown();

			// click on Register button
			registerButton = (ImageView) soloRegisterValid
					.getView(R.id.register_btn);
			soloRegisterValid.clickOnView(registerButton);
			for (int i = 0; i < 5; i++) {

				if (soloRegisterValid.searchText(data.CONFIRM_PASSWORD_DIALOG)) {
					soloRegisterValid.clickOnView(confPassField);
					soloRegisterValid.sleep(1000);
					backButton = (ImageView) soloRegisterValid.getView(R.id.back_btn);
					soloRegisterValid.clickOnView(backButton);

					break;
				} else {
					soloRegisterValid.waitForActivity("CustomDialogActivity",1000);
				}

			}

		} catch (TestException e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}

	}

}
