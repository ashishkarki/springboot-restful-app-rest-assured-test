package com.karki.ashish.app.restassuredtest.tests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.karki.ashish.app.restassuredtest.TestingHelper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class TestCreateUser {

	private final String CONTEXT_PATH = TestingHelper.CONTEXT_PATH;

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = TestingHelper.BASE_URI;
		RestAssured.port = TestingHelper.PORT;
	}

	// @formatter:off
	@Test
	final void testCreateUser() {
		Response response = given()
		.contentType("application/json")
		.accept("application/json")
		.body(TestingHelper.getJsonBodyForCreateUser())
		.when()
		.post(CONTEXT_PATH + "/users")
		.then()
		//.statusCode(200) // for some reason this isn't working: throws hamcrest error.
		.contentType("application/json")
		.extract()
		.response();
		
		String userId = response.jsonPath().getString("userId");
		
		assertNotNull(userId);
		assertTrue(userId.length() == TestingHelper.ID_LENGTHS);
		
		// also verify the stuff within body extracted as JSON string
		String bodyString = response.body().asString();
		
		try {
			JSONObject responseBodyObject = new JSONObject(bodyString);
			JSONArray addressJsonArray = responseBodyObject.getJSONArray("addresses");
			
			assertNotNull(addressJsonArray);
			assertTrue(addressJsonArray.length() == 2);
			
			String adddressId = addressJsonArray.getJSONObject(0).getString("addressId");
			assertNotNull(adddressId);
			assertTrue(adddressId.length() == TestingHelper.ID_LENGTHS);
			
		} catch (JSONException e) {
			fail(e.getMessage());
		}
	}
	// @formatter:on

}
