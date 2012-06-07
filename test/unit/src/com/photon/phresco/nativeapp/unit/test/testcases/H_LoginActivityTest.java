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
public class H_LoginActivityTest extends TestCase{
	private static final String TAG = "H_LoginActivityTest  *********** ";

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
	 *  send valid login email id and password to web server
	 *
	 */
	@Test
	public final void testLogin() {

		JSONObject jObjMain = new JSONObject();
		JSONObject jObj = new JSONObject();

		try {
			PhrescoLogger.info(TAG + " testLogin -------------- START ");

				jObj.put("loginEmail", "tester@phresco.com");
				jObj.put("password", "123");
				jObjMain.put("login", jObj);

				JSONObject responseJSON = null;
				responseJSON=JSONHelper.postJSONObjectToURL(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.LOGIN_POST_URL, jObjMain.toString());
				assertNotNull("Login response is not null",responseJSON.length() > 0);

				PhrescoLogger.info(TAG + " testLogin -------------- END ");

		} catch (IOException ex) {
			PhrescoLogger.info(TAG + " - testLogin  - IOException : " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + " - testLogin  - JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}


	/**
	 *  send valid login email id and password to web server
	 *
	 */
	@Test
	public final void testLoginCredantial() {

		JSONObject jObjMain = new JSONObject();
		JSONObject jObj = new JSONObject();

		try {
			PhrescoLogger.info(TAG + " testLoginCredantial -------------- START ");

				jObj.put("loginEmail", "");
				jObj.put("password", "123");

				jObjMain.put("login", jObj);

				JSONObject responseJSON = null;
				responseJSON=JSONHelper.postJSONObjectToURL(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.LOGIN_POST_URL, jObjMain.toString());
				assertNotNull("Login failed",responseJSON.length() > 0);

				PhrescoLogger.info(TAG + " testLoginCredantial -------------- END ");

		} catch (IOException ex) {
			PhrescoLogger.info(TAG + " - testLoginCredantial  - IOException : " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (JSONException ex) {
			PhrescoLogger.info(TAG + " - testLoginCredantial  - JSONException : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

}
