package com.camping.camp.service;

import java.util.List;

import com.camping.camp.dto.CampDto;

public interface CampService {
	List<CampDto> getOpenApi();
	List<CampDto> getPlaceDetail(String id);
}
