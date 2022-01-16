package step_definition;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import static util.constant.LMSApiConstant.CONST_GET_SUCCESS_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_POST_SUCCESS_STATUS_CODE;

public class UserSkills {
	private LMSPojo lmsPojo;
	private Send_Request_For_Method send_Request_For_Method;

	public UserSkills() {
		Fetch_Data_From_Properties_File data_From_Properties_File = new Fetch_Data_From_Properties_File();
		this.lmsPojo = data_From_Properties_File.getLmsPojo();
		this.send_Request_For_Method = new Send_Request_For_Method();
	}

	@Given("User is on Endpoint: url\\/UserSkills with valid username and password")
	public void user_is_on_endpoint_url_user_skills_with_valid_username_and_password() throws IOException {
		this.lmsPojo.setRequest_URL(
				Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(), this.lmsPojo.getPassword()));

	}

//
//	@Given("User is on Endpoint: url\\/UserSkills")
//	public void user_is_on_endpoint_url_user_skills() throws IOException {
//		this.lmsPojo.setRequest_URL(
//				Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(), this.lmsPojo.getPassword()));
//
//	}

	@When("User sends GET request")
	public void user_sends_request() throws InterruptedException, InvalidFormatException, IOException {
		this.lmsPojo.setStr_basePath("/UserSkills");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));

	}

	@When("User sends GET request on  id from {string} and {int}")
	public void user_sends_request_with_specific(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get("UserSkills_ID"));
		this.lmsPojo.setStr_basePath("/UserSkills/" + this.lmsPojo.getStr_userskillsid());

		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0);
		this.lmsPojo.setRes_response(response);

	}

	@When("User sends POST request body from {string} and {int} with valid JSON Schema")
	public void user_sends_POST_request_body_from(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {

		this.lmsPojo.setStr_basePath("/UserSkills");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.POST, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
		this.lmsPojo.setRes_response(response);

	}

	@When("User sends PUT request on id and request body from {string} and {int} with valid JSON schema")
	public void user_sends_put_request_body_from_and(String sheetName, Integer rowNumber)
			throws InvalidFormatException, IOException {

		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get("UserSkills_ID"));
		this.lmsPojo.setStr_basePath("/UserSkills/" + this.lmsPojo.getStr_userskillsid());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.PUT, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
		this.lmsPojo.setRes_response(response);

	}

	@When("User sends request id ON DELETE Method from {string} and {int}")
	public void user_sends_request_on_DELETE_method(String sheetName, int rowNumber)
			throws InvalidFormatException, IOException {

		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get("user_skill_id"));
		this.lmsPojo.setStr_basePath("/UserSkills/" + this.lmsPojo.getStr_userskillsid());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.DELETE, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
		this.lmsPojo.setRes_response(response);
	}

	@When("JSON schema is valid")
	public void json_schema_is_valid() {
		this.lmsPojo.setStr_SchemaFileallusers("user_skill_schema_GET_all.json");
		JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),this.lmsPojo.getStr_SchemaFileallusers());
		}

	@Then("JSON schema is valid for {string}")
	public void json_schema_is_valid_for(String Method) {
		System.out.println(Method);
		switch (Method) {
		case "GET":
			this.lmsPojo.setStr_SchemaFileallusers("user_skill_schema_GET.json");
		case "POST":
			this.lmsPojo.setStr_SchemaFileallusers("user_skill_schema_POST_Resp.json");
		case "PUT":
			this.lmsPojo.setStr_SchemaFileallusers("user_skill_schema_POST_Resp.json");
		}
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
					this.lmsPojo.getStr_SchemaFileallusers());
		}

	}

	@Then("User validates StatusCode")
	public void user_receives_status_code() throws InvalidFormatException, IOException {
		assertEquals(this.lmsPojo.getRes_response().getStatusCode(), 200);

	}

	@Then("User validates StatusCode and StatusMessage from {string} sheet and {int} row")
	public void user_receives_status_code_with(String sheetName, int rowNumber)
			throws InvalidFormatException, IOException {
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

		Map<String, Object> jsonMap = extractResponse(response);
		assertNotNull(jsonMap);
		if (this.lmsPojo.getStatus_message()!="") {
		assertEquals(jsonMap.get("message"), this.lmsPojo.getStatus_message());
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
			//System.err.println(e.getMessage());
		}
		return jsonMap;
	}

	@Then("User should receive a list of users with skills in JSON body with the fields like user_skill_id,user_id,Skill_Id,months_of_exp from {string} and {int}")
	public void user_should_receive_a_list_of_users_with_skills_in_json_body_with_the_fields_user_skill_id_user_id_skill_id_months_of_exp_from_and(
			String sheetName, int rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());

		Map<String, String> row = testData.get(rowNumber);
		for (Map.Entry<String, String> entry : row.entrySet()) {
			for (Map.Entry<String, Object> mapResponse : jsonMap.entrySet()) {
				if (mapResponse.getKey() == entry.getKey()) {
					assertEquals(mapResponse.getKey(), entry.getKey());
				}

			}
		}

	}

	@And("^check the Database$")
	public void check_the_database() {
		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());
		String queryString = "select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map where user_skill_id='"
				+ jsonMap.get("user_skill_id") + "'";

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

	@And("check the Database for userSkills")
	public void check_the_database_for_all_users() {
		String getStr_Query = "select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map";
		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), getStr_Query);

		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());

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

	@And("check the Database to validate deletion")
	public void check_the_Database_to_validate_deletion() {

		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());
		String queryString = "SELECT EXISTS(SELECT * FROM tbl_lms_userskill_map WHERE user_skill_id='"
				+ jsonMap.get("user_skill_id") + "'";

		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), queryString);
		Boolean Output;
		if (queryResult == null || queryResult.isEmpty()) {

			Output = true;
		} else {
			Output = false;
		}

		if (Output == true) {

			System.out.println("Success");

		} else {

			System.out.println("Failed to delete");
		}

	}

}
