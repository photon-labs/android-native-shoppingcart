/**
 *
 */
package com.photon.phresco.nativeapp.eshop.util;

import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * @author viral_b
 *
 */
public class StringUtils {

	private static final String TAG = "StringUtils ***** ";
	/**
	 * Check if the required field is present, or left blank
	 *
	 * @param param
	 * @return boolean
	 */
	public boolean isEmpty(String param) {
		boolean isEmpty = false;
		try {
			isEmpty = param.trim().length() == 0 ? true : false;
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - isEmpty  - Exception : " + ex.toString());
			PhrescoLogger.warning(ex);
			isEmpty = true;
		}
		return isEmpty;
	}

}
