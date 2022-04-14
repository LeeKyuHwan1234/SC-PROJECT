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
	
	public List<CampDto> getOpenApi(){
		List<CampDto> mem = campDao.getOpenApi();
		return mem;
	}
	
	
	public List<CampDto> getPlaceDetail(String id){
		List<CampDto> mem = campDao.getPlaceDetail(id);
		return mem;
	}


}