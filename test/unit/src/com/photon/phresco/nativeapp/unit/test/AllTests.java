/*
 * ###
 * PHR_AndroidNative
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
