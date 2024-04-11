package API_Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class Post_Test2 {

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
	public void createuserJsonString() {
		
		String jsonData="{"
				+ "    \"name\": \"Tejas\","
				+ "    \"job\": \"leader\""
				+ "}";
		APIResponse response= context.post("https://reqres.in/api/users",
				RequestOptions.create()
				.setHeader("content-Type", "application/json")
				.setData(jsonData)
				);
		System.out.println(response.status());
		System.out.println(response.text());
	}
	
	@Test(priority = 2)
	public void createuserJsonfile() throws IOException {
		byte [] fileBytes=null;
		File file=new File("src/test/resources/Data/user.json");
		fileBytes=Files.readAllBytes(file.toPath());
		
		APIResponse response= context.post("https://reqres.in/api/users",
				RequestOptions.create()
				.setHeader("content-Type", "application/json")
				.setData(fileBytes)
				);
		System.out.println(response.status());
		System.out.println(response.text());
		Assert.assertTrue(response.text().contains("Tejas Aware"));
	}
	
	@AfterTest
	public void tearDown() {
		playwright.close();
	}
}
