package org.springframework.social.lastfm.api.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class UserAgentHelper {

	public static String getUserAgent()
	{
		String pomVersion = getPomVersion();
		return "spring-social-lastfm" + (pomVersion == null ? "" : "/" + pomVersion);
	}
	
	private static String getPomVersion() {
		Properties properties = new Properties();
		try {
			properties.load(new ClassPathResource("project.properties").getInputStream());
			return properties.getProperty("pom.version");

		} catch (IOException e) {
			return null;
		}
	}
	
	
}
