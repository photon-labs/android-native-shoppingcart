/**
 *
 */
package com.photon.phresco.nativeapp.unit.test;

import junit.framework.TestSuite;

import com.photon.phresco.nativeapp.unit.test.testcases.A_MainActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.B_CategoryListActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.C_ProductListActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.D_ProductDetailActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.E_OffersActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.F_ProductReviewActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.G_OrderReviewActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.H_LoginActivityTest;
import com.photon.phresco.nativeapp.unit.test.testcases.I_RegistrationActivityTest;

/**
 * @author viral_b
 *
 */
public class AllTests extends TestSuite {
	public static TestSuite suite() {

		TestSuite suite = new TestSuite(AllTests.class.getName());

		suite.addTestSuite(A_MainActivityTest.class);
		suite.addTestSuite(B_CategoryListActivityTest.class);
		suite.addTestSuite(C_ProductListActivityTest.class);
		suite.addTestSuite(D_ProductDetailActivityTest.class);
		suite.addTestSuite(E_OffersActivityTest.class);
		suite.addTestSuite(F_ProductReviewActivityTest.class);
		suite.addTestSuite(G_OrderReviewActivityTest.class);
		suite.addTestSuite(H_LoginActivityTest.class);
		suite.addTestSuite(I_RegistrationActivityTest.class);

		return suite;

	}
	 public ClassLoader getLoader() {
	        return AllTests.class.getClassLoader();
	    }
}
