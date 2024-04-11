package API_Test_POJO;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class Post_call {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;

	@BeforeTest
	public void setup() {
		playwright=Playwright.create();
		request= playwright.request();
		context= request.newContext();

	}
	
	@Test()
	public void testwithpojoclass() throws JsonProcessingException {
		
		User user=new User();
		user.setName("stephan");
		user.setJob("CA");
		user.setAddress("Pune");
		user.setSurname("king");
		
		ObjectMapper obj=new ObjectMapper();
		String jsonData= obj.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		System.out.println(jsonData);
		
		APIResponse response= context.post("https://reqres.in/api/users",
				RequestOptions.create()
				.setHeader("content-Type", "application/json")
				.setData(user));
		
		System.out.println(response.status());
		System.out.println(response.text());
		
		User users= obj.readValue(jsonData,User.class);
		System.out.println("name    : "+users.getName());
		System.out.println("job     : "+users.getJob());
		System.out.println("surname : "+users.getSurname());
		System.out.println("address : "+users.getAddress());
		
		Assert.assertEquals(response.status(), 201);
		
	}
}
