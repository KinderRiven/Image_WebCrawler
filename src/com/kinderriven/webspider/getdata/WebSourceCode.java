package com.kinderriven.webspider.getdata;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class WebSourceCode {

	
	private String urlStr;
	private String encoding;
	
	private static int time_out = 5000;
	
	private static final int  BUFFER_SIZE = 1024;
	
	public WebSourceCode(String urlStr, String encoding){
		
		this.urlStr = urlStr;
		this.encoding = encoding;
	}
	
	public WebSourceCode(){
		
	}
	
	/*
	 *  正常获得网页源代码（POST 不带cookie）
	 *  	1.如果不响应或者产生问题返回的串为空
	 *  	2.调用前需要利用2个参数的构造进行构造
	 */
	public String getSourceCode(){
		
		StringBuffer sb = new StringBuffer();
		
		try {
			
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			
			conn.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(time_out);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			if(conn.getResponseCode() == 200){
				
				InputStream is = conn.getInputStream();
				int len = 0;
				byte[] buf = new byte[BUFFER_SIZE];
			
				while((len = is.read(buf)) != -1){
					
					sb.append(new String(buf, 0, len, encoding));
					
				}
				
				is.close();
			}
			else{
				
				//System.out.println(urlStr + " connect error! " + conn.getResponseCode());
				
			}
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return sb.toString();
	}
	
	/*
	 *  正常获得网页源代码（GET 不带cookie）
	 *  	1.如果不响应或者产生问题返回的串为空
	 *  	2.调用前需要利用2个参数的构造进行构造
	 */
	public String getSourceCode2(){
		
		StringBuffer sb = new StringBuffer();
		
		try {
			
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			
			conn.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(time_out);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			if(conn.getResponseCode() == 200){
				
				InputStream is = conn.getInputStream();
				int len = 0;
				byte[] buf = new byte[BUFFER_SIZE];
			
				while((len = is.read(buf)) != -1){
					
					sb.append(new String(buf, 0, len, encoding));
					
				}
				
				is.close();
			}
			else{
				
				//System.out.println(urlStr + " connect error! " + conn.getResponseCode());
				
			}
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return sb.toString();
	}
	/*
	 * 	获得网页源代码（带cookie以及代理）
	 * 		1.如果不响应或者产生问题返回的串为空
	 * 		2.调用前需要利用2个参数的构造进行构造
	 */
	public String getSourceWithCookie(String cookie){
		
		StringBuffer sb = new StringBuffer();
		
		try {
			URL url;
			url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			httpConn.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			
			httpConn.setRequestProperty("Cookie", cookie);
			
			httpConn.setConnectTimeout(time_out);
			
			InputStream is = httpConn.getInputStream();
			
			int len = 0;
			byte[] buf = new byte[BUFFER_SIZE];
		
			while((len = is.read(buf)) != -1){
				
				sb.append(new String(buf, 0, len, encoding));
				
			}
			
			is.close();
			
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/*
	 * 	获得Roame网站的cookie
	 * 		1.传入用户名以及密码
	 * 		2.cookie传入连接目前写死为
	 * 			[http://www.roame.net/ajax.php?a=4098&_nc=1450249979720]		
	 */
	public String getRoameCookie(String username, String password){
		
		
		String cookie = "";
		
		try {
			
			Response res = Jsoup.connect("http://www.roame.net/ajax.php?a=4098&_nc=1450249979720")
					.data("m", username,"p", password)
					.method(Method.POST)
					.execute();
			
			cookie += "uid=" + res.cookie("uid") + "; ";
			cookie += "cmd=" + res.cookie("cmd") + "; ";
			cookie += "upw=" + res.cookie("upw") + ";";
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cookie;
	}
	
	
	public static void main(String[] args) {
		
		
		String cookie = new WebSourceCode().getRoameCookie("baiyuan", "125506");
		
		System.out.println(cookie);
		
//		String htmlStr = new WebSourceCode("http://www.roame.net/index/11eyes", "UTF-8")
//				.getSourceWithCookie(cookie);
//		
//		System.out.println(htmlStr);
		
	}
	

}
