package com.kinderriven.webspider.roame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 *  根据传入的图片信息以及cookie进行图片的保存
 */
public class GetRoameImage extends Thread{

	private String imageUrl;
	private String cookie;
	private String folder;

	public String getCookie(){
		
		return cookie;
		
	}
	/*
	 * 	根据传入图片的url
	 * 	以及带入cookie保存图片
	 * 	路径为folder
	 */ 
	public GetRoameImage(String imageUrl, String cookie, String folder){
		
		this.cookie = cookie;
		this.imageUrl = imageUrl;
		this.folder = folder;
		
	}
	
	/*
	 *	开始进行图片的下载
	 *		1.调用前确保imageUrl正确
	 *		2.确保cookie没有过期
	 *		3.确保文件夹路径正确
	 */ 
	public void downalRoameImage(){
		
		try {
			
			URL url = new URL(imageUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			httpConn.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			
			httpConn.setRequestProperty("Cookie", cookie);
			
			httpConn.setConnectTimeout(10000);
			
//			httpConn.setRequestProperty("Accept", "image/webp,*/*;q=0.8");
//			
//			httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
//			
//			httpConn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
//			
//			httpConn.setRequestProperty("Cache-Control" ,"max-age=0");
//			
//			httpConn.setRequestProperty("Connection","keep-alive");
//						
//			httpConn.setRequestProperty("Host",	"ios.roame.net");
			
			InputStream is = httpConn.getInputStream();
				
			File file = new File(folder);
			FileOutputStream fos = new FileOutputStream(file);
			
			int len;
			while((len = is.read()) != -1){
				
				fos.write(len);
				
			}
			
			fos.close();
			
			
		} catch (MalformedURLException e) {
		
			//System.out.println("[ERROR]" + cookie + " " + imageUrl);
			
		} catch (IOException e) {
			
			System.out.println("[ERROR]" + cookie + " " + imageUrl);
			
		}
		
	}
	
	
	public void run(){
		
		downalRoameImage();
		
	}
	
	
	public static void main(String[] args) {
		
		String urlStr1 = "http://ios.roame.net/files/Ypt@NG2cVh6GDcS8oYso6iKZuFam@7Kjxk@6FH2WZmL8LDLDZl557Y0GtSk/ROAME_266410_0CC0E446.jpg";
		String urlStr2 = "http://ios.roame.net/files/JkC3nY0qt564GQb4xKTaCd2ibi0HgeCT0lUcY5myG8Npuk@@x5BLy56KaL/ROAME_266407_8ABC88CF.jpg";
		
		String cookie1 = "uid=199579; cmd=Jup1Uvia4ekJ0mpu2t%40cVkg9TxUZk9ZeZ; upw=ee46755e50d993f25c83cd53baf3ad9b;";
		String cookie2 = "uid=206578; cmd=Jwd0vjJuBIoj7senGXs54SgBQqzuPus8f; upw=ee46755e50d993f25c83cd53baf3ad9b;";
		
		GetRoameImage thread1 = new GetRoameImage(urlStr1, cookie1, "F:/image/1.jpg");
		GetRoameImage thread2 = new GetRoameImage(urlStr2, cookie2, "F:/image/2.jpg");
		
		try {
			
			thread1.start();
			thread2.start();
			thread1.join();
			thread2.join();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}


}
