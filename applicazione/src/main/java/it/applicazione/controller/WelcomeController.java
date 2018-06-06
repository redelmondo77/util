package it.applicazione.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class WelcomeController {

    @RequestMapping("/")
    public String welcome() {
		return "welcome";
    }

    @RequestMapping("/welcome2")
    public String welcome2() {
		return "welcome2";
    }
}
