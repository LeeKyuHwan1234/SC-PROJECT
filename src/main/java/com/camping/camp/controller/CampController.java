package com.camping.camp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.fasterxml.jackson.annotation.JsonProperty;

@Controller 
public class CampController {
	
	@Autowired
	CampService campService;
	
	@RequestMapping(value = "openapi")
	public String getOpenApi(Model model) {
		List<CampDto> cam = campService.getOpenApi();
		model.addAttribute("data",cam);
		return "index";
	}
	
	@RequestMapping(value = "search")
	public String getSearch(Model model) {
		List<CampDto> cam = campService.getDoCategory();
		model.addAttribute("data",cam);
		return "search_keyword";
	}
	
	@RequestMapping(value = "search/keyword")
	@JsonProperty("keyword")
	public @ResponseBody List<CampDto> getSearchKeyword(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam String keyword) throws Exception {
		List<CampDto> cam = null;
		try {
			String encurl = URLDecoder.decode(keyword, "UTF-8");
			cam = campService.getSearchCamp(encurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cam;
	}
	
	@RequestMapping(value = "search/do")
	@JsonProperty("category")
	public @ResponseBody List<CampDto> getSearchDo(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam String category) throws Exception {
		List<CampDto> cam = null;
		try {
			String encurl = URLDecoder.decode(category, "UTF-8");
			cam = campService.getSearchDo(encurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cam;
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
	public @ResponseBody List<CampDto> getSigunguCamp(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String, Object> ajaxdata) throws Exception {
		List<CampDto> cam = null;
		try {
			cam = campService.getSigunguCamp(ajaxdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cam;
	}



}
