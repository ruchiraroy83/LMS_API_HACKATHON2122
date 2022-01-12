package util;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LMSPojo {
	private String str_SchemaFilePath="user_skill_schema1.json";
	private String str_SchemaFileallusers;
	private String excelPath;

	private RequestSpecification request_URL;
	private String str_baseURL;
	private  String str_basePath;
	private  String UserName;
	private String Password;
	private String str_FinalURI;
	private String strID;
	private Response res_response;
	private String res_responsebody;
	private String str_DBURL;
	private String str_DBUserName;
	private String str_DBPWD;
	private String str_Query;
	private String str_userskillsid;
	private int status_code;
	private String status_message;

	
	
	public String getStr_SchemaFilePath() {
		return str_SchemaFilePath;
	}
	public void setStr_SchemaFilePath(String str_SchemaFilePath) {
		this.str_SchemaFilePath = str_SchemaFilePath;
	}
	public String getStr_SchemaFileallusers() {
		return str_SchemaFileallusers;
	}
	public void setStr_SchemaFileallusers(String str_SchemaFileallusers) {
		this.str_SchemaFileallusers = str_SchemaFileallusers;
	}
	
	public String getExcelPath() {
		return excelPath;
	}
	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}
	public RequestSpecification getRequest_URL() {
		return request_URL;
	}
	public void setRequest_URL(RequestSpecification request_URL) {
		this.request_URL = request_URL;
	}
	public String getStr_baseURL() {
		return str_baseURL;
	}
	public void setStr_baseURL(String str_baseURL) {
		this.str_baseURL = str_baseURL;
	}
	public String getStr_basePath() {
		return str_basePath;
	}
	public void setStr_basePath(String str_basePath) {
		this.str_basePath = str_basePath;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getStr_FinalURI() {
		return str_FinalURI;
	}
	public void setStr_FinalURI(String str_FinalURI) {
		this.str_FinalURI = str_FinalURI;
	}
	public String getStrID() {
		return strID;
	}
	public void setStrID(String strID) {
		this.strID = strID;
	}
	public Response getRes_response() {
		return res_response;
	}
	public void setRes_response(Response res_response) {
		this.res_response = res_response;
	}
	public String getRes_responsebody() {
		return res_responsebody;
	}
	public void setRes_responsebody(String res_responsebody) {
		this.res_responsebody = res_responsebody;
	}
	public String getStr_DBURL() {
		return str_DBURL;
	}
	public void setStr_DBURL(String str_DBURL) {
		this.str_DBURL = str_DBURL;
	}
	public String getStr_DBUserName() {
		return str_DBUserName;
	}
	public void setStr_DBUserName(String str_DBUserName) {
		this.str_DBUserName = str_DBUserName;
	}
	public String getStr_DBPWD() {
		return str_DBPWD;
	}
	public void setStr_DBPWD(String str_DBPWD) {
		this.str_DBPWD = str_DBPWD;
	}
	public String getStr_Query() {
		return str_Query;
	}
	public void setStr_Query(String str_Query) {
		this.str_Query = str_Query;
	}
	public String getStr_userskillsid() {
		return str_userskillsid;
	}
	public void setStr_userskillsid(String str_userskillsid) {
		this.str_userskillsid = str_userskillsid;
	}
	public int getStatus_code() {
		return status_code;
	}
	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	public String getStatus_message() {
		return status_message;
	}
	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	
	

	
}