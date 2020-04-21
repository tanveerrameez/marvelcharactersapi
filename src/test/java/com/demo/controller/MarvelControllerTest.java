package com.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.controllers.MarvelController;
import com.demo.dto.MarvelCharacter;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MarvelControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MarvelController marvelController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(marvelController).build();
	}

	@Test
	public void testGetCharacters() throws Exception {
		String json = mockMvc.perform(get("/marvel/characters")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse()
				.getContentAsString();
		List ids = mapper.readValue(json, List.class);
		assertNotNull(ids);
		assertTrue(ids.size() > 0);

	}

	@Test
	public void testGetCharacter() throws Exception {
		int characterId = 1009718;
		String json = mockMvc.perform(get("/marvel/characters/"+characterId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse()
				.getContentAsString();
		MarvelCharacter character = mapper.readValue(json, MarvelCharacter.class);
		assertNotNull(character);
		assertEquals(character.getId(), characterId);
		assertFalse(character.getName().isEmpty());
	}
	
	@Test
	public void testGetCharacterWithTranslation() throws Exception {
		int characterId = 1009718;
		String language="fr";
		String json = mockMvc.perform(get("/marvel/characters/"+characterId+"?language="+language)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse()
				.getContentAsString();
		MarvelCharacter character = mapper.readValue(json, MarvelCharacter.class);
		assertNotNull(character);
		assertEquals(character.getId(), characterId);
		assertFalse(character.getName().isEmpty());
		
	}
	
	@Test
	public void testGetCharacterWithWithInvalidLanguageCode() throws Exception {
		int characterId = 1009718;
		String language="xx";
		String message = mockMvc.perform(get("/marvel/characters/"+characterId+"?language="+language)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(String.format("Invalid Language code: %s", language), message);
		
	}
	
	@Test
	public void testGetCharacterWithWithInvalidCharacterId() throws Exception {
		int characterId = 99;
		String message = mockMvc.perform(get("/marvel/characters/"+characterId)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(String.format("Invalid ID: %s", characterId), message);
		
	}
	
	@Test
	public void testGetCharacterWithWithInvalidFormatForCharacterId() throws Exception {
		String characterId = "xx";
		mockMvc.perform(get("/marvel/characters/"+characterId)).andExpect(status().isNotFound());
	}

}
