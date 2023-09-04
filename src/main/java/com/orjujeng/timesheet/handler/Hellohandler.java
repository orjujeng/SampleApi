package com.orjujeng.timesheet.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hellohandler {
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
