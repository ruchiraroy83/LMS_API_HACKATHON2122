package step_definition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import util.ExcelReader;
import util.Fetch_Data_From_Properties_File;
import util.LMSPojo;
import util.Send_Request_For_Method;

public class User_Authourization {
	private LMSPojo lmsPojo;
	private Send_Request_For_Method send_Request_For_Method;

	public User_Authourization() {
		Fetch_Data_From_Properties_File data_From_Properties_File = new Fetch_Data_From_Properties_File("Users");
		this.lmsPojo = data_From_Properties_File.getLmsPojo();
		this.send_Request_For_Method = new Send_Request_For_Method("Users");
	}
	@Given("User API User is on Endpoint: url\\/Users username & password from {string} and {int}")
	public void user_is_on_endpoint_url_users_username_password_from_and(String sheetName, Integer rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		this.lmsPojo.setRequest_URL(
				Send_Request_For_Method.request_URL(testData.get(rowNumber).get("UserName"), testData.get(rowNumber).get("Password")));
		System.out.println("usr nm :" + testData.get(rowNumber).get("UserName"));
		System.out.println("pwd :" + testData.get(rowNumber).get("Password"));
	    
	}

	@When("userAPI User sends GET request")
	public void user_api_user_sends_get_request() throws Throwable, IOException {
		this.lmsPojo.setStr_basePath("/Users");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));
		
	    
	}

	@Then("userAPI User validates StatusCode and StatusMessage from {string} sheet and {int} row")
	public void user_api_user_validates_status_code_and_status_message_from_sheet_and_row(String sheetName, Integer rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		Map<String, String> scenario = testData.get(rowNumber);
		String statusCodeString = scenario.get("StatusCode");

		if (!StringUtils.isEmpty(statusCodeString)) {
			this.lmsPojo.setStatus_code(Integer.parseInt(statusCodeString));
		}
		this.lmsPojo.setStatus_message(testData.get(rowNumber).get("StatusMessage"));
		Response response = this.lmsPojo.getRes_response();
		assertNotNull(response);
		assertEquals(response.getStatusCode(), this.lmsPojo.getStatus_code());

		if (this.lmsPojo.getStatus_message() != "") {
			assertEquals(response.asString(), this.lmsPojo.getStatus_message());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, Object> extractResponse(Response response) {
		ResponseBody responseBody = response.getBody();
		assertNotNull(responseBody);
		Map<String, Object> jsonMap = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonMap = mapper.readValue(responseBody.asString(), Map.class);
		} catch (JsonProcessingException e) {
			// System.err.println(e.getMessage());
		}
		return jsonMap;
	

	}

}
