package com.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.AppConfig;
import com.demo.dto.MarvelCharacter;
import com.demo.exception.InvalidIDException;
import com.demo.exception.InvalidLanguageCodeException;
import com.demo.resttemplate.GoogleTranslateRestTemplate;
import com.demo.resttemplate.MarvelRestTemplate;
import com.demo.service.inf.MarvelCharacterInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class) 
@TestPropertySource("classpath:application.properties")
public class MarvelCharacterInfoServiceTest {

	@Autowired
	private MarvelRestTemplate marvelRestTemplate; 

	@Autowired
	private GoogleTranslateRestTemplate googleTranslateRestTemplate;

	private MarvelCharacterInfoService service;
	@Before
	public void setUp() throws Exception {
		service =  new MarvelCharacterInfoServiceImpl(marvelRestTemplate,
				googleTranslateRestTemplate);
	}

	@Test
	public void getCharacterIdListTest() throws Exception{
		Set<Integer> characterIds = service.getCharacterIdList();
		assertNotNull(characterIds);
		assertTrue(characterIds.size()>1);
	}
	
	@Test
	public void getCharacterInfo() throws Exception{
		int characterId = 1009718;
		MarvelCharacter character = service.getCharacterInfo(characterId, null);
		assertNotNull(character);
		assertEquals(character.getId(),characterId);
		assertEquals(character.getName(),"Wolverine");
	}
	
	@Test(expected=InvalidIDException.class)
	public void getNonExistentCharacterInfo() throws Exception{
		int characterId = -1;
		MarvelCharacter character = service.getCharacterInfo(characterId, null);
 	}
	
	@Test
	public void getCharacterInfoWithTranslation() throws Exception{
		int characterId = 1009718;
		MarvelCharacter character = service.getCharacterInfo(characterId, "es");
		assertNotNull(character);
		assertEquals(character.getId(),characterId);
		assertEquals(character.getName(),"Wolverine");
		assertNotNull(character.getDescription());
	}
	
	@Test(expected=InvalidLanguageCodeException.class)
	public void getCharacterInfoWithInvalidLanguageCode() throws Exception{
		int characterId = 1009718;
		MarvelCharacter character = service.getCharacterInfo(characterId, "xx");
	}
	
	@Test
	public void getCharacterInfoWithTranslationForEmptyDescription() throws Exception{
		int characterId = 1010892;
		MarvelCharacter character = service.getCharacterInfo(characterId, "es");
		assertNotNull(character);
		assertEquals(character.getId(),characterId);
		assertEquals(character.getName(),"Rawhide Kid");
		assertNotNull(character.getDescription());
	}
}
