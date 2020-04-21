package com.demo.resttemplate;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.demo.utils.MD5HashGenerator;

@Component
public class MarvelRestTemplate extends BaseRestTemplate {
	final static Logger log = Logger.getLogger(MarvelRestTemplate.class);
	@Value("${MARVEL_PUBLIC_KEY}")
	private  String publicKey;
	@Value("${MARVEL_PRIVATE_KEY}")
	private String privateKey;
	@Value("${marvel.characters.url:gateway.marvel.com/v1/public/characters}")
	private String url;

	
	@PostConstruct
	public void postConstruct() {
		log.info("@PostConstruct called");
		if(publicKey == null ) {
		log.error("MARVEL_PUBLIC_KEY not available!");
	}
	
	if(privateKey == null ) {
		log.error("MARVEL_PRIVATE_KEY not available!");
	}
	
	if(url == null ) {
		log.error("url not available!");
	}

	}
	public String getCharacterList(String timestamp, List<NameValuePair> queryParamsList) throws HttpClientErrorException, NoSuchAlgorithmException, URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder = generateAuthenticationQueryParams(timestamp,builder.setScheme("https").setHost(url).addParameters(queryParamsList));
		String urlString  = builder.build().toString();
		return get(urlString);
	}
	
	public String getCharacterInfo(int characterId)  throws HttpClientErrorException, NoSuchAlgorithmException, URISyntaxException {
		long time=System.currentTimeMillis();
		URIBuilder builder = new URIBuilder();
		builder = generateAuthenticationQueryParams(""+time, builder.setScheme("https").setHost(url).setPath(""+characterId));
		return get(builder.build().toString());
	}
	
	private URIBuilder generateAuthenticationQueryParams(String timestamp, URIBuilder ub ) throws NoSuchAlgorithmException{
		String hash = MD5HashGenerator.generateHash(publicKey, privateKey, ""+timestamp);
		return ub.addParameter("ts", timestamp).addParameter("apikey", publicKey).addParameter("hash", hash);
	}
}
