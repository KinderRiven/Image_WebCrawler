package com.kinderriven.webspider.roame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.kinderriven.mysql.mysql.MySQLServer;
import com.kinderriven.mysql.mysql.MySQLSetting;
import com.kinderriven.webspider.getdata.WebSourceCode;

public class RoameManger {

	
	/*
	 *  Database information
	 */
	private static String mysql_ip = MySQLSetting.getMySQLSetting().mysql_ip;
	private static String mysql_database = MySQLSetting.getMySQLSetting().mysql_database;
	private static int mysql_port = MySQLSetting.getMySQLSetting().mysql_port;
	
	private String cookie;
	
	Vector<String>vecList = new Vector<>();		//需要抓的动漫名字
	Vector<String>vecImage = new Vector<>();	//图片链接
	Vector<String>vecID = new Vector<>();		//图片编码（用于数据库判重）
	
	Map<String, Boolean>mapCookie = new HashMap<>();
	Vector<String>vecCookie = new Vector<>();
	
	private static String image_suffix = "http://www.roame.net";
	
	private static RoameManger rManger = new RoameManger();
	private RoameManger(){};
	
	public static RoameManger getRM(){
		
		return rManger;
		
	}
	
	/*
	 *  获得抓取的动漫表
	 */
	public void getData(){
		
		vecList.add("hibike-euphonium");
		
		ResultSet result = 
				new MySQLServer(mysql_ip, mysql_database, mysql_port)
				.getResult("SELECT cookie FROM roame_cookie");
		
		try {
			
			while(result.next()){
				
				mapCookie.put(result.getString(1), false);
				vecCookie.add(result.getString(1));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		cookie = vecCookie.get(0);
	}
	
	/*
	 *  更新数据库信息
	 */
	public void upDate(){
		
		
	}
	
	/*
	 *  获得图片地址
	 */
	public String getImageUrl2(String urlStr){
		
		String tmpUrl = null;
		/*
		 *  获得第一次网页源码
		 */
		String htmlStr;
		htmlStr = new WebSourceCode(urlStr, "UTF-8").getSourceWithCookie(cookie);
		
		/*
		 *  获得第二层网页的地址
		 */
		Document doc = Jsoup.parse(htmlStr);
		String str = doc.getElementById("darlnks").toString();
		
		Pattern pattern = Pattern.compile("href=\"(http.*/)\"");
		Matcher matcher = pattern.matcher(str);
		
		if(matcher.find()){	
			tmpUrl = matcher.group(1);		
		}
		else{
			return null;
		}
		
		/*
		 *  从第二层网址获得图片链接
		 */
		urlStr = tmpUrl;
		htmlStr = new WebSourceCode(urlStr, "UTF-8").getSourceWithCookie(cookie);
		
		doc = Jsoup.parse(htmlStr);
		
		str = doc.toString();
		
		pattern = Pattern.compile("\"/(ROAME_.*).jpg\"");
		matcher = pattern.matcher(str);
		
		if(matcher.find()){
			
			System.out.println(matcher.group(1));
			vecID.add(matcher.group(1));
			
			tmpUrl += matcher.group(1) + ".jpg";
		}
		else 
			return null;

		return tmpUrl;
	}
	
	/*
	 *  获得一个专题下所有页面的子页面
	 *  对子页面包含的图片链接进行提取
	 */
	public void getImageUrl(String urlStr, int pageNumber){

		Vector<String>vecHtml = new Vector<String>();
		
		for(int i = 1; i <= pageNumber; i++){
			
			String url = urlStr + i + ".html";
			String htmlStr = new WebSourceCode(url, "UTF-8").getSourceCode();
			
			Document doc = Jsoup.parse(htmlStr);
			Elements elements = doc.getElementsByClass("fbi");
			
			for(int j = 0; j < elements.size(); j++){
				
				vecHtml.add(image_suffix + elements.get(j).getElementsByTag("a").attr("href"));
			}
		}
		
		int imageNumber = vecHtml.size();
		System.out.println("[Image Number] : " + imageNumber);
		
		/*
		 *  对子页面进行图片链接的提取
		 */
		for(int i = 0; i < imageNumber; i++){
			
			String str = getImageUrl2(vecHtml.get(i));
			
			if(str != null){
				
				System.out.println(str);
				vecImage.add(str);
			}
		}
	}
	
	/*
	 *  用于Cookie和数据的同步
	 *  管理整个抓图的流程
	 */
	public void runManager(){
		
		Vector<GetRoameImage>vecThread = new Vector<>();
		
		/* 
		 * 开始图片的下载 
		 * 这里只支持一个Cookie下载一张 QAQ 
		 * 给跪了 
		 */
		System.out.println(vecCookie.size());
		
		int now = 0, imageNumber = vecImage.size();
		
		while(now < imageNumber){

			/*
			 *  检查执行结束的线程 
			 *  	1.对于结束的线程解放它的cookie
			 * 		2.将结束的线程从线程的容器中删除
			 */
			for(int i = 0; i < vecThread.size(); ){

				if(vecThread.get(i).isAlive() == false){

					String tmpCookie = vecThread.get(i).getCookie();
					mapCookie.put(tmpCookie, false);
					vecThread.remove(i);
				}
				else 
					i ++;
			}
			
			System.out.println("Running Thread " + vecThread.size());
			/*
			 *  这里启动下载
			 *  如果cookie没有被占用，就启动一个线程，加锁这个cookie
			 */
			while(now < imageNumber){
				
				String tmpCookie = null, tmpStr;
				for(int i = 0; i < vecCookie.size(); i++){
					
					tmpStr = vecCookie.elementAt(i);
					if(mapCookie.get(tmpStr) == false){
						
						tmpCookie = tmpStr;
						mapCookie.put(tmpCookie, true);
						break;
					}
					
				}
				
				if(tmpCookie == null) break;

				String folder = "F:/image/test/" + vecID.get(now) + ".jpg";
				
				System.out.println("start " + vecImage.get(now));
				System.out.println("start " + tmpCookie);
				
				vecThread.add( new GetRoameImage(vecImage.get(now),  tmpCookie,  folder));
				vecThread.get(vecThread.size() - 1).start();
				
				now ++;
			}
			
			/* 
			 * 设置延迟 
			 * 每隔一段时间检查cookie使用情况决定是否进行下一个爬取人物
			 */
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*Join进行阻塞 */
		
		for(int i = 0; i < vecThread.size(); i++){
			
			try {
				vecThread.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	/*
	 *  开始运行
	 *  主要起到 单独抓取某一动漫的所有图片进行多线程爬去
	 *  动漫一个一个爬取，图片多线程爬取
	 */
	public void startRun(){
		
		getData();

		for(int i = 0; i < vecList.size(); i++){
			
			/* 清空容器 */
			vecID.clear();
			vecImage.clear();
			
			String urlStr = new GetRoamePageData().AllImagePage(vecList.get(i));
			System.out.println(urlStr);
			
			/* 获得专题一共有多少页 */
			int pageNumber = new GetRoamePageData().getPageNumber(urlStr);
			
			/* 获得该专题所有链接 */
			System.out.println("[PageNumber] : " + pageNumber);
			getImageUrl(urlStr, pageNumber);
			
			runManager();
		}
		
	}
	
	public static void main(String[] args) {
		
		 RoameManger.getRM().startRun();
	}

}
