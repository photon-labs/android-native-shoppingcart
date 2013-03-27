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
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 *
 * HTTP Request class
 *
 * @author viral_b
 */

public class HttpRequest {

	private static final String TAG = "HttpRequest ***** ";
	private static final int TIME_OUT = 5000;

	/**
	 * HttpGet request
	 *
	 * @param sUrl
	 * @return
	 */

	protected HttpRequest() {
	}


	/**
	 * Get the content from web url, and parse it using json parser
	 *
	 * @param sURL
	 *            : URL to hit to get the json content
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream get(String sURL) throws IOException {

		PhrescoLogger.info(TAG + "get: " + sURL);
		InputStream is = null;
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = TIME_OUT;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = TIME_OUT;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpGet httpGet = new HttpGet(sURL);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		is = entity.getContent();

		return is;
	}

	/**
	 * Send the JSON data to specified URL and get the httpResponse back
	 *
	 * @param sURL
	 * @param jObject
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream post(String sURL, JSONObject jObject) throws IOException {
		HttpResponse httpResponse = null;
		InputStream is = null;

		PhrescoLogger.info(TAG + " post: " + sURL);
		PhrescoLogger.info(TAG + " jObject: " + jObject);

		HttpPost httpPostRequest = new HttpPost(sURL);
		HttpEntity entity;

		httpPostRequest.setHeader("Accept", "application/json");
		httpPostRequest.setHeader(HTTP.CONTENT_TYPE, "application/json");
		httpPostRequest.setEntity(new ByteArrayEntity(jObject.toString().getBytes("UTF-8")));
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = TIME_OUT;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = TIME_OUT;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		httpResponse = httpClient.execute(httpPostRequest);

		if (httpResponse != null) {
			entity = httpResponse.getEntity();
			is = entity.getContent();
		}
		return is;
	}
}
