/*
 * Classname: ProductListViewCustomAdapter
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.activity.ProductReviewActivity;
import com.photon.phresco.nativeapp.eshop.activity.ProductReviewCommentActivity;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.product.Product;

/**
 * Custom adapter class extending the BaseAdapter for Product listing
 *
 * @author viral_b
 *
 */
public class ProductListViewCustomAdapter extends BaseAdapter {
	private static final String TAG = "ProductListViewCustomAdapter ***** ";
	private List<Product> productList;
	private final Context context;
	private LayoutInflater inflater;
	private String currActivity = "currentActivity";
	private String prevActivity = "previousActivity";

	// private Bundle extras;

	public ProductListViewCustomAdapter(Context context, List<Product> productList) { // ,
																						// Bundle
																						// extras)
																						// {
		super();
		this.context = context;
		this.productList = productList;
		// this.extras = extras;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return productList.get(position).getId();
	}

	/**
	 * Static class to hold the product listview single item
	 *
	 * @author viral_b
	 *
	 */
	public class ViewHolder {
		private ImageView imgProductImage;
		private TextView txtProductName;
		private TextView txtCurrency;
		private TextView txtProductPrice;
		private RatingBar rbProductRating;
		private ImageView imgProductReview;
		private ImageView imgArrow;

		protected ViewHolder() {
		}
	}

	@Override
	public View getView(final int position, View cView, ViewGroup parent) {

		ViewHolder holder;
		View convertView = cView;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.product_listview_item, null);

				holder.imgProductImage = (ImageView) convertView.findViewById(R.id.img_product_image);
				holder.txtProductName = (TextView) convertView.findViewById(R.id.product_title);
				holder.txtCurrency = (TextView) convertView.findViewById(R.id.lbl_currency);
				holder.txtProductPrice = (TextView) convertView.findViewById(R.id.product_price);
				holder.rbProductRating = (RatingBar) convertView.findViewById(R.id.product_rating);
				holder.imgProductReview = (ImageView) convertView.findViewById(R.id.product_review);
				holder.imgArrow = (ImageView) convertView.findViewById(R.id.arrow_icon);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final Product item = (Product) productList.get(position);
			ImageCacheManager imgCacheManager = new ImageCacheManager();
			imgCacheManager.renderImage(holder.imgProductImage, Constants.PRODUCT_FOLDER_PATH + item.getImage().substring(item.getImage().lastIndexOf("/") + 1));

			holder.txtProductName.setText(item.getName());
			holder.txtCurrency.setText(Constants.getCurrency());
			holder.txtProductPrice.setText(String.valueOf(item.getPrice()));
			holder.rbProductRating.setRating(item.getRating());
			holder.imgProductReview.setBackgroundResource(R.drawable.review_btn);
			// holder.imgArrow.setBackgroundResource(R.id.arrow_icon);

			holder.imgArrow.setBackgroundResource(R.drawable.arrow_icon);

			holder.rbProductRating.setFocusable(false);
			holder.rbProductRating.setClickable(false);

			holder.imgProductReview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						startProductReviewActivity(item);
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - imgProductReview  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			holder.rbProductRating.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						PhrescoLogger.info(TAG + " - rbProductRating.setOnTouchListener fired: ");
						startProductReviewCommentActivity();
					}
					return false;
				}
			});
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getView  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		return convertView;
	}

	/**
	 * Show product review activity when review button is clicked
	 *
	 * @param item
	 */
	private void startProductReviewActivity(final Product item) {
		try {
			Intent productReviewActivity = new Intent(context, ProductReviewActivity.class);
			productReviewActivity.putExtra("product", item);
			if (context.getClass().getName().contains("ProductListActivity")) {
				productReviewActivity.putExtra(currActivity, "browse");
				productReviewActivity.putExtra(prevActivity, "productList");
			} else {
				productReviewActivity.putExtra(currActivity, "offers");
				productReviewActivity.putExtra(prevActivity, "offersList");
			}
			// productReviewActivity.putExtra("categoryName",
			// extras.getString("categoryName"));
			// productReviewActivity.putExtra("categoryId",
			// extras.getInt("categoryId"));
			context.startActivity(productReviewActivity);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductReviewActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Start the product review comment activity
	 */
	private void startProductReviewCommentActivity() {
		try {
			Intent productReviewCommentActivity = new Intent(context, ProductReviewCommentActivity.class);
			productReviewCommentActivity.putExtra(currActivity, "browse");
			context.startActivity(productReviewCommentActivity);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductReviewCommentActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
