package com.karki.ashish.app.restassuredtest.tests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import com.karki.ashish.app.restassuredtest.TestingHelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestUsersWebServiceEndpoint {
	
	private static String authorizationHeader;
	private static String userId;
	private static String userPublcId;
	private static String userEmail;
	private static String firstName;
	private static String lastName;
	private static List<Map<String, String>> extractedAddresses;

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = TestingHelper.BASE_URI;
		RestAssured.port = TestingHelper.PORT;
	}

	/**
	 * method actual name: testUserLogin. Tests the login action of the user by
	 * sending /users/login POST request.
	 */
	@Test
	final void a() {
		Map<String, String> loginDetailMap = TestingHelper.getLoginDetailsJsonbody();

		Response response = given().contentType(TestingHelper.JSON_CONTENT).accept(TestingHelper.JSON_CONTENT)
				.body(loginDetailMap).when().post(TestingHelper.CONTEXT_PATH + "/users/login").then()
				// .statusCode(200)
				.extract().response();

		authorizationHeader = response.header("Authorization");
		userId = response.header("UserID");

		assertNotNull(authorizationHeader);
		assertNotNull(userId);
	}
	
	/**
	 * method actual name: testGetUserDetails(). works in path /users/<userId>
	 */
	@Test
	final void b() {
		Response response = given()
		.pathParam("id", userId)
		.header("Authorization", authorizationHeader)
		.accept(TestingHelper.JSON_CONTENT)
		.when()
		.get(TestingHelper.CONTEXT_PATH + "/users/{id}")
		//.get(TestingHelper.CONTEXT_PATH + "/users/" + userId)
		.then()
		////.statusCode(200)
		.contentType(TestingHelper.JSON_CONTENT)
		.extract()
		.response();
		
		final JsonPath jsonPath = response.jsonPath();
		
		userPublcId = jsonPath.getString("userId");
		userEmail = jsonPath.getString("email");
		firstName = jsonPath.getString("firstName");
		lastName = jsonPath.getString("lastName");
		extractedAddresses = jsonPath.getList("addresses");
		String firstAddressId = extractedAddresses.get(0).get("addressId");
		
		assertNotNull(userPublcId);
		assertNotNull(userEmail);
		assertNotNull(firstName);
		assertNotNull(lastName);
		assertEquals(TestingHelper.EMAIL_ADDRESS, userEmail);
		
		assertTrue(extractedAddresses.size() == 2);
		assertTrue(firstAddressId.length() == TestingHelper.ID_LENGTHS);
	}
	
	/**
	 * actual method name: testUpdateUserDetails()
	 */
	@Test
	final void c() {
		Map<String, String> userDetailsMap = new HashMap<String, String>();
		userDetailsMap.put("firstName", "Bandar");
		userDetailsMap.put("lastName", "Kumar chaudhary");
		
		Response response = given()
		.contentType(TestingHelper.JSON_CONTENT)
		.accept(TestingHelper.JSON_CONTENT)
		.header("Authorization", authorizationHeader)
		.pathParam("id", userId)
		.body(userDetailsMap)
		.when()
		.put(TestingHelper.CONTEXT_PATH + "/users/{id}")
		.then()
		//.statusCode(200)
		.contentType(TestingHelper.JSON_CONTENT)
		.extract()
		.response();
		
		// verify the response object has the things we wanted to update
		final JsonPath jsonPath = response.jsonPath();
		final String fName = jsonPath.getString("firstName");
		final String lName = jsonPath.getString("lastName");
		List<Map<String, String>> updatedAddressList = jsonPath.getList("addresses");
		
		// assertions
		assertNotNull(fName);
		assertNotNull(lName);
		assertNotNull(updatedAddressList);
		
		assertTrue(extractedAddresses.size() == updatedAddressList.size());
		assertEquals(extractedAddresses.get(0).get("streetName"), updatedAddressList.get(0).get("streetName"));
	}
	
	/**
	 * actual method name: testDeleteUserDetails()
	 */
	@Test
	final void d() {
		Response response =  given()
		.header("Authorization", authorizationHeader)
		.accept(TestingHelper.JSON_CONTENT)
		.pathParam("id", userId)
		.when()
		.delete(TestingHelper.CONTEXT_PATH + "/users/{id}")
		.then()
		//.statusCode(200)
		.contentType(TestingHelper.JSON_CONTENT)
		.extract()
		.response();
		
		JsonPath jsonPath = response.jsonPath();
		
		final String operationResult = jsonPath.getString("operationResult");
		assertEquals("SUCCESS", operationResult);
	}
}
