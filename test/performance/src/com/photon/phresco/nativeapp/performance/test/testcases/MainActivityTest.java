/**
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.nativeapp.performance.test.testcases;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.eshop.activity.MainActivity;

@SuppressWarnings("unchecked")
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

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
	private OffersTest specialOffers;
	private CategoryListValidationTest browseValid;
	private LoginValidationTest loginValid;
	private CategoryListVerificationTest browseTestCase;
	private RegistrationVerificationTest registerTestCase;
	private RegisterValidationTest registrationValid;
	private final String TAG = "MainTestCase****";

	/**
	 * This block will be executed first and it will loads the SplashActivity .
	 */
	static {
		try {
			mainActivity = (Class<MainActivity>) Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
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
	public MainActivityTest() throws Exception {
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
	 * With this method we can check the Registration Verification scenario by
	 * using testRegisterScenario().
	 * 
	 */

	public void testRegististrationVerification() throws TestException {

		try {

			Log.i(TAG, "testRegististrationVerification---------Start");
			// RegisterTestcase object creating and passing Solo class object
			registerTestCase = new RegistrationVerificationTest(soloMain);
			// calling the test method with Register object
			registerTestCase.testRegisterScenario();
			Log.i(TAG, "testRegististrationVerification---------End");

		} catch (TestException e) {
			e.printStackTrace();

		}

	}

	/**
	 * With this method we can check the Registration validation scenario .
	 * 
	 * @throws TestException
	 */

	@Smoke
	public void testRegististrationValidation() throws TestException {

		try {
			Log.i(TAG, "testRegististrationValidation---------Start");
			// creating object of the testclass RegisterValidationTestCase
			registrationValid = new RegisterValidationTest(soloMain);
			registrationValid.testRegisterValidation();
			Log.i(TAG, "testRegististrationValidation---------End");

		} catch (TestException e) {
			e.printStackTrace();

		}

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
			loginTestCase.testLoginVerification();
			Log.i(TAG, "testVerificationLogin---------End");

		} catch (TestException e) {
			e.printStackTrace();
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
			// creating objectof the class LoginValidationTestCase
			loginValid = new LoginValidationTest(soloMain);
			loginValid.testLoginValidation();
			Log.i(TAG, "testValidationLogin---------End");
		} catch (TestException e) {
			e.printStackTrace();

		}

	}

	/**
	 * This test method will call the testBrowseVerification().It will execute
	 * the Browse verification scenario.
	 * 
	 */

	@Smoke
	public void testBrowseVerification() throws TestException {

		try {
			Log.i(TAG, "testBrowseVerification---------Start");
			// creatingobject of the class
			browseTestCase = new CategoryListVerificationTest(soloMain);
			// calling the test method withBrowseTestCase object
			browseTestCase.testBrowseVerification();
			Log.i(TAG, "testBrowseVerification---------End");

		} catch (TestException e) {
			e.printStackTrace();

		}

	}

	/**
	 * This test method will call the testBrowseValidation() .It will check the
	 * Browse Validation scenario.
	 */

	@Smoke
	public void testBrowseValidation() throws TestException {

		try {
			Log.i(TAG, "testBrowseValidation---------Start");
			// creating object of the class BrowseValidationTestCase
			browseValid = new CategoryListValidationTest(soloMain);
			browseValid.testBrowseValidation();
			Log.i(TAG, "testBrowseValidation---------End");

		} catch (TestException e) {
			e.printStackTrace();

		}

	}

	/**
	 * 
	 * This method will call the testSpecialOffers() by this we can see the test
	 * scenario for specialoffers
	 */

	public void testSpecialOffers() throws TestException {

		try {
			Log.i(TAG, "testSpecialOffers---------Start");
			specialOffers = new OffersTest(soloMain);
			// calling the test method with BrowseTestCase object
			specialOffers.testSpecialOffers();
			Log.i(TAG, "testSpecialOffers---------End");

		} catch (TestException e) {
			e.printStackTrace();

		}

	}

	/**
	 * Once the testcases executed completely. This method will be called and
	 * will close the all activities this is overridden with super class
	 * tearDown()method
	 */
	@Override
	protected void tearDown() throws Exception {

		try {
			soloMain.finishOpenedActivities();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();

	}

	
	
	
}
