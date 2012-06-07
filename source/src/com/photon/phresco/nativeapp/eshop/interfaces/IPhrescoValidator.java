/**
 *
 */
package com.photon.phresco.nativeapp.eshop.interfaces;

/**
 * Interface to be implemented by model classes where input fields need to be
 * validated
 *
 * @author viral_b
 *
 */
public interface IPhrescoValidator {
	/**
	 *	Check if required field is not left blank
	 * @param param
	 * @return
	 */
	boolean isEmpty(String param);
}
