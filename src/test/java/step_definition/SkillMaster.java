package step_definition;

import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static util.constant.LMSApiConstant.CONST_GET_SUCCESS_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_POST_SUCCESS_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_SKILL_ID;
import static util.constant.LMSApiConstant.CONST_SKILL_NAME;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hamcrest.collection.IsMapContaining;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
public class SkillMaster {
	
	private LMSPojo lmsPojo;
	private Send_Request_For_Method send_Request_For_Method;

	public SkillMaster() {
		Fetch_Data_From_Properties_File data_From_Properties_File = new Fetch_Data_From_Properties_File("Skills");
		this.lmsPojo = data_From_Properties_File.getLmsPojo();
		this.send_Request_For_Method = new Send_Request_For_Method("Skills");
	}

	@Given("User is on Endpoint: url\\/Skills with valid username and password")
	public void user_is_on_endpoint_url_user_skills() throws IOException {
		
		this.lmsPojo.setRequest_URL(
				Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(), this.lmsPojo.getPassword()));

	}
	
	@When("User sends GET request on Skills")
	public void user_sends_get_request_on_skills() throws InvalidFormatException, IOException {
		
		this.lmsPojo.setStr_basePath("/Skills");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));
	}
	
	@When("User sends GET request on skill id from {string} and {int}")
	public void user_sends_get_request_on_skill_id_from_and(String SheetName, int RowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);
        this.lmsPojo.setStr_skillid(testData.get(RowNumber).get(CONST_SKILL_ID));
		
		
		this.lmsPojo.setStr_basePath("/Skills/" + this.lmsPojo.getStr_skillid());

		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0);
		this.lmsPojo.setRes_response(response);

	}
	@When("User sends POST request body in skills from {string} and {int} with valid JSON Schema")
	public void user_sends_POST_request_body_from_and(String SheetName,int RowNumber) throws InvalidFormatException, IOException {
		this.lmsPojo.setStr_basePath("/Skills");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
        System.out.println("Final URI:" + this.lmsPojo.getStr_FinalURI());
		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.POST, this.lmsPojo.getExcelPath(), SheetName, RowNumber);
		this.lmsPojo.setRes_response(response);
		
	}
	
	@When("User sends PUT request body in skills from {string} and {int} with valid JSON Schema")
	public void user_sends_put_request_body_in_skills_from_and_with_valid_json_schema(String SheetName, Integer RowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);
		this.lmsPojo.setStr_skillid(testData.get(RowNumber).get(CONST_SKILL_ID));
		System.out.println(this.lmsPojo.getStr_skillid());
		this.lmsPojo.setStr_basePath("/Skills/" + this.lmsPojo.getStr_skillid());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
        System.out.println(testData.get(RowNumber).get(CONST_SKILL_NAME));
		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.PUT, this.lmsPojo.getExcelPath(), SheetName, RowNumber);
		this.lmsPojo.setRes_response(response);
	}

	@When("User sends DELETE skill id ON DELETE Method from {string} and {int}")
	public void user_sends_delete_skill_id_on_delete_method_from_and_row_number(String SheetName, Integer RowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);

		this.lmsPojo.setStr_skillid(testData.get(RowNumber).get(CONST_SKILL_ID));
		this.lmsPojo.setStr_basePath("/Skills/" + this.lmsPojo.getStr_skillid());
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());

		Response response = this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.DELETE, this.lmsPojo.getExcelPath(), SheetName, RowNumber);
		this.lmsPojo.setRes_response(response);
	}
	
	@Then("User should receive the posted skill in JSON body from {string} and {int}")
	public void user_should_receive_created_skill_in_json_body_from_and(String SheetName, Integer RowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);

		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());

		Map<String, String> row = testData.get(RowNumber);
		for (Map.Entry<String, String> entry : row.entrySet()) {
			for (Map.Entry<String, Object> mapResponse : jsonMap.entrySet()) {
				if (mapResponse.getKey() == entry.getKey()) {
					assertEquals(mapResponse.getKey(), entry.getKey());
				}

			}
			
		}
	   
	}
	@Then("JSON schema validity is checked")
	public void json_schema_is_valid() {
		
		this.lmsPojo.setStr_Schemajsonskill(this.lmsPojo.getStr_Schemajsonskill());
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
					this.lmsPojo.getStr_SchemaFileSkills());
		}
	
		
	}

	@Then("JSON schema is valid for {string} in Skills")
	public void json_schema_is_valid_for_get_in_skills(String Method) {
		switch (Method) {
		case "GET":
			this.lmsPojo.setStr_SchemaFileSkills(this.lmsPojo.getStr_Schemajsonskill());
			
		case "POST":
			this.lmsPojo.getStr_SchemaFileSkills();
			System.out.println("schema validated");
		case "PUT":
			this.lmsPojo.getStr_SchemaFileSkills();
		}
		if ((this.lmsPojo.getRes_response().getStatusCode() == CONST_GET_SUCCESS_STATUS_CODE)
				|| (this.lmsPojo.getRes_response().getStatusCode() == CONST_POST_SUCCESS_STATUS_CODE)) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
					this.lmsPojo.getStr_SchemaFileSkills());
		}
	}

	@Then("User validates the StatusCode")
	public void user_validates_the_status_code() {
		assertEquals(this.lmsPojo.getRes_response().getStatusCode(), 200);
	}
	
	@Then("User validates the StatusCode and StatusMessage from {string} sheet and {int} row")
	public void user_validates_the_statuscode_and_statusmessage_from(String SheetName, int RowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);

		Map<String, String> scenario = testData.get(RowNumber);
		String statusCodeString = scenario.get("StatusCode");

		if (!StringUtils.isEmpty(statusCodeString)) {
			this.lmsPojo.setStatus_code(Integer.parseInt(statusCodeString));
		}

		this.lmsPojo.setStatus_message(testData.get(RowNumber).get("StatusMessage"));

		Response response = this.lmsPojo.getRes_response();
        System.out.println("GetStatusCode"+ response.getStatusCode());
        System.out.println("comparison:"+ this.lmsPojo.getStatus_code());
		assertNotNull(response);
		assertEquals(response.getStatusCode(), this.lmsPojo.getStatus_code());

		Map<String, Object> jsonMap = extractResponse(response);
		assertNotNull(jsonMap);
		if (this.lmsPojo.getStatus_message()!="") {
		assertEquals(jsonMap.get("message"), this.lmsPojo.getStatus_message());
		}
	}
	

	@Then("User should receive a particular skill from {string} and {int}")
	public void user_should_receive_a_particular_skill_from_and(String SheetName, int RowNumber) throws InvalidFormatException, IOException {
		
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);

		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());

		Map<String, String> row = testData.get(RowNumber);
		for (Map.Entry<String, String> entry : row.entrySet()) {
			for (Map.Entry<String, Object> mapResponse : jsonMap.entrySet()) {
				if (mapResponse.getKey() == entry.getKey()) {
					assertEquals(mapResponse.getKey(), entry.getKey());
				}

			}
		}

	    
	}

	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, Object> extractResponse(Response response) throws JsonMappingException, JsonProcessingException {
		ResponseBody responseBody = response.getBody();
		System.out.println("RSBody:" + responseBody.asString());
		assertNotNull(responseBody);
		Map<String, Object> jsonMap = null;
		ObjectMapper mapper = new ObjectMapper();
		jsonMap = mapper.readValue(responseBody.asString(), Map.class);
		
		
		return jsonMap;
	}


	
	@SuppressWarnings("unchecked")
	@Then("check the Database for all skills")
	public void check_the_database_for_all_skills() throws JsonMappingException, JsonProcessingException {
		    String getStr_Query="select skill_id,skill_name from tbl_lms_skill_master";
			Map<String, String> queryResult = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
					this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), getStr_Query);
            System.out.println(queryResult);
            System.out.println("Res_response:" + this.lmsPojo.getRes_response());
            ResponseBody responseBody = this.lmsPojo.getRes_response().getBody();
    		System.out.println("RSBody:" + responseBody.asString());
    		assertNotNull(responseBody);
    		//Object jsonMap = null;
    		ObjectMapper mapper = new ObjectMapper();
    		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    		@SuppressWarnings("rawtypes")
			List<HashMap> jsonMap = mapper.readValue(responseBody.asString(), List.class);
    		//List<MyClass> myObjects = mapper.readValue(mapData , new TypeReference<List<MyClass>>(){});
    		//jsonMap = mapper.readValue(responseBody.asString(), new TypeReference<Object>() {});
    		//mapper.readValue(file, new TypeReference<Object>() {});
            
			if (queryResult == null || queryResult.isEmpty()) {

				System.out.println("No records found");

			} else {
				System.out.println("JSONMAP" + jsonMap);
				for (Entry<String, String> jsonMapFinal : ((Map<String, String>) jsonMap).entrySet()) {
					for (Map.Entry<String, String> queryResultFinal : queryResult.entrySet()) {
						if (jsonMapFinal.getKey().equalsIgnoreCase(queryResultFinal.getKey())) {
							Object obj = jsonMapFinal.getValue();
							System.out.println(obj);
							if(obj!=null && obj instanceof Integer) {
								assertEquals(jsonMapFinal.getValue(), queryResultFinal.getValue());
								
							}else {
								assertEquals((String)jsonMapFinal.getValue(), queryResultFinal.getValue());
							}
						}
					}

				}
			}
	}

	@And("check the Database for validation")
	public void check_the_database() throws JsonMappingException, JsonProcessingException {
		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());
		
		if (jsonMap.get("Skill_Id") != null) {
			String queryString = "select skill_id,skill_name from tbl_lms_skill_master where skill_id='"
					+ jsonMap.get("Skill_Id") + "'";
		
		

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
	}
	@Then("check the Database for newly created skill")
	public void check_the_database_for_skill() throws JsonMappingException, JsonProcessingException {Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());
	if (jsonMap.get("Skill_Id") != null) {
	String queryString = "select skill_id,skill_name from tbl_lms_skill_master where skill_id='"
			+ jsonMap.get("Skill_Id") + "'";

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
	}
	@Then("check the Database at {String} and {int} to validate the deletion")
	public void check_the_database_to_validate_the_deletion(String SheetName,int RowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), SheetName);
		Map<String, Object> jsonMap = extractResponse(this.lmsPojo.getRes_response());
		this.lmsPojo.setStr_skillid(testData.get(RowNumber).get(CONST_SKILL_ID));
		System.out.println("Deleted id:" + jsonMap.get("Skill_Id"));
		String queryString = "SELECT EXISTS(SELECT * FROM tbl_lms_userskill_map WHERE user_skill_id='"
				+ this.lmsPojo.getStr_skillid() + "'";

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
