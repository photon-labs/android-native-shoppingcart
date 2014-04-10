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
