
/*
 * Classname: HeaderAssets
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.model.appconfig;


/**
 * Holds the HeaderAssets information of JSON object read from web server
 * @author viral_b
 *
 */
public class HeaderAssets {

	private String name;
	private String urlValue;

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
	 * @return the urlValue
	 */
	public String getUrlValue() {
		return urlValue;
	}

	/**
	 * @param urlValue the urlValue to set
	 */
	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HeaderAssets [name=");
		builder.append(name);
		builder.append(", urlValue=");
		builder.append(urlValue);
		builder.append("]");
		return builder.toString();
	}

}
