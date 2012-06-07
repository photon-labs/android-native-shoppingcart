
/*
 * Classname: FeatureConfig
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.appconfig;

/**
 * Holds the FeatureConfig information of JSON object read from web server
 * @author viral_b
 *
 */
public class FeatureConfig {

	private String name;
	private FeatureIcon featureIcon;
	private String featureUrl;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the featureIcon
	 */
	public FeatureIcon getFeatureIcon() {
		return featureIcon;
	}

	/**
	 * @param featureIcon the featureIcon to set
	 */
	public void setFeatureIcon(FeatureIcon featureIcon) {
		this.featureIcon = featureIcon;
	}

	/**
	 * @return the featureUrl
	 */
	public String getFeatureUrl() {
		return featureUrl;
	}

	/**
	 * @param featureUrl the featureUrl to set
	 */
	public void setFeatureUrl(String featureUrl) {
		this.featureUrl = featureUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeatureConfig [name=");
		builder.append(name);
		builder.append(", featureIcon=");
		builder.append(featureIcon);
		builder.append(", featureUrl=");
		builder.append(featureUrl);
		builder.append("]");
		return builder.toString();
	}

}
