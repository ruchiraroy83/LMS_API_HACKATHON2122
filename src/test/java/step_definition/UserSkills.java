package step_definition;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import util.Capturing_Actual_Output;
import util.ExcelReader;
import util.Fetch_Data_From_Properties_File;
import util.Fetch_Data_From_SQL;
import util.JSON_Schema_Validation;
import util.LMSPojo;
import util.Send_Request_For_Method;


public class UserSkills {
	private LMSPojo lmsPojo;
	
	public UserSkills() {
		this.lmsPojo = new LMSPojo();
	}
	

	@Given("User is on GET Method Endpoint: url\\/UserSkills")
	public void user_is_on_get_method_endpoint_url_user_skills() throws IOException {
		Properties prop = Fetch_Data_From_Properties_File.readPropertiesFile("./src/test/resources/config/credentials.properties");
		this.lmsPojo.setStr_baseURL(prop.getProperty("URL")) ;
		this.lmsPojo.setUserName(prop.getProperty("Username"));
		this.lmsPojo.setPassword(prop.getProperty("Pwd"));
		this.lmsPojo.setStr_DBURL(prop.getProperty("SQLDatabaseURL"));
		this.lmsPojo.setStr_DBUserName(prop.getProperty("DBUname"));
		this.lmsPojo.setStr_DBPWD(prop.getProperty("BDPWD"));
		this.lmsPojo.setExcelPath("./src/test/resources/TestData/InputData_UserSkills.xlsx");
		
		this.lmsPojo.setRequest_URL(Send_Request_For_Method.request_URL(this.lmsPojo.getUserName(),this.lmsPojo.getPassword()));


	}

	
	@When("User sends request")
	public void user_sends_request() throws InterruptedException {
		this.lmsPojo.setStr_basePath("/UserSkills");
		this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL() + this.lmsPojo.getStr_basePath());
		Thread.sleep(2000);
		this.lmsPojo.setRes_response(Send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(), this.lmsPojo.getRequest_URL(),HttpMethod.GET));

	}
	@When("User sends request id from {string} and {int}")
	public void user_sends_request_with_specific(String sheetName,int rowNumber) throws IOException, InvalidFormatException, InterruptedException {
		System.out.println("eexcel path is :"+this.lmsPojo.getExcelPath());
		ExcelReader reader = new ExcelReader();
		List<Map<String,String>> testData = 
				reader.getData(this.lmsPojo.getExcelPath(), sheetName);
		
		this.lmsPojo.setStr_userskillsid(testData.get(rowNumber).get("UserSkills_ID"));
		System.out.println(this.lmsPojo.getStr_userskillsid());
		this.lmsPojo.setStr_basePath("/UserSkills/" + this.lmsPojo.getStr_userskillsid());
		
		
	    this.lmsPojo.setStr_FinalURI(this.lmsPojo.getStr_baseURL()  + this.lmsPojo.getStr_basePath());
	    System.out.println(this.lmsPojo.getStr_FinalURI());
	    System.out.println(this.lmsPojo.getRequest_URL());
		Thread.sleep(2000);
		this.lmsPojo.setRes_response(Send_Request_For_Method.Sent_request(this.lmsPojo.getStr_FinalURI(),this.lmsPojo.getRequest_URL(),HttpMethod.GET));
	}
//	@When("JSON schema is valid for all users")
//	public void json_schema_is_valid_for_all_users() {
//		System.out.println(res_response);
//		if (Send_Request_For_Method.check_response_code(res_response,  200)) {
//			JSON_Schema_Validation.cls_JSON_SchemaValidation(res_response, str_SchemaFileallusers);
//		}
//		
//	}

	
	
  

	
	@When("JSON schema is valid")
	public void json_schema_is_valid() {
		System.out.println(this.lmsPojo.getRes_response());
		this.lmsPojo.setStr_SchemaFileallusers("user_skill_schema_all_users.json");
		if (Send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(),  200)) {
			JSON_Schema_Validation.cls_JSON_SchemaValidation(this.lmsPojo.getRes_response(),this.lmsPojo.getStr_SchemaFileallusers());
		}
		else {
			System.out.println("Schema not valid");
		}
		
	}
	
	@Then("User validates StatusCode")
	public void user_receives_status_code() throws InvalidFormatException, IOException {
		
        
		Send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(),200);
		this.lmsPojo.setRes_responsebody(Capturing_Actual_Output.capture_output(this.lmsPojo.getRes_response()));
		
		System.out.println("Body is:" + this.lmsPojo.getRes_responsebody());
			
			
			
		
		
	}
	
	@Then("User validates StatusCode and StatusMessage from {string} and {int}")
	public void user_receives_status_code_with(String sheetName,int rowNumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		System.out.println("eexcel path is :"+this.lmsPojo.getExcelPath());
		List<Map<String,String>> testData = 
				reader.getData(this.lmsPojo.getExcelPath(),sheetName);
		this.lmsPojo.setStatus_code(testData.get(rowNumber).get("StatusCode"));
         int i=Integer.parseInt(this.lmsPojo.getStatus_code());
		 
         this.lmsPojo.setStatus_message(testData.get(rowNumber).get("StatusMessage"));
		System.out.println(this.lmsPojo.getStatus_message());
		System.out.println(rowNumber);
		Send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(),i);
		this.lmsPojo.setRes_responsebody(Capturing_Actual_Output.capture_output(this.lmsPojo.getRes_response()));
		
		System.out.println(this.lmsPojo.getStatus_code());
		if (this.lmsPojo.getStatus_message()!="") {
			System.out.println("Body is:" + this.lmsPojo.getRes_responsebody());
			
			
			if (this.lmsPojo.getRes_responsebody().equals(this.lmsPojo.getStatus_message())){
				System.out.println("Status message is checked successfully");
			
			}
		}
		
	}


	@Then("User should receive a list of users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp")
	public void user_should_receive_a_list_of_users_with_skills_in_json_body_with_the_fields_user_skill_id_user_id_skill_id_months_of_exp() {
		
			if ((this.lmsPojo.getRes_responsebody().contains("user_skill_id")) && (this.lmsPojo.getRes_responsebody().contains("user_id")) && (this.lmsPojo.getRes_responsebody().contains("Skill_id")) && (this.lmsPojo.getRes_responsebody().contains("months_of_exp"))){
				System.out.println("Users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp is received successfully");
			}
		
		

	}
	 @And("^check the Database$")
	  public void check_the_database() {
		 this.lmsPojo.setStr_Query("select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map where user_skill_id='" +  this.lmsPojo.getStr_userskillsid() + "'");
		 String[] OutputArray=Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), this.lmsPojo.getStr_Query());
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
				
				if (Send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 200)) {
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
		 this.lmsPojo.setStr_Query( "select user_skill_id,user_id,skill_Id,months_of_exp from tbl_lms_userskill_map");
		 String[] OutputArray=Fetch_Data_From_SQL.connect(this.lmsPojo.getStr_DBURL(),this.lmsPojo.getStr_DBUserName(), this.lmsPojo.getStr_DBPWD(), this.lmsPojo.getStr_Query());
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
				
				if (Send_Request_For_Method.check_response_code(this.lmsPojo.getRes_response(), 200)) {
					if (k == int_Arraylen) {
						System.out.println("All the fields matched with database");
					} else {
						System.out.println("Database validation failed");
					}
				}
			}
		}
	

	

}
