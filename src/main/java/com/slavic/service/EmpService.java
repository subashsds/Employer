package com.slavic.service;


import java.util.HashMap;

import javax.ws.rs.BadRequestException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.slavic.dto.ErrorMsg;
import com.slavic.dto.Response;
import com.slavic.dto.req.Login;

import javassist.tools.web.BadHttpRequest;

@Service
public class EmpService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestTemplate restTemplate;

	public Response<?> login(Login login){

		Response<?> res = new Response<String>();
		final HashMap<String, String> map= new HashMap<String, String>();
		map.put("username", login.getUsername());

		HttpEntity<HashMap<String, String>> request = new HttpEntity<HashMap<String, String>>(map);
		
		try {
			
			String baseUrl = "http://localhost:8083/auth/getAuthToken";
			res = restTemplate.exchange(baseUrl, HttpMethod.POST, request,Response.class).getBody();

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if("success".equals(res.getMessage())) {	
			LOG.info(" token generated successfully !");
			//sendEmail("email sent successfuly !!!");
		
			res.setMessage("success");
			String baseUrl = "http://localhost:8085/email/sendEmail";
			String response1 = (String) restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class).getBody();

			String baseUrlMessage = "http://localhost:8086/message/send-otp-message";
			String response2 = (String) restTemplate.exchange(baseUrlMessage, HttpMethod.POST, request, String.class).getBody();

			if("failed".equals(response1)|| "failed".equals(response2) ) {

				res.setMessage("failed");
				res.setError(error(606,"Error"));
				return res;
			}

		}else {
			res.setMessage("failed");
			res.setError(error(400,"Error"));
			throw new BadRequestException("Request is null");

		}
		res.setError(error(0,"success"));
		return res;
	}



//	public String sendEmail(String message) {
//		String emailId = "test@gmail.com";
//
//		HttpHeaders headers =new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> requestEntity = new HttpEntity<String>(emailId,headers);
//
//		String baseUrl = "http://localhost:8087/email/sendEmail";
//		String response = restTemplate.exchange(baseUrl, HttpMethod.POST,null, String.class).getBody();
//		LOG.info("The response recieved by method1 is " + response);
//		return response;
//	}


	public ErrorMsg error(Integer code,String message) {
		ErrorMsg error = new ErrorMsg();
		error.setErrorCode(0);
		error.setMessage(message);
		return error;
	}

}
