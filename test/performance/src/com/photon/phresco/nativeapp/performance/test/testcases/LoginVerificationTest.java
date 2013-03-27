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
package com.photon.phresco.nativeapp.performance.test.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.performance.test.core.Constants;

/**
 * @author pradeep_si This testcase is for clicking on LoginImage button in
 *         homepage. After click on button will see one LoginScreen in that it
 *         contains two text fields and login ,cancel and register image
 *         buttons. in first field will give email address and in second field
 *         will give password it will take as hidden format i.e *****
 */

public class LoginVerificationTest extends TestCase {

	private Solo soloLogin;
	private View dialogBox;
	private String activityName;
	private ImageView homeLoginButton, loginButton;
	private EditText passwordField, emailField;
	private final String TAG = "LoginTestCase***";

	public LoginVerificationTest(Solo solo) {
		this.soloLogin = solo;

	}

	/**
	 * 
	 * @throws TestException
	 *             After execution of setUp() method in MainTest class this
	 *             testLogin() will be called and it contains methods for click
	 *             on EmailAddress field and Password field then click on Login
	 *             button. In this application every button is image button
	 *             only..
	 */
	public void testLoginVerification() throws TestException {

		try {
			Log.i(TAG, "------It is testLoginVerification()-----------");

			activityName = soloLogin.getCurrentActivity().getClass().getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloLogin.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloLogin.getCurrentActivity().getClass().getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop-----");
						break;
					}

					soloLogin.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testLoginVerification failed-----------");
				throw new TestException("Current Activity Failed----"
						+ soloLogin.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				System.out.println(" Activity name ---->" + soloLogin.getCurrentActivity());
				ArrayList<View> al = soloLogin.getViews();
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
				throw new TestException(TAG + soloLogin.getCurrentActivity().getClass().getSimpleName()
						+ "------>failed");
			}
			// click on Loginbutton
			// soloLogin.waitForActivity("SplashActivity", 5000);
			// get the login button view with id i.e home_login_btn
			homeLoginButton = (ImageView) soloLogin.getView(R.id.home_login_btn);
			// click on login button with view id
			soloLogin.clickOnView(homeLoginButton);
			// control waits for 2 seconds to activate the screen
			soloLogin.waitForActivity("SplashActivity", 2000);
			// clears the text at first Editfield
			emailField = (EditText) soloLogin.getView(R.id.txt_email);
			soloLogin.clickOnView(emailField);
			soloLogin.clearEditText(emailField);
			// it will type the text at first field which i gave in method
			soloLogin.enterText(emailField, Constants.EMAIL_ID);
			soloLogin.goBack();
			// clear the text at second Editfield
			passwordField = (EditText) soloLogin.getView(R.id.txt_password);
			soloLogin.clickOnView(passwordField);
			soloLogin.clearEditText(passwordField);
			// finding the password field view
			// click the password field based on EditText view object

			soloLogin.waitForActivity("LoginActivity", 1000);

			soloLogin.enterText(passwordField, Constants.PASSWORD);
			soloLogin.goBack();
			soloLogin.waitForActivity("LoginActivity", 1000);
			// click on Login button
			loginButton = (ImageView) soloLogin.getView(R.id.login_btn);
			soloLogin.clickOnView(loginButton);
			// soloSplash.clickOnImageButton(1);
			// soloLogin.waitForText("Login Successfully");

			for (int i = 0; i < 3; i++) {
				if (soloLogin.searchText(Constants.MESSAGE_LOGIN_SUCCESS)) {
					dialogBox = soloLogin.getView(R.id.btn_dialog_ok);
					soloLogin.clickOnView(dialogBox);
					soloLogin.waitForActivity("HomeActivity");
					break;
				} else {
					if (soloLogin.searchText(Constants.MESSAGE_LOGIN_FAIL)) {
						soloLogin.clickOnView(dialogBox);
						soloLogin.waitForActivity("HomeActivity");
						break;
					} else if (soloLogin.waitForActivity("CustomDialogActivity", 2000)) {
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}