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
	
	public List<CampDto> getTotalCamp(){
		List<CampDto> cam = campDao.getTotalCamp();
		return cam;
	}
	
	public List<CampDto> getTotalCampCount(){
		List<CampDto> cam = campDao.getTotalCampCount();
		return cam;
	}
	
	
	public List<CampDto> getPlaceDetail(String encurl){
		List<CampDto> cam = campDao.getPlaceDetail(encurl);
		return cam;
	}
	
	public List<CampDto> getSearchCamp(HashMap<String, Object> ajaxdata) {
		List<CampDto> cam = campDao.getSearchCamp(ajaxdata);
		return cam;
	}

	
	
	public List<CampDto> getSearchCamp2(HashMap<String, Object> ajaxdata) {
		List<CampDto> cam = campDao.getSearchCamp2(ajaxdata);
		return cam;
	}
	public List<CampDto> getSearchRound(HashMap<String, Object> ajaxdata) {
		List<CampDto> cam = campDao.getSearchRound(ajaxdata);
		return cam;
	}

	public List<CampDto> getSearchDo(HashMap<String, String> ajaxdata) {
		List<CampDto> cam = campDao.getSearchDo(ajaxdata);
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
	public List<CampDto> getSearchDoCount(HashMap<String, String> ajaxdata) {
		List<CampDto> cam = campDao.getSearchDoCount(ajaxdata);
		return cam;
	}
	public List<CampDto> getSearchSigunguCount(HashMap<String, Object> ajaxdata) {
		List<CampDto> cam = campDao.getSearchSigunguCount(ajaxdata);
		return cam;
	}
}