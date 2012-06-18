/*
 * ###
 * PHR_AndroidNative
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
/**
 *
 */
package com.photon.phresco.nativeapp.eshop.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.activity.PhrescoActivity;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.customer.Customer;

/**
 * @author chandankumar_r
 *
 */
public class CustomEmailDialogActivity extends PhrescoActivity {

	private static final String TAG = "CustomEmailDialogActivity ***** ";

	private EditText emailEdit = null;
	private ImageButton btnOK, btnCancel;
	private String emailId, updatedEmailId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.custom_email_dialog);
			initEnvironment();

			Intent customEmailIntent = getIntent();
			if (customEmailIntent != null && customEmailIntent.getExtras() != null) {
				emailId = customEmailIntent.getStringExtra("currentEmailId");
			}

			PhrescoLogger.info(TAG + " Intent Extra Value is:  " + emailId);
			if (emailId != null) {
				((EditText) findViewById(R.id.edit_email_box)).setText(emailId);
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					PhrescoLogger.info(TAG + " Dialog OK Clicked :  ");
					emailEdit = (EditText) findViewById(R.id.edit_email_box);
					updatedEmailId = emailEdit.getText().toString();

					if (isValid()) {
						Intent checkOutActivity = new Intent(getApplicationContext(), CustomEmailDialogActivity.class);
						checkOutActivity.putExtra("updatedEmailId", updatedEmailId);
						setResult(RESULT_OK, checkOutActivity);
						finish();
					}
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - btnOK  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					PhrescoLogger.info(TAG + " Dialog CANCEL Clicked :  ");
					setResult(RESULT_OK, null);
					finish();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - btnOK  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			btnOK = (ImageButton) findViewById(R.id.btn_dialog_ok);
			btnCancel = (ImageButton) findViewById(R.id.btn_dialog_cancel);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Validate the email id
	 *
	 * @return boolean
	 */
	private boolean isValid() {
		boolean isValidFlag = true;
		Customer customerObj = new Customer();
		try {
			if (customerObj.isEmpty(emailEdit.getText().toString())) {
				emailEdit.setError(getString(R.string.email_required));
				isValidFlag = false;
			} else if (!(customerObj.isValidEmailId(emailEdit.getText().toString()))) {
				emailEdit.setError(getString(R.string.invalid_email));
				isValidFlag = false;
			}
		} catch (Exception ex) {
			isValidFlag = false;
			PhrescoLogger.info(TAG + " - isValid  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return isValidFlag;
	}

	@Override
	public void onBackPressed() {
		return;
	}
}
