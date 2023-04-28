package com.slavic.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.slavic.dto.Response;
import com.slavic.dto.req.Login;
import com.slavic.repo.EmployeeRepo;
import com.slavic.service.EmpService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	EmpService empService;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/employee_details")
	public String empDetails() {
		LOG.info("employee_details !!!!!!!!!!");
		
		employeeRepo.findAll();
		
		String baseUrl = "http://localhost:8081/participant_details";
		String response = (String) restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class).getBody();
		LOG.info("The response recieved by method1 is " + response);
		return response;
	}
	
	@PostMapping("/login")
	public @ResponseBody Response<?> login(@RequestBody Login login){		
		return empService.login(login);
	}
	
	
	
	
	
	
	
	

}
