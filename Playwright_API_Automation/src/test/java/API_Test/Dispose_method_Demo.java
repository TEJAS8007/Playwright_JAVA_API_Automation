package API_Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;

public class Dispose_method_Demo {

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
		
		System.out.println(response.status());
		System.out.println(response.statusText());
		System.out.println(response.url());
		System.out.println(response.text());

		try {
		       response.dispose();
		}
		catch(PlaywrightException e) {
			System.out.println(e);
		}
		
		System.out.println("after using dispose method");
		//System.out.println(response.text());// dispose method is only applicable for text body 
		System.out.println(response.status());
		System.out.println(response.statusText());
		System.out.println(response.url());
	}
}
