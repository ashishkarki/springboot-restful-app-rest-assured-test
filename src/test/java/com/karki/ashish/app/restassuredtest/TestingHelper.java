package com.karki.ashish.app.restassuredtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ashish Karki
 * @description contains commonly used properties and setup methods
 */
public class TestingHelper {
	
	public static final int ID_LENGTHS = 30; // as set in the "mobile-app-ws" project
	public static final String BASE_URI = "http://localhost";
	public static final int PORT = 8080;
	public static final String CONTEXT_PATH = "/spring-boot-app";
	public static final String EMAIL_ADDRESS = "newmail6181=08@gmail.com";
	public static final String ACCOUNT_PW = "123"; // the password used to log into our system (not gmail)
	public static final String JSON_CONTENT = "application/json";

	public static Map<String, Object> getJsonBodyForCreateUser() {
		List<Map<String, Object>> userAddresses = new ArrayList<>();

		Map<String, Object> shippingAddress = new HashMap<>();
		shippingAddress.put("city", "Austin");
		shippingAddress.put("country", "USA");
		shippingAddress.put("streetName", "123 Jumbo st");
		shippingAddress.put("postalCode", "123456");
		shippingAddress.put("type", "shipping");

		Map<String, Object> billingAddress = new HashMap<>();
		billingAddress.put("city", "Boston");
		billingAddress.put("country", "USA");
		billingAddress.put("streetName", "123 Englishy st");
		billingAddress.put("postalCode", "555678");
		billingAddress.put("type", "billing");

		userAddresses.add(shippingAddress);
		userAddresses.add(billingAddress);

		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("firstName", "John");
		userDetails.put("lastName", "Doe");
		userDetails.put("email", EMAIL_ADDRESS);
		userDetails.put("password", ACCOUNT_PW);
		userDetails.put("addresses", userAddresses);

		return userDetails;
	}
	
	public static Map<String, String> getLoginDetailsJsonbody() {
		Map<String, String> loginCredentials = new HashMap<String, String>();
		loginCredentials.put("email", EMAIL_ADDRESS);
		loginCredentials.put("password", ACCOUNT_PW);
		
		return loginCredentials;
	}
}
