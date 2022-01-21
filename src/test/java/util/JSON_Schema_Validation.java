/*******************************************************************************************************************************************************
 * class Name: JSON_Schema_Validation
 * 
 * Methods: cls_JSON_SchemaValidation(Response response, String fileName)
 * 
 * Purpose: to check if the given Response is a valid JSON or not
 * 
 *******************************************************************************************************************************************************/


package util;

import org.hamcrest.Matchers;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class JSON_Schema_Validation {

	public static ValidatableResponse _resp;
	

	public static void cls_JSON_SchemaValidation(Response response, String fileName) {
		_resp = response.then();
//		_resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(fileName));
		_resp.assertThat().body(Matchers.notNullValue())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(fileName));
//			_resp.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(fileName));
	}
	
}
