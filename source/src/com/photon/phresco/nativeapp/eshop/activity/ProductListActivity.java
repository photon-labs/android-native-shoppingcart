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

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.adapter.ProductListViewCustomAdapter;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.errormessage.ErrorManager;
import com.photon.phresco.nativeapp.eshop.model.product.Product;

/**
 * Show product listing screen depending upon the category selected
 *
 * @author viral_b
 *
 */
public class ProductListActivity extends PhrescoActivity {

	private static final String TAG = "ProductListActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton;

	private ListView productListview;
	private static List<Product> productList = null;
	private int categoryId = -1;
	private String previousActivity = null;
	private String search = null;
	private Bundle extras;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	private Exception ioException = null;
	private ErrorManager errorObj = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_list);
		initEnvironment();
		String previousActivityOrientactionCheck = "";
		try {
			extras = getIntent().getExtras();
			if (extras != null) {
				categoryId = getExtrasCategoryId();
				previousActivity = getExtrasPreviousActivity();
				search = getExtrasSearch();
			}
			previousActivityOrientactionCheck = (String) getLastNonConfigurationInstance();

			PhrescoLogger.info(TAG + " - previousActivity == " + previousActivity);
			PhrescoLogger.info(TAG + " - previousActivityOrientactionCheck ==  " + previousActivityOrientactionCheck);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Intent : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		try {

			// Create an array list to hold the the categories
			if (previousActivity != null && previousActivityOrientactionCheck == null) {
				PhrescoLogger.info(TAG + " - preparing ProductList for first time - coming from CategoryList screen ");
				// Create an array list to hold the products for current
				// category
				prepareProductList();

			} else if (previousActivity != null && previousActivityOrientactionCheck != null) {
				PhrescoLogger.info(TAG + " - ProductList - screen orientation changed");
				// Create an array list to hold the the categories
				buildProductListAdapter();

			} else {
				if (productList == null || productList.size() <= 0) {
					PhrescoLogger.info(TAG + " - preparing ProductList for first time when previousActivity == null AND productList == null");
					prepareProductList();
				} else {
					PhrescoLogger.info(TAG + " - Using the existing product list");
					buildProductListAdapter();
				}
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - previousActivity check : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		productListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				PhrescoLogger.info(TAG + " - productListview.onItemClick  - position : " + position);
				PhrescoLogger.info(TAG + " - productListview.onItemClick  - rowId : " + rowId);
				try {
					startProductDetailActivity(position);
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - productListview  - Exception : " + ex.toString());
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

		browseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startCategoryListActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - browseButton  - Exception : " + ex.toString());
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
	 * Get search phrase from intent extras
	 *
	 * @return
	 */
	private String getExtrasSearch() {
		return (extras.getString("search") != null && extras.getString("search").length() > 0) ? extras.getString("search") : null;
	}

	/**
	 * Get previous activity name from intent extras
	 *
	 * @return
	 */
	private String getExtrasPreviousActivity() {
		return (extras.getString(prevActivity) != null && extras.getString(prevActivity).length() > 0) ? extras.getString(prevActivity) : null;
	}

	/**
	 * Get category id from intent extras
	 *
	 * @return
	 * @throws NumberFormatException
	 */
	private int getExtrasCategoryId() {
		return String.valueOf(extras.getInt("categoryId")) != null ? extras.getInt("categoryId") : -1;
	}

	/**
	 * Move back to previous activity
	 */
	private void goBackToPreviousActivity() {
		try {
			Intent categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra(currActivity, "browse");
			startActivity(categoryListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start category list activity
	 */
	private void startCategoryListActivity() {
		try {
			goBackToPreviousActivity();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCategoryListActivity  - Exception : " + ex.toString());
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
	 * Show the product details screen, for selected product
	 *
	 * @param position
	 */
	private void startProductDetailActivity(int position) {
		PhrescoLogger.info(TAG + " - startProductDetailActivity(position) : ");
		try {
			Intent productDetailActivity = new Intent(getApplicationContext(), ProductDetailActivity.class);
			productDetailActivity.putExtra("product", ((Product) productList.get(position)));
			productDetailActivity.putExtra(currActivity, "browse");
			productDetailActivity.putExtra(prevActivity, "productList");
			startActivity(productDetailActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductDetailActivity  - Exception : " + ex.toString());
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
			productListview = (ListView) findViewById(R.id.product_listview);

			browseButton.setBackgroundResource(R.drawable.browse_tab_selected);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			myCartButton.setBackgroundResource(R.drawable.mycart_tab_normal);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the array list to hold the products in selected category
	 */
	private void prepareProductList() {
		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(ProductListActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.load_products));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					PhrescoLogger.info(TAG + " prepareProductList - processOnStart() ");
					getProductsFromServer();
				}

				@Override
				public void processOnComplete() {
					PhrescoLogger.info(TAG + " prepareProductList - processOnComplete() ");
					showProducts();
				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - prepareProductList  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the products from server
	 */
	private void getProductsFromServer() {
		try {
			getProductJSONObject();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the products list, if there is no error, else show the error message
	 */
	private void showProducts() {
		if (ioException == null) {
			buildProductListAdapter();
		} else {
			showErrorDialogWithCancel();
		}
	}

	/**
	 * Build the adapter to bind the product listview
	 */
	private void buildProductListAdapter() {
		PhrescoLogger.info(TAG + " Inside buildProductListAdapter");
		try {
			if (productList != null) {
				ProductListViewCustomAdapter adapter = new ProductListViewCustomAdapter(this, productList); // ,
				productListview.setAdapter(adapter);
			} else if (errorObj != null ) {
				toast(errorObj.getMessage());
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " buildCategoryListAdapter - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		PhrescoLogger.info(TAG + " Inside buildProductListAdapter - Ends ");
	}

	/**
	 * Read the products JSON from web server
	 */
	private void getProductJSONObject() throws IOException {

		PhrescoLogger.info(TAG + " Inside getProductJSONObject ");
		JSONObject jObject = null;
		Product productObj = new Product();
		if (search == null && categoryId != -1) {
			PhrescoLogger.info(TAG + " Get Products for Category: " + categoryId);
			jObject = productObj.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.CATEGORIES_URL + categoryId);
		} else if (search != null) {
			PhrescoLogger.info(TAG + " Get Products for search phrase: " + search);
			jObject = productObj.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.PRODUCTS_URL + Constants.SEARCH_URL + search);
		}
		try {
			if (jObject != null) {
				if (jObject.optString("type") != null && jObject.optString("type").equalsIgnoreCase("failure")) {
					errorObj = getErrorGSONObject(jObject);
				} else {
					productList = productObj.getProductGSONObject(jObject);
					downloadProductImages();
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "getProductJSONObject : Exception - " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Download the product images on local storage
	 */
	private void downloadProductImages() {
		try {
			Product productObj = new Product();
			productObj.downloadProductImages(productList);
		} catch (IOException ioe) {
			PhrescoLogger.warning(ioe);
			showErrorDialogWithCancel();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "downloadProductImages - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}