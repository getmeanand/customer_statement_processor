package com.rabo.filevalidator.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Anandha
 *
 */
@ConfigurationProperties(prefix = "file")
public class FileProperties {
	private String location;

	private String processed;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

}
