package com.camping.camp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camping.camp.dto.CampDto;
import com.camping.camp.service.CampService;

@Controller 
public class CampController {
	
	@Autowired
	CampService campService;
	
	@RequestMapping(value = "/123")
	public String getManyTodoList123(Model model) {
		List<CampDto> cam = campService.getManyTodoList();
		model.addAttribute("data",cam);
		return "index";
	}
	
	@RequestMapping(value = "camp")
	public String getFruit(Model model) {
	Map<String, String> campingmap = new HashMap<String, String>();
	campingmap.put("fruit1", "apple");
	campingmap.put("fruit2", "banana");
	campingmap.put("fruit3", "orange");
	model.addAttribute("fruit", campingmap);
	return "camp";
	}

}
