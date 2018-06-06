package it.applicazione.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
class SecController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Map<String, Object> model) {
		return "login";
    }

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Map<String, Object> model) {
		System.out.println("ffff");
		return "logout";
	}


}
