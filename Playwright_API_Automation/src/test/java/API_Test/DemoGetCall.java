package API_Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

public class DemoGetCall {

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
	public void testGetUser() throws IOException {

		APIResponse response= context.get("https://gorest.co.in/public/v2/users");
		
		ObjectMapper obj=new ObjectMapper();
		JsonNode jsonresponse= obj.readTree(response.body());
		System.out.println(jsonresponse);

		System.out.println(response.status());
		System.out.println(response.statusText());
		System.out.println(response.url());
		System.out.println(response.text());

		Map<String, String> headerMap=response.headers();
		System.out.println(headerMap);
		Set<String> headerset= headerMap.keySet();

		Iterator<String> itr= headerset.iterator();
		while(itr.hasNext()) {
			String key= itr.next();
			String value= headerMap.get(key);

			System.out.println(key+" "+ " : "+" "+value);
		}
	}

	@Test(priority = 2)
	public void testquerryparam() {
		APIResponse response= context.get("https://gorest.co.in/public/v2/users",
		RequestOptions.create()
		.setQueryParam("gender", "male")
		.setQueryParam("status", "active"));
	
		System.out.println(response.status());
		System.out.println(response.statusText());
		System.out.println(response.url());
		System.out.println(response.text());

	}
	
	@AfterTest
	public void tearDown() {
		playwright.close();
	}
}
