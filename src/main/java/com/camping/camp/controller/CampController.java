package com.camping.camp.controller;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.camping.camp.vo.Criteria;
import com.camping.camp.vo.PageVO;
import com.fasterxml.jackson.annotation.JsonProperty;

@Controller 
public class CampController {
	
	@Autowired
	CampService campService;
	
	@RequestMapping(value = "camp")
	@JsonProperty("keyword")
	public String getCamp(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam("searchKeyword") String keyword) throws Exception {
			System.out.println(keyword);
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
			model.addAttribute("data",campService.getPlaceDetail(encurl)); 
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
	public @ResponseBody HashMap<String,Object> getSearchDo(Model model, HttpServletRequest req,HttpServletResponse resp,Criteria cri ,@RequestParam String category,@RequestParam HashMap<String,String> ajaxdata) throws Exception {
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
