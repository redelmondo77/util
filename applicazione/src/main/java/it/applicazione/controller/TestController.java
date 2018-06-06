package it.applicazione.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Profile("preprod")
@Controller
public class TestController {
	
	@RequestMapping(value = { "/test"}, method = RequestMethod.GET)
	public String test(ModelMap model) {
		return "test";
	}
	
	@RequestMapping(value = { "/testexception" }, method = RequestMethod.GET)
	public String exception(ModelMap model) {
		new Integer("fff");
		return "testexception";
	}
	

}
