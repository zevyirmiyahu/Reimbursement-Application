package com.zevyirmiyahu.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String URL = "jdbc:oracle:thin:"
			+ "@zevy-database.c03ubl7onve3.us-east-2.rds.amazonaws.com" // @endpoint on AWS
			+ ":1521:ORCL";
	// jdbc:oracle:thin:@hostname:1521:sid   <- host name is endpoint
	private static final String USERNAME = "zyMasterUserLogin987";
	private static final String PASSWORD = "zya1b2c3!#";
	
	public static Connection getConnection() throws SQLException {
		try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}
