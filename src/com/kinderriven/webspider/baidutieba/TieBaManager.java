package com.kinderriven.webspider.baidutieba;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import com.kinderriven.mysql.mysql.MySQLServer;
import com.kinderriven.mysql.mysql.MySQLSetting;

/*
 *  管理类
 *  	- 连接数据库（从上次爬取的进行再次爬取，更新数据库信息等）
 *  	- 控制多线程进行帖子图片的抓取
 *  
 *  这里控制方式为单贴爬取，一个帖子所有页面同时爬取
 */
public class TieBaManager {

	/*
	 *  Database information
	 */
	
	public final String mysql_ip = MySQLSetting.getMySQLSetting().mysql_ip;
	public final String mysql_database = MySQLSetting.getMySQLSetting().mysql_database;
	public final int mysql_port = MySQLSetting.getMySQLSetting().mysql_port;
	
	
	private static TieBaManager tbManager = new TieBaManager();

	public String folder = MySQLSetting.getMySQLSetting().savePath + "Tieba/";
	
	
	private TieBaManager(){};
	
	
	/* 设置保存目录  */
	
	public void setFolder(String folder){
		
		this.folder = MySQLSetting.getMySQLSetting().savePath + folder;
		System.out.println(this.folder);
		
	}
	
	
	public static TieBaManager getTBM(){
		
		return tbManager;
		
	}
	
	public Vector<TieBaPage>vecPage = new Vector<TieBaPage>();
	
	/* 根据用户选择进行抓取 */
	public void getDataFromUI(Vector<Integer>ID){
		
		for(int i = 0; i < ID.size(); i++){
			
			String sql = "SELECT * FROM Tieba WHERE id = " + ID.get(i);
			
			ResultSet reslut = 
					new MySQLServer(mysql_ip, mysql_database, mysql_port).getResult(
							sql);
			int id, last;
			String url, name;
			Date date;
			try {
				
				while(reslut.next()){
						
					id = Integer.parseInt(reslut.getString(1));
					url = reslut.getString(2);
					name = reslut.getString(3);
					last = Integer.parseInt(reslut.getString(4));
					date = new SimpleDateFormat("yyyy-MM-dd").parse(reslut.getString(5));
					
					System.out.println(id);
					
					vecPage.add(new TieBaPage(id, url, name, last, date));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* 从数据库 贴吧的表中获得需要抓取的信息 */
	public void getData(){
		
		ResultSet reslut = 
				new MySQLServer(mysql_ip, mysql_database, mysql_port).getResult(
						"SELECT * FROM TieBa");
		int id, last;
		String url, name;
		Date date;
		
		try {
			
			while(reslut.next()){
					
				id = Integer.parseInt(reslut.getString(1));
				url = reslut.getString(2);
				name = reslut.getString(3);
				last = Integer.parseInt(reslut.getString(4));
				date = new SimpleDateFormat("yyyy-MM-dd").parse(reslut.getString(5));
				
				vecPage.add(new TieBaPage(id, url, name, last, date));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  更新数据库
	 * 		1.将每个帖子抓取到第几页导入数据库
	 * 		2.更新每条帖子的最后抓取时间
	 */
	public void updateData(){
		
		MySQLServer mysql = new MySQLServer("127.0.0.1", "webspider", 3306);
		String sql1 = "UPDATE TieBa SET last_end_page = ";
		String sql2 = " WHERE id = ";
		
		for(int i = 0; i < vecPage.size(); i++){
			
			int id = vecPage.get(i).getId();
			int last = vecPage.get(i).getLast();
			
			String sql = sql1 + last + sql2 + id;
			System.out.println(sql);
			mysql.updaeData(sql);
			
		}	
		vecPage.clear();
		
	}
	
	/*
	 *  开始运行贴吧抓取模块
	 *  	1.链接数据库 获得数据库抓取的信息
	 *  	2.根据抓取信息，逐个帖子进行抓取
	 *  	3.对于每个贴子以每一页为单位进行多线程抓取
	 */
	public void startRun(){
				
		Vector<TieBaPageManager>vecThread = new Vector<TieBaPageManager>();
		
		for(int i = 0; i < vecPage.size(); i++){
			
			vecThread.clear();
			
			/*	url 帖子链接、start 上次抓到的页码、name 文件夹名称 */
			String url = vecPage.get(i).getUrl();
			int start  = vecPage.get(i).getLast();
			String name = vecPage.get(i).getName();
			
			String path = folder + name + "/";
			/*	获得帖子的总页数 */
			int end = new GetTieBaPageData(url, "UTF-8").getPageNumber();
						
			/*	多线程抓取每页帖子 */
			for(int now = start, j = 0; now <= end; now++, j++){
				
				String _path = path + now + "/";
				System.out.println("Saving... " + url + "?pn=" + now);
				
				vecThread.add(new TieBaPageManager(url + "?pn=" + now, "UTF-8", _path));
				vecThread.elementAt(j).start();
				
			}
			
			for(int j = 0; j < vecThread.size(); j++){
				
				try {
					vecThread.elementAt(j).join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			/*	设置每个帖子最后抓取时间 */
			vecPage.elementAt(i).setLast(end);
			System.out.println("[" + url + "]" + "Finished!");
		}
		/* 更新数据库 */
		updateData();
	}
	
	
	public static void main(String[] args) {
		
		getTBM().getData();
		getTBM().startRun();
	}

}
