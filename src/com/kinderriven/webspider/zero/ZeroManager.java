package com.kinderriven.webspider.zero;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.kinderriven.mysql.mysql.MySQLSetting;
import com.kinderriven.webspider.getdata.WebSourceCode;

public class ZeroManager {
	
	/*
	 *  Database information
	 */
	public final String mysql_ip = MySQLSetting.getMySQLSetting().mysql_ip;
	public final String mysql_database = MySQLSetting.getMySQLSetting().mysql_database;
	public final int mysql_port = MySQLSetting.getMySQLSetting().mysql_port;
	
	private static ZeroManager zeroManager = new ZeroManager();

	private String folder_suffix = MySQLSetting.getMySQLSetting().savePath + "Zero/";
	private String html_suffix = "http://www.lingyu.me/";
	private String folder;
	
	public static ZeroManager getZM(){
		
		return zeroManager;
	}

	public void setFolder(String folder){
		
				
		this.folder =  MySQLSetting.getMySQLSetting().savePath + folder;
		System.out.println(this.folder);
		
	}
	
	Vector<ZeroProjectManager>vecProject = new Vector<>();
	/*
	 *  获得一个网页内所有的套图页面
	 */
	public void getProject(String urlStr){
		
		String htmlStr = new WebSourceCode(urlStr, "UTF-8").getSourceCode2();
		
		Document doc = Jsoup.parse(htmlStr);
		
		
		Elements elements = doc.getElementsByClass("thumbnail");
		
		
		for(int i = 0; i < elements.size(); i++){
			
			String str = elements.get(i).getElementsByTag("a").attr("href").toString();
			
			String newFold;
			
			Pattern r = Pattern.compile("tp_(.+)/");
			
		    Matcher m = r.matcher(str);
		    
			if(m.find()){
				
				 newFold = folder + m.group(1) + "/";
				
			}
			else{
				
				newFold = folder + "defult/";
			}
			
			System.out.println(newFold);
			
			vecProject.add(new ZeroProjectManager(str, newFold));
			
			vecProject.get(vecProject.size() - 1).start();
;		}
	}
	
	/*
	 *  线程管理
	 */
	public void threadManager(){
				
		System.out.println(vecProject.size());
		
		for(int i = 0; i < vecProject.size(); i++){
			
			try {
				
				vecProject.elementAt(i).join();
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	/*
	 *  传入抓取的类别，进行抓取
	 */
	public void startRun(String type){
		
		String urlSuffix = html_suffix + type + "page/";
		
		System.out.println(urlSuffix);
		
		folder = folder_suffix + type;
		
		int pageNumber = new GetZeroPageData().getPageNumber(urlSuffix);
		
		
		/* 获得专题下的页码总数 */
		System.out.println(pageNumber);
		
		for(int i = 1; i <= pageNumber; i++){
			
			String urlStr = urlSuffix + i + "/";
			
			System.out.println(urlStr);
			
			getProject(urlStr);
			
		}
		threadManager();
	}
	
	public static void main(String[] args) {
		
		ZeroManager.getZM().startRun("tag/bingjiao/");
	}

}
