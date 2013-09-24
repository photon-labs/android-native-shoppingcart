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
package com.photon.phresco.nativeapp.eshop.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.net.HttpRequest;

/**
 * Base class to read the JSON from web server
 *
 * @author viral_b
 *
 */
public class JSONHelper {
	private static final String TAG = "JSONHelper ***** ";

	protected JSONHelper() {}

	/**
	 * Get the JSON object from the specified URL
	 *
	 * @param url
	 * @return JSONObject
	 * @throws IOException
	 * @throws ioException
	 * @throws ClientProtocolException
	 */
	public static JSONObject getJSONObjectFromURL(String url)
			throws IOException {
		InputStream is = null;
		String result = null;
		JSONObject jObject = null;

		try {
			is = HttpRequest.get(url);
		} catch (MalformedURLException ex) {
			PhrescoLogger.info(TAG
					+ "getJSONObjectFromURL - MalformedURLException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		} catch (UnsupportedEncodingException ex) {
			PhrescoLogger.info(TAG
					+ "getJSONObjectFromURL - UnsupportedEncodingException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		} catch (IllegalStateException ex) {
			PhrescoLogger.info(TAG
					+ "getJSONObjectFromURL - IllegalStateException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}

		// Convert the input stream in to string
		if (is != null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), Integer.parseInt("8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				reader.close();
				result = sb.toString();
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + ex.toString());
				PhrescoLogger.warning(ex);
			}

			// Parse the string to a JSON object
			try {
				jObject = new JSONObject(result);
			} catch (Exception ex) {
				PhrescoLogger.info(TAG
						+ "getJSONObjectFromURL() - JSONException: "
						+ ex.toString());
				PhrescoLogger.warning(ex);
			}
		}

		return jObject;
	}

	/**
	 * POST the JSON object to the specified URL, and get the response back
	 *
	 * @param url
	 * @return JSONObject
	 * @throws Exception
	 */
	public static JSONObject postJSONObjectToURL(String url, String jsonParam)
			throws Exception {
		InputStream is = null;
		String result = null;
		JSONObject jObject = null;

		// Post json data to server, and get the response
		try {
//			jObject = new JSONObject();
			jObject = new JSONObject(jsonParam);
//			jObject.getJSONObject(jsonParam);
			PhrescoLogger.info(TAG + "JSON POST OBJECT: " + jObject);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "postJSONObjectToURL: " + ex.toString());
			PhrescoLogger.warning(ex);
		}

		try {
			is = HttpRequest.post(url, jObject);
		} catch (ClientProtocolException ex) {
			PhrescoLogger.info(TAG
					+ "postJSONObjectToURL - ClientProtocolException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		} catch (MalformedURLException ex) {
			PhrescoLogger.info(TAG
					+ "postJSONObjectToURL - MalformedURLException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		} catch (UnsupportedEncodingException ex) {
			PhrescoLogger.info(TAG
					+ "postJSONObjectToURL - UnsupportedEncodingException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		} catch (IllegalStateException ex) {
			PhrescoLogger.info(TAG
					+ "postJSONObjectToURL - IllegalStateException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}

		// Convert the input stream in to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), Integer.parseInt("8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			reader.close();
			result = sb.toString();
//			PhrescoLogger.info(TAG + "JSON Response String: " + result);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + ex.toString());
			PhrescoLogger.warning(ex);
		}

		// Parse the string to a JSON object
		try {
			jObject = new JSONObject(result);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "postJSONObjectToURL() - JSONException: "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}

		return jObject;
	}
}