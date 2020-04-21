package com.demo.resttemplate;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleTranslateRestTemplate extends BaseRestTemplate {
	final static Logger log = Logger.getLogger(GoogleTranslateRestTemplate.class);
	@Value("${GOOGLE_API_KEY}")
	private String apiKey;
	@Value("${google.translation.url: translation.googleapis.com/language/translate/v2}")
	private String url; 

	@PostConstruct
	public void postConstruct() {
		log.info("@PostConstruct called");
		if(apiKey == null ) {
		log.error("apiKey not available!");
		}
	}
		
	public String getTranslation(String content, String srcLangCode, String targetLangCode)
			throws UnsupportedEncodingException , URISyntaxException {
		URIBuilder builder = new URIBuilder();
		String urlString  = builder.setScheme("https").setHost(url).addParameter("key", apiKey).
				addParameter("q", content).addParameter("source", srcLangCode).
				addParameter("target", targetLangCode).build().toString();
		return get(urlString);

	}

	public String getLanguages()  throws URISyntaxException{
		URIBuilder builder = new URIBuilder();
		String urlString = builder.setScheme("https").setHost(url).setPath("languages").addParameter("key", apiKey).build().toString();
		return get(urlString);
	}
}
