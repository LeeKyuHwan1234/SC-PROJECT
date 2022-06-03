package com.camping.camp.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camping.camp.service.ErrorService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping(value = "/error")
public class ErrorController {

	@Autowired
	ErrorService errorService;
	
	@RequestMapping(value = "correction")
	public void insertErrorCorrection(Model model,HttpServletRequest req,HttpServletResponse resp, @RequestParam HashMap<String, Object> ajaxdata) throws Exception {
		try {
			errorService.insertErrorCorrection(ajaxdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "correction/list")
	public @ResponseBody HashMap<String,Object> getErrorCorrection(Model model, HttpServletRequest req,HttpServletResponse resp) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		try {
			map.put("data", errorService.getErrorCorrection());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "correction/one")
	public @ResponseBody HashMap<String,Object> getErrorCorrectionOne(Model model, HttpServletRequest req,HttpServletResponse resp,@RequestParam HashMap<String, Object> ajaxdata) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		System.out.println(ajaxdata);
		try {
			map.put("data", errorService.getErrorCorrectionOne(ajaxdata));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
}
