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
public class C_ProductListActivityTest extends TestCase {
	private static final String TAG = "C_ProductListActivityTest *********** ";

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
	 * get the product list from web server
	 *
	 */
	@Test
	public final void testGetProducts() {
		Product product = new Product();

		try {
			PhrescoLogger.info(TAG + " testProduct -------------- START ");

			JSONObject productJSONObj = product.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.PRODUCTS_URL);
			assertNotNull(productJSONObj);
			JSONArray productArray = productJSONObj.getJSONArray("product");
			assertTrue(productArray.length() > 0);

			PhrescoLogger.info(TAG + " testProduct -------------- END ");
		} catch (IOException ex) {
			PhrescoLogger.info(TAG + "testProduct - IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + "testProduct - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Check products for available category id
	 *
	 */

	@Test
	public final void testGetProductsForExistingCategoryId() {
		Product product = new Product();
		int categoryId = 1;
		try {
			PhrescoLogger.info(TAG + " testGetProductsForExistingCategoryId -------------- START ");

			JSONObject productJSONObj = product.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.CATEGORIES_URL + categoryId);
			assertNotNull(productJSONObj);

			if (productJSONObj != null) {
				JSONArray productArray = productJSONObj.getJSONArray("product");
				assertFalse("Products available for category " + categoryId, productArray.length() < 0);
			}

			PhrescoLogger.info(TAG + " testGetProductsForExistingCategoryId -------------- END ");
		} catch (IOException ex) {
			PhrescoLogger.info(TAG + "testGetProductsForExistingCategoryId - IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + "testGetProductsForExistingCategoryId - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "testGetProductsForExistingCategoryId - Exception: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Check products for available category id
	 *
	 */

	@Test
	public final void testGetProductsForNonExistingCategoryId() {
		Product product = new Product();
		int categoryId = -1;
		try {
			PhrescoLogger.info(TAG + " testGetProductsForNonExistingCategoryId -------------- START ");

			JSONObject productJSONObj = product.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.CATEGORIES_URL + categoryId);
			assertNotNull(productJSONObj);

			if (productJSONObj != null && productJSONObj.optString("type") != null && productJSONObj.optString("type").equalsIgnoreCase("failure")) {
//				ErrorManager errorObj = PhrescoActivity.getErrorGSONObject(productJSONObj);
				assertTrue(productJSONObj.optString("message"), productJSONObj.optString("message") != null);
			}

			PhrescoLogger.info(TAG + " testGetProductsForNonExistingCategoryId -------------- END ");
		} catch (IOException ex) {
			PhrescoLogger.info(TAG + "testGetProductsForNonExistingCategoryId - IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "testGetProductsForNonExistingCategoryId - Exception: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

}
