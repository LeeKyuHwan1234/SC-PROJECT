package com.camping.camp.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.camping.camp.dto.AuthDto;
import com.camping.camp.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService as;
	
	@RequestMapping(value = "/kakaoLogin", method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code, HttpSession session) throws Exception {
		//카카오에서 받은 
		String access_Token = as.getAccessToken(code);
		HashMap<String, Object> UserInfo = as.getUserInfo(access_Token);
		List<AuthDto> list = as.selectUser(UserInfo);
		//유저 정보가 없을 때만 정보 추가
		System.out.println(list.size());
		if(list.isEmpty()) {
			as.insertUser(UserInfo);
			session.setAttribute("user_nickname", UserInfo.get("user_nickname"));
			session.setAttribute("user_role", 0);
			session.setAttribute("user_image", UserInfo.get("user_image"));
			session.setAttribute("access_Token",access_Token);
		}
		//세션에 별명,이미지, 액세스 토큰 추가
		else {	
			session.setAttribute("user_nickname", UserInfo.get("user_nickname"));
			session.setAttribute("user_role", list.get(0).getUser_role());
			session.setAttribute("user_image", UserInfo.get("user_image"));
			session.setAttribute("access_Token",access_Token);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        String access_Token = (String)session.getAttribute("access_Token");
        //카카오 로그아웃
        as.kakaoLogout(access_Token);
        // 세션제거
        session.removeAttribute("access_Token");
        session.removeAttribute("user_nickname");
        session.removeAttribute("user_role");
        session.removeAttribute("user_image");
        return "redirect:/";
    }
}
