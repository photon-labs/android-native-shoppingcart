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
 * Classname: AsyncTaskHelper
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.os.AsyncTask;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * AsyncTaskHelper enables proper and easy use of the UI thread. This class
 * allows to perform background operations and publish results on the UI thread
 * without having to manipulate threads and/or handlers.
 *
 * @author viral_b
 *
 */
public class AsyncTaskHelper extends AsyncTask<String, Integer, Entity> {
	private static final String TAG = "AsyncTaskHelper ***** ";
	private IAsyncTaskListener asyncTaskListener;
	private Context context = null;
	private ProgressDialog dialog = null;
	private String progressMessage = "Loading ... please wait";

	public AsyncTaskHelper(Context context) {
		this.context = context;
	}

	/**
	 * If progress bar should be displayed or not
	 *
	 * @param flag
	 */
	public void showProgressbar(boolean flag) {
//		PhrescoLogger.info(TAG + "showProgressbar()");
		try {
			if (flag) {
				/*if (dialog != null && dialog.isShowing()) {
					PhrescoLogger.info(TAG + "showProgressbar() - previous dialog is showling.. dismiss it");
					dialog.dismiss();
					dialog = null;
				}*/
				dialog = new ProgressDialog(this.context);
//				PhrescoLogger.info(TAG + "showProgressbar() - create new dialog");
			} else {
				dialog = null;
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "showProgressbar - Exception:" + ex.getMessage());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Show the message in progress bar
	 *
	 * @param msg
	 */
	public void setMessage(String msg) {
		this.progressMessage = msg;
	}

	protected void onPreExecute() {
		try {
//			PhrescoLogger.info(TAG + "onPreExecute()");
			if (dialog != null) {
				/*if (dialog.isShowing()) {

					PhrescoLogger.info(TAG + "onPreExecute() - previous dialog is showling.. dismiss it");
					dialog.dismiss();
					dialog = null;
					dialog = new ProgressDialog(this.context);
				}*/
				dialog.setMessage(progressMessage);
				dialog.setTitle(R.string.app_name);
				dialog.show();
//				PhrescoLogger.info(TAG + "onPreExecute() - show the dialog");
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "Timeout exception:" + ex.getMessage());
			PhrescoLogger.warning(ex);
		}
	}

	protected Entity doInBackground(String[] urls) {
		if (asyncTaskListener != null) {
			asyncTaskListener.processOnStart();
		}
		return null;
	}

	protected void onProgressUpdate(Integer[] progress) {
	}

	protected void onPostExecute(Entity results) {
//		PhrescoLogger.info(TAG + "onPostExecute()");
		if (dialog != null && dialog.isShowing()) {
//			PhrescoLogger.info(TAG + "onPostExecute() - previous dialog is showling.. dismiss it");
			dialog.dismiss();
			dialog = null;
		}
		if (asyncTaskListener != null) {
			asyncTaskListener.processOnComplete();
		}
	}

	/**
	 * Listener interface for current async task object
	 *
	 * @param wl
	 */
	public void setAsyncTaskListener(IAsyncTaskListener wl) {
		this.asyncTaskListener = wl;
	}

}
