package it.applicazione.esperimento.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.service.EmailServiceImpl;


@Controller
class EsperimentoController {

    @Autowired
	EmailServiceImpl emailService;
    
    
    @RequestMapping(value = "/esperimento/new", method = RequestMethod.GET)
    public String initCreationForm(ModelMap model) {
    	model.addAttribute("esperimento", new Esperimento());
        return "mail/createOrUpdateEsperimentoForm";
    }

    @RequestMapping(value = "/esperimento/new", method = RequestMethod.POST)
    public String processCreationForm(Model model,@Valid Esperimento esperimento,BindingResult bindingResult) {
    		
        if (bindingResult.hasErrors()) {
            return "mail/createOrUpdateEsperimentoForm";
        }else{
    	
        	model.addAttribute("esperimento", esperimento);
        	
        	String ogg = "mail avvenuto inserimento";
        			
        	String text = esperimento.getId()
        			+" "+esperimento.getInfo()
        			+" "+esperimento.getFase1()
        			+" "+esperimento.getFase2();
        	
        	emailService.sendSimpleMessage(ogg,text);
        	
        	return "mail/result";
        }
        
    	}
        
    
    
    
    
	@RequestMapping(value = { "/test"}, method = RequestMethod.GET)
	public String test(ModelMap model) {
		return "test";
	}
	
	@RequestMapping(value = { "/testexception" }, method = RequestMethod.GET)
	public String exception(ModelMap model) {
		new Integer("fff");
		return "testexception";
	}
	
	
    @RequestMapping("/welcome")
    public String welcome() {
		return "welcome";
    }

    @RequestMapping("/welcome2")
    public String welcome2() {
		return "welcome2";
    }
 }
    
    
