package API_Test;

import java.io.IOException;
import java.util.List;
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
import com.microsoft.playwright.options.HttpHeader;

public class headers_Demo {

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
	public void testGetUser1() throws IOException {

		APIResponse response= context.get("https://reqres.in/api/users?page=2");
		
		System.out.println(response.status());
		System.out.println(response.statusText());
		System.out.println(response.url());
		System.out.println(response.text());
		
		Map<String, String>HeaderMap= response.headers();
        System.out.println("total header : "+HeaderMap.size());
        
		/*
		for (Map.Entry<String, String> entry : HeaderMap.entrySet()) {
			String key=entry.getKey();
			String value=entry.getValue();
			
			System.out.println(key + "  :  "+value);
		}
		*/
        
        HeaderMap.forEach((key,value)->System.out.println(key+"  :  "+value));
        System.out.println("--------------------------------------------------------------------");
	}
     
	@Test(priority = 2)
	public void testGetUser2() throws IOException {

		APIResponse response= context.get("https://reqres.in/api/users?page=2");
		ObjectMapper obj=new ObjectMapper();
		JsonNode jsonresponse= obj.readTree(response.body());
		System.out.println(jsonresponse);

		System.out.println(response.status());
		System.out.println(response.statusText());
		System.out.println(response.url());
		System.out.println(response.text());
		
		List<HttpHeader> headerList= response.headersArray();
		
		for(HttpHeader e :headerList) {
			System.out.println(e.name +" : " +e.value);
		}
	}
	
	@AfterTest
	public void tearDown() {
		playwright.close();
	}
}
