/**
 * PHR_AndroidNative
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.photon.phresco.nativeapp.unit.test.testcases;

import java.io.IOException;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.category.Category;
import com.photon.phresco.nativeapp.unit.test.core.Constants;

public class B_CategoryListActivityTest extends TestCase {
	private static final String TAG = "B_CategoryListActivityTest  *********** ";

	@BeforeClass
	public static void setUpBeforeClass() {

	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 *  get the category list
	 *  from web server
	 *
	 */
	@Test
	public final void testCategoryList() {

		Category category = new Category();
		try {
			PhrescoLogger.info(TAG + " testCategory -------------- START ");

			JSONObject categoryJSONObj = category.getCategoryJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.CATEGORIES_URL);
			assertNotNull(categoryJSONObj);
			JSONArray categoryArray = categoryJSONObj.getJSONArray("category");
			assertTrue("Categories available",categoryArray.length() > 0);

			PhrescoLogger.info(TAG + " testCategory -------------- END ");
		} catch (IOException ex) {
			PhrescoLogger.info(TAG + "testCategory - IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + "testCategory - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
