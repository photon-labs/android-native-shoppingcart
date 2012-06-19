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
