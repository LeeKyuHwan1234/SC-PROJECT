package com.camping.camp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camping.camp.dao.CampDao;
import com.camping.camp.dto.CampDto;

@Service
public class CampServiceImpl implements CampService {

	@Autowired
	CampDao campDao;
	
	public List<CampDto> getManyTodoList(){
		List<CampDto> mem = campDao.getManyTodoList();
		return mem;
	}



}