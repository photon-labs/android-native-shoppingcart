/**
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.nativeapp.functional.test.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.functional.test.core.Constants;

public class RegistrationVerificationTest extends TestCase {

	private Solo soloRegister;
	private final String TAG = "*******RegisterTestcase*********";
	private String activityName;
	private ImageView homeRegisterButton, registerButton, dialogBoxOK;
	private EditText emailField, passwordField, confPassField, firstNameField, lastNameField;

	public RegistrationVerificationTest(Solo soloRegister) {
		this.soloRegister = soloRegister;
	}

	/**
	 * @throws TestException
	 */
	public void testRegisterScenario() throws TestException {

		try {

			Log.i(TAG, "------It is testRegisterScenario-----------");

			activityName = soloRegister.getCurrentActivity().getClass().getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloRegister.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloRegister.getCurrentActivity().getClass().getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop-----");
						break;
					}

					soloRegister.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ SplashActivity is not launched -----------");
				throw new TestException("****The Activity name****"
						+ soloRegister.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				System.out.println(" Activity name ---->" + soloRegister.getCurrentActivity());
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
				throw new TestException(TAG + soloRegister.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			soloRegister.waitForActivity("HomeActivity", 2000);
			// click on Registerbutton
			homeRegisterButton = (ImageView) soloRegister.getView(R.id.home_register_btn);
			soloRegister.clickOnView(homeRegisterButton);
			// click on FirstName field and enter the value
			for (int i = 0; i < 5; i++) {
				if (soloRegister.waitForText("First Name")) {
					firstNameField = (EditText) soloRegister.getView(R.id.txt_fistName);
					soloRegister.clickOnView(firstNameField);
					soloRegister.clearEditText(firstNameField);
					soloRegister.enterText(firstNameField, Constants.FIRST_NAME);
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
			soloRegister.enterText(lastNameField, Constants.LAST_NAME);
			soloRegister.goBack();

			// clears the text at first Editfield
			emailField = (EditText) soloRegister.getView(R.id.txt_email);
			soloRegister.clickOnView(emailField);
			soloRegister.clearEditText(emailField);
			// soloRegister.waitForActivity("SplashActivity", 2000);
			// it will type the text at first field which i give in method
			soloRegister.enterText(emailField, Constants.EMAIL_ID);
			// soloRegister.sleep(1000);
			soloRegister.goBack();
			passwordField = (EditText) soloRegister.getView(R.id.txt_password);
			// click the password field based on EditText view object
			soloRegister.clickOnView(passwordField);
			soloRegister.clearEditText(passwordField);
			soloRegister.enterText(passwordField, Constants.PASSWORD);
			soloRegister.sleep(1000);
			soloRegister.goBack();
			confPassField = (EditText) soloRegister.getView(R.id.txt_confirm_password);
			// click the password field based on EditText view object
			soloRegister.clickOnView(confPassField);
			soloRegister.clearEditText(confPassField);
			soloRegister.enterText(confPassField, Constants.CONFIRM_PASSWORD);
			// soloRegister.sleep(1000);
			soloRegister.goBack();
			soloRegister.scrollDown();

			// click on Register button
			registerButton = (ImageView) soloRegister.getView(R.id.register_btn);
			soloRegister.clickOnView(registerButton);
			for (int i = 0; i < 5; i++) {
				Log.i(TAG, "********Searching for dialog box*********");
				if (soloRegister.searchText(Constants.MESSAGE_EXIST)) {
					Log.i(TAG, "********Searching for Already Exist text*********");
					dialogBoxOK = (ImageView) soloRegister.getView(R.id.btn_dialog_ok);
					soloRegister.clickOnView(dialogBoxOK);
					soloRegister.waitForActivity("HomeActivity");
					/*
					 * If user already exists the login button will be enabled
					 */
					// new
					// LoginVerificationTestCase(soloRegister).testLoginScenario();
					break;
				} else if (soloRegister.searchText(Constants.MESSAGE_INSERTED)) {
					Log.i(TAG, "********Searching for Inserted text*********");
					dialogBoxOK = (ImageView) soloRegister.getView(R.id.btn_dialog_ok);
					soloRegister.clickOnView(dialogBoxOK);
					soloRegister.waitForActivity("HomeActivity", 2000);
					break;
				} else {
					soloRegister.waitForActivity("CustomDialogActivity", 5000);
				}
			}

		} catch (TestException e) {
			e.printStackTrace();
		}

	}
}
