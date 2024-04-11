package Practice;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class Booking {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;
	
	@BeforeSuite
	public void setup() {
		playwright=Playwright.create();
		request= playwright.request();
		context=request.newContext();
	}
	
	
	@Test(priority = 1)
	public void create_Bokoing() {
		
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("firstname", "Tejas");
		data.put("lastname", "QA");
		data.put("totalprice", "5454");
		data.put("depositpaid", "true");
		data.put("checkin", "2022-01-01");
		data.put("checkout", "2023-01-01");
		data.put("additionalneeds", "breakfast");
		
		
		APIResponse res= context.post("https://reqres.in/api/users" ,
				RequestOptions.create()
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json")
				.setData(data)
				);
		System.out.println(res.status());
		System.out.println(res.text());
	}
}
