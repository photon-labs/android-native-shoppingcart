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
package com.photon.phresco.nativeapp.eshop.model.appconfig;

/**
 * Holds the path for HDPI, MDPI and LDPI images for android application
 * @author viral_b
 *
 */
public class AndroidFeatureImagePaths {
	private String hdpi;
	private String mdpi;
	private String ldpi;
	private String xhdpi;

	/**
	 * @return the hdpi
	 */
	public String getHdpi() {
		return hdpi;
	}

	/**
	 * @param hdpi the hdpi to set
	 */
	public void setHdpi(String hdpi) {
		this.hdpi = hdpi;
	}

	/**
	 * @return the mdpi
	 */
	public String getMdpi() {
		return mdpi;
	}

	/**
	 * @param mdpi the mdpi to set
	 */
	public void setMdpi(String mdpi) {
		this.mdpi = mdpi;
	}

	/**
	 * @return the ldpi
	 */
	public String getLdpi() {
		return ldpi;
	}

	/**
	 * @param ldpi the ldpi to set
	 */
	public void setLdpi(String ldpi) {
		this.ldpi = ldpi;
	}

	/**
	 * @return the xhdpi
	 */
	public String getXhdpi() {
		return xhdpi;
	}

	/**
	 * @param xhdpi the xhdpi to set
	 */
	public void setXhdpi(String xhdpi) {
		this.xhdpi = xhdpi;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AndroidFeatureImagePaths [hdpi=");
		builder.append(hdpi);
		builder.append(", mdpi=");
		builder.append(mdpi);
		builder.append(", ldpi=");
		builder.append(ldpi);
		builder.append("]");
		return builder.toString();
	}
}
