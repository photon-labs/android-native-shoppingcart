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

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.jayway.android.robotium.solo.Solo;
import com.photon.phresco.nativeapp.R;

public class LongPauseTest extends TestCase {

	private Solo soloLongPause;
	private String activityName;
	private static final String TAG = "*****LongPauseTest******";
	private GridView gridView;

	public LongPauseTest(Solo solo) {
		this.soloLongPause = solo;

	}

	public void testLongPause() throws TestException {

		try {
			Log.i(TAG, "------It is testLongPause()-----------");

			activityName = soloLongPause.getCurrentActivity().getClass()
					.getSimpleName();

			if (activityName.equalsIgnoreCase("MainActivity")) {
				Log.i(TAG, "------It is MainActivity-----------" + activityName);
				soloLongPause.waitForActivity("HomeActivity", 2000);

				for (int i = 0; i < 40; i++) {
					activityName = soloLongPause.getCurrentActivity()
							.getClass().getSimpleName();
					if (activityName.equalsIgnoreCase("HomeActivity")) {

						Log.i(TAG, "------for()-- loop-----");
						break;
					}

					soloLongPause.waitForActivity("HomeActivity", 2000);
				}

			} else {
				Log.i(TAG, "------ testLongPause failed-----------");
				throw new TestException("Current Activity Failed----"
						+ soloLongPause.getCurrentActivity().getClass()
								.getSimpleName() + "failed");
			}

			if (activityName.equalsIgnoreCase("HomeActivity")) {
				Log.i(TAG, "------HomeActivity-----------");
				Log.i(TAG," Activity name ---->"+soloLongPause.getCurrentActivity());
				ArrayList<View> al = soloLongPause.getViews();
				Iterator<View> it = al.iterator();
				while (it.hasNext()) {
					String viewName = it.next().getClass().getSimpleName();
					if (viewName.equalsIgnoreCase("ImageView")) {
						Log.i(TAG, "------ImageView found-----------");
						break;
					}
					continue;
				}

			} else {
				Log.i(TAG, "------HomeActivity not  found-----------");
				throw new TestException(TAG
						+ soloLongPause.getCurrentActivity().getClass()
								.getSimpleName() + "failed");
			}

			// click on the ImageView according to the index value

			soloLongPause.clickOnImage(11);

			soloLongPause.waitForActivity("HomeActivity", 5000);

			gridView = (GridView) soloLongPause.getView(R.id.grid_menu_options);
			// click on the element in the GridView by using the index values.

			soloLongPause.clickLongOnView(gridView.getChildAt(1));

			soloLongPause.waitForActivity("MoreOptionsActivity", 8000);

			soloLongPause.clickOnText("Option two");
			soloLongPause.waitForActivity("MoreOptionsActivity", 5000);

		} catch (TestException e) {
			Log.e(TAG,Log.getStackTraceString(e));
		}

	}

}
