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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.adapter.CategoryListViewCustomAdapter;
import com.photon.phresco.nativeapp.eshop.application.PhrescoApp;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.db.AppConfiguration;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.category.Category;
import com.photon.phresco.nativeapp.eshop.util.Utility;

/**
 * Show category listing screen
 *
 * @author viral_b
 *
 */
public class CategoryListActivity extends PhrescoActivity {

	private static final String TAG = "CategoryListActivity ***** ";

	private ImageButton backButton, searchButton, browseButton, offersButton, myCartButton;
	private ListView categoryListview;
	private static List<Category> categoryList = null;

	private String previousActivity = null;
	private String currentActivity = null;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	private Exception ioException = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list);
		initEnvironment();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			previousActivity = extras.getString(prevActivity);
			currentActivity = extras.getString(currActivity);
		}
		String previousActivityOrientactionCheck = (String) getLastNonConfigurationInstance();

		PhrescoLogger.info(TAG + " - previousActivity == " + previousActivity);
		PhrescoLogger.info(TAG + " - currentActivity == " + currentActivity);
		PhrescoLogger.info(TAG + " - previousActivityOrientactionCheck ==  " + previousActivityOrientactionCheck);
		try {
			if (previousActivity != null && previousActivityOrientactionCheck == null) {
				PhrescoLogger.info(TAG + " - CategoryList -  Home screen ");
				// Create an array list to hold the the categories
				prepareCategoryList();

			} else if (previousActivity != null && previousActivityOrientactionCheck != null) {
				PhrescoLogger.info(TAG + " - CategoryList -  Home screen - screen orientation changed");
				// Create an array list to hold the the categories
				buildCategoryListAdapter();

			} else {
				if (categoryList == null || categoryList.size() <= 0) {
					PhrescoLogger.info(TAG + " - CategoryList - previousActivity AND categoryList == null");
					prepareCategoryList();
				} else {
					PhrescoLogger.info(TAG + " - Using the existing category list");
					buildCategoryListAdapter();
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - previousActivity check : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		categoryListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				try {

					PhrescoLogger.info(TAG + " - categoryListview.onItemClick  - position : " + position);
					PhrescoLogger.info(TAG + " - categoryListview.onItemClick  - rowId : " + rowId);
					startProductListActivity(position);
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - categoryListview  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					goBackToPreviousActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startProductListActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - searchButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		offersButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startOffersActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - offersButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		myCartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startMyCartActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - myCartButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

	}

	@Override
	public String onRetainNonConfigurationInstance() {
		// String retValue;
		PhrescoLogger.info(TAG + " - onRetainNonConfigurationInstance - previousActivity = : " + previousActivity);
		return previousActivity;
	}

	/**
	 * Move back to home screen
	 */
	private void goBackToPreviousActivity() {
		try {
			Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
			homeActivity.putExtra(currActivity, "home");
			startActivity(homeActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start offers activity
	 */
	private void startOffersActivity() {
		try {
			Intent offersActivity = new Intent(getApplicationContext(), OffersActivity.class);
			offersActivity.putExtra(currActivity, "offers");
			startActivity(offersActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startOffersActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start my cart activity
	 */
	private void startMyCartActivity() {
		try {
			Intent myCartActivity = new Intent(getApplicationContext(), MyCartActivity.class);
			myCartActivity.putExtra(currActivity, "mycart");
			startActivity(myCartActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startMyCartActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);

			browseButton = (ImageButton) findViewById(R.id.tab_browse);
			offersButton = (ImageButton) findViewById(R.id.tab_specialoffer);
			myCartButton = (ImageButton) findViewById(R.id.tab_mycart);

			searchButton = (ImageButton) findViewById(R.id.category_search_btn);
			categoryListview = (ListView) findViewById(R.id.category_listview);

			browseButton.setBackgroundResource(R.drawable.browse_tab_selected);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			myCartButton.setBackgroundResource(R.drawable.mycart_tab_normal);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show to product list screen when a category is selected from the list
	 *
	 * @param position
	 *            current position of the item in category list
	 */
	private void startProductListActivity(int position) {
		try {
			Intent productListActivity = new Intent(getApplicationContext(), ProductListActivity.class);
			productListActivity.putExtra(currActivity, "browse");
			productListActivity.putExtra(prevActivity, "categoryList");
			productListActivity.putExtra("categoryId", ((Category) categoryList.get(position)).getId());
			startActivity(productListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductListActivity(position)  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show to product list screen when a product search is initiated
	 */
	private void startProductListActivity() {
		try {
			PhrescoLogger.info(TAG + " - startProductListActivity : ");
			Intent productListActivity = new Intent(getApplicationContext(), ProductListActivity.class);
			productListActivity.putExtra(currActivity, "browse");
			productListActivity.putExtra(prevActivity, "categoryList");
			if (((EditText) findViewById(R.id.txt_search)).getText().toString().length() > 0) {
				productListActivity.putExtra("search", ((EditText) findViewById(R.id.txt_search)).getText().toString());
			}
			startActivity(productListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductListActivity - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Read the categories JSON from web server, in async task.
	 */
	private void prepareCategoryList() {
		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(CategoryListActivity.this);

			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.load_categories));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					PhrescoLogger.info(TAG + " prepareCategoryList - processOnStart() ");
					getCategoriesFromServer();
				}

				@Override
				public void processOnComplete() {
					PhrescoLogger.info(TAG + " prepareCategoryList - processOnComplete() ");
					showCategories();
				}

			});
			asyncTask.execute();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the categories information from web server
	 */
	private void getCategoriesFromServer() {
		try {
			getCategoriesJSONObject();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the categories list, if there is no error, else show the error
	 * message
	 */
	private void showCategories() {
		if (ioException == null) {
			buildCategoryListAdapter();
		} else {
			showErrorDialogWithCancel();
		}
	}

	/**
	 * Build the adapter to bind the category listview
	 */
	private void buildCategoryListAdapter() {
		PhrescoLogger.info(TAG + " Inside buildCategoryListAdapter");
		CategoryListViewCustomAdapter adapter = null;
		try {
			adapter = new CategoryListViewCustomAdapter(this, categoryList);
			categoryListview.setAdapter(adapter);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " buildCategoryListAdapter - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Read the categories JSON from web server
	 */
	private void getCategoriesJSONObject() throws IOException {

		PhrescoLogger.info(TAG + " Inside getCategoriesJSONObject ");
		JSONObject jObject = null;

		Category categoryObj = new Category();
		jObject = categoryObj.getCategoryJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.CATEGORIES_URL);

		if (jObject != null) {
			try {
				// Create an object for Gson (used to create the JSON object)
				categoryList = categoryObj.getCategoryGSONObject(jObject);
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "getCategoriesJSONObject() - getCategoryGSONObject - Exception : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
			try {
				searchCategoryVersionNoInLocalDatabase();
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "getCategoriesJSONObject(): searchCategoryVersionNoInLocalDatabase - Exception : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
		} else {
			categoryList = null;
		}

	}

	/**
	 * Search the category version number in local database. Update the category
	 * version number with AppVersionNo read from AppConfig JSON, if it's
	 * present, Else insert the AppVersionNo as category version number in
	 * database
	 *
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	private void searchCategoryVersionNoInLocalDatabase() {
		try {
			AppConfiguration appConf = new AppConfiguration(((PhrescoApp) getApplication()).getDatabase());

			// Check if categoryVersionNo is stored in local database
			Cursor searchCategoryVersionRow = appConf.searchAllRow("category_version");

			if (searchCategoryVersionRow.getCount() > 0) {
				updateCategoryVersionNoInLocalDatabase(appConf, searchCategoryVersionRow);
			} else {
				insertCategoryVersionNoInLocalDatabase(appConf);
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " buildCategoryListAdapter - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * insert the AppVersionNo as category version number in database
	 *
	 * @param appConf
	 */
	private void insertCategoryVersionNoInLocalDatabase(AppConfiguration appConf) {
		try {
			// if CategoryVersion does not exist in local database,
			// insert CategoryVersion into database.
			appConf.createRow("category_version", String.valueOf(getAppConfigJSONObj().getAppVersionInfo().getConfigVersion()));
			PhrescoLogger.info(TAG + " - INSERT Category Version = " + getAppConfigJSONObj().getAppVersionInfo().getConfigVersion() + " in local database");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "insertCategoryVersionNoInLocalDatabase - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Update the Category VersionNo in local database, if already exists
	 *
	 * @param appConf
	 * @param searchCategoryVersionRow
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	private void updateCategoryVersionNoInLocalDatabase(AppConfiguration appConf, Cursor searchCategoryVersionRow) {
		try {
			searchCategoryVersionRow.moveToFirst();

			// Get the row id of current categoryVersionNo from SQLite db
			long currentRowId = searchCategoryVersionRow.getLong(searchCategoryVersionRow.getColumnIndexOrThrow(AppConfiguration.KEY_ROWID));

			// Get the current categoryVersionNo from SQLite db
			String categoryVersionNo = searchCategoryVersionRow.getString(searchCategoryVersionRow.getColumnIndex(AppConfiguration.KEY_META_VALUE));

			PhrescoLogger.info(TAG + " - Existing CategoryVersion = " + categoryVersionNo + " in local database");
			PhrescoLogger.info(TAG + " - appConfigJSONObj.appVersionInfo.configVersionn = " + getAppConfigJSONObj().getAppVersionInfo().getConfigVersion());

			if (Integer.parseInt(getAppConfigJSONObj().getAppVersionInfo().getConfigVersion()) != Integer.parseInt(categoryVersionNo)) {
				Utility.deleteDir(new File(Constants.CATEGORIES_FOLDER_PATH));
				Utility.createDirectory(new File(Constants.CATEGORIES_FOLDER_PATH));
				downloadCategoryImages();
				appConf.updateRow(currentRowId, "category_version", String.valueOf(getAppConfigJSONObj().getAppVersionInfo().getConfigVersion()));
				PhrescoLogger.info(TAG + " - UPDATE CategoryVersion = " + getAppConfigJSONObj().getAppVersionInfo().getConfigVersion() + " in local database");
			}
			searchCategoryVersionRow.close();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "updateCategoryVersionNoInLocalDatabase - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Download category images on local storage device
	 */
	private void downloadCategoryImages() {
		try {
			Category categoryObj = new Category();
			categoryObj.downloadCategoryImages(categoryList);
		} catch (IOException ioe) {
			PhrescoLogger.warning(ioe);
			showErrorDialogWithCancel();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "downloadCategories - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
