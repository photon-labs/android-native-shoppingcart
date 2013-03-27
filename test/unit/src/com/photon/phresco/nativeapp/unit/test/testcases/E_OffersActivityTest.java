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
 *
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
import com.photon.phresco.nativeapp.eshop.model.product.Product;
import com.photon.phresco.nativeapp.unit.test.core.Constants;

/**
 * @author chandankumar_r
 *
 */
public class E_OffersActivityTest extends TestCase{
	private static final String TAG = "E_OffersActivityTest *********** ";
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() {
	}

	/**
	 *  get the Special offer list
	 *  from web server
	 *
	 */
	@Test
	public final void testSpecialOffers() {
		Product product=new Product();

		try{
			PhrescoLogger.info(TAG + " testSpecialOffers -------------- START ");

			JSONObject offersJSONObj = product.getProductJSONObject(Constants.getWebContextURL()+ Constants.getRestAPI()+ Constants.SPECIAL_PRODUCTS_URL);
			assertNotNull(offersJSONObj);
			JSONArray offerProductArray = offersJSONObj.getJSONArray("product");
			assertTrue(offerProductArray.length() > 0);

			PhrescoLogger.info(TAG + " testSpecialOffers -------------- END ");
		}catch(IOException ex){
			PhrescoLogger.info(TAG + "testSpecialOffers - IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}catch (JSONException ex) {
			PhrescoLogger.info(TAG + "testSpecialOffers - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * get the Special offer list from web server
	 * and check if list is null or not
	 *
	 */

/*	@Test
	public final void testSpecialOffersList() {
		Product product=new Product();

		try{
			PhrescoLogger.info(TAG + " testSpecialOffersList -------------- START ");

			JSONObject offersJSONObj = product.getProductJSONObject(Constants.getWebContextURL()+ Constants.getRestAPI()+ Constants.SPECIAL_PRODUCTS_URL);
			assertNotNull(offersJSONObj);
			JSONArray offersProductArray = offersJSONObj.getJSONArray("product");
			assertTrue("Special offer list avilabel",offersProductArray.length() < 0);

			PhrescoLogger.info(TAG + " testSpecialOffersList -------------- END ");
		}catch(IOException ex){
			PhrescoLogger.info(TAG + "testSpecialOffersList - IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}catch (JSONException ex) {
			PhrescoLogger.info(TAG + "testSpecialOffersList - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}*/

}
