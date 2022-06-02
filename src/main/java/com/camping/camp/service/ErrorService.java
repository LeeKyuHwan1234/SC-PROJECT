package com.camping.camp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camping.camp.dao.ErrorDao;
import com.camping.camp.dto.ErrorDto;

@Service
public class ErrorService {
	@Autowired
	ErrorDao errorDao;
	
	
	public List<ErrorDto> getErrorCorrection() {
		List<ErrorDto> map = errorDao.getErrorCorrection();
		return map;
	}
	public void insertErrorCorrection(HashMap<String, Object> ajaxdata) {
		errorDao.insertErrorCorrection(ajaxdata);
	}
}
