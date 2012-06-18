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
 * Classname: MoreOptionsCustomAdapter
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Custom adapter class extending the BaseAdapter for more options screen
 *
 * @author viral_b
 *
 */
public class MoreOptionsCustomAdapter extends BaseAdapter {
	private static final String TAG = "MoreOptionsCustomAdapter ***** ";
	private Context mContext;

	public MoreOptionsCustomAdapter(Context c) {
		super();
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = null;
		try {
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(mContext);

				imageView.setLayoutParams(new GridView.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(Integer.parseInt("8"), Integer.parseInt("8"), Integer.parseInt("8"), Integer.parseInt("8"));
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setBackgroundResource(mThumbIds[position]);
			imageView.setTag(mTags[position]);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - getView  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return imageView;
	}

	// references to our images
	private Integer[] mThumbIds = { R.drawable.wishlist_icon, R.drawable.coupons_icon };

	// references to our images tags
	private String[] mTags = { "Wishlist", "Coupens" };
}
