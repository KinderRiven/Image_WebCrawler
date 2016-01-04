package com.kinderriven.webspider.zero;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.kinderriven.webspider.getdata.WebSourceCode;

public class GetZeroPageData {

	static String url = "http://www.lingyu.me/";
	
	public GetZeroPageData(){};
	
	public Vector<String> getTag(){
		
		Vector<String> vec = new Vector<>();
		
		String htmlStr = new WebSourceCode(url, "UTF-8").getSourceCode2();
		
		if(htmlStr != null){
		
			Document doc = Jsoup.parse(htmlStr);
			
			Elements elements = doc.getElementsByClass("tagcloud");
			
			if(elements.size() > 0){
				String tagStr = elements.get(0).toString();
		
				Pattern pattern = Pattern.compile("http://www.lingyu.me/(.+/)\"");
				
				Matcher m = pattern.matcher(tagStr);
				
				while(m.find()){
					
					System.out.println(m.group(1));
					vec.add(m.group(1));
					
				}
			}
		}
		return vec;
	}
	/*
	 *  获得专题网页的页码
	 */
	public int getPageNumber(String urlStr){

		int number;
		
		for(number = 1;  ;number++){
			
			String url = urlStr + number + "/"; 
			
			String htmlStr = new WebSourceCode(url, "UTF-8").getSourceCode2();
			
			if(htmlStr.isEmpty()){
				number --;
				break;
			}
		}
	
		return number;
	}
	
	/*
	 *  获得图片一共有几页
	 */
	public int getPageNumber2(String urlStr){
		
		int number = 0;
		
		System.out.println(urlStr);
		
		String htmlStr = new WebSourceCode(urlStr, "UTF-8").getSourceCode2();

		Document doc = Jsoup.parse(htmlStr);
		
		Elements elements = doc.getElementsByClass("pagelist");
		
		if(elements.size() > 0)
			number = elements.get(0).getElementsByTag("a").size();
		
		return number + 1;
	}
	
	public static void main(String[] args) {
		
		new GetZeroPageData().getTag();
	}

}
