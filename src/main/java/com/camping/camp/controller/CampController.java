package com.camping.camp.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camping.camp.dto.CampDto;
import com.camping.camp.service.CampService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;



import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;



@Controller 
@Slf4j
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
			System.out.println(model);
			return "camp";
	}
	
	@RequestMapping(value = "camp2")
	public String getCamp1(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam(value="Listx1") String Listx1,@RequestParam(value="Listx2") String Listx2
			,@RequestParam(value="Listx3") String Listx3,@RequestParam(value="Listx4") String Listx4,@RequestParam(value="Listx5") String Listx5,@RequestParam(value="Listx6") String Listx6,@RequestParam(value="Listx7") String Listx7) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
	
		Gson gson = new Gson();
		List<Map<String, Object>> list1 = (List<Map<String, Object>>) gson.fromJson(Listx1, List.class);
		List<Map<String, Object>> list2 = (List<Map<String, Object>>) gson.fromJson(Listx2, List.class);
		List<Map<String, Object>> list3 = (List<Map<String, Object>>) gson.fromJson(Listx3, List.class);
		List<Map<String, Object>> list4 = (List<Map<String, Object>>) gson.fromJson(Listx4, List.class);
		List<Map<String, Object>> list5 = (List<Map<String, Object>>) gson.fromJson(Listx5, List.class);
		List<Map<String, Object>> list6 = (List<Map<String, Object>>) gson.fromJson(Listx6, List.class);
		List<Map<String, Object>> list7 = (List<Map<String, Object>>) gson.fromJson(Listx7, List.class);
		
		List<String> doList = list1.stream().map(item -> (String)item.get("doid")).toList();
		List<String> lcList = list2.stream().map(item -> (String)item.get("lcid")).toList();
		List<String> inList = list3.stream().map(item -> (String)item.get("inid")).toList();
		List<String> sbList = list4.stream().map(item -> (String)item.get("sbid")).toList();
		List<String> siList = list5.stream().map(item -> (String)item.get("siid")).toList();
		List<String> faList = list6.stream().map(item -> (String)item.get("faid")).toList();
		List<String> boList = list7.stream().map(item -> (String)item.get("boid")).toList();
		
		map.put("doList",doList);
		map.put("lcList",lcList);
		map.put("inList",inList);
		map.put("sbList",sbList);
		map.put("siList",siList);
		map.put("faList",faList);
		map.put("boList",boList);
		System.out.println(">>>>>>>>"+map);
		model.addAttribute("data",campService.getSearchCamp2(map));
		model.addAttribute("checkdata",map);

		return "camp2";
	}
	
	
	@RequestMapping(value = "place")
	@JsonProperty("keyword")
	public String getPlaceDetail(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam("contentid") String contentid) throws Exception {
		//장소 아이디
		String encurl = URLDecoder.decode(contentid, "UTF-8");
		//캠핑장 갤러리 사진 크롤링
		String address = "https://www.gocamping.or.kr/bsite/camp/info/read.do?c_no="+contentid;
		Document rawData = Jsoup.connect(address).timeout(5000).get();
		Elements rawOption = rawData.select("div.box_photo > #gallery > a ");
		String a = "";
		ArrayList<String> arrList = new ArrayList<String>();
		for (Element option : rawOption) {
			a = option.select("img").attr("src");
			arrList.add(a);
		}
		//크롤링할 데이터가 있는지 확인
		if (arrList.isEmpty()) {
			model.addAttribute("gall","xxxxx");
		} else {
			model.addAttribute("gall",arrList);
		}
		//캠핑장 데이터 호출
		List<CampDto> campdetail = campService.getPlaceDetail(encurl);
		//날씨 api
		StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/weather"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("lat","UTF-8") + "=" + URLEncoder.encode(campdetail.get(0).getMapy(), "UTF-8")); /*위도 , 37.9330692*/
        urlBuilder.append("&" + URLEncoder.encode("lon","UTF-8") + "=" + URLEncoder.encode(campdetail.get(0).getMapx(), "UTF-8")); /*경도, 128.6740240*/
        urlBuilder.append("&" + URLEncoder.encode("appid","UTF-8") + "=" + URLEncoder.encode("d34aeb285b6205f0378e01aff69b323c", "UTF-8")); /*API KEY*/
        //urlBuilder.append("&" + URLEncoder.encode("lang","UTF-8") + "=" + URLEncoder.encode("kr", "UTF-8")); /*한글 지원*/
      
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //상태 코드 확인
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
        rd.close();
        conn.disconnect();
        
        //네이버 뷰 크롤링 부분
        int i = 1;
		String blog_title = "";
		String blog_content = "";
		String blog_image = "";
		String blog_writer = "";
		String blog_writer_image = "";
		String blog_writer_date = "";
		String blog_url = "";

		ArrayList<Object> blog_title_list = new ArrayList<Object>();
		ArrayList<Object> blog_content_list = new ArrayList<Object>();
		ArrayList<Object> blog_image_list = new ArrayList<Object>();
		ArrayList<Object> blog_writer_list = new ArrayList<Object>();
		ArrayList<Object> blog_writer_image_list = new ArrayList<Object>();
		ArrayList<Object> blog_writer_date_list = new ArrayList<Object>();
		ArrayList<Object> blog_url_list = new ArrayList<Object>();
		
		String blogaddress = "https://search.naver.com/search.naver?where=view&query=";
		blogaddress += campdetail.get(0).getFacltnm();
		Document blogData = Jsoup.connect(blogaddress).timeout(5000).get();
		HashMap<String,Object> blogdata = new HashMap<String,Object>();
		for(i =1; i<31;i++) {
			Elements blogOption = blogData.select("#main_pack > section > div > div._list > panel-list > div > more-contents > div > ul > li:nth-child("+i+")");

			
			blog_title = blogOption.select("div > div > a.api_txt_lines").text();
			blog_content = blogOption.select("div > div > div > div.total_dsc_wrap > a > div.api_txt_lines").text();
			blog_image = blogOption.select("div.total_wrap.api_ani_send > a > span > img").attr("src");
			blog_writer_image = blogOption.select("div.total_wrap.api_ani_send > div > div.total_info > div.total_sub > a > span > img").attr("src");
			blog_writer = blogOption.select("div.total_wrap.api_ani_send > div > div.total_info > div.total_sub > span > span > span.elss.etc_dsc_inner > a").text();
			blog_writer_date = blogOption.select("div.total_wrap.api_ani_send > div > div.total_info > div.total_sub > span > span > span.etc_dsc_area > span.sub_time.sub_txt").text();
			blog_url = blogOption.select("div.total_wrap.api_ani_send > div.total_area >  a").attr("href");
			

			if(blog_content != null && !blog_content.equals("") && blog_image != null && !blog_image.equals("")) {
				blog_title_list.add(blog_title);
				blog_content_list.add(blog_content);
				blog_image_list.add(blog_image);
				blog_writer_list.add(blog_writer);
				blog_writer_image_list.add(blog_writer_image);
				blog_writer_date_list.add(blog_writer_date);
				blog_url_list.add(blog_url);
				
				blogdata.put("blog_title",blog_title_list);
				blogdata.put("blog_content",blog_content_list);
				blogdata.put("blog_image",blog_image_list);
				blogdata.put("blog_writer",blog_writer_list);
				blogdata.put("blog_writer_image",blog_writer_image_list);
				blogdata.put("blog_writer_date",blog_writer_date_list);
				blogdata.put("blog_url", blog_url_list);
			}
		}
		model.addAttribute("blog", blogdata);
        model.addAttribute("weather", jsonObj);
		model.addAttribute("data",campdetail);
		return "search_keyword_detail";
	}
	
	@RequestMapping(value = "search")
	public String getSearch() {
		return "search_keyword";
	}
	
	@RequestMapping(value = "koreamap")
	public String getKoreaMap(Model model) {
		HashMap<String, Object> map = new HashMap<String,Object>(); 
		map.put("cate", campService.getDoCategory());
		map.put("camplist",campService.getTotalCamp());
		map.put("count",campService.getTotalCampCount());
		model.addAttribute("data",map);
		return "koreaMap";
	}

	
	//--------------------------------------------------- api -----------------------------------------------------
	
	@RequestMapping(value = "search/round")
	@JsonProperty("keyword")
	public @ResponseBody HashMap<String,Object> getSearchRound(HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String,Object> ajaxdata) throws Exception {
		System.out.println(ajaxdata);
       	HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("data",campService.getSearchRound(ajaxdata)); 
		return map;
	}
	
	@RequestMapping(value = "search/display")
	@JsonProperty("keyword")
	public @ResponseBody HashMap<String,Object> getSearchKeyword(HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String,Object> ajaxdata) throws Exception {
		System.out.println(ajaxdata);
       	HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("data",campService.getSearchCamp(ajaxdata)); 
		return map;
	}
	
	@RequestMapping(value = "search/display2")
	@JsonProperty("keyword")
	public @ResponseBody HashMap<String,Object> getSearchKeyword2(HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String,Object> ajaxdata) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("data",campService.getSearchCamp2(ajaxdata)); 
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
