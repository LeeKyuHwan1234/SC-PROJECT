package com.camping.camp.controller;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.camping.camp.dto.CampDto;
import com.camping.camp.service.CampService;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;



@Controller 
public class CampController {
	
	@Autowired
	CampService campService;
	
	@RequestMapping(value = "camp")
	@JsonProperty("keyword")
	public String getCamp(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam("searchKeyword") String keyword) throws Exception {
			String encurl = URLDecoder.decode(keyword, "UTF-8");
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("keyword", encurl);
			model.addAttribute("data",map);
			return "camp";
	}
	
	@RequestMapping(value = "place")
	@JsonProperty("keyword")
	public String getPlaceDetail(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam("contentid") String contentid) throws Exception {
		    
		String encurl = URLDecoder.decode(contentid, "UTF-8");
		String address = "https://www.gocamping.or.kr/bsite/camp/info/read.do?c_no="+contentid;
		Document rawData = Jsoup.connect(address).timeout(5000).get();
		Elements blogOption = rawData.select("div.box_photo > #gallery > a ");
		String a = "";
		ArrayList<String> arrList = new ArrayList<String>();
		for (Element option : blogOption) {
			a = option.select("img").attr("src");
			arrList.add(a);
		}
		if (arrList.isEmpty()) {
			model.addAttribute("gall","xxxxx");
		} else {
			model.addAttribute("gall",arrList);
		}
		//캠핑장 데이터 호출
		List<CampDto> campdetail = campService.getPlaceDetail(encurl);
		//날씨
		StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/weather"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(campdetail.get(0).getMapy(), "UTF-8")); /*위도 , 37.9330692*/
        urlBuilder.append("&" + URLEncoder.encode("lon","UTF-8") + "=" + URLEncoder.encode(campdetail.get(0).getMapx(), "UTF-8")); /*경도, 128.6740240*/
        urlBuilder.append("&" + URLEncoder.encode("appid","UTF-8") + "=" + URLEncoder.encode("d34aeb285b6205f0378e01aff69b323c", "UTF-8")); /*API KEY*/
        urlBuilder.append("&" + URLEncoder.encode("lang","UTF-8") + "=" + URLEncoder.encode("kr", "UTF-8")); /*API KEY*/
      
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //상태 코드 확인
        //System.out.println("Response code: " + conn.getResponseCode()); 
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
       while ((line = rd.readLine()) != null) {
            sb.append(line);
     	}
       	String c = sb.toString();
       	JSONParser jsonParser = new JSONParser();
       	JSONObject jsonObj = (JSONObject)jsonParser.parse(c);
       	System.out.println(jsonObj);
        rd.close();
        conn.disconnect();
        model.addAttribute("weather", jsonObj);
		model.addAttribute("data",campdetail);
		return "search_keyword_detail";
	}
	
	@RequestMapping(value = "search")
	public String getSearch(Model model) {
		HashMap<String, Object> map = new HashMap<String,Object>(); 
		map.put("cate", campService.getDoCategory());
		map.put("camplist",campService.getTotalCamp());
		map.put("count",campService.getTotalCampCount());
		model.addAttribute("data",map);
		return "search_keyword";
	}
	
	
	//--------------------------------------------------- api -----------------------------------------------------
	
	
	@RequestMapping(value = "search/display")
	@JsonProperty("keyword")
	public @ResponseBody HashMap<String,Object> getSearchKeyword(HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String,Object> ajaxdata) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("data",campService.getSearchCamp(ajaxdata)); 
		return map;
	}
	
	@RequestMapping(value = "search/do")
	@JsonProperty("category")
	public @ResponseBody HashMap<String,Object> getSearchDo(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam String category,@RequestParam HashMap<String,String> ajaxdata) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
	   try {
			String encurl = URLDecoder.decode(category, "UTF-8");
			map.put("camplist",campService.getSearchDo(ajaxdata));
			map.put("count",campService.getSearchDoCount(ajaxdata));
			map.put("campcategory", campService.getSigunguCategory(encurl));
	   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}	
	@RequestMapping(value = "search/sigungu")
	@JsonProperty("category")
	public @ResponseBody List<CampDto> getSearchSigungu(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam String category) throws Exception {
		List<CampDto> cam = null;
		try {
			String encurl = URLDecoder.decode(category, "UTF-8");
			cam = campService.getSearchSigungu(encurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cam;
	}
	
	@RequestMapping(value = "search/sigungucategory")
	@JsonProperty("category")
	public @ResponseBody List<CampDto> getSigunguCategory(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam String category) throws Exception {
		List<CampDto> cam = null;
		try {
			String encurl = URLDecoder.decode(category, "UTF-8");
			cam = campService.getSigunguCategory(encurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cam;
	}
	
	@RequestMapping(value = "search/sigungucamp")
	@JsonProperty("category")
	public @ResponseBody HashMap<String,Object> getSigunguCamp(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String, Object> ajaxdata) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		try {
			map.put("count", campService.getSearchSigunguCount(ajaxdata));
			map.put("camplist",campService.getSigunguCamp(ajaxdata));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}



}
