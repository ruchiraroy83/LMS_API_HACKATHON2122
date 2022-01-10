package util;

import org.json.simple.JSONObject;
import org.testng.Assert;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Send_Request_For_Method {
	public static RequestSpecification request_URL(String Username, String Password) {
		
		//Request object
		RequestSpecification requestSpecification=RestAssured.given().auth().basic(Username, Password);
		return requestSpecification;
		
	}
	//public static Response Sent_request(String strURL,RequestSpecification httpRequest, HttpMethod httpmethod,String strCoulumnName[], String str_Input[]) {
		public static Response Sent_request(String strURL,RequestSpecification httpRequest, HttpMethod httpmethod) {
		Response response;
		JSONObject requestParams=new JSONObject();
		
	
		switch (httpmethod) 
		{
		case POST:
			//Request payload sending along with POST request
			/*for (int i=0;i<strCoulumnName.length;i++)
			{
				if (strCoulumnName[i]=="First Name" && strCoulumnName[i+1]=="Last Name" ) 
				{
					requestParams.put("name", str_Input[i] + "," + str_Input[i+1]);
				
				} else 
				{
					requestParams.put(strCoulumnName[i], str_Input[i]);
				}
			
			}
			httpRequest.header("Content-Type","application/json");
			httpRequest.body(requestParams.toJSONString());//attach above data to the request
			
			//Response Object
			response=httpRequest.request(Method.POST);*/
					
			
		case PUT:
			/*for (int i=0;i<strCoulumnName.length;i++)
			{
				if (strCoulumnName[i]!=null ) 
				{
					requestParams.put(strCoulumnName[i], str_Input[i]);
				
				} 
			
			}
			
			httpRequest.header("Content-Type","application/json");
			httpRequest.body(requestParams.toJSONString());//attach above data to the request
			
			//Response Object
			response=httpRequest.request(Method.PUT);*/
		case GET:
			RestAssured.baseURI=strURL;
			response = httpRequest.when().get(RestAssured.baseURI);
			System.out.println("Response :"+response.asString());
			return response;
		case DELETE:
			RestAssured.baseURI=strURL;
			response = httpRequest.when().delete(RestAssured.baseURI);
			System.out.println("Response :"+response.asString());
			return response;
				
		
		}

		return null;
	}

		public static boolean check_response_code(Response response, int Status_Code) {
			int statusCode=response.getStatusCode();
			
			if (statusCode==Status_Code)
					{
						System.out.println("Response code matches with the expected output response code");
						return true;
					}else {
						System.out.println("Response code does not matches with the expected output response code");
						return false;
					}

			
		}

}

