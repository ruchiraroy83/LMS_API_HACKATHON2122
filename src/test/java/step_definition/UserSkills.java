/*******************************************************************************************************************************************************
 * class Name: UserSkills
 * 
 * Purpose: Step Definition for User Skills API
 * 
 *******************************************************************************************************************************************************/

package step_definition;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static util.constant.LMSApiConstant.CONST_GET_SUCCESS_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_POST_SUCCESS_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_STATUS_MESSAGE;
import static util.constant.LMSApiConstant.CONST_USERSKILLS_API;
import static util.constant.LMSApiConstant.CONST_USER_SKILL_ID;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

public class UserSkills {
	/**
	 * Constructor - initialize LMS Pojo with data from properties files
	 */
	private LMSPojo lmsPojo;
	private Send_Request_For_Method send_Request_For_Method;

	public UserSkills() {

		/**
		 * Fetch the data from the respective property files
		 */
		Fetch_Data_From_Properties_File data_From_Properties_File = new Fetch_Data_From_Properties_File(
				CONST_USERSKILLS_API);
		this.lmsPojo = data_From_Properties_File.getLmsPojo();
		this.send_Request_For_Method = new Send_Request_For_Method(CONST_USERSKILLS_API);
	}

	/**
	 * Create the request Specification with the UserName & Password fetched from
	 * the property file
	 * 
	 */
	@Given("userSkills User is on Endpoint: url\\/UserSkills with valid username and password")
	public void userSkills_user_is_on_endpoint_url_user_skills_with_valid_username_and_password() throws IOException {
		this.lmsPojo.setRequest_URL(
				Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(), this.lmsPojo.getPassword()));

	}

	/**
	 * Create the request Specification with the UserName & Password fetched from
	 * excel data used mostly in Authorization test case
	 * 
	 */
	@Given("userSkill User with  username & password from {string} and {int} is on Endpoint: url\\/UserSkills")
	public void user_skill_user_with_username_password_from_and_is_on_endpoint_url_user_skills(String sheetName,
			Integer rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		this.lmsPojo.setRequest_URL(Send_Request_For_Method.request_URL(testData.get(rowNumber).get("UserName"),
				testData.get(rowNumber).get("Password")));

	}

	/**
	 * Create a GET All response
	 */
	@When("userSkills User sends GET request")
	public void userSkills_user_sends_request() throws InterruptedException, InvalidFormatException, IOException {
		this.lmsPojo.setStr_basePath("/" + CONST_USERSKILLS_API);
		System.out.println("base path is: " + this.lmsPojo.getStr_basePath());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));

	}

	/**
	 * Create a GET response with specific userSkill ID, The userSkill ID is fetched
	 * from the excel sheet whose row no is mentioned in the feature file
	 */
	@When("userSkills User sends GET request on  id from {string} and {int}")
	public void userSkills_user_sends_request_with_specific(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get(CONST_USER_SKILL_ID));
		this.lmsPojo.setStr_basePath("/" + CONST_USERSKILLS_API + "/" + this.lmsPojo.getStr_userskillsid());

		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0);
		this.lmsPojo.setRes_response(response);

	}

	/**
	 * Create a POST response where all the fields for request body is being fetched
	 * from the excel sheet where row no is mentioned in the feature file
	 */
	@When("userSkills User sends POST request body from {string} and {int} with valid JSON Schema")
	public void userSkills_user_sends_POST_request_body_from(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {

		this.lmsPojo.setStr_basePath("/" + CONST_USERSKILLS_API);
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.POST, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
		this.lmsPojo.setRes_response(response);

	}

	/**
	 * Create a PUT response with specific user Skill id & request body all the
	 * fields is being fetched from the excel sheet where row no is mentioned in the
	 * feature file
	 */
	@When("userSkills User sends PUT request on id and request body from {string} and {int} with valid JSON schema")
	public void userSkills_user_sends_put_request_body_from_and(String sheetName, Integer rowNumber)
			throws InvalidFormatException, IOException {

		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get(CONST_USER_SKILL_ID));
		this.lmsPojo.setStr_basePath("/" + CONST_USERSKILLS_API + "/" + this.lmsPojo.getStr_userskillsid());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.PUT, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
		this.lmsPojo.setRes_response(response);

	}

	/**
	 * Create a DELETE response with specific userSkill ID, The userSkill ID is
	 * fetched from the excel sheet whose row no is mentioned in the feature file
	 */

	@When("userSkills User sends request id ON DELETE Method from {string} and {int}")
	public void userSkills_user_sends_request_on_DELETE_method(String sheetName, int rowNumber)
			throws InvalidFormatException, IOException {

		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> DeltestData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(DeltestData.get(rowNumber).get(CONST_USER_SKILL_ID));
		this.lmsPojo.setStr_basePath("/" + CONST_USERSKILLS_API + "/" + this.lmsPojo.getStr_userskillsid());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.DELETE, this.lmsPojo.getExcelPath(), sheetName, rowNumber);
		this.lmsPojo.setRes_response(response);
	}

	/**
	 * Validate the JSON schema of response for GET all users
	 */

	@Then("userSkills JSON schema is valid")
	public void userSkills_json_schema_is_valid() {

		this.lmsPojo.setStr_SchemaFilePath(this.lmsPojo.getGET_AllSchemaFilePath());
		JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
				this.lmsPojo.getStr_SchemaFilePath());
	}

	/**
	 * Validate the JSON schema of response for Method as mentioned in the feature
	 * file
	 */
	@Then("userSkills JSON schema is valid for {string}")
	public void userSkills_json_schema_is_valid_for(String MethodName) {
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
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
					this.lmsPojo.getStr_SchemaFilePath());
		}

	}

	/**
	 * Validate the status Code for the GET request for All user Skills
	 */

	@Then("userSkills User validates StatusCode")
	public void userSkills_user_receives_status_code() throws InvalidFormatException, IOException {
		assertEquals(this.lmsPojo.getRes_response().getStatusCode(), 200);

	}

	/**
	 * Validate the status Code & Status Message from the excel for the Request
	 * Method for the specified user
	 */

	@Then("userSkills User validates StatusCode and StatusMessage from {string} sheet and {int} row")
	public void userSkills_user_receives_status_code_with(String sheetName, int rowNumber)
			throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		Map<String, String> scenario = testData.get(rowNumber);
		String statusCodeString = scenario.get(CONST_STATUS_CODE);

		if (!StringUtils.isEmpty(statusCodeString)) {
			this.lmsPojo.setStatus_code(Integer.parseInt(statusCodeString));
		}

		this.lmsPojo.setStatus_message(testData.get(rowNumber).get(CONST_STATUS_MESSAGE));

		Response response = this.lmsPojo.getRes_response();

		assertNotNull(response);
		assertEquals(response.getStatusCode(), this.lmsPojo.getStatus_code());

		Map<String, Object> jsonMap = extractResponse(response);
		assertNotNull(jsonMap);
		if (this.lmsPojo.getStatus_message() != "") {
			assertEquals(jsonMap.get("message"), this.lmsPojo.getStatus_message());
		}

	}

	/**
	 * Validate the status Code & Status Message from the excel for the user
	 * Authorization testCases
	 */

	@Then("userSkills User Checks for StatusCode StatusCode and StatusMessage from {string} sheet and {int} row")
	public void user_skills_user_checks_for_status_code_status_code_and_status_message_from_sheet_and_row(
			String sheetName, Integer rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		Map<String, String> scenario = testData.get(rowNumber);
		String statusCodeString = scenario.get(CONST_STATUS_CODE);

		if (!StringUtils.isEmpty(statusCodeString)) {
			this.lmsPojo.setStatus_code(Integer.parseInt(statusCodeString));
		}
		this.lmsPojo.setStatus_message(testData.get(rowNumber).get(CONST_STATUS_MESSAGE));
		Response response = this.lmsPojo.getRes_response();
		assertNotNull(response);
		assertEquals(response.getStatusCode(), this.lmsPojo.getStatus_code());

		if (this.lmsPojo.getStatus_message() != "") {
			assertEquals(response.getBody().asString(), this.lmsPojo.getStatus_message());
		}

	}

	private Map<String, Object> extractResponse(Response response) {
		ResponseBody<?> responseBody = response.getBody();
		System.out.println("Response body is :" + response.getBody().asString());
		assertNotNull(responseBody);
		Map<String, Object> jsonMap = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonMap = mapper.readValue(responseBody.asString(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonProcessingException e) {
		}
		return jsonMap;
	}

	/**
	 * Validate response output with the input passed from the excel based on key
	 * value pair.
	 */

	@Then("userSkills User should receive a list of users with skills in JSON body with the fields like user_skill_id,user_id,Skill_Id,months_of_exp from {string} and {int}")
	public void userSkills_user_should_receive_a_list_of_users_with_skills_in_json_body_with_the_fields_user_skill_id_user_id_skill_id_months_of_exp_from_and(
			String sheetName, int rowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());

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

	/**
	 * Validate Database based on the query & the resulted output of the query with
	 * the response.
	 */

	@And("^check the Database$")
	public void check_the_database() {
		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());
		String queryString = "select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map where user_skill_id='"
				+ jsonMap.get(CONST_USER_SKILL_ID) + "'";

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

	/**
	 * Validate Database based on the query & the  output result of the query with
	 * the response.
	 */
	@And("check the Database for userSkills")
	public void check_the_database_for_all_users() {
		String getStr_Query = "select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map";
		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), getStr_Query);

		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());

		if (CollectionUtils.sizeIsEmpty(queryResult)) {
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

	/**
	 * Validate Database based on the query For the DELETE method.
	 */

	@And("userSkills users check the Database to validate deletion from {string} sheet and {int} row")
	public void check_the_Database_to_validate_deletion(String sheetName, int rowNumber) throws Throwable, IOException {

		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> DeltestData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		String queryString = "SELECT EXISTS(SELECT * FROM tbl_lms_userskill_map WHERE user_skill_id='"
				+ DeltestData.get(rowNumber).get(CONST_USER_SKILL_ID) + "'";

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

	@When("userSkills User check the database for creation time & modification time of a user with specified UserSkillsId from {string} and {int}")
	public void user_skills_user_check_the_database_for_creation_time_modification_time_of_a_user_with_specified_user_skills_id_from_and(
			String SheetName,int RowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);
		String queryString = "SELECT creation_time,last_mod_time FROM tbl_lms_userskill_map WHERE user_skill_id='"
				+ testData.get(RowNumber).get(CONST_USER_SKILL_ID) + "'";
		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), queryString);
		System.out.println(queryResult.values());
		for (Map.Entry<String, String> queryResultFinal : queryResult.entrySet()) {
			if (queryResultFinal.getKey().equalsIgnoreCase("last_mod_time")) {
				this.lmsPojo.setStr_modificationTime(queryResultFinal.getValue());
			}else if (queryResultFinal.getKey().equalsIgnoreCase("creation_time")) {
				this.lmsPojo.setStr_creationTime(queryResultFinal.getValue());
			}
		}
		System.out.println(this.lmsPojo.getStr_creationTime());
		System.out.println(this.lmsPojo.getStr_modificationTime());
	}
	


	@Then("userSkills User check the database for new creation time & modification time of a user with specified UserSkillsId from {string} and {int}")
	public void user_skills_user_check_the_database_for_new_creation_time_modification_time_of_a_user_with_specified_user_skills_id_from_and(String SheetName, int RowNumber) throws Throwable, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> DeltestData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);
		String queryString = "SELECT creation_time,last_mod_time FROM tbl_lms_userskill_map WHERE user_skill_id='"
				+ DeltestData.get(RowNumber).get(CONST_USER_SKILL_ID) + "'";

		Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), queryString);
		for (Map.Entry<String, String> queryResultFinal : queryResult.entrySet()) {
			if (queryResultFinal.getKey().equalsIgnoreCase("last_mod_time")) {
				this.lmsPojo.setStr_newmodificationTime(queryResultFinal.getValue());
			}else if (queryResultFinal.getKey().equalsIgnoreCase("creation_time")) {
				this.lmsPojo.setStr_newcreationTime(queryResultFinal.getValue());
			}
		}
		System.out.println(this.lmsPojo.getStr_newcreationTime());
		System.out.println(this.lmsPojo.getStr_newmodificationTime());
	}
	
	@Then("userSkills User check that the creation time is same but modification is different.")
	public void user_skills_user_check_that_the_creation_time_is_same_but_modification_is_different() {
	    assertEquals(this.lmsPojo.getStr_creationTime(), this.lmsPojo.getStr_newcreationTime());
	    assertNotEquals(this.lmsPojo.getStr_modificationTime(), this.lmsPojo.getStr_newmodificationTime());
	}
}