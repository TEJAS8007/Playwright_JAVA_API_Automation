package API_Test_POJO;

import java.util.Random;

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

public class Put_Call {

	Playwright playwright;
	APIRequest request;
	APIRequestContext context;

	@BeforeTest
	public void setup() {
		playwright=Playwright.create();
		request= playwright.request();
		context= request.newContext();

	}
	
	public int getId() {
		Random random=new Random();
		int id=random.nextInt(5);
		return id;
	}
	
	@Test()
	public void testwithpojoclass() throws JsonProcessingException {
		
		Student stu=new Student();
		stu.setId(getId());
		stu.setName("Stephan");
		stu.setJob("CA");
		
		ObjectMapper obj=new ObjectMapper();
		String jsonData= obj.writerWithDefaultPrettyPrinter().writeValueAsString(stu);
		System.out.println(jsonData);
		
		APIResponse response= context.post("https://reqres.in/api/users",
				RequestOptions.create()
				.setHeader("content-Type", "application/json")
				.setData(stu));
		
		System.out.println(response.status());
		System.out.println(response.text());
		
		Student users= obj.readValue(jsonData,Student.class);
		System.out.println("name    : "+users.getName());
		System.out.println("job     : "+users.getJob());
		System.out.println("id      : "+users.getId());
		
		Assert.assertEquals(response.status(), 201);
		

		System.out.println();
		System.out.println("------------------put Call Data---------------------------------------------------");
		System.out.println();
		
		Student stu1=new Student();
		stu1.setName("Tejas");
		stu1.setJob("QA");
		
		APIResponse putresponse= context.put("https://reqres.in/api/users/"+users.getId() ,
				RequestOptions.create()
				.setHeader("content-Type", "application/json")
				.setData(stu1));
		
		System.out.println(putresponse.status());
		System.out.println(putresponse.text());
		
		System.out.println();
		System.out.println("------------------Get Call Data---------------------------------------------------");
		System.out.println();
		
		APIResponse getresponse= context.get("https://reqres.in/api/users/"+users.getId());
		System.out.println(getresponse.status());
		System.out.println(getresponse.text());
		System.out.println(getresponse.url());
	}
}
