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
 * Classname: ImageCacheManager
 * Version information: 1.0
 * Date: Dec 01, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.core;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.util.Utility;

/**
 * Handle the image downloading and local caching operations for menu, category,
 * products and product detail images
 *
 * @author viral_b
 *
 */
public class ImageCacheManager {

	private static final String TAG = "ImageCacheManager ******** ";

	public ImageCacheManager() {

	}

	/**
	 * Download the image from specified source to destination, if the image is
	 * not present at the destination path
	 *
	 * @param sourcePath
	 * @param destinationPath
	 */
	public void downloadImage(String sourcePath, String destinationPath) throws IOException {
		try {
			File imageFile = new File(destinationPath);
			if (!imageFile.exists()) {
				Utility.downloadFileToLocalStorage(sourcePath, destinationPath);
				PhrescoLogger.info(TAG + "downloadImage : " + sourcePath + " >> " + destinationPath);
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "downloadImage - Exception: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the image from the sourcePath and render it on imgView control
	 *
	 * @param imgView
	 * @param sourcePath
	 */
	public void renderImage(ImageView imgView, String sourcePath) {

//		PhrescoLogger.info(TAG + "renderImage : " + sourcePath );
		ImageView img = imgView;
		try {
			Bitmap bmp = BitmapFactory.decodeFile(sourcePath);
//			PhrescoLogger.info(TAG + "bitmap : " + bmp );
			if (bmp != null) {
				img.setImageBitmap(BitmapFactory.decodeFile(sourcePath));
			} else {
				if (sourcePath.indexOf(Constants.CATEGORIES_FOLDER_PATH) > -1) {
					img.setImageResource(R.drawable.noimage_category);
				} else if (sourcePath.indexOf(Constants.PRODUCT_FOLDER_PATH) > -1) {
					img.setImageResource(R.drawable.noimage_product);
				} else if (sourcePath.indexOf(Constants.PRODUCT_DETAIL_FOLDER_PATH) > -1) {
					img.setImageResource(R.drawable.noimage_productdetail);
				}
			}

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "renderImage - Exception: " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}
}
