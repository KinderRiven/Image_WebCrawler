package com.kinderriven.webspider.zero;

import java.io.File;
import java.util.Vector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kinderriven.webspider.getdata.WebSourceCode;

public class ZeroProjectManager extends Thread{

	private String folder;
	private String urlStr;
	
	public ZeroProjectManager(String urlStr, String folder){
		
		this.urlStr = urlStr;
		this.folder = folder;
		
		File file = new File(folder);
		
		if(file.exists() == false)
			file.mkdirs();
		
	}

	Vector<String>vecImage = new Vector<>();
	private int pageNumber;
	
	public void getImage(){
		
		for(int i = 1; i <= pageNumber; i++){
			
			String url = urlStr + i + "/";
			String htmlStr = new WebSourceCode(url, "UTF-8").getSourceCode2();
			
			Document doc = Jsoup.parse(htmlStr);
			
			Element post_contest = doc.getElementById("post_content");
			
			Elements elements = post_contest.getElementsByTag("p");
			
			for(int j = 0; j < elements.size(); j++){
				
				Elements href = elements.get(j).getElementsByTag("a");
				
				for(int k = 0; k < href.size(); k++){
					
					String imageUrl = href.get(k).attr("href");
					
					System.out.println(imageUrl);
					vecImage.add(imageUrl);
				}
			}
		}
		
	}
	
	public void downalImage(){
		
		for(int i = 0; i < vecImage.size(); i++){
			
			new GetZeroImage(folder).downalImage(vecImage.get(i), i + ".jpg");
			
		}
	}
	public void run(){
		
		pageNumber = new GetZeroPageData().getPageNumber2(urlStr);
		
		System.out.println(urlStr + " " + pageNumber + " " + folder);
		
		getImage();
		
		downalImage();
	}
	
	public static void main(String[] args) {
			
		ZeroProjectManager zero = 
				new ZeroProjectManager("http://www.lingyu.me/tp_20767/", 
						"F:/image/Zero/tp_20706/");
		
		zero.start();
	}

}
