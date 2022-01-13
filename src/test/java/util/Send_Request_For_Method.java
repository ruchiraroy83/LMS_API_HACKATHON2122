package util;

import static util.constant.LMSApiConstant.CONST_SENARIO;
import static util.constant.LMSApiConstant.CONST_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_STATUS_MESSAGE;
import static util.constant.LMSApiConstant.CONST_USERSKILL_ID;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.core.options.CurlOption.HttpMethod;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Send_Request_For_Method {

	private static final String CONST_USER_SKILL_ID = "user_skill_id";
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

				Map<String, Object> unmatchedFields = getUnMatchedFields(requestString, response.asString());

				for (Map.Entry<String, Object> map : unmatchedFields.entrySet()) {
					ExcelWriter writer = new ExcelWriter();
					boolean status = writer.writeExcelFile(ExcelPath, SheetName, rowNumber + 1, map.getKey(),
							map.getValue());
					System.out.println("File Updated with " + map.getKey() + ": " + map.getValue() + "? " + status);
				}

			}
			return response;

		case PUT:
			
			System.out.println("Inside method excel path is :" + ExcelPath);
			// Request payload sending along with POST request
			
			// ArrayList<String> colList =
			// reader.getColumnNames("./src/test/resources/"+ExcelPath, SheetName);
			ExcelReader reader_put = new ExcelReader();
			
			
			List<Map<String, String>> excelRows_put = reader_put.getData("./" + ExcelPath, SheetName);
			Map<String, Object> finalMap_put = new HashMap<>();
			Map<String, String> row_put = excelRows_put.get(rowNumber);
			row_put.remove(CONST_USERSKILL_ID);
			
			for (Map.Entry<String, String> entry : row_put.entrySet()) {
				Object value = entry.getValue();
				String numericColumns = (String) this.prop.get("numeric.coloms");
				if (numericColumns.contains(entry.getKey())) {
					value = StringUtils.isEmpty(entry.getValue()) ? 0 : Integer.parseInt(entry.getValue());
				}
				finalMap_put.put(entry.getKey(), value);
			}
			ObjectMapper mapper_put = new ObjectMapper();
			String requestString_put = mapper_put.writeValueAsString(finalMap_put);
			System.out.println("Request string is:" + requestString_put);
			httpRequest.header(HttpHeaders.CONTENT_TYPE, ContentType.JSON);
			httpRequest.body(requestString_put);
			System.out.println("Put Request :" + requestString_put);
			System.out.println("URL is " + strURL);
			response = httpRequest.put(strURL);
			
			if (response != null) {
				System.out.println("Response :" + response.asString());

				Map responseMap = mapper_put.readValue(response.asString(), Map.class);
				if (responseMap.get("user_skill_id") != null) {
					String userSkillId = (String) responseMap.get("user_skill_id");
					
					
				}
				System.out.println(responseMap);
			}
			return response;

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

	private Map<String, Object> getUnMatchedFields(String requestString, String responseString) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashedMap<>();
		try {
			Map<String, Object> requestMap = mapper.readValue(requestString, Map.class);
			Map<String, Object> responseMap = mapper.readValue(responseString, Map.class);

			for (Map.Entry<String, Object> response : responseMap.entrySet()) {
				boolean matchFound = Boolean.FALSE;
				for (Map.Entry<String, Object> request : requestMap.entrySet()) {
					if (response.getKey().equals(request.getKey())) {
						matchFound = Boolean.TRUE;
					}
				}
				if (!matchFound && !"message".equalsIgnoreCase(response.getKey())) {
					map.put(response.getKey(), response.getValue());
				}
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return map;
	}

}
