package com.demo.resttemplate;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class BaseRestTemplate {
	final static Logger log = Logger.getLogger(BaseRestTemplate.class);
	private RestTemplate restTemplate = new RestTemplate();
	
	public String get(String url) throws HttpClientErrorException{
		log.debug("URL accessed: "+url);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return response.getBody();
	}

}
