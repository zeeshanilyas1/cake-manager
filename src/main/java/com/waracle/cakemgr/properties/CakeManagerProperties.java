package com.waracle.cakemgr.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CakeManagerProperties {
	
	@Value("${cake.initial-data.url}")
	private String endpoint;

	public String getEndpoint() {
		return endpoint;
	}
	
	

}