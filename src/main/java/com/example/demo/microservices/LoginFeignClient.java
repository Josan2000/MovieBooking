package com.example.demo.microservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Login;

@FeignClient("login")
public interface LoginFeignClient {
	

	@PostMapping("login")
	public ResponseEntity<Login> registerUser(@RequestBody String username, String password);

}
