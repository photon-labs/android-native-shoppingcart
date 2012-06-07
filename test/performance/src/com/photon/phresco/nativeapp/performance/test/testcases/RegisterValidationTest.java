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

public class RegisterValidationTest extends TestCase {

	private Solo soloRegisterValid;

	private String activityName;
	private ImageView homeRegisterButton, registerButton, backButton;
	private EditText emailField, passwordField, confPassField, firstNameField, lastNameField;

	public static final String TAG = "********RegisterVerification testcase*******";

	public RegisterValidationTest(Solo soloRegister) {
		Log.i(TAG, "Enter into Constructor");
		this.soloRegisterValid = soloRegister;
	}

	public void testRegisterValidation() throws TestException {

		try {
			Log.i(TAG, "------It is testRegisterValidation()-----------");

			activityName = soloRegisterValid.getCurrentActivity().getClass().getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloRegisterValid.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloRegisterValid.getCurrentActivity().getClass().getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop-----");
						break;
					}

					soloRegisterValid.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testRegisterValidation failed-----------");
				throw new TestException("Current Activity Failed----"
						+ soloRegisterValid.getCurrentActivity().getClass().getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				System.out.println(" Activity name ---->" + soloRegisterValid.getCurrentActivity());
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
				throw new TestException(TAG + soloRegisterValid.getCurrentActivity().getClass().getSimpleName()
						+ "failed");
			}
			Log.i(TAG, "Enter into try{} block");
			soloRegisterValid.waitForActivity("HomeActivity", 2000);
			// click on Registerbutton
			homeRegisterButton = (ImageView) soloRegisterValid.getView(R.id.home_register_btn);
			soloRegisterValid.clickOnView(homeRegisterButton);
			// click on FirstName field and enter the value
			for (int i = 0; i < 5; i++) {
				if (soloRegisterValid.waitForText("First Name")) {
					firstNameField = (EditText) soloRegisterValid.getView(R.id.txt_fistName);
					soloRegisterValid.clickOnView(firstNameField);
					soloRegisterValid.clearEditText(firstNameField);
					soloRegisterValid.enterText(firstNameField, Constants.FIRST_NAME);
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
			soloRegisterValid.enterText(lastNameField, Constants.LAST_NAME);
			soloRegisterValid.goBack();

			// clears the text at first Editfield
			emailField = (EditText) soloRegisterValid.getView(R.id.txt_email);
			soloRegisterValid.clickOnView(emailField);
			soloRegisterValid.clearEditText(emailField);
			// soloRegister.waitForActivity("SplashActivity", 2000);
			// it will type the text at first field which i give in method
			soloRegisterValid.enterText(emailField, Constants.EMAIL_ID);
			// soloRegister.sleep(1000);
			soloRegisterValid.goBack();
			passwordField = (EditText) soloRegisterValid.getView(R.id.txt_password);
			// click the password field based on EditText view object
			soloRegisterValid.clickOnView(passwordField);
			soloRegisterValid.clearEditText(passwordField);
			soloRegisterValid.enterText(passwordField, Constants.PASSWORD);
			soloRegisterValid.sleep(1000);
			soloRegisterValid.goBack();
			confPassField = (EditText) soloRegisterValid.getView(R.id.txt_confirm_password);
			// click the password field based on EditText view object
			soloRegisterValid.clickOnView(confPassField);
			soloRegisterValid.clearEditText(confPassField);
			soloRegisterValid.enterText(confPassField, Constants.WRONNG_PASSWORD);
			// soloRegister.sleep(1000);
			soloRegisterValid.goBack();
			soloRegisterValid.scrollDown();

			// click on Register button
			registerButton = (ImageView) soloRegisterValid.getView(R.id.register_btn);
			soloRegisterValid.clickOnView(registerButton);
			for (int i = 0; i < 5; i++) {

				if (soloRegisterValid.searchText(Constants.CONFIRM_PASSWORD_DIALOG)) {
					soloRegisterValid.clickOnView(confPassField);
					soloRegisterValid.sleep(1000);
					backButton = (ImageView) soloRegisterValid.getView(R.id.back_btn);
					soloRegisterValid.clickOnView(backButton);

					break;
				} else {
					soloRegisterValid.waitForActivity("CustomDialogActivity", 1000);
				}

			}

		} catch (TestException e) {
			e.printStackTrace();
		}

	}

}
