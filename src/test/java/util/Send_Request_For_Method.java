/*******************************************************************************************************************************************************
 * class Name: Send_Request_For_Method
 * 
 * Methods: 1.request_URL(String Username, String Password)
 * 			2.Sent_request(String strURL, RequestSpecification httpRequest, HttpMethod httpmethod,String ExcelPath, String SheetName,int rowNumber)
 * 
 * Purpose: 1.request_URL will take the input of username & password & it will create a request Specification of basic auth type with the given username &password.
 * 			Return type is the Request Specification created in the method.
 * 			2.Sent_request: To sent request  & get corresponding response based on the type of method.For Post & PUT method it will create a request body dynamically from the external excel data based on the column names & row number.
 * 			Return Type : Response
 * 
 *******************************************************************************************************************************************************/

package util;

import static util.constant.LMSApiConstant.CONST_SCENARIO;
import static util.constant.LMSApiConstant.CONST_SKILLS_API;
import static util.constant.LMSApiConstant.CONST_SKILL_ID;
import static util.constant.LMSApiConstant.CONST_STATUS_CODE;
import static util.constant.LMSApiConstant.CONST_STATUS_MESSAGE;
import static util.constant.LMSApiConstant.CONST_USERSKILLS_API;
import static util.constant.LMSApiConstant.CONST_USERS_API;
import static util.constant.LMSApiConstant.CONST_USER_ID;
import static util.constant.LMSApiConstant.CONST_USER_SKILL_ID;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static String NewAPIEndpoint;
	private LMSPojo lmsPojo;

	public Send_Request_For_Method(String API_Endpoint) {
		NewAPIEndpoint = API_Endpoint; 
	/**
	 * endpoint name has been assigned to a variable which is used to create POST or PUT the request body for that particular endpoint
	 */

		/**
		 *  fetching all the data from the properties files for a given endpoint
		 */
		Fetch_Data_From_Properties_File data_From_Properties_File = new Fetch_Data_From_Properties_File(API_Endpoint);
		this.lmsPojo = data_From_Properties_File.getLmsPojo();
	}

	 /**
	  * method to create a request Specification of basic auth type with the given username & password
	  */
	 
	public static RequestSpecification request_URL(String Username, String Password) {

		/**
		 *  return Request object
		 */
		return RestAssured.given().auth().preemptive().basic(Username, Password);

	}

	/**
	 *  Method to create a request body based on the request type dynamically from the excel & get the response based on the method
	 */
	 
	public Response Sent_request(String strURL, RequestSpecification httpRequest, HttpMethod httpmethod,
			String ExcelPath, String SheetName, int rowNumber) throws InvalidFormatException, IOException {
		Response response = null;
		String numericColumns = this.lmsPojo.getNumericColumns();
		switch (httpmethod) {
		case POST:
			 /**
			  * Getting the data from the excel in form of key value pair for a particular row.
			  */
			ExcelReader reader = new ExcelReader();
			List<Map<String, String>> excelRows = reader.getData("./" + ExcelPath, SheetName);
			Map<String, Object> finalMap = new HashMap<>();

			Map<String, String> row = excelRows.get(rowNumber);
			 /**
			  * removing the columns & their values from the fetched data of excel which are not required in the request body
			  */
			row.remove(CONST_SCENARIO);
			row.remove(CONST_STATUS_CODE);
			row.remove(CONST_STATUS_MESSAGE);
			row.remove(CONST_USER_SKILL_ID);
			row.remove(CONST_SKILL_ID);

			 /**
			  * User_id with be removed from the request body only if the API end point is Users as it will be automatically get generated in the response
			  */
			if (NewAPIEndpoint == CONST_USER_ID) {
				row.remove(CONST_USER_ID);
			}

			/**
			 * creating the request body by taking the column name as key & column value as value of the entry
			 */
			for (Map.Entry<String, String> entry : row.entrySet()) {
				Object value = entry.getValue();
				if (NewAPIEndpoint == CONST_SKILLS_API) {
					if (value.equals("true") || value.equals("false")) {
						boolean b = Boolean.valueOf(value.toString());
						value = b;
					}
					/**
					 * converting the data from the excel to integar for the fields which are mentioned to be numeric in the properties file
					 */
					if (StringUtils.isNumeric(entry.getValue())) {
						value = Integer.parseInt(entry.getValue());
					}
				}
				if (NewAPIEndpoint == CONST_USERS_API) {
					if (numericColumns.contains(entry.getKey())) {
						System.out.println("Entry value:" + entry.getValue());
						value = (StringUtils.isNotEmpty(entry.getValue()) && StringUtils.isNumeric(entry.getValue()))
								? Double.parseDouble(entry.getValue())
								: entry.getValue();
					}
				}

				if (NewAPIEndpoint == CONST_USERSKILLS_API) {
					if (numericColumns.contains(entry.getKey())) {
						System.out.println("Entry value:" + entry.getValue());
						value = (StringUtils.isNotEmpty(entry.getValue()) && StringUtils.isNumeric(entry.getValue()))
								? Integer.parseInt(entry.getValue())
								: entry.getValue();
					}
				}
				finalMap.put(entry.getKey(), value);
			}
			ObjectMapper mapper = new ObjectMapper();
			String requestString = mapper.writeValueAsString(finalMap);

			/**
			 * Captures JSON response from HTTP POST
			 */
			response = httpRequest.header(HttpHeaders.CONTENT_TYPE, ContentType.JSON).body(requestString).post(strURL);

			if (response != null) {
				/**
				 * Captures extra fields in response as key-value pair that are missing in the
				 * request
				 */
				Map<String, Object> unmatchedFields = getUnMatchedFields(requestString, response.asString());

				for (Map.Entry<String, Object> map : unmatchedFields.entrySet()) {
					/**
					 * writing into the excel in the required column value which was capture in
					 * response as extra field...like the autogenerate ID, User_Id incase of
					 * users,Skills_Id in case of Skills & User-Skill_id in case of UserSkills
					 */

					ExcelWriter writer = new ExcelWriter();
					boolean status = writer.writeExcelFile(ExcelPath, SheetName, rowNumber + 1, map.getKey(),
							map.getValue());
				}

			}
			break;

		case PUT:
			/**
			 * Getting the data from the excel in form of key value pair for a particular row.
			 */
			ExcelReader reader_put = new ExcelReader();

			List<Map<String, String>> excelRows_put = reader_put.getData("./" + ExcelPath, SheetName);
			Map<String, Object> finalMap_put = new HashMap<>();
			Map<String, String> row_put = excelRows_put.get(rowNumber);

			/**
			 * removing the colums & their values from the fetched data of excel which are not required in the request body
			 */
			row_put.remove(CONST_USER_SKILL_ID);
			row_put.remove(CONST_SKILL_ID);
			row_put.remove(CONST_SCENARIO);
			row_put.remove(CONST_STATUS_CODE);
			row_put.remove(CONST_STATUS_MESSAGE);
			/**
			 * creating the request body by taking the column name as key & column value as value of the entry
			 */
			for (Map.Entry<String, String> entry : row_put.entrySet()) {
				Object value = entry.getValue();
				if (NewAPIEndpoint == CONST_SKILLS_API) {
					if (value.equals("true") || value.equals("false")) {
						boolean b = Boolean.valueOf(value.toString());
						value = b;
					}
					/**
					 * converting the data from the excel to integar for the fields which are mentioned to be numeric in the properties file
					 */
					if (StringUtils.isNumeric(entry.getValue())) {
						value = Integer.parseInt(entry.getValue());

					}
				}
				if (numericColumns.contains(entry.getKey())) {
					value = (StringUtils.isNotEmpty(entry.getValue()) && StringUtils.isNumeric(entry.getValue()))
							? Long.parseLong(entry.getValue())
							: entry.getValue();
				}
				finalMap_put.put(entry.getKey(), value);
			}
			/**
			 * creating the JSON response for put
			 */
			ObjectMapper mapper_put = new ObjectMapper();
			String requestString_put = mapper_put.writeValueAsString(finalMap_put);
			httpRequest.header(HttpHeaders.CONTENT_TYPE, ContentType.JSON);
			httpRequest.body(requestString_put);
			response = httpRequest.put(strURL);

			if (response != null) {
				System.out.println("Response :\n" + response.asString());
			}
			break;

		case GET:
			/**
			 * creating the JSON response for GET
			 */
			RestAssured.baseURI = strURL;
			response = httpRequest.when().get(RestAssured.baseURI);
			System.out.println("Response :" + response.asString());
			break;

		case DELETE:
			/**
			 * creating the JSON response for DELETE
			 */
			RestAssured.baseURI = strURL;
			response = httpRequest.when().delete(RestAssured.baseURI);
			System.out.println("Response :" + response.asString());
			break;

		default:
			break;

		}

		return response;
	}

	/**
	 * method to find the extra field in the response body by comparing it with request as key value pair.If key to key is not matching then capturing the value
	 * @param requestString
	 * @param responseString
	 * @return
	 */
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
