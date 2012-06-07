/*
 * Classname: ProductReviewListViewCustomAdapter
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.activity.ProductReviewCommentActivity;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.product.ProductReviewComments;

/**
 * Custom adapter class extending the BaseAdapter for product review listing
 *
 * @author viral_b
 *
 */
public class ProductReviewListViewCustomAdapter extends BaseAdapter {
	private static final String TAG = "ProductReviewListViewCustomAdapter ***** ";
	private List<ProductReviewComments> productReviewList;
	private Context context;
	private LayoutInflater inflater;

	public ProductReviewListViewCustomAdapter(Context context, List<ProductReviewComments> productReviewList) {
		super();
		this.context = context;
		this.productReviewList = productReviewList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return productReviewList.size();
	}

	@Override
	public Object getItem(int position) {
		return productReviewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Static class to hold the product review list single item
	 *
	 * @author viral_b
	 *
	 */
	public class ViewHolder {
		private TextView txtReviewComment;
		private TextView txtReviewUser;
		private TextView txtReviewDate;
		private RatingBar rbReviewRating;
		private ImageView imgArrow;

		protected ViewHolder() {
		}
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		ViewHolder holder;
		View convertView = cView;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.product_review_item, null);

				holder.txtReviewComment = (TextView) convertView.findViewById(R.id.review_comment);
				holder.txtReviewUser = (TextView) convertView.findViewById(R.id.review_user);
				holder.txtReviewDate = (TextView) convertView.findViewById(R.id.review_date);
				holder.rbReviewRating = (RatingBar) convertView.findViewById(R.id.review_rating);
				holder.imgArrow = (ImageView) convertView.findViewById(R.id.arrow_icon);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ProductReviewComments item = (ProductReviewComments) productReviewList.get(position);

			holder.txtReviewComment.setText(item.getComment());
			holder.txtReviewUser.setText((item.getUser() == null || item.getUser().length() <= 0) ? "" : item.getUser());
			holder.rbReviewRating.setRating(item.getRating());
			holder.imgArrow.setBackgroundResource(R.drawable.arrow_icon);

			SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date dtSource = sdfSource.parse(item.getCommentDate());
			SimpleDateFormat sdfCommentDate = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
			holder.txtReviewDate.setText(sdfCommentDate.format(dtSource));

			holder.rbReviewRating.setFocusable(false);
			holder.rbReviewRating.setClickable(false);

			holder.rbReviewRating.setOnTouchListener(new OnTouchListener() {

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
	 * Start the product review comment activity
	 */
	private void startProductReviewCommentActivity() {
		try {
			Intent productReviewCommentActivity = new Intent(context, ProductReviewCommentActivity.class);
			productReviewCommentActivity.putExtra("currentActivity", "browse");
			context.startActivity(productReviewCommentActivity);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - startProductReviewCommentActivity  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

}
