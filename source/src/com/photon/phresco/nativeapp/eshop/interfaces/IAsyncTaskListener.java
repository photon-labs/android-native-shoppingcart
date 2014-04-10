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
