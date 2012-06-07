/*
 * Classname: RegistrationActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.activity;

import java.io.IOException;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.dialog.CustomDialogActivity;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.customer.Customer;
import com.photon.phresco.nativeapp.eshop.model.customer.Registration;

/**
 * Show the registration screen
 *
 * @author viral_b
 *
 */
public class RegistrationActivity extends PhrescoActivity {

	private static final String TAG = "RegistrationActivity ***** ";
	private EditText firstName,lastName,emailId, password, registerConfirmPassword;
	private ImageButton cancelButton, registerButton, backButton;
	private Registration registrationObj = null;

	private String prevActivity = "previousActivity";
	private JSONObject registerationJSONResponse = null;
	private Exception ioException = null;
	private Customer customerObj = new Customer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		initEnvironment();

		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startHomeActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - cancelButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					PhrescoLogger.info(TAG + " Register Button Clicked....");
					if (isValid()) {
						submitRegistrationDetails();
					}

				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - registerButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startHomeActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
		} catch(Exception ex){
			PhrescoLogger.info(TAG + " - onCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start home activity
	 */
	private void startHomeActivity() {
		try {
			Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
			homeActivity.putExtra("currentActivity", "home");
			startActivity(homeActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startHomeActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			cancelButton = (ImageButton) findViewById(R.id.cencel_btn);
			registerButton = (ImageButton) findViewById(R.id.register_btn);
			backButton = (ImageButton) findViewById(R.id.back_btn);

			firstName=(EditText)findViewById(R.id.txt_fistName);
			lastName=(EditText)findViewById(R.id.txt_lastName);
			emailId = (EditText) findViewById(R.id.txt_email);
			password = (EditText) findViewById(R.id.txt_password);
			registerConfirmPassword = (EditText) findViewById(R.id.txt_confirm_password);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * submit the registration details to server
	 */
	private void submitRegistrationDetails() {
		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(RegistrationActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.registration_auth));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					PhrescoLogger.info(TAG + " submitRegistration Details - processOnStart() ");
					postRegistrationRequestToServer();
				}

				@Override
				public void processOnComplete() {
					PhrescoLogger.info(TAG + " submitRegistrationDetails - processOnComplete() ");
					getRegistrationResponseFromServer();

				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " submit Registration Details - Exception " + ex.toString());
			PhrescoLogger.warning(ex);

		}
	}

	/**
	 * Wrapper method to post the registration details to server for
	 * authentication
	 */
	private void postRegistrationRequestToServer() {
		try {
			postRegistrationDetails();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " postLoginRequestToServer - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Post the registration details to server for authentication
	 *
	 * @throws IOException
	 */
	protected void postRegistrationDetails() throws IOException {
		registerationJSONResponse = customerObj.postCredentialDetails(firstName.getText().toString(),lastName.getText().toString(),emailId.getText().toString(), password.getText().toString());

		if(registerationJSONResponse!=null){
			Registration regObj=new Registration();
			registrationObj = regObj.getRegistrationGSONObject(registerationJSONResponse.toString());
		}
	}

	/**
	 * Wrapper method to show the response message from server
	 */
	private void getRegistrationResponseFromServer() {
		try {
			PhrescoLogger.info(TAG + "createOrderJSONObject() - Registration Details response : " + registerationJSONResponse.toString());
			if (ioException == null) {
				showCustomDialogMessage();
			} else {
				showErrorDialogWithCancel();
			}

		} catch (Exception ex) {

			PhrescoLogger.info(TAG + " - getRegistrationResponseFromServer - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);

		}
	}

	/**
	 * Show the custom dialog with message
	 */
	private void showCustomDialogMessage() {
		try {
			Intent customDialogActivity = new Intent(getApplicationContext(), CustomDialogActivity.class);
			customDialogActivity.putExtra(prevActivity, "RegistrationActivity");
			customDialogActivity.putExtra("obj", registrationObj);
			startActivityForResult(customDialogActivity, 1);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " showCustomDialogMessage - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode == RESULT_OK && requestCode == 1) {
				Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
				homeActivity.putExtra("currentActivity", "home");
				if (data != null) {
					Constants.setUserId(data.getExtras().getInt("userId"));
				} else {
					Constants.setUserId(0);
				}
				startActivity(homeActivity);
				finish();
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/*
	 * This method is implemented for verification
	 */
	private boolean isValid() {
		PhrescoLogger.info(TAG + " - Inside isValid(): ");
		boolean isValidFlag = true;
		try {
			if (customerObj.isEmpty(firstName.getText().toString())) {
				firstName.setError(getString(R.string.first_name_required));
				isValidFlag = false;
			}else if (customerObj.isEmpty(lastName.getText().toString())) {
				lastName.setError(getString(R.string.last_name));
				isValidFlag = false;
			}else if (customerObj.isEmpty(emailId.getText().toString())) {
				emailId.setError(getString(R.string.email_required));
				isValidFlag = false;
			} else if (customerObj.isEmpty(password.getText().toString())) {
				password.setError(getString(R.string.password_required));
				isValidFlag = false;
			} else if (!customerObj.isValidEmailId(emailId.getText().toString())) {
				emailId.setError(getString(R.string.invalid_email));
				isValidFlag = false;
			} else if (!customerObj.isConfirmPasswordCorrect(password.getText().toString(), registerConfirmPassword.getText().toString())) {
				registerConfirmPassword.setError(getString(R.string.invalid_confirm_password));
				isValidFlag = false;
			}
		} catch (Exception ex) {
			isValidFlag = false;
			PhrescoLogger.info(TAG + " - isValid  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return isValidFlag;
	}

}
