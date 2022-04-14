package com.camping.camp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camping.camp.dto.CampDto;
import com.camping.camp.service.CampService;

@Controller 
public class CampController {
	
	@Autowired
	CampService campService;
	
	@RequestMapping(value = "/openapi")
	public String getOpenApi(Model model) {
		List<CampDto> cam = campService.getOpenApi();
		model.addAttribute("data",cam);
		return "index";
	}
	
	@RequestMapping(value = "camp")
	public String getFruit(Model model) {
	return "camp";
	}
	
	@RequestMapping(value = "place/{id}")
	public String getFruit(Model model, @PathVariable String id) {
		List<CampDto> cam = campService.getPlaceDetail(id);
		model.addAttribute("data",cam);
		return "campdetail";
	}

}
