/*
 * ###
 * PHR_AndroidNative
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
/*
 * Classname: MyCartListViewCustomAdapter
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.adapter;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.core.ImageCacheManager;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCart;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCartItem;

/**
 * Custom adapter class extending the BaseAdapter for my cart listing
 *
 * @author viral_b
 *
 */
public class MyCartListViewCustomAdapter extends BaseAdapter {
	private static final String TAG = "MyCartListViewCustomAdapter ***** ";
	private List<MyCartItem> itemList;
	private LayoutInflater inflater;

	public MyCartListViewCustomAdapter(Context context) {
		super();
		this.itemList = MyCart.getMyCart();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Static class to hold the single item in cart listing
	 *
	 * @author viral_b
	 *
	 */
	public class ViewHolder {
		private ImageView imgProductImage;
		private TextView txtProductName;
		private TextView txtCurrency;
		private TextView txtProductPrice;
		private EditText txtProductQuantity;
		private ImageView imgRemove;

		protected ViewHolder() {
		}
	}

	@Override
	public View getView(final int position, View cView, ViewGroup parent) {
//		PhrescoLogger.info(TAG + " - getView - position : " + position);

		View convertView = cView;
		int prodPrice;

		 final ViewHolder holder;
		try {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.mycart_listview_item, null);

			holder.imgProductImage = (ImageView) convertView.findViewById(R.id.img_product_image);
			holder.txtProductName = (TextView) convertView.findViewById(R.id.product_title);
			holder.txtCurrency = (TextView) convertView.findViewById(R.id.lbl_currency);
			holder.txtProductPrice = (TextView) convertView.findViewById(R.id.product_price);
			holder.txtProductQuantity = (EditText) convertView.findViewById(R.id.product_quantity);
			holder.imgRemove = (ImageView) convertView.findViewById(R.id.remove_btn);
			convertView.setTag(holder);

			final MyCartItem item = (MyCartItem) this.getItem(position);

			ImageCacheManager imgCacheManager = new ImageCacheManager();
			imgCacheManager.renderImage(holder.imgProductImage, Constants.PRODUCT_FOLDER_PATH + item.getProduct().getImage().substring(item.getProduct().getImage().lastIndexOf("/") + 1));
			holder.txtProductName.setText(item.getProduct().getName());
			prodPrice = item.getProduct().getPrice() * item.getProductQuantity();

			holder.txtCurrency.setText(Constants.getCurrency());
			holder.txtProductPrice.setText(String.valueOf(prodPrice));
			holder.txtProductQuantity.setText(String.valueOf(item.getProductQuantity()));

			holder.imgRemove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						MyCart.removeFromList(position);
						PhrescoLogger.info(TAG + " - Item Removed from position = " + position);
						notifyDataSetInvalidated();
						notifyDataSetChanged();
					} catch (Exception ex) {
						PhrescoLogger.info(TAG + " - imgProductReview  - Exception : " + ex.toString());
						PhrescoLogger.warning(ex);
					}
				}
			});

			holder.txtProductQuantity.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}
				public void afterTextChanged(Editable s) {
					changeProductQuantity(position, holder, item, s);
				}
			});

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getView  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return convertView;
	}

	/**
	 * Change the product price when quantity is changed in textbox
	 *
	 * @param position
	 * @param holder
	 * @param item
	 * @param s
	 */
	private void changeProductQuantity(final int position, final ViewHolder holder, final MyCartItem item, final Editable s) {
		int prodQuantity, prodPrice, totalPrice;
		try {
			if (s.toString().equalsIgnoreCase("")) {
				prodQuantity = 0;
			} else {
				prodQuantity = Integer.parseInt(s.toString());
			}

			PhrescoLogger.info(TAG + " - Item quantity changed at position " + position + " = " + s);
			prodPrice = MyCart.getMyCartItem(position).getProduct().getPrice();
			totalPrice = prodPrice * prodQuantity;

			/*PhrescoLogger.info(TAG + " - item.getProduct().getPrice(): **** " + item.getProduct().getPrice());
			PhrescoLogger.info(TAG + " - MyCart.getMyCartItem(position).getProduct().getPrice(): **** " + MyCart.getMyCartItem(position).getProduct().getPrice());
			PhrescoLogger.info(TAG + " - prodQuantity : ******** " + prodQuantity);
			PhrescoLogger.info(TAG + " - prodPrice : ******** " + prodPrice);*/

			PhrescoLogger.info(TAG+" - TotalPrice : ******************** "+ totalPrice);
			holder.txtProductPrice.setText(String.valueOf(totalPrice));

			PhrescoLogger.info(TAG + " - Product Price is : " + holder.txtProductPrice.getText());
			item.setProductQuantity(prodQuantity);

		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " - afterTextChanged() -  NumberFormatException  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}
}
