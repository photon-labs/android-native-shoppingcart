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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.adapter.MoreOptionsCustomAdapter;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Show the other options when More menu item is selected
 *
 * @author viral_b
 *
 */
public class MoreOptionsActivity extends PhrescoActivity {

	private static final String TAG = "MoreOptionsActivity ***** ";

	private ImageButton backButton;
	private GridView moreOptionsGrid;
	private int count=0;
	private int counter=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_options);

		initEnvironment();

		moreOptionsGrid.setAdapter(new MoreOptionsCustomAdapter(this));

		moreOptionsGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				if(count==0 && v.getTag().toString().equalsIgnoreCase("Wishlist")){
					Toast.makeText(MoreOptionsActivity.this, "You have selected: " + v.getTag(), Toast.LENGTH_SHORT).show();
					PhrescoLogger.info(TAG + " - Wishlist is clicked : " + v.getTag());
					count++;
				}else if(counter==0 && v.getTag().toString().equalsIgnoreCase("Coupens")){
					Toast.makeText(MoreOptionsActivity.this, "You have selected: " + v.getTag(), Toast.LENGTH_SHORT).show();
					PhrescoLogger.info(TAG + " - Coupens  is clicked : " + v.getTag());
					counter++;
				}
			}
		});

		moreOptionsGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				showOptions();
				return false;
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startCategoryListActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

	}

	/**
	 * start category list activity
	 */
	private void startCategoryListActivity() {
		try {
			Intent categoryListActivity = null;
			categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra("currentActivity", "browse");
			startActivity(categoryListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCategoryListActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);
			moreOptionsGrid = (GridView) findViewById(R.id.grid_menu_options);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	private void showOptions() {
		final CharSequence[] items = {"Option One", "Option two", "Option Three"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("Make your selection");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		         Toast.makeText(getBaseContext(), items[item] + " selected", Toast.LENGTH_SHORT).show();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
