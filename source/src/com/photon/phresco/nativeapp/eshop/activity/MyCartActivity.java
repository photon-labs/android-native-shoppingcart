/*
 * Classname: MyCartActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.adapter.MyCartListViewCustomAdapter;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCart;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCartItem;
import com.photon.phresco.nativeapp.eshop.model.product.ProductDetail;

/**
 * Show MyCart screen
 *
 * @author viral_b
 *
 */
public class MyCartActivity extends PhrescoActivity {

	private static final String TAG = "MyCartActivity ***** ";

	private ImageButton backButton, browseButton, offersButton, myCartButton;
	private ImageButton updateCartButton, viewCartButton;
	private ListView myCartListview;
	private ProductDetail product;
	private Intent productItemIntent;
	private TextView currency, subTotal;
	private int count;

	private String currActivity = "currentActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycart);
		PhrescoLogger.info(TAG + " - onCreate()********** ");
		try {
			initEnvironment();
			productItemIntent = getIntent();
			if (productItemIntent != null && (ProductDetail) productItemIntent.getSerializableExtra("productDetail") != null) {
				product = (ProductDetail) productItemIntent.getSerializableExtra("productDetail");
				PhrescoLogger.info(TAG + " - product - Title : " + product.getName());
			}
			currency.setText(Constants.getCurrency());
			String screenOrientactionCheck = (String) getLastNonConfigurationInstance();

			// Create an array list to hold the products in cart
			if (screenOrientactionCheck == null) {
				prepareMyCart();
			}

			if (MyCart.getMyCart() != null && MyCart.size() > 0) {
				try {
					MyCartListViewCustomAdapter adapter = new MyCartListViewCustomAdapter(this);
					myCartListview.setAdapter(adapter);
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - ProductListViewCustomAdapter  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

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

		updateCartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (MyCart.getMyCart() != null && MyCart.size() > 0) {
						updateMyCartSubTotal();
					} else {
						subTotal.setText(String.valueOf(0));
						if (count == 0) {
							toast(getString(R.string.empty_cart_message));
							count++;
						}
					}
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - updateCartButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});

		viewCartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (MyCart.getMyCart() != null && MyCart.size() > 0) {
						startViewMyCartActivity();
					} else if (count == 0) {
						subTotal.setText(String.valueOf(0));
						toast(getString(R.string.empty_cart_message));
						count++;
					}
				} catch (Exception ex) {
					PhrescoLogger.info(TAG + " - viewCartButton  - Exception : " + ex.toString());
					PhrescoLogger.warning(ex);
				}
			}
		});
	}

	@Override
	public String onRetainNonConfigurationInstance() {
		// String retainNonConfigurationInstance = "orientation";
		return "orientation";
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
	 * Move back to product list screen
	 */
	private void goBackToPreviousActivity() {
		try {
			Intent productListActivity = new Intent(getApplicationContext(), ProductListActivity.class);
			productListActivity.putExtra(currActivity, "browse");
			productListActivity.putExtra("categoryName", productItemIntent.getExtras().getString("categoryName"));
			productListActivity.putExtra("categoryId", productItemIntent.getExtras().getInt("categoryId"));
			startActivity(productListActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - goBackToPreviousActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show View MyCart screen
	 */
	private void startViewMyCartActivity() {
		try {
			Intent viewMyCartActivity = new Intent(getApplicationContext(), ViewMyCartActivity.class);
			viewMyCartActivity.putExtra(currActivity, "mycart");
			startActivity(viewMyCartActivity);
			finish();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startViewMyCartActivity  - Exception : " + ex.toString());
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
	 * Initialize all the controls for current screen
	 */
	private void initEnvironment() {
		try {
			backButton = (ImageButton) findViewById(R.id.back_btn);

			subTotal = (TextView) findViewById(R.id.lbl_amount);
			currency = (TextView) findViewById(R.id.lbl_currency);

			browseButton = (ImageButton) findViewById(R.id.tab_browse);
			offersButton = (ImageButton) findViewById(R.id.tab_specialoffer);
			myCartButton = (ImageButton) findViewById(R.id.tab_mycart);

			updateCartButton = (ImageButton) findViewById(R.id.update_cart_btn);
			viewCartButton = (ImageButton) findViewById(R.id.view_cart_button);
			myCartListview = (ListView) findViewById(R.id.mycart_listview);

			// myCartListview.setLayoutParams(new
			// LayoutParams(LayoutParams.FILL_PARENT, screenHeight));
			myCartButton.setBackgroundResource(R.drawable.mycart_tab_selected);
			browseButton.setBackgroundResource(R.drawable.browse_tab_normal);
			offersButton.setBackgroundResource(R.drawable.specialoffer_tab_normal);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - initEnvironment  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	protected void onStart() {
		PhrescoLogger.info(TAG + " - onStart()  - ");
		super.onStart();
		try {
			if (MyCart.getMyCart() != null && MyCart.size() > 0) {
				updateMyCartSubTotalOnCreate();
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - onStart()  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		PhrescoLogger.info(TAG + " - onResume()  - ");
	}

	/**
	 * Show the total amount of cart, when user comes to this screen from other
	 * screen
	 */
	public void updateMyCartSubTotalOnCreate() {
		try {
			int subTotalPrice = 0;
			subTotalPrice = MyCart.updateMyCartTotal();
			subTotal.setText(String.valueOf(subTotalPrice));
			PhrescoLogger.info(TAG + " - updateMyCartSubTotalOnCreate  - : " + subTotalPrice);
		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - updateMyCartSubTotalOnCreate  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Update the cart total when Update Cart button is clicked
	 */
	public void updateMyCartSubTotal() {
		try {
			int subTotalPrice = 0;
			subTotalPrice = MyCart.updateMyCartTotal();
			subTotal.setText(String.valueOf(subTotalPrice));
			PhrescoLogger.info(TAG + " - updateMyCartSubTotal  - : " + MyCart.getTotalPrice());

		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - updateMyCartSubTotal  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create MyCart object to hold the products in cart
	 */
	public void prepareMyCart() {
		try {
			// PhrescoLogger.info(TAG + " - prepareMyCart: ");
			if (MyCart.getMyCart() == null && product == null) {
				if (count == 0) {
					toast(getString(R.string.empty_cart_message));
					count++;
				}

			} else {
				if (product != null) {
					boolean isProductInCart = false;
					for (int i = 0; MyCart.getMyCart() != null && i < MyCart.size(); i++) {
						if (MyCart.getMyCartItem(i).getProduct().getId() == product.getId()) {
							// PhrescoLogger.info(TAG + " - Updating product: "
							// + product.getName());
							updateProductQuantityInCart(MyCart.getMyCartItem(i));
							isProductInCart = true;
							PhrescoLogger.info(TAG + " - isProductInCart inside for loop: " + isProductInCart);
							break;
						}
					}
					PhrescoLogger.info(TAG + " - isProductInCart: " + isProductInCart);
					if (!isProductInCart) {
						addProductToCart(product, 1);
					}
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - prepareMyCart  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Add product to cart
	 *
	 * @param product
	 * @param productQuantity
	 */
	public void addProductToCart(ProductDetail product, int productQuantity) {
		try {
			MyCartItem item = new MyCartItem();
			item.setProduct(product);
			item.setProductQuantity(productQuantity);
			MyCart.add(item);
			// PhrescoLogger.info(TAG + " - addProductToCart : Name: " +
			// product.getName());
			// PhrescoLogger.info(TAG + " - addProductToCart : Qty: " +
			// productQuantity);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - AddObjectToList  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Update the quantity of selected product in cart
	 *
	 * @param product
	 * @param productQuantity
	 */
	public void updateProductQuantityInCart(MyCartItem product) {
		try {
			MyCart.updateProductQuantityInCart(product);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - updateCart  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
