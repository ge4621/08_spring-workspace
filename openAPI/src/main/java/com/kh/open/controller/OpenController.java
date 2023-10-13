package com.kh.open.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OpenController {

	private static final String serviceKey = "xLzLwM%2Fkb2A7cbPFlb4bkwQIV78NIwVvTX2wQ6%2FYD0XSxKGwqdEj6oqvd73jeTBU%2F8IwEXfImCwHLwawa75phQ%3D%3D";
	
	//XML
	@ResponseBody
	@RequestMapping(value="check.do", produces = "text/xml; charset=UTF-8")
	public String ajaxMethod() throws IOException {
		
		String url = "https://apis.data.go.kr/1741000/EarthquakeIndoors2/getEarthquakeIndoors1List";  //내가 가지고 온 공공데이터 url 
		
		url += "?serviceKey=" + serviceKey;  //서비스키
		url += "&type=xml";	//타입
		url +="&numOfRows=20";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText="";
		String line;  //한줄씩 읽어 내려간다.??
		
		while((line=br.readLine())!=null) {
			responseText +=line;
		}
		
		br.close();
		urlConnection.disconnect();
		
		return responseText;
		
	}
	@ResponseBody
	@RequestMapping(value="ajax.bo",produces = "application/json; charset=UTF-8")
	public String ajaxMethod2() throws IOException {
		
		String url = "https://apis.data.go.kr/1741000/EarthquakeIndoors2/getEarthquakeIndoors1List";
		
		url += "?serviceKey=" + serviceKey;
		url += "&type=json";
		url +="&numOfRows=30";
		//System.out.println(url);
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText1="";
		String line;
		
		while((line=br.readLine())!=null) {
			responseText1 += line;
		}
		br.close();
		urlConnection.disconnect();
		System.out.println(responseText1);
		
		return responseText1;
	}
	
	
}
