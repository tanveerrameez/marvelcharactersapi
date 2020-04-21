package com.demo.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.MarvelCharacter;
import com.demo.exception.InvalidIDException;
import com.demo.exception.InvalidLanguageCodeException;
import com.demo.exception.ServiceException;
import com.demo.service.inf.MarvelCharacterInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "/marvel", consumes="application/json", produces="application/json")
@RestController
@RequestMapping(path = "/marvel")
public class MarvelController {

	private String invalidLanguageCodeMessage = "Invalid Language code: %s";
	private String invalidIdMessage = "Invalid ID: %s";
	@Autowired
	MarvelCharacterInfoService service;
		
	@ApiOperation(value="Returns all available Marvel character ids, as array of numbers ",  response=Integer.class, responseContainer="Set", tags="getCharacters")
	@ApiResponses(value= { @ApiResponse(code=200,message="OK"), @ApiResponse(code=422, message="Unprocessable Entity"), @ApiResponse(code=400, message="Bad Request")})
	@ResponseBody
	@RequestMapping(path = "/characters", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer[]> getCharacters() throws ServiceException{
		
		Set<Integer> characterIdSet = service.getCharacterIdList();
        return new ResponseEntity<>(characterIdSet.stream().toArray(Integer[] ::new), HttpStatus.OK);
    }
	
    @ApiOperation(value="Returns a Marvel character info based on supplied characterId and language code", response=MarvelCharacter.class, tags="getCharacter")
	@ApiResponses(value= {@ApiResponse(code=200,message="OK"), @ApiResponse(code=422, message="Unprocessable Entity"), @ApiResponse(code=400, message="Bad Request")})
	@ResponseBody
	@RequestMapping(path = "/characters/{id:[\\d]+}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MarvelCharacter> getCharacter(@PathVariable(name="id") int characterId, @ApiParam(value = "language code") @RequestParam(name="language", required=false) String languageCode) throws ServiceException{
		
		MarvelCharacter character = service.getCharacterInfo(characterId, languageCode);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }
	
	@ExceptionHandler({ServiceException.class})
	public ResponseEntity<String> serviceExceptionHandler(ServiceException e){
	
		return new ResponseEntity<>(String.format("Unexpected exception occured",
				e.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({InvalidIDException.class})
	public ResponseEntity<String> invalidIdExceptionHandler(InvalidIDException e){
	
		return new ResponseEntity<>(String.format(invalidIdMessage, e.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({InvalidLanguageCodeException.class})
	public ResponseEntity<String> invalidLanguageExceptionHandler(InvalidLanguageCodeException e){
	
		return new ResponseEntity<>(String.format(invalidLanguageCodeMessage, e.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
}
