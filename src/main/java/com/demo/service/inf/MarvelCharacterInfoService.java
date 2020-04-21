package com.demo.service.inf;

import java.util.Set;

import com.demo.dto.MarvelCharacter;
import com.demo.exception.ServiceException;

public interface MarvelCharacterInfoService {

	MarvelCharacter getCharacterInfo(int characterId, String languageCode) throws ServiceException;
	
	Set<Integer> getCharacterIdList() throws ServiceException;
}
