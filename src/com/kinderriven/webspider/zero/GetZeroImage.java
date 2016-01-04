package com.kinderriven.webspider.zero;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetZeroImage {

	private static int time_out = 5000;
	private static int read_time = 20000;
	
	String folder;
	
	/*
	 *  传入图片的保存路径
	 */
	public GetZeroImage(String folder){
		
		this.folder = folder;
	}
	
	/*
	 *  传入图片链接以及图片名称进行下载
	 */
	public boolean downalImage(String urlStr, String imageName){
			
		try {
			
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			
			conn.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			
			conn.setRequestProperty("", "");
			conn.setConnectTimeout(time_out);
			
			conn.setReadTimeout(read_time);
			
			File file = new File(folder + imageName);
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(file);
			
			int len;
			while((len = is.read()) != -1){
				
				fos.write(len);
				
			}
			
			fos.close();
			
			return true;
			
		} catch (MalformedURLException e) {
			
			System.out.println("Get image " +  urlStr + " error");
			
			return false;
			
		} catch (IOException e) {
			
			System.out.println("Get image " +  urlStr + " error");
			
			return false;
			
		}
	}

	public void run(){
		
	}
	
	public static void main(String[] args) {
		
	}

}
