package Practice;

import java.io.IOException;
import java.util.List;

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
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;

public class Demo_Get {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;
	
	@BeforeSuite
	public void setup() {
		playwright=Playwright.create();
		request= playwright.request();
		context=request.newContext();
	}
	
	@Test
	public void GET_Test() {
	
		APIResponse response= context.get("https://reqres.in/api/users?page=2",
				RequestOptions.create()
				.setQueryParam("page", 2));
		
		System.out.println(response.status());
		System.out.println(response.statusText());
		
		try {
			ObjectMapper obj=new ObjectMapper();
			
			JsonNode jsonData= obj.readTree(response.body());
			
			String json= obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
			System.out.println(json);
		}
		
		catch(JsonProcessingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<HttpHeader> HeaderList= response.headersArray();
		
		HeaderList.forEach((ele) -> System.out.println(ele.name+" : "+ele.value));
		
		
		
	}
	
	@AfterTest
	public void tear_down() {
		playwright.close();
	}
}
