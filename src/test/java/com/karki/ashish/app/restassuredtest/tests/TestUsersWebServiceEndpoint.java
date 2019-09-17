package com.karki.ashish.app.restassuredtest.tests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.karki.ashish.app.restassuredtest.TestingHelper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class TestUsersWebServiceEndpoint {
	
	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = TestingHelper.BASE_URI;
		RestAssured.port = TestingHelper.PORT;
	}

	@Test
	void testUserLogin() {
		Map<String, String> loginDetailMap = TestingHelper.getLoginDetailsJsonbody();
		
		Response response = 
				given()
				.contentType(TestingHelper.JSON_CONTENT)
				.accept(TestingHelper.JSON_CONTENT)
				.body(loginDetailMap)
				.when()
				.post(TestingHelper.CONTEXT_PATH + "/users/login")
				.then()
				//.statusCode(200)
				.extract()
				.response();
		
		final String authorizationHeader = response.header("Authorization");
		final String userId= response.header("UserID");
		
		assertNotNull(authorizationHeader);
		assertNotNull(userId);
	}

}
