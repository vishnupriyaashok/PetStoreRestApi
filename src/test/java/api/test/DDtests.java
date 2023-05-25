package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;



import api.endPoints.UserEndPoints;
import api.payLoad.User;
import api.utilities.Dataproviders;
import io.restassured.response.Response;

public class DDtests {
	
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=Dataproviders.class)
	public void testPostUser(String UserId,String userName,String fName,String lName,String email,String pwd,String phone) {
		User userPayload=new User();
		userPayload.setId(Integer.parseInt(UserId));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fName);
		userPayload.setLastName(lName);
		userPayload.setEmail(email);
		userPayload.setPassword(phone);
		userPayload.setPhone(phone);		
	Response response=UserEndPoints.createUser(userPayload);
	Assert.assertEquals(response.getStatusCode(),200);
	}
	
	
	@Test(priority=2,dataProvider = "UserNames",dataProviderClass = Dataproviders.class)
	public void testDeleteUser(String userName) {
		Response response=UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);	
	}
	
	}
