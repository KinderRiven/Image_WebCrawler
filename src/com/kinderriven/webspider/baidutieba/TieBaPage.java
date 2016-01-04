package com.kinderriven.webspider.baidutieba;

import java.util.Date;

/*
 * 	用于统一规划数据库的数据格式
 */
public class TieBaPage {

	int id, last;
	private String url;
	private String name;
	private Date date;
	
	public  TieBaPage(int id, String url, String name, int last, Date date){
		
		this.id = id;
		this.url = url;
		this.name = name;
		this.last = last;
		this.date = date;
		
	}
	
	void disPlay(){
		
		System.out.println("[ID]: " + id + " [URL]: " + url
				+ " [NAME]: " + name + " [LAST]: " + last + " [DATE]: " + date);
		
	}
	
	public void loadData(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static void main(String[] args) {

	}

}
