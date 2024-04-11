package Auth_Token;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class Token {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;
	ObjectMapper obj;
	public static String token;

	@BeforeTest
	public void setup() {
		playwright=Playwright.create();
		request= playwright.request();
		context= request.newContext();
        obj=new ObjectMapper();
	}
	
	@Test(priority = 1)
	public void Create_Token() {
		
		String JsonData="{\r\n"
				+ "    \"username\" : \"admin\",\r\n"
				+ "    \"password\" : \"password123\"\r\n"
				+ "}";
		
		APIResponse response= context.post("https://restful-booker.herokuapp.com/auth" ,
				  RequestOptions.create()
				  .setHeader("Content-Type", "application/json")
				  .setData(JsonData)
				);
		
		try {
			JsonNode node= obj.readTree(response.text());
			System.out.println(node.toPrettyString());
			
			token= node.get("token").toPrettyString();
			System.out.println("Token : "+token);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("----------Put----Call-----------");
		
		String putData="{\r\n"
				+ "    \"firstname\" : \"James\",\r\n"
				+ "    \"lastname\" : \"Brown\",\r\n"
				+ "    \"totalprice\" : 111,\r\n"
				+ "    \"depositpaid\" : true,\r\n"
				+ "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \"2018-01-01\",\r\n"
				+ "        \"checkout\" : \"2019-01-01\"\r\n"
				+ "    },\r\n"
				+ "    \"additionalneeds\" : \"Lunch\"\r\n"
				+ "}";
		
		APIResponse putResponse= context.put("https://restful-booker.herokuapp.com/booking/1",
				RequestOptions.create()
				.setHeader("Content-Type", "application/json")
				.setHeader("Accept", "application/json")
				.setHeader("Cookie", "token="+token)
				.setData(putData)
				);
		
		System.out.println(putResponse.status());
	}
}
