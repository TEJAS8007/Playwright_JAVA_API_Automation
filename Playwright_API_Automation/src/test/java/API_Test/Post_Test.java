package API_Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class Post_Test {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;

	@BeforeTest
	public void setup() {
		playwright=Playwright.create();
		request= playwright.request();
		context= request.newContext();

	}

	@Test(priority = 1)
	public void postuser() throws IOException {

		Map<String, Object> data=new HashMap<String, Object>();
		data.put("name", "Tejas");
		data.put("job", "QA");

		APIResponse response= context.post("https://reqres.in/api/users",
				RequestOptions.create()
				.setHeader("content-Type", "application/json")
				.setData(data)
				);
		System.out.println(response.status());

		ObjectMapper obj=new ObjectMapper();
		JsonNode responsebody= obj.readTree(response.body());
		System.out.println(responsebody);
		System.out.println("----------------------------------------");
	}

	@Test(priority = 2)
	public void getpostuser() throws IOException {

		APIResponse response= context.get("https://reqres.in/api/users?name=Tejas",
				RequestOptions.create()
				.setQueryParam("name", "Tejas"));
		System.out.println(response.status());

		ObjectMapper obj=new ObjectMapper();
		JsonNode responsebody= obj.readTree(response.body());
		System.out.println(responsebody);
	}

	@AfterTest
	public void tearDown() {
		playwright.close();
	}
}
