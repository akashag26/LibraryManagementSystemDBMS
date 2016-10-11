package com.dbms.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManagerFactory {
	
	static final String JDBCURL	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
	static final String USERNAME = "aagrawa6";
	static final String PASSWORD = "200103973";
	
	
	public static Connection createConnection()
	{
		return getConnection();
	}

	/**
	 * new Connection for the application
	 * @return
	 */
	private static Connection getConnection()
	{
		Connection conn = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error fetching connection\n");
		}
		return conn;
		
	}
	
	public static void CloseConnection(Connection conn,Statement stmt)
	{
		close(stmt);
		close(conn);
	}
	
	
	public static void CloseConnection(Connection conn,Statement stmt, ResultSet results)
	{
		close(results);
		close(stmt);
		close(conn);
	}
	
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Closing connection\n");
			}
		}
	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error closing Statement\n");
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error closing ResultSet\n");
			}
		}
	}

}
