package com.camping.camp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camping.camp.dao.AuthDao;
import com.camping.camp.dto.AuthDto;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Service
public class AuthService {
	
	@Autowired
	AuthDao authDao;
	
	public void insertUser(HashMap<String, Object> UserInfo){
		authDao.insertUser(UserInfo);
		
	}
	
	public List<AuthDto> selectUser(HashMap<String, Object> UserInfo){
		List<AuthDto> user = authDao.selectUser(UserInfo);
		return user;
	}
	
	
	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");

			sb.append("&client_id=572d30428a009e93e6498270de9323e9"); // 본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/auth/kakaoLogin"); // 본인이 설정한 주소

			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
//			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
//			System.out.println("response body : " + result);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

			access_Token = jsonObj.get("access_token").toString();
			refresh_Token = jsonObj.get("refresh_token").toString();

//			System.out.println("access_token : " + access_Token);
//			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return access_Token;
	}

	public HashMap<String, Object> getUserInfo(String access_Token) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
//			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
//			System.out.println("response body : " + result);

		    JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(result);
            JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
            JSONObject properties = (JSONObject) obj.get("properties");
//			System.out.println("kakao_account : " + kakao_account);
//			System.out.println("obj : " + obj);
			String userid = obj.get("id").toString();
			String nickname = properties.get("nickname").toString();
			String image = properties.get("thumbnail_image").toString();
            String email  = kakao_account.get("email").toString();
            
			userInfo.put("user_id", userid);
			userInfo.put("user_image", image);
            userInfo.put("user_nickname", nickname); 
            userInfo.put("user_email", email);
			 

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	 public void kakaoLogout(String access_Token) {
	        String reqURL = "https://kapi.kakao.com/v1/user/logout";
	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

	            int responseCode = conn.getResponseCode();
	            //System.out.println("responseCode : " + responseCode);

	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	            String result = "";
	            String line = "";

	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            //System.out.println(result);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	
}
