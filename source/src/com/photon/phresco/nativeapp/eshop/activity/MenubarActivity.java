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
package com.photon.phresco.nativeapp.eshop.activity;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.appconfig.FeatureConfig;

/**
 * Custom menubar, to render the menu options read from web server at the bottom
 * of each screen
 *
 * @author viral_b
 *
 */
public class MenubarActivity extends LinearLayout implements OnClickListener, Serializable {

	private static final String TAG = "MenubarActivity  *********** ";

	private static final long serialVersionUID = 1L;
	private Intent menuItemIntent;

	private String currActivity = "currentActivity";
	private String myCartActivity = "mycart";

	private String categoryListActivity = "browse";
	private String homeActivity = "home";
	private String moreOptionsActivity = "more";
	private String offersActivity = "offers";

	/**
	 * Create the menu bar using the menu images read from web server
	 *
	 * @param context
	 * @param attrs
	 */
	public MenubarActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			setOrientation(HORIZONTAL);
			setGravity(Gravity.CENTER | Gravity.BOTTOM);

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.menubar, this, true);

			RelativeLayout rl = (RelativeLayout) findViewById(R.id.menubar_layout);

			LinearLayout l = new LinearLayout(getContext());
			l.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			l.setOrientation(HORIZONTAL);
			l.setGravity(Gravity.BOTTOM | Gravity.CENTER);
			l.setBaselineAligned(true);
			rl.addView(l);

			buildMenu(context, l);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - MenubarActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Build the menu items
	 *
	 * @param context
	 * @param l
	 */
	private void buildMenu(Context context, LinearLayout l) {
		try {
			menuItemIntent = ((Activity) context).getIntent();
			ImageCacheManager imgCacheManager = new ImageCacheManager();

			// Set the default images for all the menu items
			setDefaultImageForMenu(l, imgCacheManager);

			// Depending upon the current screen, show the corresponding menu
			// item as highlighted
			setHighlightedImageForMenu(imgCacheManager);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - buildMenu  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Set the default images for all the menu items
	 *
	 * @param l
	 * @param imgCacheManager
	 */
	private void setDefaultImageForMenu(LinearLayout l, ImageCacheManager imgCacheManager) {
		ImageView imgView = null;
		try {
			if (PhrescoActivity.getAppConfigJSONObj().getFeatureConfig() != null) {
				for (FeatureConfig f : PhrescoActivity.getAppConfigJSONObj().getFeatureConfig()) {

					imgView = new ImageView(getContext());
					imgView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					imgView.setOnClickListener(this);
					imgView.setTag(f);
					imgCacheManager.renderImage(imgView, Constants.MENU_FOLDER_PATH + f.getFeatureIcon().getDefaultTab().substring(f.getFeatureIcon().getDefaultTab().lastIndexOf("/") + 1));
					l.addView(imgView);
				}

			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - setDefaultImageForMenu  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Set the highlighted image for the selected menu item
	 *
	 * @param imgCacheManager
	 */
	private void setHighlightedImageForMenu(ImageCacheManager imgCacheManager) {
		try {
			if (PhrescoActivity.getAppConfigJSONObj().getFeatureConfig() != null) {
				for (FeatureConfig f : PhrescoActivity.getAppConfigJSONObj().getFeatureConfig()) {
					if (menuItemIntent != null && menuItemIntent.getStringExtra(currActivity).equalsIgnoreCase(f.getName())) {
						PhrescoLogger.info(TAG + f.getName());
						ImageView img = (ImageView) findViewWithTag(f);
						imgCacheManager.renderImage(img, Constants.MENU_FOLDER_PATH + f.getFeatureIcon().getHighlightedTab().substring(f.getFeatureIcon().getHighlightedTab().lastIndexOf("/") + 1));
						break;
					}
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - setHighlightedImageForMenu  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	public void onClick(View v) {
		try {

			int mOffset[] = new int[2];
		    v.getLocationOnScreen( mOffset );
		    PhrescoLogger.info(TAG + "Left (X): " +  mOffset[0]);
		    PhrescoLogger.info(TAG + "Top (Y): " +  mOffset[1]);

			FeatureConfig f = (FeatureConfig) v.getTag();
			String menuItem = f.getName();

			finishCurrentActivity();
			startActivity(menuItem);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onClick  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Send one string parameter with intent for currently selected menu, to
	 * identity which menu item should be highlighted
	 *
	 * @param menuItem
	 */
	private void startActivity(String menuItem) {
		Intent activity = null;

		if (menuItem.equalsIgnoreCase(homeActivity)) {
			activity = new Intent(getContext(), HomeActivity.class);
			activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.putExtra(currActivity, homeActivity);
			getContext().startActivity(activity);
		} else if (menuItem.equalsIgnoreCase(categoryListActivity)) {
			activity = new Intent(getContext(), CategoryListActivity.class);
			activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			if (menuItemIntent != null && menuItemIntent.getStringExtra(currActivity).equalsIgnoreCase(homeActivity)) {
				activity.putExtra("previousActivity", homeActivity);
			}
			activity.putExtra(currActivity, categoryListActivity);
			getContext().startActivity(activity);
		} else if (menuItem.equalsIgnoreCase(myCartActivity)) {
			activity = new Intent(getContext(), MyCartActivity.class);
			activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.putExtra(currActivity, myCartActivity);
			getContext().startActivity(activity);
		} else if (menuItem.equalsIgnoreCase(offersActivity)) {
			activity = new Intent(getContext(), OffersActivity.class);
			activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.putExtra(currActivity, offersActivity);
			getContext().startActivity(activity);
		} else if (menuItem.equalsIgnoreCase(moreOptionsActivity)) {
			activity = new Intent(getContext(), MoreOptionsActivity.class);
			activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			activity.putExtra(currActivity, moreOptionsActivity);
			getContext().startActivity(activity);
		}
	}

	/**
	 * If current activity is Login, Registration or Checkout, and any of the
	 * menu item is clicked,finish the current activity
	 */
	private void finishCurrentActivity() {

		if (((Activity) getContext()).getClass().getName().contains("LoginActivity")) {
			((LoginActivity) getContext()).finish();
		} else if (((Activity) getContext()).getClass().getName().contains("RegistrationActivity")) {
			((RegistrationActivity) getContext()).finish();
		} else if (((Activity) getContext()).getClass().getName().contains("CheckOutActivity")) {
			((CheckOutActivity) getContext()).finish();
		} else if (((Activity) getContext()).getClass().getName().contains("ProductReviewCommentActivity")) {
			((ProductReviewCommentActivity) getContext()).finish();
		}
	}
}
