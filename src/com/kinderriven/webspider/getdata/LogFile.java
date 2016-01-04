package com.kinderriven.webspider.getdata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* 日志文件   */
public class LogFile {
	
	BufferedWriter bw;
	String logFileName;

	public LogFile(){
		
		logFileName = "log.txt";
	
	};
	
	public void setPath(String name){
		
		logFileName = name;
		
	}
	public void writeLog(String str){
			
		try {
			
			bw = new BufferedWriter(new FileWriter(logFileName, true));
			bw.write(str + "\n");
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
