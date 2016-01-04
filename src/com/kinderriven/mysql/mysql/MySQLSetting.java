package com.kinderriven.mysql.mysql;

public class MySQLSetting {

	public String mysql_ip = "127.0.0.1";
	public String mysql_database = "webspider";
	public int mysql_port = 3306;
	
	public String username = "root";
	public String password = "";
	
	public String savePath = "F:/image/";
	
	private static MySQLSetting mys = new MySQLSetting();
	
	static public MySQLSetting getMySQLSetting(){
		
		return mys;
		
	}
	private MySQLSetting(){};
	
	public static void main(String[] args) {

	}

}
