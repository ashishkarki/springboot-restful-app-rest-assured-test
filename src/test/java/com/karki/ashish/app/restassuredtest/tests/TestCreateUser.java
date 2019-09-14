package com.karki.ashish.app.restassuredtest.tests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.karki.ashish.app.restassuredtest.TestingHelper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class TestCreateUser {

	private final String CONTEXT_PATH = "/spring-boot-app";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
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
		.statusCode(200) // for some reason this isn't working: throws hamcrest error.
		.contentType("application/json")
		.extract()
		.response();
		
		String userId = response.jsonPath().getString("userId");
		
		assertNotNull(userId);
	}
	// @formatter:on

}
