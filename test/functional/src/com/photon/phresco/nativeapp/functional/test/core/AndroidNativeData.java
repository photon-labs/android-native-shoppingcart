package com.photon.phresco.nativeapp.functional.test.core;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.photon.phresco.nativeapp.functional.R;


import android.content.Context;
import android.content.res.Resources;




public class AndroidNativeData {
	private ReadXMLFile readXml;

	
	public String CONFIRM_PASSWORD_DIALOG = "confirm_password_dialog";
	public String CONFIRM_LOGIN_DIALOG = "confirm_login_dialog";
	public String CONFIRM_CATEGORY_DIALOG = "confirm_category_dialog";
	
	public String MESSAGE_EXIST = "message_exist";
	public String MESSAGE_INSERTED = "message_inserted";
	public String MESSAGE_LOGIN_SUCCESS = "message_login_sucess";
	public String MESSAGE_LOGIN_FAIL = "message_login_fail";
	public String USER_COMMENTS = "user_comments";

	
	public String WRONG_EMAIL = "wrong_email";
	public String WRONG_PASSWORD = "wrong_passWord";

	public String FIRSTNAME = "firstName";
	public String LASTNAME = "lastName";
	public String EMAIL = "email";
	
	public String DELIVERY_FIRSTNAME="delivary_firstName"; 
	public String DELIVERY_ADDRESS1="delivary_address1"; 
	public String DELIVERY_ZIPCODE="delivary_zipcode"; 
	public String DELIVERY_PHONENUMBER="delivary_phonenumber"; 
	public String DELIVERY_CITYNAME="delivary_cityname";
	
	public String BILLING_FIRSTNAME="billing_firstname"; 
	public String BILLING_LASTNAME="billing_lastname"; 
	public String BILLING_COMPANY="billing_company";
	public String BILLING_ADDRESS1="billing_address1"; 
	public String BILLING_CITY="billing_city"; 
	public String BILLING_ZIP="billing_zip";
	
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
