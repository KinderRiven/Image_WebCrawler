package com.kinderriven.mysql.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.kinderriven.webspider.getdata.WebSourceCode;

/*
 *  通过这个来更新数据库中的cookie
 */
public class UpdateRoameCookie {

	
	/*
	 *  Database information
	 */
	private static String mysql_ip = "127.0.0.1";
	private static String mysql_database = "webspider";
	private static int mysql_port = 3306;
	
	public UpdateRoameCookie(){};
	
	public void upDate(){
		
		ResultSet resutl = 
				new MySQLServer(mysql_ip, mysql_database, mysql_port)
				.getResult("SELECT username, password FROM roame_cookie");
		
		try {
			
			while(resutl.next()){
				
				String username = resutl.getString(1);
				String password = resutl.getString(2);
				
				String cookie = new WebSourceCode().getRoameCookie(username, password);
				
				/*
				 *  UPDATE roame_cookie SET cookie = 'cookie' WHERE username = 'username'
				 */
				
				System.out.println(cookie);
				
				String sql = "UPDATE roame_cookie SET cookie = '" 
				+ cookie 
				+ "' WHERE username = '" + username + "'";
				
				new MySQLServer(mysql_ip, mysql_database, mysql_port)
				.updaeData(sql);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {

		new UpdateRoameCookie().upDate();
		
	}

}
