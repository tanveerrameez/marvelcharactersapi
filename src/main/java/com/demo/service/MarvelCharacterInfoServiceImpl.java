package com.demo.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.demo.dto.MarvelCharacter;
import com.demo.dto.Thumbnail;
import com.demo.exception.InvalidIDException;
import com.demo.exception.InvalidLanguageCodeException;
import com.demo.exception.ServiceException;
import com.demo.resttemplate.GoogleTranslateRestTemplate;
import com.demo.resttemplate.MarvelRestTemplate;
import com.demo.service.inf.MarvelCharacterInfoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope("singleton")
@PropertySource("classpath:application.properties")
public class MarvelCharacterInfoServiceImpl implements MarvelCharacterInfoService {

	private final static Logger log = Logger.getLogger(MarvelCharacterInfoServiceImpl.class);
	private static final String ENGLISH_LANGUAGE_CODE = "en";

	private MarvelRestTemplate marvelRestTemplate ;
	private GoogleTranslateRestTemplate googleRestTemplate ;
	private ObjectMapper mapper = new ObjectMapper();

	private Set<Integer> characterSet = new HashSet<>();
	private Set<String> languageSet = new HashSet<>();

	public MarvelCharacterInfoServiceImpl(MarvelRestTemplate marvelRestTemplate,GoogleTranslateRestTemplate googleRestTemplate ) {
		this.marvelRestTemplate = marvelRestTemplate;
		this.googleRestTemplate = googleRestTemplate;
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			cacheMarvelCharacters();
			availableLanguages();

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void cacheMarvelCharacters() throws ServiceException {

		int offset = 0;
		int limit = 100;
		int total = -1;
		try {

			long time = System.currentTimeMillis();
			do {
				String queryString = "offset=" + offset + "&limit=" + limit;
				final int finalOffset=offset;
				final int finalLimit = limit;
				List<NameValuePair> queryParamsList = new ArrayList<>();
				queryParamsList.add(new NameValuePair(){
					@Override
					public String getValue() {
						
						return ""+finalOffset;
					}
					
					@Override
					public String getName() {

						return "offset";
					}
				});
				queryParamsList.add(new NameValuePair(){
					@Override
					public String getValue() {
						
						return ""+finalLimit;
					}
					
					@Override
					public String getName() {

						return "limit";
					}
				});
				String json = marvelRestTemplate.getCharacterList("" + time, queryParamsList);

				JsonNode dataNode = mapper.readTree(json).path("data");
				offset = dataNode.get("offset").asInt();
				limit = dataNode.get("limit").asInt();
				total = dataNode.get("total").asInt();
				int count = dataNode.get("count").asInt();
				JsonNode resultsNode = dataNode.path("results");
				if (resultsNode.isArray()) {
					for (JsonNode resultNode : resultsNode) {
						characterSet.add(resultNode.get("id").asInt());
					}
				}
				offset = offset + count;
			} while (offset < total);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} 

 		log.debug("Total character ids loaded:" + characterSet.size() + ". Total characters available in Marvel Comics:"
				+ total);
	}

	@Override
	public Set<Integer> getCharacterIdList() throws ServiceException {

		return characterSet;
	}

	@Override
	public MarvelCharacter getCharacterInfo(int characterId, String languageCode) throws ServiceException {
		log.debug("In getCharacterInfo....");

		if (!characterSet.contains(characterId))
			throw new InvalidIDException("" + characterId);
		if (languageCode != null && !languageSet.contains(languageCode))
			throw new InvalidLanguageCodeException(languageCode);
		MarvelCharacter character = null;
		try {

			String json = marvelRestTemplate.getCharacterInfo(characterId);
			JsonNode resultsNode = mapper.readTree(json).path("data").path("results");
			if (resultsNode.isArray()) {
				JsonNode characterNode = resultsNode.get(0);
				Thumbnail thumbnail = mapper.treeToValue(characterNode.get("thumbnail"), Thumbnail.class);
				character = new MarvelCharacter(characterNode.get("id").asInt(), characterNode.get("name").asText(),
						characterNode.get("description").asText(), thumbnail);
				if (languageCode != null && !StringUtils.isEmpty(character.getDescription())) {
					// use translation service
					character
							.setDescription(translate(character.getDescription(), ENGLISH_LANGUAGE_CODE, languageCode));
				}
			}
		} catch (HttpClientErrorException httpException) {
			httpException.printStackTrace();
			throw new ServiceException(httpException.getMessage());

		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			throw new ServiceException(uee.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new ServiceException(ioe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return character;

	}

	/**
	 * Use translation service
	 * 
	 * @param content
	 * @param srcLangCode
	 * @param targetLangCode
	 * @return
	 * @throws IOException
	 */
	private String translate(String content, String srcLangCode, String targetLangCode) throws IOException, URISyntaxException {

		String json = googleRestTemplate.getTranslation(content, srcLangCode, targetLangCode);
		JsonNode translations = mapper.readTree(json).path("data").path("translations");
		if (translations.isArray()) {
			JsonNode translation = translations.get(0);
			JsonNode translatedText = translation.get("translatedText");
			content = translatedText.asText();
		}

		return content;
	}

	private void availableLanguages() throws HttpClientErrorException, IOException, URISyntaxException {

		String json = googleRestTemplate.getLanguages();
		JsonNode languages = mapper.readTree(json).path("data").path("languages");
		if (languages.isArray()) {
			for (JsonNode language : languages) {
				languageSet.add(language.get("language").asText());
			}
		}

	}

}
