package com.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

public class MD5HashGenerator {
	
	private final static Logger logger = Logger.getLogger(MD5HashGenerator.class);	

	
	public static String generateHash(String publicKey, String privateKey, String prependText) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update((prependText+privateKey+publicKey).getBytes());
		byte[] digest = md.digest();
	    
	    StringBuilder sb = new StringBuilder();
        for(int i=0; i< digest.length ;i++)
        {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        String generatedPassword = sb.toString();
        //logger.info(generatedPassword);
	    String hash = DatatypeConverter.printHexBinary(digest).toLowerCase();
	    //logger.info(myHash);
	    return generatedPassword;
	   
	}
	
}
