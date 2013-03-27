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
import com.photon.phresco.nativeapp.eshop.model.product.Product;

/**
 * Show special offers screen
 *
 * @author viral_b
 *
 */
public class OffersActivity extends PhrescoActivity {

	private static final String TAG = "OffersActivity ***** ";
	private ImageButton backButton, browseButton, offersButton, myCartButton;
	private ListView productListview;
	private static List<Product> productList = null;
	private Bundle extras;
	private Exception ioException = null;

	private String previousActivity = null;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.offers);

		initEnvironment();

		List<Product> previousActivityOrientactionCheck = null;
		try {
			extras = getIntent().getExtras();
			if (extras != null) {
				previousActivity = getExtrasPreviousActivity();
			}
			previousActivityOrientactionCheck = (List<Product>) getLastNonConfigurationInstance();

			PhrescoLogger.info(TAG + " - previousActivity == " + previousActivity);
			PhrescoLogger.info(TAG + " - previousActivityOrientactionCheck ==  " + previousActivityOrientactionCheck);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Intent : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		try {
			// Create an array list to hold the the categories
			if (productList == null && previousActivityOrientactionCheck == null) {
				// Create an array list to hold the products for current
				// category
				prepareProductList();

			} else if (productList != null && previousActivityOrientactionCheck != null) {
				PhrescoLogger.info(TAG + " - ProductList - screen orientation changed");
				// Create an array list to hold the the categories
				buildProductListAdapter();

			} else {
				if (productList == null || productList.size() <= 0) {
					prepareProductList();
				} else {
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
					startHomeActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - backButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		browseButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					startCategoryListActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - browseButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}

			}
		});

		myCartButton.setOnClickListener(new OnClickListener() {

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
	public List<Product> onRetainNonConfigurationInstance() {
		return productList != null ? productList : null;
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
	 * start home activity
	 */
	private void startHomeActivity() {
		try {
			Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
			homeActivity.putExtra(currActivity, "home");
			startActivity(homeActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startHomeActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start category list activity
	 */
	private void startCategoryListActivity() {
		try {
			Intent categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra(currActivity, "browse");
			startActivity(categoryListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startCategoryListActivity  - Exception : " + ex.toString());
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
			productDetailActivity.putExtra(currActivity, "offers");
			productDetailActivity.putExtra(prevActivity, "offersList");
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

			browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_selected);
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
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(OffersActivity.this);
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
			ProductListViewCustomAdapter adapter = new ProductListViewCustomAdapter(this, productList); // ,
																										// extras);
			productListview.setAdapter(adapter);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " buildCategoryListAdapter - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * Read the products JSON from web server
	 */
	private void getProductJSONObject() throws IOException {

		PhrescoLogger.info(TAG + " Inside getProductJSONObject ");
		JSONObject jObject = null;
		Product productObj = new Product();
		jObject = productObj.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.SPECIAL_PRODUCTS_URL);

		try {
			if (jObject != null) {
				productList = productObj.getProductGSONObject(jObject);
				downloadProductImages();
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
