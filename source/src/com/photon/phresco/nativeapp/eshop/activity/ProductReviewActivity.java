/*
 * Classname: ProductReviewActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
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
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.adapter.ProductReviewListViewCustomAdapter;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.json.JSONHelper;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.product.Product;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReview;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewComments;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewRating;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewRatings;

/**
 * Show product review screen
 *
 * @author viral_b
 *
 */
public class ProductReviewActivity extends PhrescoActivity {

	private static final String TAG = "ProductReviewActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton, submitReviewButton;

	private ListView productReviewListview;
	private static ProductReview productReview = null;
	private Intent productItemIntent;
	private static Product productItem;
	private RatingBar productRating;
	private int productId = 0;

	private String previousActivity = null;
	private String currentActivity = null;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";
	private String browse = "browse";
	private String offers = "offers";
	private String product = "product";
	private String productReviewVar = "productReview";

	private Exception ioException = null;
	private int totalRatingValue;

	private static final int REVIEW_RATING_ONE = 1;
	private static final int REVIEW_RATING_TWO = 2;
	private static final int REVIEW_RATING_THREE = 3;
	private static final int REVIEW_RATING_FOUR = 4;
	private static final int REVIEW_RATING_FIVE = 5;
	private static final int PERCENTAGE = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_review);
		String previousActivityOrientactionCheck = "";
		try {
			productItemIntent = getIntent();
			if (productItemIntent != null && productItemIntent.getExtras() != null) {
				productItem = (Product) productItemIntent.getExtras().getSerializable(product);

				productId = productItem.getId();
				previousActivity = getExtrasPreviousActivity();
				currentActivity = productItemIntent.getExtras().getString(currActivity);

				PhrescoLogger.info(TAG + " - productId  from Intent: " + productId);
				PhrescoLogger.info(TAG + " - previousActivity  from Intent: ============ " + previousActivity);
				PhrescoLogger.info(TAG + " - currentActivity   from Intent: ============ " + currentActivity);
			}
			previousActivityOrientactionCheck = (String) getLastNonConfigurationInstance();
			PhrescoLogger.info(TAG + " - previousActivityOrientactionCheck ==  " + previousActivityOrientactionCheck);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Intent : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		initEnvironment();

		try {
			// Create an array list to hold the the categories
			if (previousActivity != null && previousActivityOrientactionCheck == null) {
				PhrescoLogger.info(TAG + " - preparing ProductReviewList for first time when previousActivity == null AND ProductReviewList == null");
				// Create an array list to hold the products reviews
				prepareProductReviewList();
			}else if (previousActivity != null && previousActivityOrientactionCheck != null){
				PhrescoLogger.info(TAG + " - productReview - screen orientation changed");
				buildProductReviewAdapter();
				showRatings();
			}
			else {
				if (productReview == null) {
					PhrescoLogger.info(TAG + " - preparing ProductReviewList for first time when previousActivity == null AND ProductReviewList == null");
					// Create an array list to hold the products reviews
					prepareProductReviewList();

				} else {
					PhrescoLogger.info(TAG + " - Using the existing product list");
					buildProductReviewAdapter();
					showRatings();
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - previousActivity check : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		productReviewListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				PhrescoLogger.info(TAG + " - productReviewListview  -  Item # " + position + "  clicked");
				try {
					startProductReviewDetailActivity(position);
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - productReviewListview  - Exception : " + ex.toString());
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

		submitReviewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startProductReviewCommentActivity();
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - myCartButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}

			}
		});
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

	/**
	 * Move back to previous activity
	 */
	private void goBackToPreviousActivity() {
		try {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - : " + previousActivity);
			PhrescoLogger.info(TAG + " - going Back ToPreviousActivity from current activity  - : " + currentActivity);
			if (previousActivity == null) {
				previousActivity = "productReviewDetails";
			}
			if (currentActivity.equalsIgnoreCase(browse)) {
				doBrowseOperation();
			} else if (currentActivity.equalsIgnoreCase(offers)) {
				doOffersOperation();
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * perform the offers menu operation
	 */
	private void doOffersOperation() {
		Intent activityIntent = null;
		try {
			if (previousActivity.equalsIgnoreCase("offersList") || previousActivity.equalsIgnoreCase(productReviewVar) || previousActivity.equalsIgnoreCase("productReviewComment")) {
				activityIntent = new Intent(getApplicationContext(), OffersActivity.class);
				activityIntent.putExtra(currActivity, offers);
				activityIntent.putExtra(prevActivity, "");

			} else if (previousActivity.equalsIgnoreCase("productDetail") || previousActivity.equalsIgnoreCase("productReviewDetails")) {
				activityIntent = new Intent(getApplicationContext(), ProductDetailActivity.class);
				activityIntent.putExtra(currActivity, offers);
				activityIntent.putExtra(prevActivity, productReviewVar);
			}
			activityIntent.putExtra(product, productItem);
			startActivity(activityIntent);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - doOffersOperation  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * perform the browse menu operation
	 */
	private void doBrowseOperation() {
		Intent activityIntent = null;
		try {
			if (previousActivity.equalsIgnoreCase("productList") || previousActivity.equalsIgnoreCase(productReviewVar) || previousActivity.equalsIgnoreCase("productReviewComment")) {
				activityIntent = new Intent(getApplicationContext(), ProductListActivity.class);
				activityIntent.putExtra(currActivity, browse);
				activityIntent.putExtra(prevActivity, "");
			} else if (previousActivity.equalsIgnoreCase("productDetail") || previousActivity.equalsIgnoreCase("productReviewDetails")) {
				activityIntent = new Intent(getApplicationContext(), ProductDetailActivity.class);
				activityIntent.putExtra(currActivity, browse);
				activityIntent.putExtra(prevActivity, productReviewVar);
			}
			activityIntent.putExtra(product, productItem);
			startActivity(activityIntent);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - doBrowseOperation  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * start category list activity
	 */
	private void startCategoryListActivity() {
		try {
			Intent categoryListActivity = new Intent(getApplicationContext(), CategoryListActivity.class);
			categoryListActivity.putExtra(currActivity, browse);
			startActivity(categoryListActivity);
			finish();
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
			offersActivity.putExtra(currActivity, offers);
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
	 * start product review comment activity
	 */
	private void startProductReviewCommentActivity() {
		try {
			if (currentActivity.equalsIgnoreCase(browse)) {
				Intent productReviewCommentActivity = new Intent(getApplicationContext(), ProductReviewCommentActivity.class);
				productReviewCommentActivity.putExtra(currActivity, browse);
				productReviewCommentActivity.putExtra(prevActivity, productReviewVar);
				productReviewCommentActivity.putExtra(product, productItem);
				startActivity(productReviewCommentActivity);
				finish();
			} else if (currentActivity.equalsIgnoreCase(offers)) {
				Intent productReviewCommentActivity = new Intent(getApplicationContext(), ProductReviewCommentActivity.class);
				productReviewCommentActivity.putExtra(currActivity, offers);
				productReviewCommentActivity.putExtra(prevActivity, "offerList");
				productReviewCommentActivity.putExtra(product, productItem);
				startActivity(productReviewCommentActivity);
				finish();
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductReviewCommentActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the product review details screen
	 *
	 * @param position
	 */
	private void startProductReviewDetailActivity(int position) {
		try {
			Intent productReviewDetailActivity = new Intent(getApplicationContext(), ProductReviewDetailActivity.class);
			productReviewDetailActivity.putExtra(currActivity, currentActivity);
			productReviewDetailActivity.putExtra("reviewComment", ((ProductReviewComments) productReview.getComments().get(position)));

			productReviewDetailActivity.putExtra(prevActivity, productReviewVar);
			productReviewDetailActivity.putExtra(product, productItem);

			startActivity(productReviewDetailActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
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
			submitReviewButton = (ImageButton) findViewById(R.id.review_comment_btn);

			productReviewListview = (ListView) findViewById(R.id.product_review_listview);
			productRating = (RatingBar) findViewById(R.id.product_rating);
			productRating.setFocusable(false);
			productRating.setClickable(false);

			PhrescoLogger.info(TAG + " - initEnvironment  currentActivity- : " + currentActivity);
			if (currentActivity.equalsIgnoreCase(browse)) {
				browseButton.setBackgroundResource(R.drawable.browse_tab_selected);
				offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
			} else if (currentActivity.equalsIgnoreCase(offers)) {
				browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
				offersButton.setBackgroundResource(R.drawable.specialoffer_tab_selected);
			}

			myCartButton.setBackgroundResource(R.drawable.mycart_tab_normal);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the array list to hold the product reviews
	 */
	private void prepareProductReviewList() {
		try {

			AsyncTaskHelper asyncTask = new AsyncTaskHelper(ProductReviewActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.load_product_reviews));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					PhrescoLogger.info(TAG + " prepareProductReviewList - processOnStart() ");

					getProductReviewsFromServer();
				}

				@Override
				public void processOnComplete() {
					PhrescoLogger.info(TAG + " prepareProductReviewList - processOnComplete() ");
					showProductReviews();
				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - prepareProductReviewList  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the product reviews from server
	 */
	private void getProductReviewsFromServer() {
		try {
			getProductReviewJSONObject();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show product reviews on screen
	 */
	private void showProductReviews() {
		if (ioException == null) {
			buildProductReviewAdapter();
			showRatings();
		} else {
			// toast("Unable to fetch products reviews from server.. Please try again later");
			showErrorDialogWithCancel();
		}
	}

	/**
	 * Read the products review JSON from web server
	 */
	private void getProductReviewJSONObject() throws IOException {
		JSONObject prodReviewJSONObj = null;
		PhrescoLogger.info(TAG + " Inside getProductReviewJSONObject ");

		prodReviewJSONObj = JSONHelper.getJSONObjectFromURL(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.PRODUCTS_URL + productId + "/" + Constants.PRODUCT_REVIEW_URL);

		if (prodReviewJSONObj != null) {
			PhrescoLogger.info(TAG + "getProductReviewJSONObject() - JSON STRING : " + prodReviewJSONObj.toString());

			try {
				// Create an object for Gson (used to create the JSON object)

				Gson jsonObj = new Gson();
				productReview = jsonObj.fromJson(prodReviewJSONObj.getJSONObject("review").toString(), ProductReview.class);
				PhrescoLogger.info(TAG + "getProductReviewJSONObject() - JSON OBJECT : " + productReview);

			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "JsonSyntaxException : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
		}

	}

	/**
	 * Build the adapter to bind the product review comments
	 */
	private void buildProductReviewAdapter() {
		try {
			if (productReview.getComments().size() == 0) {
				PhrescoLogger.info(TAG + " - product review comment size  - : " + productReview.getComments().size());
				// Toast.makeText(getApplicationContext(),
				// "no comment has been posted", Toast.LENGTH_LONG).show();
				toast(getString(R.string.empty_review_comment_message));
			} else {
				ProductReviewListViewCustomAdapter adapter = null;
				PhrescoLogger.info(TAG + " Total Comments : " + productReview.getComments().size());
				adapter = new ProductReviewListViewCustomAdapter(this, productReview.getComments());
				productReviewListview.setAdapter(adapter);
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - buildProductReviewAdapter  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Display the product ratings
	 */
	private void showRatings() {

		try {

			// Show the average rating as star
			productRating.setRating(productReview.getAverage());
			ProductReviewRatings ratings = productReview.getRatings();
			List<ProductReviewRating> productReviewRatingObj = ratings.getRating();
			PhrescoLogger.info(TAG + " - Showing Current Ratings : LIST CREATED " + productReviewRatingObj.size());

			for (ProductReviewRating productReviewRating : productReviewRatingObj) {

				// method to get the average value for rating
				getAverage();

				if(totalRatingValue>0){
					if (productReviewRating.getKey() == REVIEW_RATING_ONE) {

						final int averageReviewRatingsOne = (productReviewRating.getValue() * PERCENTAGE) / totalRatingValue;
						PhrescoLogger.info(TAG + " - Average Value for Ratings One   : " + averageReviewRatingsOne);
						((ProgressBar) findViewById(R.id.pb_1star)).setProgress(averageReviewRatingsOne);

					} else if (productReviewRating.getKey() == REVIEW_RATING_TWO) {

						final int averageReviewRatingsTwo = (productReviewRating.getValue() * PERCENTAGE) / totalRatingValue;
						PhrescoLogger.info(TAG + " - Average Value for Ratings Two   : " + averageReviewRatingsTwo);
						((ProgressBar) findViewById(R.id.pb_2star)).setProgress(averageReviewRatingsTwo);

					} else if (productReviewRating.getKey() == REVIEW_RATING_THREE) {

						final int averageReviewRatingsThree = (productReviewRating.getValue() * PERCENTAGE) / totalRatingValue;
						PhrescoLogger.info(TAG + " - Average Value for Ratings Three   : " + averageReviewRatingsThree);
						((ProgressBar) findViewById(R.id.pb_3star)).setProgress(averageReviewRatingsThree);

					} else if (productReviewRating.getKey() == REVIEW_RATING_FOUR) {

						final int averageReviewRatingsFour = (productReviewRating.getValue() * PERCENTAGE) / totalRatingValue;
						PhrescoLogger.info(TAG + " - Average Value for Ratings Four   : " + averageReviewRatingsFour);
						((ProgressBar) findViewById(R.id.pb_4star)).setProgress(averageReviewRatingsFour);

					} else if (productReviewRating.getKey() == REVIEW_RATING_FIVE) {

						final int averageReviewRatingsFive = (productReviewRating.getValue() * PERCENTAGE) / totalRatingValue;
						PhrescoLogger.info(TAG + " - Average Value for Ratings Five   : " + averageReviewRatingsFive);
						((ProgressBar) findViewById(R.id.pb_5star)).setProgress(averageReviewRatingsFive);

					}
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - showRatings  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/*
	 * To get the average value for rating
	 */
	private void getAverage() {

		int reviewRatingsOneValue = 0, reviewRatingsTwoValue = 0, reviewRatingsThreeValue = 0, reviewRatingsFourValue = 0, reviewRatingsFiveValue = 0;

		ProductReviewRatings ratings = productReview.getRatings();
		List<ProductReviewRating> productReviewRatingObj = ratings.getRating();
		try {
			for (ProductReviewRating productReviewRating : productReviewRatingObj) {

				if (productReviewRating.getKey() == REVIEW_RATING_ONE) {
					reviewRatingsOneValue = productReviewRating.getValue();
				}
				if (productReviewRating.getKey() == REVIEW_RATING_TWO) {
					reviewRatingsTwoValue = productReviewRating.getValue();
				}
				if (productReviewRating.getKey() == REVIEW_RATING_THREE) {
					reviewRatingsThreeValue = productReviewRating.getValue();
				}
				if (productReviewRating.getKey() == REVIEW_RATING_FOUR) {
					reviewRatingsFourValue = productReviewRating.getValue();
				}
				if (productReviewRating.getKey() == REVIEW_RATING_FIVE) {
					reviewRatingsFiveValue = productReviewRating.getValue();
				}
			}
			totalRatingValue = (reviewRatingsOneValue + reviewRatingsTwoValue + reviewRatingsThreeValue + reviewRatingsFourValue + reviewRatingsFiveValue);
			PhrescoLogger.info(TAG + " - getAverage  -: " + totalRatingValue);
		} catch (ArithmeticException ex) {
			PhrescoLogger.info(TAG + " - getAverage  -: " + ex.toString());
		}
	}
}
