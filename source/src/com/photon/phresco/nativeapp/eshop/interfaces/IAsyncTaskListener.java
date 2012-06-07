
/*
 * Interface name: AsyncTaskListener
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.interfaces;


/**
 * Interface to be used by all the async task methods
 * @author viral_b
 *
 */
public interface IAsyncTaskListener {

	/**
	 * Method to be implemented when background process starts
	 */
	void processOnStart();
	/**
	 * Method to be implemented when background process completes
	 */
	void processOnComplete();
}
