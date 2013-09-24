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
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import android.text.StaticLayout;
import android.util.Log;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.activity.MainActivity;
import com.photon.phresco.nativeapp.eshop.activity.PhrescoActivity;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

public class NetworkManager {
	private static final String TAG = "NetworkManager ********";
	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	
	public static boolean checkURLStatus(final String url) {
		
		int n = 4;
		int m =5;
		boolean statusFlag = false;
		String s = url;
		String httpUrl= s.substring(0,n);
		String httpsUrl= s.substring(0,m);
	
		
		if(HTTP.equalsIgnoreCase(httpUrl)&& !HTTPS.equalsIgnoreCase(httpsUrl))
		{	
			
			statusFlag = checkHttpURLStatus(url);
			
		}else{
			
			statusFlag = checkHttpsURLStatus(url);
	    }
		return statusFlag;
	}
   
	public static boolean checkHttpURLStatus(final String url) {
		boolean httpStatusFlag = false;
		
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				httpStatusFlag = true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpStatusFlag;
		
	}
	public static boolean checkHttpsURLStatus(final String url) {
		boolean https_StatusFlag = false;
		System.out.println("Entered in checkHttpsURLStatus >>>>>>>>>>>>>>>");
		
		URL httpsurl;
		try {

			// Create a context that doesn't check certificates.
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[ ] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null,                // key manager
					trust_mgr,           // trust manager
					new SecureRandom()); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
            System.out.println("Url ========="+url);
			httpsurl = new URL(url);
			
			HttpsURLConnection con = (HttpsURLConnection)httpsurl.openConnection();
		    con.setHostnameVerifier(DO_NOT_VERIFY);
			int statusCode = con.getResponseCode();
			System.out.println("statusCode ========="+statusCode);
			
			if (statusCode==HttpURLConnection.HTTP_OK) {
				
				https_StatusFlag = true;
				
			}
			
			} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}	
		
		return https_StatusFlag;
	}
	
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	
	private static TrustManager[ ] get_trust_mgr() {
		TrustManager[ ] certs = new TrustManager[ ] {
				new X509TrustManager() {
					public X509Certificate[ ] getAcceptedIssuers() { return null; }
					public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
					public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
				}
		};
		return certs;
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
