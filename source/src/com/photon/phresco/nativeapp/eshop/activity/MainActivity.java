/*
 * Classname: MainActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */

package com.photon.phresco.nativeapp.eshop.activity;

import java.io.File;
import java.io.IOException;
import java.util.Currency;
import java.util.Locale;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.photon.phresco.nativeapp.R;
import com.photon.phresco.nativeapp.eshop.application.PhrescoApp;
import com.photon.phresco.nativeapp.eshop.core.AsyncTaskHelper;
import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.db.AppConfiguration;
import com.photon.phresco.nativeapp.eshop.interfaces.IAsyncTaskListener;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.model.appconfig.AppConfig;
import com.photon.phresco.nativeapp.eshop.util.Utility;

/**
 * Show splash screen for the application, with current version number
 *
 * @author viral_b
 *
 */
public class MainActivity extends PhrescoActivity {

	private static final String TAG = "SplashActivity ******* ";
	private static final int NEXT_ACTIVITY = 0;
	private static final int SPLASH_DELAY = 1000;
	private String curSymbol;
	private Exception ioException = null;
	private String appConfigRow = "app_config_version";
	private int screenDensity;

	private String currActivity = "currentActivity";

	@Override
	public void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			setContentView(R.layout.splash);
			// PhrescoLogger.info(TAG + " - onCreate()********** ");

			initApplicationEnvironment();
			readConfigXML();
			screenDensity = getScreenDensity();
			readAppConfigJSON();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " onCreate -  Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the required folder structures on external storage Read the device
	 * information
	 */
	private void initApplicationEnvironment() {

		try {
			// Delete existing log file, when application starts, so the log
			// file doesn't consume more place on external storage device
			Utility.deleteLogFile();

			// Delete Product image folders
			Utility.deleteDir(new File(Constants.PRODUCT_FOLDER_PATH));

			// Create all the directories required for this application
			Utility.createRequiredDirectory();

			// Get the current currency symbol from device
			curSymbol = Currency.getInstance(Locale.getDefault()).getSymbol();
			PhrescoLogger.info(TAG + " - Currency Symbol:  " + curSymbol);

			// Get the package version information
			String versionName = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA).versionName;
			TextView versionNo = (TextView) findViewById(R.id.version_no);
			versionNo.setText(versionName);

			// Get the device information
			Utility.getDeviceInfo();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " initApplicationEnvironment -  Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Read the application configuration JSON from web server, in async task.
	 */
	private void readAppConfigJSON() {
		try {
			AsyncTaskHelper asyncTask = new AsyncTaskHelper(MainActivity.this);

			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.load_configuration));
			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					getAppConfigFromServer();
				}

				@Override
				public void processOnComplete() {
					processAppConfigResponse();
				}
			});
			asyncTask.execute();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Get the app config json from server
	 */
	private void getAppConfigFromServer() {
		try {
			getAppConfigJSONObject();
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " getAppConfigFromServer - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * @throws NumberFormatException
	 */
	private void processAppConfigResponse() {

		try {
			PhrescoLogger.info(TAG + " processAppConfigResponse() : ");
			if (ioException == null) {

				setImageSourcePath();
				setCurrencySymbol();

				// Get the AppConfigVersion number from local SQLite db
				int localAppConfigVersion = getLocalAppConfigVersion();

				PhrescoLogger.info(TAG + " readAppConfigJSON() - appConfigJSONObj.appVersionInfo.configVersion: " + getAppConfigJSONObj().getAppVersionInfo().getConfigVersion());
				if (Integer.parseInt(getAppConfigJSONObj().getAppVersionInfo().getConfigVersion()) != localAppConfigVersion) {
					Utility.deleteDir(new File(Constants.MENU_FOLDER_PATH));
					Utility.createDirectory(new File(Constants.MENU_FOLDER_PATH));
					downloadMenus();
				} else {
					startNextActivity();
				}
			} else {
				showNormalErrorDialog();
			}
		} catch (NumberFormatException ex) {
			PhrescoLogger.info(TAG + " NumberFormatException " + ex.toString());
			PhrescoLogger.warning(ex);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}

	}

	/**
	 * Set the currency symbol for current device
	 */
	private void setCurrencySymbol() {
		boolean isSupportedCurranciesAvailableWithDevice = false;
		for (String currency : getAppConfigJSONObj().getSupportedCurrencies()) {
			if (currency.equalsIgnoreCase(curSymbol)) {
				Constants.setCurrency(currency);
				isSupportedCurranciesAvailableWithDevice = true;
				break;
			}
		}
		if (!isSupportedCurranciesAvailableWithDevice) {
			Constants.setCurrency(getAppConfigJSONObj().getDefaultCurrency());
		}

		PhrescoLogger.info(TAG + " Currency : " + Constants.getCurrency());
	}

	/**
	 * Set the image source paths for the device depending upon the device
	 * screen density
	 */
	private void setImageSourcePath() {
		try {
			String imageSourcePath = Constants.getWebContextURL();
			PhrescoLogger.info(TAG + " imageSourcePath : " + imageSourcePath);

			switch (screenDensity) {
			case DisplayMetrics.DENSITY_LOW:
				imageSourcePath += getAppConfigJSONObj().getAndroidFeatureImagePaths().getLdpi();
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				imageSourcePath += getAppConfigJSONObj().getAndroidFeatureImagePaths().getMdpi();
				break;
			case DisplayMetrics.DENSITY_HIGH:
				imageSourcePath += getAppConfigJSONObj().getAndroidFeatureImagePaths().getHdpi();
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				imageSourcePath += getAppConfigJSONObj().getAndroidFeatureImagePaths().getXhdpi();
				break;
			}
			Constants.setCurrentScreenResolution(imageSourcePath);
			PhrescoLogger.info(TAG + " Constants.currentScreenResolution Set to : " + Constants.getCurrentScreenResolution());
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " setImageSourcePath " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Download the menu images from server, in async task.
	 */
	private void downloadMenus() {
		try {

			AsyncTaskHelper asyncTask = new AsyncTaskHelper(MainActivity.this);
			asyncTask.showProgressbar(true);
			asyncTask.setMessage(getString(R.string.load_menus));

			asyncTask.setAsyncTaskListener(new IAsyncTaskListener() {

				@Override
				public void processOnStart() {
					updatetLocalAppConfigVersion(getAppConfigJSONObj().getAppVersionInfo().getConfigVersion());
					downloadMenusFromServer();
				}

				@Override
				public void processOnComplete() {
					startNextActivity();
				}

			});
			asyncTask.execute();

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " Download Image Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Download the menu images from server, and store it on sdcard
	 */
	private void downloadMenusFromServer() {
		try {
			AppConfig.downloadMenuImages(getAppConfigJSONObj().getFeatureConfig());
		} catch (IOException ioe) {
			ioException = ioe;
			PhrescoLogger.warning(ioe);
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " readAppConfigJSON - Exception " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Start the next activity from splash screen
	 */
	private void startNextActivity() {
		if (ioException == null) {
			startHomeActivity();
		} else {
			showErrorDialogWithCancel();
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	/**
	 * Show the home screen
	 */
	private void startHomeActivity() {
		PhrescoLogger.info(TAG + " Inside startNextActivity() ");
		Message msg = new Message();
		msg.what = NEXT_ACTIVITY;
		splashHandler.sendMessageDelayed(msg, SPLASH_DELAY);
	}

	/**
	 * Handler to call the method to start next activity from splash screen
	 */
	private Handler splashHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
				homeActivity.putExtra(currActivity, "home");
				startActivity(homeActivity);
				finish();
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + " - splashHandler  - Exception : " + ex.toString());
				PhrescoLogger.warning(ex);
			}
		}
	};

	/**
	 * Get the Application Configuration Version from local SQLite database.
	 *
	 * @return appConfigVersion
	 */
	private int getLocalAppConfigVersion() {
		int appConfigVersion = 0;
		try {

			AppConfiguration appConf = new AppConfiguration(((PhrescoApp) getApplication()).getDatabase());

			// Check if App Config Version is stored in local database
			Cursor searchAppVersionRow = appConf.searchAllRow(appConfigRow);

			if (searchAppVersionRow.getCount() > 0) {
				searchAppVersionRow.moveToFirst();
				appConfigVersion = searchAppVersionRow.getInt(searchAppVersionRow.getColumnIndex(AppConfiguration.KEY_META_VALUE));
				// Close the database connection
				searchAppVersionRow.close();
			}
			PhrescoLogger.info(TAG + " - getLocalAppConfigVersion() - Local App Config Version  : " + appConfigVersion);

		} catch (SQLException ex) {
			PhrescoLogger.info(TAG + " - getLocalAppConfigVersion() - Exception:  " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return appConfigVersion;
	}

	/**
	 * Update the Application Configuration Version in local SQLite database, if
	 * it already exist in SQLite database, else add it into database. Also
	 * store the current Application Configuration Version of local SQLite
	 * database, into another row, called Previous Application Configuration
	 * Version, to check that when category and product download process comes
	 * into picture
	 *
	 * @param configVersion
	 */
	private void updatetLocalAppConfigVersion(String configVersion) {

		try {
			PhrescoLogger.info(TAG + " - updatetLocalAppConfigVersion() -  new configVersion = " + configVersion);
			AppConfiguration appConf = new AppConfiguration(((PhrescoApp) getApplication()).getDatabase());

			// Check if AppConfigVersion is stored in local database
			Cursor searchAppVersionRow = appConf.searchAllRow(appConfigRow);

			if (searchAppVersionRow.getCount() > 0) {
				searchAppVersionRow.moveToFirst();

				// Get current AppConfigVersion number row id from SQLite db
				long currentRowId = searchAppVersionRow.getLong(searchAppVersionRow.getColumnIndexOrThrow(AppConfiguration.KEY_ROWID));

				// Get the current categoryVersionNo from SQLite db
				String appConfigVersionNo = searchAppVersionRow.getString(searchAppVersionRow.getColumnIndex(AppConfiguration.KEY_META_VALUE));

				PhrescoLogger.info(TAG + " - Existing Config Version = " + appConfigVersionNo + " in local database");

				// if App Config Version exist in local database, update
				// configVersion into database.
				appConf.updateRow(currentRowId, appConfigRow, configVersion);
				PhrescoLogger.info(TAG + " - UPDATE Local App Config Version = " + configVersion + " in local database");
				searchAppVersionRow.close();
			} else {

				// if App Config Version not exist in local database, insert
				// configVersion from JSON.
				// Also create CategoryVersion and ProductVersion entries
				// into database. They are required to check on category and
				// product screens for image download

				appConf.createRow(appConfigRow, configVersion);
				appConf.createRow("category_version", String.valueOf(0));
				appConf.createRow("product_version", String.valueOf(0));

				PhrescoLogger.info(TAG + " - INSERT CategoryVersion = 0 in local database");
				PhrescoLogger.info(TAG + " - INSERT ProductVersion = 0 in local database");
				PhrescoLogger.info(TAG + " - INSERT App Config Version = " + configVersion + " in local database");
			}
		} catch (SQLException ex) {
			PhrescoLogger.info(TAG + " - updatetLocalAppConfigVersion() - Exception:  " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the AppConfig class object, by reading the configuration JSON from
	 * web server
	 *
	 * @throws IOException
	 *
	 */
	public void getAppConfigJSONObject() throws IOException {
		setAppConfigJSONResponseObj(AppConfig.getAppConfigJSONObject(Constants.getWebContextURL() + Constants.getRestAPI() + Constants.CONFIG_URL));
		if (getAppConfigJSONResponseObj() != null) {
			setAppConfigJSONObj(AppConfig.getAppConfigGSONObject(getAppConfigJSONResponseObj().toString()));
		}
	}


}
