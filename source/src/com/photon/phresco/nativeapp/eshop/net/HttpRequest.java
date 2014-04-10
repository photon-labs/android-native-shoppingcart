/**
 * PHR_AndroidNative
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
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

import android.util.Log;

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
	private static final String HTTP_STRING = "http";
	private static final String HTTPS_STREING = "https";

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
		
		int n = 4;
		int m =5;
		String s = sURL;
		
		String httpUrl= s.substring(0,n);
		String httpsUrl= s.substring(0,m);
		InputStream is = null;
		if(HTTP_STRING.equalsIgnoreCase(httpUrl)&& !HTTPS_STREING.equalsIgnoreCase(httpsUrl))
		{	
			System.out.println(" Entered in Http block ");
			is = httpGet(sURL);
			
		}else{
			//System.out.println(" Entered in Https block ");
			is = httpsGet(sURL);
	    }

		
		return is;
	}
	public static InputStream httpGet(String sURL) throws IOException {
		
		PhrescoLogger.info(TAG + "get: " + sURL);
		InputStream httpis = null;
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
		httpis = entity.getContent();

		return httpis;
		
	}
	
    public static InputStream httpsGet(String sURL) throws IOException {
    	PhrescoLogger.info(TAG + "httpsGet: " + sURL);
    	URL httpsurl;
    	InputStream https_is = null;
    	try {

			// Create a context that doesn't check certificates.
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[ ] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null,                // key manager
					trust_mgr,           // trust manager
					new SecureRandom()); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());

			httpsurl = new URL(sURL);
			HttpsURLConnection con = (HttpsURLConnection)httpsurl.openConnection();
			con.setHostnameVerifier(DO_NOT_VERIFY);
			int statusCode = con.getResponseCode();
			//int actualCode = 200;
			
			if (statusCode==HttpURLConnection.HTTP_OK) {
				
				https_is = con.getInputStream();
				
				
				
			}
			// Guard against "bad hostname" errors during handshake.
			con.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String host, SSLSession sess) {
					return true;	                    
				}
			});

			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		}	
    	
    	
		return https_is;
		
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
	/**
	 * Send the JSON data to specified URL and get the httpResponse back
	 *
	 * @param sURL
	 * @param jObject
	 * @return InputStream
	 * @throws Exception 
	 */
	public static InputStream post(String sURL, JSONObject jObject) throws Exception {
		
		PhrescoLogger.info(TAG + "post: " + sURL);
		PhrescoLogger.info(TAG + "post: " + jObject);
		int n = 4;
		int m =5;
		
		String s = sURL;
		
		String httpUrl= s.substring(0,n);
		String httpsUrl= s.substring(0,m);
		InputStream is = null;
		if(HTTP_STRING.equalsIgnoreCase(httpUrl)&& !HTTPS_STREING.equalsIgnoreCase(httpsUrl))
		{	
			System.out.println(" Entered in Http block ");
			is = httppost(sURL,jObject);
			
		}else{
			System.out.println(" Entered in Https block ");
			is = httpspost(sURL,jObject);
	    }

		
		return is;
		
	}
	
	
	public static InputStream httppost(String sURL,JSONObject jObject) throws IOException {
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
		
	
	public static InputStream httpspost(String sURL,JSONObject jObject) throws Exception {
		
		PhrescoLogger.info(TAG + "post: " + sURL);
		PhrescoLogger.info(TAG + "post: " + jObject);
		
		URL httpsurl;
    	InputStream https_is = null;
    	
    	HttpsURLConnection con =null;
    		
    	try
    	{
		// Create a context that doesn't check certificates.
			SSLContext ssl_ctx = SSLContext.getInstance("TLS");
			TrustManager[ ] trust_mgr = get_trust_mgr();
			ssl_ctx.init(null,                // key manager
					trust_mgr,           // trust manager
					new SecureRandom()); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());

			httpsurl = new URL(sURL);
			
			
				 con = (HttpsURLConnection)httpsurl.openConnection();
				    try {
				    	con.setRequestMethod("POST"); //use post method
				    	con.setDoOutput(true); //we will send stuff
				    	con.setDoInput(true); //we want feedback
				    	con.setUseCaches(false); //no caches
				    	
				    	con.setRequestProperty("Accept","application/json");
				    	con.setRequestProperty("Content-Type","application/json");
				    } 
				    catch (ProtocolException e) {
				    }
				    
				    OutputStream out = con.getOutputStream();
				    try {
				      OutputStreamWriter wr = new OutputStreamWriter(out);
				      
						
				      wr.write(jObject.toString()); 
				      wr.flush();
				      wr.close();
				    }
				    catch (IOException e) {
				    }
				    finally { //in this case, we are ensured to close the output stream
				      if (out != null)
				        out.close();
				    }
				    https_is = con.getInputStream();
				   
				    try {
				      BufferedReader rd  = new BufferedReader(new InputStreamReader(https_is));
				      String responseSingle=null;
				      Object response ="";
					while ((responseSingle = rd.readLine()) != null) {
						 ((Writer) response).append(responseSingle);
						
				      }
				      rd.close(); //close the reader
				      //println("The server response is " + response);
				    }
				    catch (IOException e) {
				    }
				   
				  }
				  catch (IOException e) {
				  } 
				  finally {  //in this case, we are ensured to close the connection itself
				    if (con != null)
				      con.disconnect();
				  }
    	   return https_is;
    	}
    }

