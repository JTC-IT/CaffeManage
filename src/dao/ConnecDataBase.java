package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnecDataBase {
	private static Connection connec;

	public static Connection getConnec() {
		return connec;
	}

	 public ConnecDataBase() {
		 super(); String url = "jdbc:sqlserver://localhost:1433;databaseName=CafeDB";
		 String user = "team_PTTKHT";
		 String password = "pttkht";
		 try {
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			 connec = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
				 e.printStackTrace();
		}
	 }

//	private static String DB_URL = "jdbc:mysql://sql10.freemysqlhosting.net/sql10405172";
//	private static String USER_NAME = "sql10405172";
//	private static String PASSWORD = "YpNrnAfrqf";	
//	 
//
//	public ConnecDataBase(){
//		super();
//		try {
//			connec = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	 
	public static void close() {
		try {
			connec.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
