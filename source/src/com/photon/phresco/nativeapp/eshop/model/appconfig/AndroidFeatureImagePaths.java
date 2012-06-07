
/*
 * Classname: AndroidFeatureImagePaths
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
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
