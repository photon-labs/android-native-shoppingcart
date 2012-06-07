
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
