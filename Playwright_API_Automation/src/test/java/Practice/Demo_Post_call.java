package Practice;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class Demo_Post_call {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;
	
	@BeforeSuite
	public void setup() {
		playwright=Playwright.create();
		request=playwright.request();
		context=request.newContext();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void  Post_Test() {
		
		JSONObject obj=new JSONObject();
		obj.put("name", "Tejas");
		obj.put("job", "Test Automation");
	
		APIResponse PostResponse= context.post("https://reqres.in/api/users", 
				RequestOptions.create()
				.setData(obj.toJSONString()));
		
		System.out.println(PostResponse.status());
		System.out.println(PostResponse.statusText());
		
		
		APIResponse GetResponse= context.get("https://reqres.in/api/users?name=Tejas",
				RequestOptions.create()
				.setQueryParam("name", "Tejas"));
		
		System.out.println("Get status : "+GetResponse.status());
		System.out.println(GetResponse.body().toString());
		
		try {
			ObjectMapper object=new ObjectMapper();
			
			JsonNode jsonData= object.readTree(GetResponse.body());
			
			String json= object.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
			System.out.println(json);
		}
		
		catch(JsonProcessingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void tear_down() {
		playwright.close();
	}
}
