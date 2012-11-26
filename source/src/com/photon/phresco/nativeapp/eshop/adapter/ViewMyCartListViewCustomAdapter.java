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
 * Classname: ViewMyCartListViewCustomAdapter
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCart;
import com.photon.phresco.nativeapp.eshop.model.mycart.MyCartItem;

/**
 * Custom adapter class extending the BaseAdapter for view cart listing
 *
 * @author viral_b
 *
 */
public class ViewMyCartListViewCustomAdapter extends BaseAdapter {
	private static final String TAG = "ViewMyCartListViewCustomAdapter ***** ";
	private List<MyCartItem> itemList;
	private LayoutInflater inflater;

	public ViewMyCartListViewCustomAdapter(Context context) {
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
		return itemList.get(position).getProduct().getId();
	}

	/**
	 * Static class to hold the single item in view cart listing
	 *
	 * @author viral_b
	 *
	 */
	public class ViewHolder {
		private TextView txtProductName;
		private TextView txtCurrency;
		private TextView txtProductPrice;
		private TextView txtProductQuantity;

		protected ViewHolder() {
		}
	}

	@Override
	public View getView(final int position, View cView, ViewGroup parent) {

//		PhrescoLogger.info(TAG + " - getView - position : " + position);

		int prodPrice;
		ViewHolder holder;
		View convertView = cView;
		try {
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.view_mycart_listview_item, null);
				holder.txtProductName = (TextView) convertView.findViewById(R.id.product_title);
				holder.txtCurrency = (TextView) convertView.findViewById(R.id.lbl_currency);
				holder.txtProductPrice = (TextView) convertView.findViewById(R.id.product_price);
				holder.txtProductQuantity = (TextView) convertView.findViewById(R.id.product_quantity);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final MyCartItem item = (MyCartItem) this.getItem(position);

			holder.txtProductName.setText(item.getProduct().getName());
			prodPrice = item.getProduct().getPrice() * item.getProductQuantity();
			holder.txtCurrency.setText(Constants.getCurrency());
			holder.txtProductPrice.setText(String.valueOf(prodPrice));
			holder.txtProductQuantity.setText(String.valueOf(item.getProductQuantity()));

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getView  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		return convertView;
	}

}
