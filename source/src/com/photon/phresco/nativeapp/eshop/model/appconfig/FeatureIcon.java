/**
 *
 */
package com.photon.phresco.nativeapp.eshop.model.appconfig;

/**
 * @author viral_b
 *
 */
public class FeatureIcon {

	private String defaultTab;
	private String highlightedTab;

	/**
	 * @return the defaultTab
	 */
	public String getDefaultTab() {
		return defaultTab;
	}

	/**
	 * @param defaultTab the defaultTab to set
	 */
	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	/**
	 * @return the highlightedTab
	 */
	public String getHighlightedTab() {
		return highlightedTab;
	}

	/**
	 * @param highlightedTab the highlightedTab to set
	 */
	public void setHighlightedTab(String highlightedTab) {
		this.highlightedTab = highlightedTab;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeatureIcon [defaultTab=");
		builder.append(defaultTab);
		builder.append(", highlightedTab=");
		builder.append(highlightedTab);
		builder.append("]");
		return builder.toString();
	}

}
