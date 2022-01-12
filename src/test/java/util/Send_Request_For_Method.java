package util;

import static util.constant.LMSApiConstant.CONST_SENARIO;
import static util.constant.LMSApiConstant.CONST_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_STATUS_MESSAGE;
import static util.constant.LMSApiConstant.CONST_USER_SKILL_ID;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Send_Request_For_Method {

	private Properties prop;

	public Send_Request_For_Method() {
		try {
			this.prop = Fetch_Data_From_Properties_File
					.readPropertiesFile("./src/test/resources/config/credentials.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static RequestSpecification request_URL(String Username, String Password) {

		// Request object
		return RestAssured.given().auth().basic(Username, Password);

	}

	// public static Response Sent_request(String strURL,RequestSpecification
	// httpRequest, HttpMethod httpmethod,String strCoulumnName[], String
	// str_Input[]) {
	public Response Sent_request(String strURL, RequestSpecification httpRequest, HttpMethod httpmethod,
			String ExcelPath, String SheetName, int rowNumber) throws InvalidFormatException, IOException {
		Response response;

		switch (httpmethod) {
		case POST:

			// Request object

			System.out.println("Inside method excel path is :" + ExcelPath);
			// Request payload sending along with POST request
			ExcelReader reader = new ExcelReader();
			// ArrayList<String> colList =
			// reader.getColumnNames("./src/test/resources/"+ExcelPath, SheetName);
			List<Map<String, String>> excelRows = reader.getData("./" + ExcelPath, SheetName);
			Map<String, Object> finalMap = new HashMap<>();

			Map<String, String> row = excelRows.get(rowNumber);
			row.remove(CONST_SENARIO);
			row.remove(CONST_STATUS_CODE);
			row.remove(CONST_STATUS_MESSAGE);
			row.remove(CONST_USER_SKILL_ID);

			for (Map.Entry<String, String> entry : row.entrySet()) {
				Object value = entry.getValue();
				String numericColumns = (String) this.prop.get("numeric.coloms");
				if (numericColumns.contains(entry.getKey())) {
					value = StringUtils.isEmpty(entry.getValue()) ? 0 : Integer.parseInt(entry.getValue());
				}
				finalMap.put(entry.getKey(), value);
			}
			ObjectMapper mapper = new ObjectMapper();
			String requestString = mapper.writeValueAsString(finalMap);
			httpRequest.header(HttpHeaders.CONTENT_TYPE, ContentType.JSON);
			httpRequest.body(requestString);
			System.out.println("Request :" + requestString);
			response = httpRequest.post(strURL);
			
			if (response != null) {
				System.out.println("Response :" + response.asString());

				Map responseMap = mapper.readValue(response.asString(), Map.class);
				if (responseMap.get("user_skill_id") != null) {
					String userSkillId = (String) responseMap.get("user_skill_id");
					
					
				}
				System.out.println(responseMap);
			}
			return response;

		case PUT:

			/*
			 * for (int i=0;i<strCoulumnName.length;i++) { if (strCoulumnName[i]!=null ) {
			 * requestParams.put(strCoulumnName[i], str_Input[i]);
			 * 
			 * }
			 * 
			 * }
			 * 
			 * httpRequest.header("Content-Type","application/json");
			 * httpRequest.body(requestParams.toJSONString());//attach above data to the
			 * request
			 * 
			 * //Response Object response=httpRequest.request(Method.PUT);
			 */

		case GET:
			RestAssured.baseURI = strURL;
			response = httpRequest.when().get(RestAssured.baseURI);
			System.out.println("Response :" + response.asString());
			return response;
		case DELETE:
			RestAssured.baseURI = strURL;
			response = httpRequest.when().delete(RestAssured.baseURI);
			System.out.println("Response :" + response.asString());
			return response;

		}

		return null;
	}

	public boolean check_response_code(Response response, int Status_Code) {
		int statusCode = response.getStatusCode();

		if (statusCode == Status_Code) {
			System.out.println("Response code matches with the expected output response code");
			return true;
		} else {
			System.out.println("Response code does not matches with the expected output response code");
			return false;
		}

	}

}
