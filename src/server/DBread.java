package server;

import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DBread {
	
	public static ArrayList<String> readDB(Connection con, String keyword) throws InterruptedException{

	    ArrayList<String> data = new ArrayList<String>();
	    Gson gson = new GsonBuilder().create();
	    Connection con1 = con; 
	          
        Statement stmt=null;  
          try {
        	   stmt = con1.createStatement();
		} catch (SQLException e) {e.printStackTrace();}
          
        Thread.sleep(100);

	    String sql = "SELECT id, name, keyword,latitude, longtitude FROM TWEETER";
		ResultSet rs =null;
		try {
					rs = stmt.executeQuery(sql);
			} catch (SQLException e1) {e1.printStackTrace();}
			    //Extract data from result set
			      
		try {
				while(rs.next()){
					     //Retrieve by column name
						
						 
					     String id  = rs.getString(1);
					     String name = rs.getString(2);
					     String kw = rs.getString(3);
					     int latitude = rs.getInt(4);
					     int longtitude = rs.getInt(5);
					     
					     inform singlemsg = new inform();
					     
					     singlemsg.Id = id;
					     singlemsg.name = name;
					     singlemsg.kw =kw;
					     singlemsg.Lat = latitude;
					     singlemsg.Long = longtitude;
					     
					     String getinfo = gson.toJson(singlemsg);
					     data.add(getinfo);
					     
					}
				} catch (SQLException e) {e.printStackTrace();}
	            
	    return data;
	}

	
	
	public static ArrayList<String> readDB_all(Connection con) throws InterruptedException{

	    ArrayList<String> data_all = new ArrayList<String>();
	    Gson gson = new GsonBuilder().create();
	    Connection con1 = con;
       
        Statement stmt=null;  
          try {
        	   stmt = con1.createStatement();
		} catch (SQLException e) {e.printStackTrace();}
          
        Thread.sleep(100);

	    String sql = "SELECT id, name,latitude, longtitude FROM TWEETER";
		ResultSet rs =null;
		try {
					rs = stmt.executeQuery(sql);
			} catch (SQLException e1) {e1.printStackTrace();}
			    //Extract data from result set
			      
		try {
				while(rs.next()){
					     //Retrieve by column name
						
						 
					     String id  = rs.getString(1);
					     String name = rs.getString(2);
				
					     int latitude = rs.getInt(3);
					     int longtitude = rs.getInt(4);
					     
					     inform singlemsg = new inform();
					     
					     singlemsg.Id = id;
					     singlemsg.name = name;
					     //singlemsg.kw =kw;
					     singlemsg.Lat = latitude;
					     singlemsg.Long = longtitude;
					     
					     String getinfo = gson.toJson(singlemsg);
					     data_all.add(getinfo);
					     
					}
				} catch (SQLException e) {e.printStackTrace();}
	            
	    return data_all;
	}
}

