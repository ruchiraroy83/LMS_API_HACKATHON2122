package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Fetch_Data_From_Properties_File {
	private LMSPojo lmsPojo;

	public Fetch_Data_From_Properties_File() {
		this.lmsPojo = new LMSPojo();
		try {
			Properties prop = readPropertiesFile();
			this.lmsPojo.setStr_baseURL(prop.getProperty("URL"));
			this.lmsPojo.setUserName(prop.getProperty("Username"));
			this.lmsPojo.setPassword(prop.getProperty("Pwd"));
			this.lmsPojo.setStr_DBURL(prop.getProperty("SQLDatabaseURL"));
			this.lmsPojo.setStr_DBUserName(prop.getProperty("DBUname"));
			this.lmsPojo.setStr_DBPWD(prop.getProperty("BDPWD"));
			this.lmsPojo.setExcelPath(prop.getProperty("UserSkills_ExcelPath"));
			this.lmsPojo.setNumericColumns(prop.getProperty("numeric.coloms"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Properties readPropertiesFile() throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream("./src/test/resources/config/credentials.properties");
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

	public LMSPojo getLmsPojo() {
		return lmsPojo;
	}

	public void setLmsPojo(LMSPojo lmsPojo) {
		this.lmsPojo = lmsPojo;
	}

	// Example of how to call in main method
	/*
	 * public static void main(String[] args) throws IOException { Properties prop =
	 * readPropertiesFile("credentials.properties"); String
	 * str_User=prop.getProperty("username"); String
	 * str_password=prop.getProperty("Password");
	 * 
	 * }
	 */

}
