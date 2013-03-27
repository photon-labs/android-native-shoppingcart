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
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.nativeapp.functional.test.testcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.eshop.activity.MainActivity;

@SuppressWarnings("unchecked")
public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	/**
	 * This is suite testcase by this testcase will call other testcases . In
	 * static block we are loading the MainActivity class and from the
	 * constructor will pass the package and activity full class name then in
	 * setUp() created the Solo class object
	 * 
	 */
	public static final String PACKAGE_NAME = "com.photon.phresco.nativeapp";
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.photon.phresco.nativeapp.eshop.activity.MainActivity";
	private static Class<MainActivity> mainActivity;
	private Solo soloMain;
	private LoginVerificationTest loginTestCase;
	private LoginValidationTest loginValid;
	
	
	private OffersTest specialOffers;
	private CategoryListValidationTest browseValid;
	private CategoryListVerificationTest browseTestCase;
	private RegistrationVerificationTest registerTestCase;
	private RegisterValidationTest registrationValid;
	private LongPauseTest longPauseTest;
	private FailureTest failureTest;
	

	private static final String TAG = "****MainTestCase****";

	/**
	 * This block will be executed first and it will loads the SplashActivity .
	 */
	static {
		try {
			mainActivity = (Class<MainActivity>) Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		}

		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * In this constructor , we have to send the packagename and activity full
	 * class name.
	 * 
	 * @throws Exception
	 */
	public MainActivityTest() throws TestException {
		super(PACKAGE_NAME, mainActivity);
	}

	/**
	 * this method for create the Solo class object having two super class
	 * methods..
	 * 
	 */
	@Override
	public void setUp() {

		soloMain = new Solo(getInstrumentation(), getActivity());

	}

	/**
	 * This test method will execute the testLoginScenario() .It will verifies
	 * the Login screen verification.
	 * 
	 * @throws TestException
	 */
	@Smoke
	public void testVerificationLogin() throws TestException {

		try {
			Log.i(TAG, "testVerificationLogin---------Start");
			// creating object of the testclass LoginVerificationTestCase
			loginTestCase = new LoginVerificationTest(soloMain);
			// calling the test method
			loginTestCase.testLoginVerification(getInstrumentation());
			Log.i(TAG, "testVerificationLogin---------End");

		} catch (TestException e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}

	}

	/**
	 * This test method will call testLoginValidation() method.It will verifies
	 * the Validation for Login screen.
	 * 
	 */

	public void testValidationLogin() throws TestException {

		try {
			Log.i(TAG, "testValidationLogin---------Start");
			// creating object of the class LoginValidationTestCase
			loginValid = new LoginValidationTest(soloMain);
			loginValid.testLoginValidation(getInstrumentation());
			Log.i(TAG, "testValidationLogin---------End");
		} catch (TestException e) {
			Log.e(TAG, Log.getStackTraceString(e));

		}

	}

	// artf549283 : As disscussed with Arunachalam sir, all the tests are
	// commented except login validation & verification

	/**
	 * With this method we can check the Registration Verification scenario by
	 * using testRegisterScenario().
	 * 
	 */

	/*
	  public void testRegististrationVerification() throws TestException {
	  
	  try {
	  
	  Log.i(TAG, "testRegististrationVerification---------Start"); 
	 // RegisterTestcase object creating and passing Solo class object
	  registerTestCase = new RegistrationVerificationTest(soloMain); // calling
	  //the test method with Register object
	  registerTestCase.testRegisterScenario(getInstrumentation()); Log.i(TAG,
	  "testRegististrationVerification---------End");
	  
	  } catch (TestException e) { Log.e(TAG,Log.getStackTraceString(e)); }
	  
	  }*/
	 

	/**
	 * With this method we can check the Registration validation scenario .
	 * 
	 * @throws TestException
	 */

	/*
	 @Smoke public void testRegististrationValidation() throws TestException {
	  
	  try { Log.i(TAG, "testRegististrationValidation---------Start"); //
	  //creating object of the testclass RegisterValidationTestCase
	  registrationValid = new RegisterValidationTest(soloMain);
	  registrationValid.testRegisterValidation(getInstrumentation());
	  Log.i(TAG, "testRegististrationValidation---------End");
	  
	  } catch (TestException e) { Log.e(TAG,Log.getStackTraceString(e));
	  
	  }
	  
	  }
	 */

	/**
	 * This test method will call the testBrowseVerification().It will execute
	 * the Browse verification scenario.
	 * 
	 */

	/*
	  @Smoke public void testXBrowseVerification() throws TestException {
	  
	  try { Log.i(TAG, "testBrowseVerification---------Start"); // creating
	  // of the class 
	  browseTestCase = new CategoryListVerificationTest(soloMain); // calling the test method
	 // withBrowseTestCase object 
	  browseTestCase.testBrowseVerification(getInstrumentation());
	 
	  Log.i(TAG, "testBrowseVerification---------End");
	  
	  }catch (TestException e) { 
		  Log.e(TAG,Log.getStackTraceString(e));
	  
	  }
	  
	  }
	 */

	/**
	 * This test method will call the testBrowseValidation() .It will check the
	 * Browse Validation scenario.
	 */

	/*
	  @Smoke public void testBrowseValidation() throws TestException {
	  
	  try { Log.i(TAG, "testBrowseValidation---------Start"); 
	  // creating object of the classBrowseValidationTestCase 
	  browseValid = new CategoryListValidationTest(soloMain); 
	  browseValid.testBrowseValidation(getInstrumentation());
	  Log.i(TAG, "testBrowseValidation---------End");
	  
	  } catch (TestException e) { Log.e(TAG,Log.getStackTraceString(e));
	 
	 }
	 
   }*/
	 

	/**
	 * 
	 * This method will call the testSpecialOffers() by this we can see the test
	 * scenario for specialoffers
	 */

	/*
	 public void testSpecialOffers() throws TestException {
	  
	 try { Log.i(TAG, "testSpecialOffers---------Start"); 
	 specialOffers = new OffersTest(soloMain); 
	 specialOffers.testSpecialOffers(getInstrumentation());
	 Log.i(TAG,"testSpecialOffers---------End");
	  
	 } catch (TestException e) { Log.e(TAG,Log.getStackTraceString(e));
	  
	  }
	 
	  }*/
	 

	/**
	 * 
	 * This method will call the testWLongPause() by this we can see the test
	 * scenario LongPause
	 */

	/*
	  public void testWLongPause() throws TestException {
	 
	  try { Log.i(TAG, "testSpecialOffers---------Start"); 
	  longPauseTest = new LongPauseTest(soloMain); 
	  longPauseTest.testLongPause();
	  Log.i(TAG,"testSpecialOffers---------End");
	  
	  } catch (TestException e) { Log.e(TAG,Log.getStackTraceString(e));
	  
	  }
	  
	  }*/
	 

	/**
	 * 
	 * This method will call the testWLongPause() by this we can see the test
	 * scenario LongPause
	 */

	/*
	  public void testXFailure() throws TestException {
	  
	  try { 
		  Log.i(TAG, "testSpecialOffers---------Start");
	      failureTest = new FailureTest(soloMain); 
	      failureTest.testFailure();
	      Log.i(TAG,"testSpecialOffers---------End");
	  
	  } catch (TestException e) { Log.e(TAG,Log.getStackTraceString(e));
	  
	  }
	  
	  }*/
	 

	/**
	 * Once the testcases executed completely. This method will be called and
	 * will close the all activities this is overridden with super class
	 * tearDown()method
	 */
	@Override
	protected void tearDown() throws Exception {

		try {
			soloMain.finishOpenedActivities();
		} catch (Exception e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}
		getActivity().finish();
		super.tearDown();

	}

}