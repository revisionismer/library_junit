package com.library.web;

import org.springframework.stereotype.Controller;

@Controller
public class HomeControllerTest {

//	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
