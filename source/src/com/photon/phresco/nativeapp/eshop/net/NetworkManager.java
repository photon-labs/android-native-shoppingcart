/**
 * PHR_AndroidNative
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.nativeapp.eshop.net;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.activity.MainActivity;
import com.photon.phresco.nativeapp.eshop.activity.PhrescoActivity;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

public class NetworkManager {
	private static final String TAG = "NetworkManager ********";

	public static boolean checkURLStatus(final String url) {
		boolean statusFlag = false;
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				statusFlag = true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statusFlag;
	}

	public static boolean checkNetworkConnectivity(final Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else if (netInfo != null
				&& (netInfo.getState() == NetworkInfo.State.DISCONNECTED
						|| netInfo.getState() == NetworkInfo.State.DISCONNECTING
						|| netInfo.getState() == NetworkInfo.State.SUSPENDED || netInfo
						.getState() == NetworkInfo.State.UNKNOWN)) {
			return false;
		} else {
			return false;
		}
	}

	public static void showNetworkConectivityAlert(final MainActivity activity) {
		activity.showErrorDialog(activity.getString(R.string.network_error));
	}

	public static void showServiceAlert(final MainActivity activity) {
		activity.showErrorDialog(activity.getString(R.string.service_error));
	}

}
