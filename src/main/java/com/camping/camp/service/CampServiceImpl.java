package com.camping.camp.service;

import java.util.HashMap;
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
		List<CampDto> cam = campDao.getOpenApi();
		return cam;
	}
	
	
	public List<CampDto> getPlaceDetail(String id){
		List<CampDto> cam = campDao.getPlaceDetail(id);
		return cam;
	}
	
	public List<CampDto> getSearchCamp(String encurl) {
		List<CampDto> cam = campDao.getSearchCamp(encurl);
		return cam;
	}

	public List<CampDto> getSearchDo(String encurl) {
		List<CampDto> cam = campDao.getSearchDo(encurl);
		return cam;
	}
	
	public List<CampDto> getSearchSigungu(String encurl) {
		List<CampDto> cam = campDao.getSearchSigungu(encurl);
		return cam;
	}
	
	public List<CampDto> getSigunguCategory(String encurl) {
		List<CampDto> cam = campDao.getSigunguCategory(encurl);
		return cam;
	}
	
	public List<CampDto> getDoCategory() {
		List<CampDto> cam = campDao.getDoCategory();
		return cam;
	}
	public List<CampDto> getSigunguCamp(HashMap<String, Object> ajaxdata) {
		List<CampDto> cam = campDao.getSigunguCamp(ajaxdata);
		return cam;
	}
	
}