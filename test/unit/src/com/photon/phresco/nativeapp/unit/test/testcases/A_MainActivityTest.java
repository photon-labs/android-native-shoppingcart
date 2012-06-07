package com.photon.phresco.nativeapp.unit.test.testcases;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.test.AndroidTestCase;

import com.photon.phresco.nativeapp.config.ConfigReader;
import com.photon.phresco.nativeapp.config.Configuration;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.appconfig.AppConfig;
import com.photon.phresco.nativeapp.unit.test.core.Constants;


/**
 * @author chandankumar_r
 *
 */

public class A_MainActivityTest extends AndroidTestCase{
	private static final String TAG = "A_MainActivityTest  *********** ";

	@BeforeClass
	public static void setUpBeforeClass() {
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}


	/**
	 * Read phresco-env-config.xml file to get to connect to web service
	 */
	public void readConfigXML() {
		try {

			
			String protocol = "protocol";
			String host = "host";
			String port = "port";
			String context = "context";
			Resources resources = this.mContext.getResources();
			AssetManager assetManager = resources.getAssets();
			Properties properties = new Properties();
			
			// Read from the /assets directory
			InputStream inputStream = assetManager.open(Constants.PHRESCO_ENV_CONFIG);
			
			ConfigReader confReaderObj = new ConfigReader(inputStream);
			
			PhrescoLogger.info(TAG + "Default ENV = " + confReaderObj.getDefaultEnvName());

			List<Configuration> configByEnv = confReaderObj.getConfigByEnv(confReaderObj.getDefaultEnvName());
			
			
			for (Configuration configuration : configByEnv) {
				properties = configuration.getProperties();
				PhrescoLogger.info(TAG + "config value = " + configuration.getProperties());
				String webServiceProtocol = properties.getProperty(protocol).endsWith("://") ? properties.getProperty(protocol) : properties.getProperty(protocol) + "://"; // http://

				String webServiceHost = properties.getProperty(port).equalsIgnoreCase("") ? (properties.getProperty(host).endsWith("/") ? properties.getProperty(host) : properties.getProperty(host) + "/")
						: properties.getProperty(host); // localhost/
														// localhost

				String webServicePort = properties.getProperty(port).equalsIgnoreCase("") ? "" : (properties.getProperty(port).startsWith(":") ? properties.getProperty(port) : ":"
						+ properties.getProperty(port)); // "" (blank)
															// :1313

				String webServiceContext = properties.getProperty(context).startsWith("/") ? properties.getProperty(context) : "/" + properties.getProperty(context); // /phresco

				Constants.setWebContextURL(webServiceProtocol + webServiceHost + webServicePort + webServiceContext + "/");
				Constants.setRestAPI(Constants.REST_API);
				PhrescoLogger.info(TAG + "Constants.webContextURL : " + Constants.getWebContextURL()+Constants.getRestAPI());
			}

		} catch (ParserConfigurationException ex) {
			PhrescoLogger.info(TAG + "readConfigXML : ParserConfigurationException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (SAXException ex) {
			PhrescoLogger.info(TAG + "readConfigXML : SAXException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (IOException ex) {
			PhrescoLogger.info(TAG + "readConfigXML : IOException: " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "readConfigXML : Exception: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

/**
 *  test method to read the properties while connecting to web server
 */
	/*public void testReadProperties() {
		readProperties();
	}*/


	/**
	 *  test method to read the properties while connecting to web server
	 */
		public void testReadConfigXML() {
			readConfigXML();
		}

	/**
	 *  get the application configuration from web server
	 *
	 */
	public void testSplash() {

		try{
			PhrescoLogger.info(TAG + " testSplash -------------- START ");

			JSONObject appConfigJSONObj = AppConfig.getAppConfigJSONObject(Constants.getWebContextURL()+ Constants.getRestAPI() + Constants.CONFIG_URL);
			assertNotNull(appConfigJSONObj);
			JSONArray appConfigArray;
			appConfigArray = appConfigJSONObj.getJSONArray("featureLayout");
			assertTrue(appConfigArray.length() > 0);

			PhrescoLogger.info(TAG + " testSplash -------------- END ");
			} catch (IOException ex) {
				PhrescoLogger.info(TAG + "testSplash - IOException: " + ex.toString());
				PhrescoLogger.warning(ex);
			}
			catch(JSONException ex){
			PhrescoLogger.info(TAG + "testSplash - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 *  get the application configuration from web server
	 *  and check its application version
	 *
	 */

	public void testSplashAppConfig(){
		try{
			PhrescoLogger.info(TAG + " testSplashAppConfig -------------- START ");

			JSONObject appConfigJSONObj = AppConfig.getAppConfigJSONObject(Constants.getWebContextURL()+ Constants.getRestAPI() + Constants.CONFIG_URL);
			assertNotNull(appConfigJSONObj);
			JSONObject appConfigObj;

			appConfigObj = appConfigJSONObj.getJSONObject("appVersionInfo");
			assertNotNull(appConfigObj);

			PhrescoLogger.info(TAG + " testSplashAppConfig -------------- END ");
			}catch (IOException ex) {
					PhrescoLogger.info(TAG + "testSplashAppConfig - IOException: " + ex.toString());
					PhrescoLogger.warning(ex);
			}catch (JSONException ex) {
			PhrescoLogger.info(TAG + "testSplashAppConfig - JSONException: " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

}
