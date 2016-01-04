package com.kinderriven.webspider.baidutieba;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 *  爬虫核心
 *  	- 基于多线程进行图片下载
 *  	- 按页进行爬取
 *  
 *  构造类 参数分别为 [爬取网页的地址、网页编码、保存的文件夹]
 *  downalImage 根据传入图片的URL进行下载
 *  getImageUrl 将该页的所有图片链接获取	
 */
public class GetTieBaImage extends Thread{

	private static int time_out = 5000;
	
	String folder;
	
	/*
	 *  传入图片的保存路径
	 */
	public GetTieBaImage(String folder){
		
		this.folder = folder;
	}
	
	/*
	 *  传入图片链接以及图片名称进行下载
	 */
	public void downalImage(String urlStr, String imageName){
			
		try {
			
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			
			conn.setConnectTimeout(time_out);
			
			
			File file = new File(folder + imageName);
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(file);
			
			int len;
			while((len = is.read()) != -1){
				
				fos.write(len);
				
			}
			
			fos.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		
	}
	
	public static void main(String[] args) {
		
	}


}
