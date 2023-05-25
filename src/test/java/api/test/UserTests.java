package api.test;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endPoints.UserEndPoints;
import api.payLoad.User;
import groovyjarjarantlr4.v4.runtime.misc.LogManager;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	Logger logger;

	@BeforeClass
	public void setUp() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		//for logs
		logger=org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testPostUser() {
		logger.info("*******Creating user****");
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("*******User is Created ****");
	}

	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("*******Reading User info ****");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
		
		logger.info("*******User info displayed ****");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {
		//regenerating some data
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		// response.then().log().body().statusCode(200);
		// this is also assertion from restAssured chai assertion library
		response.then().log().body();
		Assert.assertEquals(response.statusCode(), 200);

		// checking data after update

		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test(priority = 4)
	public void testDeleteUserByName() {
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.statusCode(), 200);
	}

}
