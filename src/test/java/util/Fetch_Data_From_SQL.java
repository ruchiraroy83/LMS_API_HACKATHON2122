/*******************************************************************************************************************************************************
 * class Name: Fetch_Data_From_SQL
 * 
 * Methods:connect(String str_URL, String str_UserName, String str_Pwd, String str_Query)
 * 
 * Purpose: 1.To Get the result of the query to the database in a key value format
 * 
 *******************************************************************************************************************************************************/


package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public class Fetch_Data_From_SQL {
	
	/**
	 * Method to initalize connection to the database and execute query
	 * @param str_URL
	 * @param str_UserName
	 * @param str_Pwd
	 * @param str_Query
	 * @return
	 */

	@SuppressWarnings("finally")
	public static Map<String, String> connect(String str_URL, String str_UserName, String str_Pwd, String str_Query) {
		PreparedStatement pst = null;
		Connection conn = null;
		Map<String, String> resultMap = new HashMap<>();
		try {
			DriverManager.registerDriver(
					(Driver) Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance());
			conn = DriverManager.getConnection(str_URL, str_UserName, str_Pwd);
			{
				if (conn != null) {
					pst = conn.prepareStatement(str_Query);
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {

						ResultSetMetaData resultsetMetaData = rs.getMetaData();
						for (int i = 1; i <= resultsetMetaData.getColumnCount(); i++) {
							String str_Column_Name = resultsetMetaData.getColumnLabel(i);
							String str_Column_Value = rs.getString(str_Column_Name);
							resultMap.put(str_Column_Name, str_Column_Value);

						}
					}

				} else {
					System.out.println("Failed to connect");
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				if (pst != null) {

					pst.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e) {
			}

			return resultMap;
		}
	}

	@SuppressWarnings("finally")
	public static Boolean connect_delete(String str_URL, String str_UserName, String str_Pwd, String str_Query,
			String id) {

		Boolean QueryResult = true;
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			DriverManager.registerDriver(
					(Driver) Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance());
			conn = DriverManager.getConnection(str_URL, str_UserName, str_Pwd);
			{
				if (conn != null) {
					/**
					 * Call to class where reading from excel for query as per TCID will used in this place
					 */
					pst = conn.prepareStatement(str_Query);
					ResultSet rs = pst.executeQuery();

					int count = 0;
					
					/**
					 * get the column count user for loop to get the column names & column value store that in an array
					 */

					while (rs.next()) {  

						++count;

						ResultSetMetaData resultsetMetaData = rs.getMetaData();
						int int_ColumnCount = resultsetMetaData.getColumnCount();
						for (int i = 1; i <= int_ColumnCount; i++) {
							String str_Column_Name = resultsetMetaData.getColumnLabel(i);
							String str_Column_Value = rs.getString(str_Column_Name);
							System.out.println(str_Column_Name + " : " + str_Column_Value);

							if (str_Column_Name == id) {

								QueryResult = false;
								return QueryResult;
							}

						}

					}

					if (count == 0) {
						System.out.println("No records found");
					}

				} else {
					System.out.println("Failed to connect");
				}

			}
		} catch (Exception e) {
		} finally {
			try {
				if (pst != null) {

					pst.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e) {
			}

			return QueryResult;
		}
	}
	
}
