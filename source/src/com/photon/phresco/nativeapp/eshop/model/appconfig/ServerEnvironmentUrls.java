/**
 *
 */
package com.photon.phresco.nativeapp.eshop.model.appconfig;


/**
 * @author viral_b
 *
 */
public class ServerEnvironmentUrls {

	private String loginService;

	/**
	 * @return the loginService
	 */
	public String getLoginService() {
		return loginService;
	}

	/**
	 * @param loginService the loginService to set
	 */
	public void setLoginService(String loginService) {
		this.loginService = loginService;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServerEnvironmentUrls [loginService=");
		builder.append(loginService);
		builder.append("]");
		return builder.toString();
	}

}
