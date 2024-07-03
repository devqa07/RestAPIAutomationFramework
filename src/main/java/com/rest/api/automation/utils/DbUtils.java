package com.rest.api.automation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB Connector responsible for building an active
 * connection to database wrt Environment
 */
public class DbUtils {
	final static String propertyFileName = "config";
	final static String environment = RestAPIUtils.getDbEnvDetails();
	public static Connection getDBConnection(String db_Name) {
		Connection conn = null;
		String connectionUrl = "";
		String username = "";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (environment.equalsIgnoreCase("qa") || environment.equalsIgnoreCase("test")) {
				connectionUrl = ConfigHelper.returnPropVal(propertyFileName, "qa-connectionURL") + db_Name + "?enabledTLSProtocols=TLSv1.2&useSSL=false";
				username = ConfigHelper.returnPropVal(propertyFileName, "qa-username");
				if(System.getProperty("dbPwd")!=null){
					password = System.getProperty("dbPwd");
				}
				else {password = ConfigHelper.returnPropVal(propertyFileName, "qa-password");}

			} else if (environment.equalsIgnoreCase("preprod")) {
				connectionUrl = "";
				username = "";
				password = "";
			} else if (environment.equalsIgnoreCase("production") || environment.equalsIgnoreCase("prod")) {
				connectionUrl = ConfigHelper.returnPropVal(propertyFileName, "prd-connectionURL");
				username = ConfigHelper.returnPropVal(propertyFileName, "prd-username");
				password = ConfigHelper.returnPropVal(propertyFileName, "prd-password");

			} else if (environment.equalsIgnoreCase("development") || environment.equalsIgnoreCase("dev")) {
				connectionUrl = ConfigHelper.returnPropVal(propertyFileName, "dev-connectionURL") + db_Name;
				username = ConfigHelper.returnPropVal(propertyFileName, "dev-username");
				if(System.getProperty("dbPwd")!=null){
					password = System.getProperty("dbPwd");
				}
				else {password = ConfigHelper.returnPropVal(propertyFileName, "dev-password");}
			} else {
				System.out.println("Inappropriate or empty Connection String");
			}
			conn = DriverManager.getConnection(connectionUrl, username, password);

		} catch (ClassNotFoundException |

				SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}