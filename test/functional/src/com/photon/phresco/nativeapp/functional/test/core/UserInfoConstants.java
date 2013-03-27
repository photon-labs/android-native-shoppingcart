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

public class UserInfoConstants {
	private ReadXMLFile readXml;

	public String USERNAME = "username";
	public String PASSWORD = "password";
	public String EMAIL = "email";
	
	public String WRONG_EMAIL = "wrong_email";
	public String WRONG_PASSWORD = "wrong_password";


	public UserInfoConstants() {

	}

	public void parser(Context context) {
		try {

			Resources res = context.getResources();
			InputStream instream = res.openRawResource(R.raw.user_info);
			readXml = new ReadXMLFile();
			readXml.loadUserInfoConstants(instream);
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
