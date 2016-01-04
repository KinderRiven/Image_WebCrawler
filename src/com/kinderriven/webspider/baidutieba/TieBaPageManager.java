package com.kinderriven.webspider.baidutieba;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import com.kinderriven.mysql.mysql.MySQLServer;
import com.kinderriven.webspider.getdata.WebSourceCode;

public class TieBaPageManager extends Thread{

	private String htmlStr;
	
	private static String image_suffix = "http://imgsrc.baidu.com/forum/pic/item/";
	
	private static String image_judge 	= "http://imgsrc.baidu.com/forum/";
	
	MySQLServer mysqlServer;
	
	String urlStr;
	
	String folder;
	
	/*
	 * 以页面为单位进行图片的提取
	 * 	1. 页面链接
	 * 	2. 页面编码
	 * 	3. 保存的文件夹
	 */
	public TieBaPageManager(String urlStr, String encoding, String folder){
		
		
		htmlStr =  new WebSourceCode(urlStr, encoding).getSourceCode();
		
		this.urlStr = urlStr;
		this.folder = folder;
		
		File file = new File(this.folder);
		
		if(!file.exists()){
			
			file.mkdirs();
			
		}
		
		/*
		 *  建立数据库连接
		 */
		mysqlServer = new MySQLServer(
				TieBaManager.getTBM().mysql_ip,
				TieBaManager.getTBM().mysql_database,
				TieBaManager.getTBM().mysql_port);
		
	}
	
	public boolean imageExist(String id){
		
		String sql = "SELECT * FROM tieba_image WHERE id = '" + id + "'";
		
		ResultSet result = mysqlServer.getResult(sql);
		
		try {
			if(result.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void insertImage(String id, String belong){
		
		mysqlServer.updaeData("INSERT INTO tieba_image(id, belong) VALUES('"
				+ id
				+ "',"
				+ "'"
				+ belong
				+ "')");
	}
	public void run(){
		
		Elements elements = Jsoup.parse(htmlStr).getElementsByClass("wrap1");
		
		for(int i = 0; i < elements.size(); i++){
			
			Elements photo = elements.get(i).getElementsByClass("BDE_Image");
			
			for(int j = 0; j < photo.size(); j++){
				
				String id = photo.get(j).attr("src").toString();
				
				if(id.startsWith(image_judge) == false)
					continue;
				
			    Pattern r = Pattern.compile("/(.*jpg)");
			    Matcher m = r.matcher(id);
				while(m.find()){
					
					id = m.group(1);
					m = r.matcher(id);
				}
				
				String image = image_suffix + id;
				
				/* 如果不存在 */
				if(imageExist(id) == false){
					
					System.out.println("Downal Image[" + id + "]");
					new GetTieBaImage(folder).downalImage(image, id);
					
					if(imageExist(id) == false)
						insertImage(id, urlStr);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		
	}

}
