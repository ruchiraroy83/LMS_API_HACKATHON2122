package step_definition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static util.constant.LMSApiConstant.CONST_GET_SUCCESS_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_POST_SUCCESS_STATUS_CODE;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import util.ExcelReader;
import util.Fetch_Data_From_Properties_File;
import util.Fetch_Data_From_SQL;
import util.JSON_Schema_Validation;
import util.LMSPojo;
import util.Send_Request_For_Method;

public class Users {
	private LMSPojo lmsPojo;
	private Send_Request_For_Method send_Request_For_Method;

	public Users() {
		Fetch_Data_From_Properties_File data_From_Properties_File = new Fetch_Data_From_Properties_File("Users");
		this.lmsPojo = data_From_Properties_File.getLmsPojo();
		this.send_Request_For_Method = new Send_Request_For_Method("Users");
	}

	@Given("UsersAPI User is on Endpoint: url\\/Users with valid username and password")
	public void UsersAPI_user_is_on_endpoint_url_users_with_valid_username_and_password() throws IOException {
		this.lmsPojo.setRequest_URL(Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(), this.lmsPojo.getPassword()));

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

	@When("UsersAPI User sends GET request")
	public void UsersAPI_user_sends_request_users_api() throws InterruptedException, InvalidFormatException, IOException {
		this.lmsPojo.setStr_basePath("/Users");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));
	}
	
	@When("usersAPI User sends GET request on userid from {string} and {int}")
	public void UsersAPI_user_sends_get_request_on_userid_from_and(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {
		
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userid(testData.get(rowNumber).get("Users_ID"));
		this.lmsPojo.setStr_basePath("/Users/" + this.lmsPojo.getStr_userid());

		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		
		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0);
		this.lmsPojo.setRes_response(response);
	}

	
	@Then("UsersAPI JSON schema is valid")
	public void UsersAPI_json_schema_is_valid() {
		this.lmsPojo.setStr_SchemaFilePath(this.lmsPojo.getGET_SchemaFilePath());
		System.out.println(this.lmsPojo.getGET_SchemaFilePath());
		System.out.println(this.lmsPojo.getStr_SchemaFilePath());
		JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
				this.lmsPojo.getGET_SchemaFilePath());
		
////		this.lmsPojo.setStr_SchemaFileallusers("User_Schema_GET.json");"src/test/resources/" +
////		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
////				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
////			System.out.println("USER Schema file is" + this.lmsPojo.getStr_SchemaFileallusers());
////			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
////					this.lmsPojo.getStr_SchemaFileallusers());
//		}
	}
	
	@Then("usersAPI JSON schema is valid for {string}")
	public void UsersAPI_json_schema_is_valid_for(String MethodName) {
		System.out.println(MethodName);
		switch (MethodName) {
		case "GET":
			this.lmsPojo.setStr_SchemaFilePath(this.lmsPojo.getGET_SchemaFilePath());
			break;
		case "POST":
			this.lmsPojo.setStr_SchemaFilePath(this.lmsPojo.getPOST_SchemaFilePath());
			break;
		case "PUT":
			this.lmsPojo.setStr_SchemaFilePath(this.lmsPojo.getPOST_SchemaFilePath());
			break;
		}
		System.out.println("Schema File Path is" + this.lmsPojo.getStr_SchemaFilePath());
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
					this.lmsPojo.getStr_SchemaFilePath());
		}

	}

	@Then("UsersAPI User validates StatusCode")
	public void UsersAPI_user_validates_status_code() throws InvalidFormatException, IOException {
		assertEquals(this.lmsPojo.getRes_response().getStatusCode(), 200);
		System.out.println("Status Code actual : " + this.lmsPojo.getRes_response().getStatusCode());
	}
	
	@Then("userAPI User Checks for StatusCode and StatusMessage from {string} sheet and {int} row")
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
	
//	@Then("userSkills User Checks for StatusCode StatusCode and StatusMessage from {string} sheet and {int} row")
//	public void user_skills_user_checks_for_status_code_status_code_and_status_message_from_sheet_and_row(String sheetName, Integer rowNumber)  throws Throwable, IOException {
//		ExcelReader reader = new ExcelReader();
//		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
//
//		Map<String, String> scenario = testData.get(rowNumber);
//		String statusCodeString = scenario.get(CONST_STATUS_CODE);
//
//		if (!StringUtils.isEmpty(statusCodeString)) {
//			this.lmsPojo.setStatus_code(Integer.parseInt(statusCodeString));
//		}
//		this.lmsPojo.setStatus_message(testData.get(rowNumber).get(CONST_STATUS_MESSAGE));
//		Response response = this.lmsPojo.getRes_response();
//		assertNotNull(response);
//		assertEquals(response.getStatusCode(), this.lmsPojo.getStatus_code());
//
//		if (this.lmsPojo.getStatus_message() != "") {
//			assertEquals(response.asString(), this.lmsPojo.getStatus_message());
//		}
//
//	}
	
	@Then("UsersAPI User validates StatusCode and StatusMessage from {string} sheet and {int} row")
	public void UsersAPI_user_receives_status_code_with(String sheetName, int rowNumber) throws InvalidFormatException, IOException {
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
		
//		Map<String, Object> jsonMap = extractResponse_user(response);
//		assertNotNull(jsonMap);
//		if (this.lmsPojo.getStatus_message()!="") {
//			assertEquals(jsonMap.get("message"), this.lmsPojo.getStatus_message());
//		}
	}

	@Then("check the Database for all users")
	public void check_the_database_for_all_users() {
		String getStr_Query = "SELECT user_id, CONCAT(user_first_name,',', user_last_name) as name, user_phone_number as phone_number, user_location as location, user_time_zone as time_zone, user_linkedin_url as linkedin_url FROM public.tbl_lms_user";
		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), getStr_Query);

		Map<String, Object> jsonMap = extractResponse_user(this.lmsPojo.getRes_response());
		
		if (queryResult == null || queryResult.isEmpty()) {

			System.out.println("No records found");

		} else {
			for (Map.Entry<String, Object> jsonMapFinal : jsonMap.entrySet()) {
				for (Map.Entry<String, String> queryResultFinal : queryResult.entrySet()) {
					if (jsonMapFinal.getKey().equalsIgnoreCase(queryResultFinal.getKey())) {
						Object obj = jsonMapFinal.getValue();

						if (obj != null && obj instanceof Integer) {
							assertEquals(Integer.toString((Integer) jsonMapFinal.getValue()),
									queryResultFinal.getValue());

						} else {
							assertEquals((String) jsonMapFinal.getValue(), queryResultFinal.getValue());
						}
					}
				}

			}
		}
	}

	
	@And("^UsersAPI check the Database$")
	public void usersAPI_check_the_database() throws Throwable {
		Map<String, Object> jsonMap = extractResponse_user(this.lmsPojo.getRes_response());
		String queryString = "\"SELECT user_id, CONCAT(user_first_name,',', user_last_name) as name, user_phone_number as phone_number, user_location as location, user_time_zone as time_zone, user_linkedin_url as linkedin_url FROM public.tbl_lms_user where user_id='"
				+ jsonMap.get("user_id") + "'";

		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), queryString);

		if (queryResult == null || queryResult.isEmpty()) {

			System.out.println("No records found");

		} else {
			for (Map.Entry<String, Object> jsonMapFinal : jsonMap.entrySet()) {
				for (Map.Entry<String, String> queryResultFinal : queryResult.entrySet()) {
					if (jsonMapFinal.getKey().equalsIgnoreCase(queryResultFinal.getKey())) {
						Object obj = jsonMapFinal.getValue();

						if (obj != null && obj instanceof Integer) {
							assertEquals(Integer.toString((Integer) jsonMapFinal.getValue()),
									queryResultFinal.getValue());

						} else {
							assertEquals((String) jsonMapFinal.getValue(), queryResultFinal.getValue());
						}
					}
				}

			}
		}
	}

	
//@When("User sends POST request body from {string} and {int} with valid JSON Schema for user api")
//public void user_sends_POST_request_body_from(String sheetName, int rowNumber)
//		throws IOException, InvalidFormatException, InterruptedException {
//
//	this.lmsPojo.setStr_basePath("/Users");
//
//	this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
//	System.out.println(this.lmsPojo.getStr_FinalURI());
//
//	System.out.println("excel path is :" + this.lmsPojo.getExcelPath() + ", SheetName is:" + sheetName
//			+ ",Row number is:" + rowNumber);
//	Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
//			this.lmsPojo.getRequest_URL(), HttpMethod.POST, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
//	this.lmsPojo.setRes_response(response);
//
//}


	@Then("User should receive a list of user in JSON body with the fields like user_id,name,phone_number,location,time_zone,linkedin_url from {string} and {int}")
	public void user_should_receive_a_list_of_users_in_json_body_with_the_fields_like_user_id_name_phone_number_location_time_zone_linkedin_url_from_and(
			String sheetName, int rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			Map<String, Object> jsonMap = extractResponse_user(this.lmsPojo.getRes_response());
	
			Map<String, String> row = testData.get(rowNumber);
			for (Map.Entry<String, String> entry : row.entrySet()) {
				for (Map.Entry<String, Object> mapResponse : jsonMap.entrySet()) {
					if (mapResponse.getKey() == entry.getKey()) {
						assertEquals(mapResponse.getValue(), entry.getValue());
					}
				}
			}
		}
	}

@SuppressWarnings({ "unchecked", "rawtypes" })
private Map<String, Object> extractResponse_user(Response response) {
	ResponseBody responseBody = response.getBody();
	assertNotNull(responseBody);
	Map<String, Object> jsonMap = null;
	ObjectMapper mapper = new ObjectMapper();
	try {
		jsonMap = mapper.readValue(responseBody.asString(), Map.class);
	} catch (JsonProcessingException e) {
		// System.err.println(e.getMessage());
	}
	System.out.println("XXXXXXX" + jsonMap.toString());
	return jsonMap;
}


}