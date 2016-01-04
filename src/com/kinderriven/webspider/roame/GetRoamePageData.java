package com.kinderriven.webspider.roame;

import com.kinderriven.webspider.getdata.WebSourceCode;

public class GetRoamePageData {

	
	public GetRoamePageData(){};
	
	
	/*
	 *  根据动漫名字获得网址
	 */
	public String AllImagePage(String name){
		
		String html = "http://www.roame.net/index/"
				+ name 
				+ "/images/index_";
		
		return html;
		
	}
	
	/*
	 *  获得页数
	 */
	public int getPageNumber(String urlStr){

		int number;
		
		for(number = 1;  ;number++){
			
			String url = urlStr + number + ".html"; 
			
			String htmlStr = new WebSourceCode(url, "UTF-8").getSourceCode();
			
			if(htmlStr.isEmpty()){
				number --;
				break;
			}
		}
	
		return number;
	}
	
	public static void main(String[] args) {
		
		System.out.println(new GetRoamePageData().getPageNumber("http://www.roame.net/index/shakugan-no-shana/images/index_"));
		
	}

}
