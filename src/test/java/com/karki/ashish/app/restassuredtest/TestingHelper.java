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
		userDetails.put("email", "newmail6181=07@gmail.com");
		userDetails.put("password", "123");
		userDetails.put("addresses", userAddresses);

		return userDetails;
	}
}
