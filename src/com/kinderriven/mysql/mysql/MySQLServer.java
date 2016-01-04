package com.kinderriven.mysql.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQLServer {

	String database;
	int port;
	String ip;
	
	
	private String username = "root";
	private String password = "";
	
	Connection conn;
	Statement stat;
	
	
	public MySQLServer(String ip, String database, int port){
		
		this.database = database;
		this.ip = ip;
		this.port = port;
		
		username = MySQLSetting.getMySQLSetting().username;
		password = MySQLSetting.getMySQLSetting().password;
		
		connect();
	}
	
	public void connect(){
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
			conn = DriverManager.getConnection(url, username, password);
			stat = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResult(String sql){
		
		ResultSet result = null;
		
		try {
			
			result = stat.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	public boolean updaeData(String sql){
		
		boolean result = false;
		try {
			result = stat.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return result;
	}
	
	public static void main(String[] args) {
		
		ResultSet rs = new MySQLServer("127.0.0.1", "webspider", 3306).getResult("SELECT * FROM TieBa");
		
		try {
			while(rs.next()){
				
				for(int i = 1; i <= 5; i++){
					
					System.out.println(rs.getString(i));
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
