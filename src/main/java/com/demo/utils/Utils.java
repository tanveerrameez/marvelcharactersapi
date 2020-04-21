package com.demo.utils;

public class Utils {

	/**
	 * Method to implement how the variables are obtained.
	 * Currently the variables are obtained from System environment
	 * @param variableName
	 * @return
	 */
	public static String getVariable(String variableName) {
		return System.getenv(variableName);
	}
	
}
