/**
 *
 */
package com.photon.phresco.nativeapp.unit.test.testcases;

import java.io.IOException;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.unit.test.core.Constants;

/**
 * @author chandankumar_r
 *
 */
public class F_ProductReviewActivityTest extends TestCase{
	private static final String TAG = "F_ProductReviewActivityTest *********** ";
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
	 * get the product review comments
	 * from web server
	 *
	 */

	@Test
	public final void testProductReview() {
		try{
			PhrescoLogger.info(TAG + " testProductReview -------------- START ");

			int productId=1;
				JSONObject prodReviewJSONObj=JSONHelper.getJSONObjectFromURL(Constants.getWebContextURL()+ Constants.getRestAPI()+ Constants.PRODUCTS_URL + productId + "/" + Constants.PRODUCT_REVIEW_URL);
				JSONObject productReviewArray = prodReviewJSONObj.getJSONObject("review");
				assertTrue(productReviewArray.length() > 0);

				PhrescoLogger.info(TAG + " testProductReview -------------- END ");
			} catch (IOException ex) {
				PhrescoLogger.info(TAG + "testProductReview - IOException: " + ex.toString());
				PhrescoLogger.warning(ex);
			}catch(JSONException ex){
			PhrescoLogger.info(TAG + "testProductReview - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 *  get the posted review comments related
	 *  product from web server
	 *
	 */
/*	@Test
	public final void testProductReviewComments() {
		try{
			PhrescoLogger.info(TAG + " testProductReviewComments -------------- START ");

			int productId=22;
				JSONObject prodReviewJSONObj=JSONHelper.getJSONObjectFromURL(Constants.getWebContextURL()+ Constants.getRestAPI() + Constants.PRODUCTS_URL + productId + "/" + Constants.PRODUCT_REVIEW_URL);
				JSONObject productReviewArray = prodReviewJSONObj.getJSONObject("review");
				assertTrue("Product reviews available", productReviewArray.length() < 0);

				PhrescoLogger.info(TAG + " testProductReviewComments -------------- END ");
			} catch (IOException ex) {
				PhrescoLogger.info(TAG + "testProductReviewComments - IOException: " + ex.toString());
				PhrescoLogger.warning(ex);
			}catch(JSONException ex){
			PhrescoLogger.info(TAG + "testProductReviewComments - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}*/

}
