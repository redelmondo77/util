package it.applicazione.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.applicazione.person.InternalPerson;


@Controller
class SecController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Map<String, Object> model) {
		
		it.applicazione.person.User user = new it.applicazione.person.User();
		model.put("user", user);
		
		return "login";
    }
/*
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@Valid it.applicazione.person.User user, BindingResult result
			) {
		
		System.out.println("tfvetv");
		if (result.hasErrors()) {
			return "login";
        } else {
			return "redirect:/login";
        }
		
		//return "login";
    }
	*/
	
	@RequestMapping(value = "/logoutDone", method = RequestMethod.GET)
	public String logoutDone(Map<String, Object> model) {
		return "logout";
	}
	


}
