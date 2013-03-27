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
package com.photon.phresco.nativeapp.functional.test.core;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.photon.phresco.nativeapp.functional.R;


import android.content.Context;
import android.content.res.Resources;




public class AndroidNativeData {
	private ReadXMLFile readXml;

	public String FIRSTNAME = "firstname";
	public String LASTNAME = "lastname";
	
	public String DELIVERY_FIRSTNAME = "delivery_firstname"; 
	public String DELIVERY_ADDRESS1 = "delivery_address1"; 
	public String DELIVERY_ZIPCODE = "delivery_zipcode"; 
	public String DELIVERY_PHONENUMBER = "delivery_phonenumber"; 
	public String DELIVERY_CITY = "delivery_city";
	
	public String BILLING_FIRSTNAME = "billing_firstname"; 
	public String BILLING_LASTNAME = "billing_lastname"; 
	public String BILLING_COMPANY = "billing_company";
	public String BILLING_ADDRESS1 = "billing_address1"; 
	public String BILLING_CITY = "billing_city"; 
	public String BILLING_ZIP = "billing_zip";
	
	public String CONFIRM_PASSWORD_DIALOG = "confirm_password_dialog";
	public String INVALID_LOGIN_MESG = "invalid_login_msg";
	public String CONFIRM_CATEGORY_DIALOG = "confirm_category_dialog";
	
	public String MESSAGE_EXIST = "message_exist";
	public String MESSAGE_INSERTED = "message_inserted";
	public String MESSAGE_LOGIN_SUCCESS = "message_login_success";
	public String MESSAGE_LOGIN_FAIL = "message_login_fail";
	public String USER_COMMENTS = "user_comments";
	
	public AndroidNativeData() {
		
	}
    public void parser(Context context)
    {
    	try {
    		
    	    Resources res = context.getResources(); 
    	    InputStream inputStream = res.openRawResource(R.raw.android_native_data);
    	
			readXml = new ReadXMLFile();
			readXml.loadUserInfoConstants(inputStream);
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
    }
}
