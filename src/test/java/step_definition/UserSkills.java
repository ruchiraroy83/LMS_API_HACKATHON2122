package step_definition;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Capturing_Actual_Output;
import util.ExcelReader;
import util.Fetch_Data_From_Properties_File;
import util.Fetch_Data_From_SQL;
import util.JSON_Schema_Validation;
import util.LMSPojo;
import util.Send_Request_For_Method;

public class UserSkills {
	private LMSPojo lmsPojo;
	private Send_Request_For_Method send_Request_For_Method;

	public UserSkills() {
		this.lmsPojo = new LMSPojo();
		this.send_Request_For_Method = new Send_Request_For_Method();
	}

	@Given("User is on Endpoint: url\\/UserSkills")
	public void user_is_on_endpoint_url_user_skills() throws IOException {
		Properties prop = Fetch_Data_From_Properties_File
				.readPropertiesFile("./src/test/resources/config/credentials.properties");
		this.lmsPojo.setStr_baseURL(prop.getProperty("URL"));
		this.lmsPojo.setUserName(prop.getProperty("Username"));
		this.lmsPojo.setPassword(prop.getProperty("Pwd"));
		this.lmsPojo.setStr_DBURL(prop.getProperty("SQLDatabaseURL"));
		this.lmsPojo.setStr_DBUserName(prop.getProperty("DBUname"));
		this.lmsPojo.setStr_DBPWD(prop.getProperty("BDPWD"));
		this.lmsPojo.setExcelPath(prop.getProperty("UserSkills_ExcelPath"));

		this.lmsPojo.setRequest_URL(
				Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(), this.lmsPojo.getPassword()));

	}

	@When("User sends GET request")
	public void user_sends_request() throws InterruptedException, InvalidFormatException, IOException {
		this.lmsPojo.setStr_basePath("/UserSkills");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		Thread.sleep(2000);
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));

	}

	@When("User sends GET request on  id from {string} and {int}")
	public void user_sends_request_with_specific(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {
		System.out.println("eexcel path is :" + this.lmsPojo.getExcelPath());
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);

		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get("UserSkills_ID"));
		System.out.println(this.lmsPojo.getStr_userskillsid());
		this.lmsPojo.setStr_basePath("/UserSkills/" + this.lmsPojo.getStr_userskillsid());

		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		System.out.println(this.lmsPojo.getStr_FinalURI());
		System.out.println(this.lmsPojo.getRequest_URL());
		Thread.sleep(2000);
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.GET, "", "", 0));
	}

	@When("User sends POST request body from {string} and {int}")
	public void user_sends_POST_request_body_from(String sheetName, int rowNumber)
			throws IOException, InvalidFormatException, InterruptedException {

		this.lmsPojo.setStr_basePath("/UserSkills");

		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		System.out.println(this.lmsPojo.getStr_FinalURI());
		Thread.sleep(2000);
		System.out.println("excel path is :" + this.lmsPojo.getExcelPath() + ", SheetName is:" + sheetName
				+ ",Row number is:" + rowNumber);
		this.lmsPojo.setRes_response(this.send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),
				this.lmsPojo.getRequest_URL(), HttpMethod.POST, this.lmsPojo.getExcelPath(), sheetName, rowNumber));
	}

	@When("JSON schema is valid")
	public void json_schema_is_valid() {
		System.out.println(this.lmsPojo.getRes_response());
		this.lmsPojo.setStr_SchemaFileallusers("user_skill_schema_all_users.json");
		if ((this.send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 200)) && (this.send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 201))) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),
					this.lmsPojo.getStr_SchemaFileallusers());
		} else {
			System.out.println("Schema not valid");
		}

	}

	@Then("User validates StatusCode")
	public void user_receives_status_code() throws InvalidFormatException, IOException {

		this.send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 200);
		this.lmsPojo.setRes_responsebody(Capturing_Actual_Output.capture_output(this.lmsPojo.getRes_response()));

		System.out.println("Body is:" + this.lmsPojo.getRes_responsebody());

	}

	@Then("User validates StatusCode and StatusMessage from {string} and {int}")
	public void user_receives_status_code_with(String sheetName, int rowNumber)
			throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		System.out.println("excel path is :" + this.lmsPojo.getExcelPath());
		List<Map<String, String>> testData = reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		if (!StringUtils.isEmpty(testData.get(rowNumber).get("StatusCode"))) {
			this.lmsPojo.setStatus_code(Integer.parseInt(testData.get(rowNumber).get("StatusCode")));
		}

		this.lmsPojo.setStatus_message(testData.get(rowNumber).get("StatusMessage"));
		System.out.println(this.lmsPojo.getStatus_message());
		System.out.println(rowNumber);
		this.send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), this.lmsPojo.getStatus_code());
		this.lmsPojo.setRes_responsebody(Capturing_Actual_Output.capture_output(this.lmsPojo.getRes_response()));

		System.out.println(this.lmsPojo.getStatus_code());
		if (this.lmsPojo.getStatus_message() != "") {
			System.out.println("Body is:" + this.lmsPojo.getRes_responsebody());

			if (this.lmsPojo.getRes_responsebody().equals(this.lmsPojo.getStatus_message())) {
				System.out.println("Status message is checked successfully");

			}
		}

	}

	@Then("User should receive a list of users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp")
	public void user_should_receive_a_list_of_users_with_skills_in_json_body_with_the_fields_user_skill_id_user_id_skill_id_months_of_exp() {

		if ((this.lmsPojo.getRes_responsebody().contains("user_skill_id"))
				&& (this.lmsPojo.getRes_responsebody().contains("user_id"))
				&& (this.lmsPojo.getRes_responsebody().contains("Skill_id"))
				&& (this.lmsPojo.getRes_responsebody().contains("months_of_exp"))) {
			System.out.println(
					"Users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp is received successfully");
		}

	}

	@And("^check the Database$")
	public void check_the_database() {
		this.lmsPojo.setStr_Query(
				"select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map where user_skill_id='"
						+ this.lmsPojo.getStr_userskillsid() + "'");
		String[] OutputArray = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), this.lmsPojo.getStr_Query());
		if (OutputArray == null) {

			System.out.println("No records found");

		} else {
			int int_Arraylen = OutputArray.length;
			int k = 0;
			for (int i = 0; i < int_Arraylen; i++) {
				if (this.lmsPojo.getRes_responsebody().trim().contains(OutputArray[i].trim())) {
					k++;
				}
			}

			if (this.send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 200)) {
				if (k == int_Arraylen) {
					System.out.println("All the fields matched with database");
				} else {
					System.out.println("Database validation failed");
				}
			}
		}
	}

	@And("check the Database for all users")
	public void check_the_database_for_all_users() {
		this.lmsPojo.setStr_Query("select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map");
		String[] OutputArray = Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),
				this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), this.lmsPojo.getStr_Query());
		if (OutputArray == null) {

			System.out.println("No records found");

		} else {
			int int_Arraylen = OutputArray.length;
			int k = 0;
			for (int i = 0; i < int_Arraylen; i++) {
				if (this.lmsPojo.getRes_responsebody().trim().contains(OutputArray[i].trim())) {
					k++;
				}
			}

			if (this.send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 200)) {
				if (k == int_Arraylen) {
					System.out.println("All the fields matched with database");
				} else {
					System.out.println("Database validation failed");
				}
			}
		}
	}

}
