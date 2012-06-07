/*
 * Classname: ProductDetailActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.activity;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.R.style;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.errormessage.ErrorManager;
import com.photon.phresco.nativeapp.eshop.model.product.Product;
import com.photon.phresco.nativeapp.eshop.model.product.ProductDetail;

/**
 * Show the product details screen
 *
 * @author viral_b
 *
 */
public class ProductDetailActivity extends PhrescoActivity {

	private static final String TAG = "ProductDetailActivity ***** ";
	private ImageView reviewButton, addToCartButton, productImage;
	private ImageButton backButton;
	private static Product productItem;
	private int productId = 0;
	private Intent productItemIntent;
	private TextView productPrice, productName, productDescription, productCurrency;
	private RatingBar productRating;
	private static List<ProductDetail> productDetail = null;

	private Exception ioException = null;
	private ErrorManager errorObj = null;

	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";
	private String previousActivity = null;
	private String currentActivity = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.product_detail);
			initEnvironment();
			productDescription.setMovementMethod(new ScrollingMovementMethod());

			productItemIntent = getIntent();
			if (productItemIntent != null && productItemIntent.getExtras() != null) {
				productItem = (Product) productItemIntent.getExtras().getSerializable("product");
				productId = productItem.getId();
				previousActivity = getExtrasPreviousActivity();
				currentActivity = productItemIntent.getExtras().getString(currActivity);
			}
			PhrescoLogger.info(TAG + " - previousActivity : " + previousActivity);
			PhrescoLogger.info(TAG + " - currentActivity : " + currentActivity);
			PhrescoLogger.info(TAG + " - productId : " + productId);

			String productIdOrientactionCheck = (String) getLastNonConfigurationInstance();
			doOperation(productIdOrientactionCheck);

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

			addToCartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startMyCartActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - addToCartButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			reviewButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startProductReviewActivity();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - reviewButton  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * Depending upon the previous activity and screen orientation flag, either
	 * load the prodcut details from server or display it as it is.
	 *
	 * @param productIdOrientactionCheck
	 */
	private void doOperation(String productIdOrientactionCheck) {

		if (previousActivity != null && productIdOrientactionCheck == null) {
			if (previousActivity.equalsIgnoreCase("productList") || previousActivity.equalsIgnoreCase("offersList")) {
				// Get the current product details from web service
				PhrescoLogger.info(TAG + " - preparing ProductDetail for first time - coming from ProductList screen ");
				getProductDetails();
			} else if (previousActivity.equalsIgnoreCase("productReview")) {
				// Get the current product details from web service
				if (productDetail != null) {
					if (productDetail.get(0).getId() == productId) {
						PhrescoLogger.info(TAG + " - preparing ProductDetail from existing details - coming from ProductReview screen ");
						showDetails();
					} else {
						PhrescoLogger.info(TAG + " - preparing ProductDetail for new product - coming from ProductReview screen ");
						getProductDetails();
					}
				} else {
					// Get the current product details from web service
					PhrescoLogger.info(TAG + " - preparing ProductDetail first time - coming from ProductReview screen ");
					getProductDetails();
				}
			}
		} else if (productId != 0 && productIdOrientactionCheck != null) {
			PhrescoLogger.info(TAG + " - ProductDetail - screen orientation changed");
			showDetails();
		}
	}

	/**
	 * Get previous activity name from intent extras
	 *
	 * @return
	 */
	private String getExtrasPreviousActivity() {
		return (productItemIntent.getExtras().getString(prevActivity) != null && productItemIntent.getExtras().getString(prevActivity).length() > 0) ? productItemIntent.getExtras().getString(
				prevActivity) : null;
	}

	@Override
	public String onRetainNonConfigurationInstance() {
		// String retValue;
		PhrescoLogger.info(TAG + " - onRetainNonConfigurationInstance - productId = : " + productId);
		return String.valueOf(productId);
	}

	/**
	 * Show the my cart screen when Add to cart button is clicked
	 */
	private void startMyCartActivity() {
		try {
			Intent myCartActivity = new Intent(getApplicationContext(), MyCartActivity.class);
			myCartActivity.putExtra("productDetail", productDetail.get(0));
			myCartActivity.putExtra(currActivity, "mycart");
			startActivity(myCartActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startMyCartActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Move back to previous activity
	 */
	private void goBackToPreviousActivity() {
		try {

			PhrescoLogger.info(TAG + " - goBackToPreviousActivity:  previousActivity == " + previousActivity);
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity:  currentActivity == " + currentActivity);
			Intent activityIntent = null;
			/*
			 * if (previousActivity.equalsIgnoreCase("productList") ||
			 * previousActivity.equalsIgnoreCase("productReview")) {
			 * activityIntent = new Intent(getApplicationContext(),
			 * ProductListActivity.class); } else { activityIntent = new
			 * Intent(getApplicationContext(), OffersActivity.class); }
			 */

			if (currentActivity.equalsIgnoreCase("browse")) {
				activityIntent = new Intent(getApplicationContext(), ProductListActivity.class);
			} else if (currentActivity.equalsIgnoreCase("offers")) {
				activityIntent = new Intent(getApplicationContext(), OffersActivity.class);
			}
			activityIntent.putExtra(currActivity, currentActivity);
			startActivity(activityIntent);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show product review screen
	 */
	private void startProductReviewActivity() {
		try {
			Intent productReviewActivity = new Intent(getApplicationContext(), ProductReviewActivity.class);
			/*
			 * if (previousActivity.equalsIgnoreCase("productList")) {
			 * productReviewActivity.putExtra(currActivity, "browse"); } else if
			 * (previousActivity.equalsIgnoreCase("offersList")) {
			 * productReviewActivity.putExtra(currActivity, "offers"); }
			 */
			productReviewActivity.putExtra(currActivity, currentActivity);
			productReviewActivity.putExtra(prevActivity, "productDetail");
			productReviewActivity.putExtra("product", productItem);
			startActivity(productReviewActivity);
			finish();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductReviewActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			productImage = (ImageView) findViewById(R.id.img_product_image);
			reviewButton = (ImageView) findViewById(R.id.product_review);
			addToCartButton = (ImageView) findViewById(R.id.add_to_cart);
			productCurrency = (TextView) findViewById(R.id.lbl_currency);
			productPrice = (TextView) findViewById(R.id.product_price);
			productName = (TextView) findViewById(R.id.product_detail_title);
			productDescription = (TextView) findViewById(R.id.product_desc);
			backButton = (ImageButton) findViewById(R.id.back_btn);
			productRating = (RatingBar) findViewById(R.id.product_rating);

			productRating.setFocusable(false);
			productRating.setClickable(false);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the product details from the web service
	 */
	private void getProductDetails() {
		try {

			AsyncTaskHelper asyncTask = new AsyncTaskHelper(ProductDetailActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.load_product_details));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					PhrescoLogger.info(TAG + " getProductDetails - processOnStart() ");
					getProductDetailsFromServer();
				}

				@Override
				public void processOnComplete() {
					PhrescoLogger.info(TAG + " getProductDetails - processOnComplete() ");
					showDetails();
				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getProductDetails  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the product details information from web server
	 */
	private void getProductDetailsFromServer() {
		try {
			getProductDetailJSONObject();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Wrapper method to show the product details on screen
	 */
	private void showDetails() {
		if (ioException == null) {
			showProductDetails();
		} else {
			// toast("Unable to fetch products details from server.. Please try again later");
			showErrorDialogWithCancel();
		}
	}

	/**
	 * Read the products JSON from web server
	 */
	private void getProductDetailJSONObject() throws IOException {

		JSONObject jObject = null;
		PhrescoLogger.info(TAG + " Inside getProductDetailJSONObject ");
		Product productObj = new Product();
		try {
			jObject = productObj.getProductJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.PRODUCTS_URL + productId);
			if (jObject != null) {
				PhrescoLogger.info(TAG + "getProductDetailJSONObject() - JSON STRING : " + jObject.toString());

				if (jObject.optString("type") != null && jObject.optString("type").equalsIgnoreCase("failure")) {
					errorObj = getErrorGSONObject(jObject);
				} else {
					// Create an object for Gson (used to create the JSON
					// object)
					productDetail = productObj.getProductDetailGSONObject(jObject);
					PhrescoLogger.info(TAG + "getProductDetailJSONObject() - JSON OBJECT : " + productDetail.toString());
					downloadProductDetailImages();
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "getProductDetailJSONObject(): Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * Download the product detail image on local storage
	 */
	private void downloadProductDetailImages() {
		try {
			Product productObj = new Product();
			productObj.downloadProductDetailImages(productDetail);
		} catch (IOException ioe) {
			PhrescoLogger.warning(ioe);
			showErrorDialogWithCancel();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "downloadProductDetailImages - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the product details on screen
	 */
	private void showProductDetails() {

		try {
			if (productDetail != null) {
				Map<String, String> details = null;
				ImageCacheManager imgCacheManager = new ImageCacheManager();
				for (ProductDetail iProductDetail : productDetail) {
					productName.setText(iProductDetail.getName());
					productDescription.setText(iProductDetail.getDescription());
					productPrice.setText(String.valueOf(iProductDetail.getPrice()));
					productCurrency.setText(Constants.getCurrency());
					productRating.setRating(iProductDetail.getRating());
					imgCacheManager.renderImage(productImage, Constants.PRODUCT_DETAIL_FOLDER_PATH + iProductDetail.getDetailImage().substring(iProductDetail.getDetailImage().lastIndexOf("/") + 1));
					details = iProductDetail.getDetails();
				}
				renderProdcutDetails(details);
			} else if (errorObj != null) {
				toast(errorObj.getMessage());
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "showProductDetails : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the details sections at the bottom of the screen dynamically
	 *
	 * @param details
	 * @throws NumberFormatException
	 */
	private void renderProdcutDetails(Map<String, String> details) {

		try {
			LinearLayout tl = (LinearLayout) findViewById(R.id.product_details_layout);
			int totalDetails = details.size();
			int cnt = 1;
			PhrescoLogger.info(TAG + "renderProdcutDetails() - totalDetails : " + totalDetails);
			TextView lblDetailLabel = null;
			for (Entry<String, String> entry : details.entrySet()) {

				// Create a TextView to hold the label for product detail
				lblDetailLabel = new TextView(this);
				if (cnt == 1) {
					lblDetailLabel.setBackgroundResource(R.drawable.view_cart_item_top);
				} else if (cnt == totalDetails) {
					lblDetailLabel.setBackgroundResource(R.drawable.view_cart_item_bottom);
				} else {
					lblDetailLabel.setBackgroundResource(R.drawable.view_cart_item_middle);
				}
				lblDetailLabel.setText(entry.getKey() + ": " + entry.getValue());
				lblDetailLabel.setGravity(Gravity.CENTER);
				lblDetailLabel.setTypeface(Typeface.DEFAULT, style.TextViewStyle);
				lblDetailLabel.setTextColor(Color.WHITE);
				lblDetailLabel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
				tl.addView(lblDetailLabel);
				cnt++;
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "renderProdcutDetails - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
