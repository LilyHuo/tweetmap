package server;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBcon {
	public static Connection createDBConnection() throws Exception
	{
	Connection conn=null;
	String userName = "*";
	String password = "*";
	String hostname = "*";
	String port = "*";
	String dbName = "*";
	String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
	          port + "/" + dbName + "?user=" + userName + "&password=" + password;	
	
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	Class.forName("com.mysql.jdbc.Driver");
	conn=DriverManager.getConnection(jdbcUrl);
	System.out.println("Get Database Connected");
	return conn;
	}
	public static void closeConnection(Connection con)	
	{
		if(con!=null)
		{
			try{con.close();}
			catch(SQLException e){e.printStackTrace();}
		}
	}
}
