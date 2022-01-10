package util;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class JSON_Schema_Validation {
	
	public static ValidatableResponse _resp;

		public static void cls_JSON_SchemaValidation(Response response, String fileName) {
		
		System.out.println(fileName);
		_resp = response.then();
		_resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(fileName));
		
		}
}

