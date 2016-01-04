package com.kinderriven.webspider.baidutieba;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kinderriven.mysql.mysql.MySQLServer;
import com.kinderriven.mysql.mysql.MySQLSetting;
import com.kinderriven.webspider.getdata.WebSourceCode;

/*
 *  获得帖子页面的一些信息
 *  
 *  getPageNumber 获得帖子一共有几页的信息
 */
public class GetTieBaPageData {

	String htmlStr;
	
	public final String mysql_ip = MySQLSetting.getMySQLSetting().mysql_ip;
	public final String mysql_database = MySQLSetting.getMySQLSetting().mysql_database;
	public final int mysql_port = MySQLSetting.getMySQLSetting().mysql_port;
	
	
	public Vector<String> getTiezi(){
		
		Vector<String> vector = new Vector<>();
		
		ResultSet result = new MySQLServer(mysql_ip, mysql_database, mysql_port)
				.getResult("SELECT url FROM webspider.tieba;");
		
		try {
			
			while(result.next()){
				
				vector.addElement(result.getString(1));
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return vector;
	}
	public GetTieBaPageData(String urlStr, String encoding){
		
		htmlStr = new WebSourceCode(urlStr, encoding).getSourceCode();
		
	}
	
	public GetTieBaPageData(){
		
	}
	
	/*
	 *  获得帖子的总页数
	 */
	public int getPageNumber(){
		
		int page = 0;
		
		Pattern r = Pattern.compile("red\">(.+)</span>页");
		
	    Matcher m = r.matcher(htmlStr);
	    
		if(m.find()){
			
			page = Integer.parseInt(m.group(1));
			
		}
		else{
			
		}
		
		return page;
	}
	
	public static void main(String[] args) {
		
	}

}
