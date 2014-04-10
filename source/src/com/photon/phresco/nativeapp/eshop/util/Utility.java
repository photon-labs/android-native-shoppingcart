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
package com.photon.phresco.nativeapp.eshop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.regex.Pattern;

import android.os.Build;

import com.photon.phresco.nativeapp.eshop.core.Constants;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;
import com.photon.phresco.nativeapp.eshop.net.HttpRequest;

/**
 * General purpose utility class
 *
 * @author viral_b
 *
 */
public class Utility {
	private static final String TAG = "Utility ********";
	private static final int ARRAY_SIZE = 1024;

	/**
	 * Creates necessary directories on sdcard
	 */

	protected Utility() {
	}

	public static void createRequiredDirectory() {
		try {
			File f = new File(Constants.LOG_FOLDER_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			PhrescoLogger.info(TAG + "log/ Directory created: " + Constants.LOG_FOLDER_PATH);

			f = new File(Constants.IMAGE_FOLDER_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			PhrescoLogger.info(TAG + "images/ Directory created: " + Constants.IMAGE_FOLDER_PATH);

			f = new File(Constants.MENU_FOLDER_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			PhrescoLogger.info(TAG + "images/menu Directory created: " + Constants.MENU_FOLDER_PATH);

			f = new File(Constants.CATEGORIES_FOLDER_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			PhrescoLogger.info(TAG + "images/categories Directory created: " + Constants.CATEGORIES_FOLDER_PATH);

			f = new File(Constants.PRODUCT_FOLDER_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			PhrescoLogger.info(TAG + "images/product Directory created: " + Constants.PRODUCT_FOLDER_PATH);

			f = new File(Constants.PRODUCT_DETAIL_FOLDER_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}
			PhrescoLogger.info(TAG + "images/product/details Directory created: " + Constants.PRODUCT_DETAIL_FOLDER_PATH);

		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "createRequiredDirectory() : Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Create the directory
	 *
	 * @param dirName
	 */
	public static void createDirectory(File dirName) {
		try {
			dirName.mkdirs();
			PhrescoLogger.info(TAG + dirName + "/ Directory created: ");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "createDirectory() -  Exception:  " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Deletes all files and subdirectories under dir. Returns true if all
	 * deletions were successful. If a deletion fails, the method stops
	 * attempting to delete and returns false.
	 *
	 * @param file
	 * @return boolean
	 */

	public static boolean deleteDir(File f) {
		boolean flag = false;
		try {
			PhrescoLogger.info(TAG + "Inside deleteDir() : " + f.getName());

			if (f.exists() && f.isDirectory()) {
				String[] children = f.list();
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteDir(new File(f, children[i]));
					if (!success) {
						return false;
					}
				}
			}

			// The directory is now empty so delete it
			flag = f.delete();
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "deleteDir -  Exception:  " + ex.toString());
			PhrescoLogger.warning(ex);
			flag = false;
		}
		return flag;
	}

	/**
	 * Deletes the log file from log directory on sdcard Returns, if log file is
	 * deleted successfully, false otherwise.
	 *
	 * @return boolean
	 */
	public static boolean deleteLogFile() {
		boolean flag = false;
		try {
			File logFile = new File(Constants.LOG_FOLDER_PATH + Constants.LOG_FILE);
			flag = logFile.delete();
			PhrescoLogger.info(TAG + Constants.LOG_FOLDER_PATH + Constants.LOG_FILE + " file deleted = " + flag);
			File logFileLck = new File(Constants.LOG_FOLDER_PATH + Constants.LOG_FILE + ".lck");
			flag = logFileLck.delete();
			PhrescoLogger.info(TAG + Constants.LOG_FOLDER_PATH + Constants.LOG_FILE + ".lck file deleted = " + flag);
			flag = true;
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + "deleteLogFile -  Exception:  " + ex.toString());
			PhrescoLogger.warning(ex);
			flag = false;
		}
		return flag;
	}

	/**
	 * Download the file from specified URL to specified local folder on sdcard.
	 * If download is successful, returns true, false otherwise
	 *
	 * @param url
	 * @param localpath
	 * @return boolean
	 */
	public static boolean downloadFileToLocalStorage(String url, String localpath) {
		boolean downloadFlag = false;
		try {
			if (url != null && url.length() > 0) {
				FileOutputStream out = new FileOutputStream(localpath);

				// Hit the url to get the file contents from web server
				InputStream in = Utility.httpGet(url);

				int len = 0;
				byte[] buffer1 = new byte[ARRAY_SIZE];

				if (in != null) {
					while ((len = in.read(buffer1)) > 0) {
						out.write(buffer1, 0, len);
					}
					in.close();
					out.close();
					downloadFlag = true;
				}
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " downloadFileToLocalStorage: Exception: --> " + ex.toString());
			PhrescoLogger.warning(ex);
		}
		return downloadFlag;
	}

	/**
	 * Download the file from web
	 *
	 * @param fileUrl
	 *            URL of the file
	 * @return InputStream
	 */
	public static InputStream httpGet(String fileUrl) {
		InputStream content = null;

		try {
			// content = HttpRequest.getWithReturnInputStream(fileUrl);
			content = HttpRequest.get(fileUrl);
		} catch (IOException ioe) {
			PhrescoLogger.log(Level.WARNING, "exception", ioe);
		} catch (Exception e) {
			PhrescoLogger.log(Level.WARNING, "exception", e);
		}
		return content;
	}

	/**
	 * Returns the device information
	 */
	public static void getDeviceInfo() {
		try {
			PhrescoLogger.info("----------------- DEVICE INFORMATION START ------------------------");
			PhrescoLogger.info("BOARD 			: " + Build.BOARD);
			// PhrescoLogger.info("BOOTLOADER 		: " + Build.BOOTLOADER);
			PhrescoLogger.info("BRAND 			: " + Build.BRAND);
			PhrescoLogger.info("CPU_ABI			: " + Build.CPU_ABI);
			// PhrescoLogger.info("CPU_ABI2		: " + Build.CPU_ABI2);
			PhrescoLogger.info("DEVICE 			: " + Build.DEVICE);
			PhrescoLogger.info("DISPLAY 		: " + Build.DISPLAY);
			PhrescoLogger.info("FINGERPRINT 	: " + Build.FINGERPRINT);
			// PhrescoLogger.info("HARDWARE		: " + Build.HARDWARE);
			PhrescoLogger.info("HOST 			: " + Build.HOST);
			PhrescoLogger.info("ID 				: " + Build.ID);
			PhrescoLogger.info("MANUFACTURER	: " + Build.MANUFACTURER);
			PhrescoLogger.info("MODEL 			: " + Build.MODEL);
			PhrescoLogger.info("PRODUCT 		: " + Build.PRODUCT);
			// PhrescoLogger.info("RADIO	 		: " + Build.RADIO);
			// PhrescoLogger.info("SERIAL	 		: " + Build.SERIAL);
			PhrescoLogger.info("TAGS 			: " + Build.TAGS);
			PhrescoLogger.info("TIME 			: " + Build.TIME);
			PhrescoLogger.info("TYPE 			: " + Build.TYPE);
			PhrescoLogger.info("UNKNOWN			: " + Build.UNKNOWN);
			PhrescoLogger.info("USER 			: " + Build.USER);
			PhrescoLogger.info("VERSION CODENAME: " + Build.VERSION.CODENAME);
			PhrescoLogger.info("----------------- DEVICE INFORMATION END --------------------------");
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " getDeviceInfo: Exception: --> " + ex.toString());
			PhrescoLogger.warning(ex);
		}
	}

	/**
	 * Returns valid email address pattern
	 *
	 * @return
	 */
	public static Pattern getValidEmailPattern() {
		return Pattern.compile("^[A-Za-z0-9-_]+(\\.[A-Za-z0-9-_]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	/**
	 * Check if the provided email address is valid
	 */
	public static boolean checkEmail(String loginEmailId) {
		return Utility.getValidEmailPattern().matcher(loginEmailId).matches();
	}

}
