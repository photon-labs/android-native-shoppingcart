/*
 * ###
 * PHR_AndroidNative
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */

/*
 * Classname: AppVersionInfo
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.appconfig;

import java.util.Arrays;

/**
 * Holds the AppVersionInfo of JSON object read from web server
 * @author viral_b
 *
 */
public class AppVersionInfo {

	private String configVersion;
	private String appStoreLink;
	private String androidMarketLink;
	private String[] supportedVersions;

	/**
	 * @return the configVersion
	 */
	public String getConfigVersion() {
		return configVersion;
	}

	/**
	 * @param configVersion the configVersion to set
	 */
	public void setConfigVersion(String configVersion) {
		this.configVersion = configVersion;
	}

	/**
	 * @return the appStoreLink
	 */
	public String getAppStoreLink() {
		return appStoreLink;
	}

	/**
	 * @param appStoreLink the appStoreLink to set
	 */
	public void setAppStoreLink(String appStoreLink) {
		this.appStoreLink = appStoreLink;
	}

	/**
	 * @return the androidMarketLink
	 */
	public String getAndroidMarketLink() {
		return androidMarketLink;
	}

	/**
	 * @param androidMarketLink the androidMarketLink to set
	 */
	public void setAndroidMarketLink(String androidMarketLink) {
		this.androidMarketLink = androidMarketLink;
	}

	/**
	 * @return the supportedVersions
	 */
	public String[] getSupportedVersions() {
		return supportedVersions;
	}

	/**
	 * @param supportedVersions the supportedVersions to set
	 */
	public void setSupportedVersions(String[] supportedVersions) {
		this.supportedVersions = supportedVersions.clone();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppVersionInfo [configVersion=");
		builder.append(configVersion);
		builder.append(", appStoreLink=");
		builder.append(appStoreLink);
		builder.append(", androidMarketLink=");
		builder.append(androidMarketLink);
		builder.append(", supportedVersions=");
		builder.append(Arrays.toString(supportedVersions));
		builder.append("]");
		return builder.toString();
	}
}
