package com.kinderriven.test.test;

import java.io.IOException;
import java.util.Vector;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class test3 {
	
	void test(){
		
		try {
			
			Response res = Jsoup.connect("http://www.roame.net/ajax.php?a=4098&_nc=1450249979720")
					.data("m","hasaki","p", "75193268")
					.method(Method.POST)
					.execute();
			
			System.out.println(res.cookie("upw"));
			
			
			Response re = Jsoup.connect("http://www.roame.net/")
					.cookies(res.cookies())
					.method(Method.GET)
					.execute();
			
			Document doc = re.parse();
			
			System.out.println(doc);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		
		
	}
	void Login(String string ,String password){
		
		Connection conn = Jsoup.connect("http://www.roame.net/");
		conn.header("User-Agent", 
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");

		try {
			
			Response rs = conn.execute();
			Document d1 = Jsoup.parse(rs.body());
			
			System.out.println(d1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String []argv){
		//new test3().test();
		
		Vector<Integer>vector = new Vector<>();
		
		for(int i = 0; i < 10; i++)
			vector.add(i);
		
		for(int i = 0; i < vector.size(); ){
			
			if(vector.get(i) == 5 || vector.get(i) == 6)
				vector.remove(i);
			else
				i++;
		}
		
		for(int i = 0; i < vector.size(); i++)
			System.out.println(vector.get(i));

	}
}
