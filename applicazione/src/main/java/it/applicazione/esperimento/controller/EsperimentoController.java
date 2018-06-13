package it.applicazione.esperimento.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.esperimento.service.EmailServiceImpl;
import it.applicazione.esperimento.service.EsperimentoGroupService;
import it.applicazione.esperimento.service.EsperimentoService;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.User;


@PreAuthorize(EsperimentoGroupController.preInvocationAuthCheck)
@Controller
@RequestMapping("/esperimentoGroup/{esperimentoGroupId}")
class EsperimentoController {

    @Autowired
	EmailServiceImpl emailService;
    
    @Autowired
    EsperimentoGroupService esperimentoGroupService;
    
    @Autowired
    EsperimentoService esperimentoService;
    
    
    @RequestMapping(value = "/esperimento/new", method = RequestMethod.GET)
    public String initCreationForm(ModelMap model
    		,@PathVariable("esperimentoGroupId") long esperimentoGroupId
    		) {
    	
    	Esperimento esperimento = new Esperimento();
        model.put("esperimento", esperimento);
        return "esperimento/createOrUpdateEsperimentoForm";
        
    }

    @RequestMapping(value = "/esperimento/new", method = RequestMethod.POST)
    public String processCreationForm(ModelMap model,
    		@Valid Esperimento esperimento,
    		BindingResult bindingResult
    		,@PathVariable("esperimentoGroupId") long esperimentoGroupId
    		) {
    	
    	
    	EsperimentoGroup esperimentoGroup = this.esperimentoGroupService.findById(esperimentoGroupId);
    		
        if (bindingResult.hasErrors()) {
            model.put("esperimento", esperimento);
            esperimento.setEsperimentoGroup(esperimentoGroup);
            return "esperimento/createOrUpdateEsperimentoForm";
        }else{
    		
        	String ogg = "mail avvenuto inserimento";
			
        	String textGroup = esperimentoGroup.getId()
        			+" "+esperimentoGroup.getInfo();
        	
        	
        	String text = esperimento.getId()
        			+" "+esperimento.getInfo()
        			+" "+esperimento.getFase1()
        			+" "+esperimento.getFase2();
        	
        	esperimento.setEsperimentoGroup(esperimentoGroup);
        	
        	this.esperimentoService.save(esperimento);
        	//emailService.sendSimpleMessage(ogg,textGroup+text);
        	
            return "redirect:/esperimentoGroup/{esperimentoGroupId}";
            
        }
        
    	}
        
    
    @RequestMapping(value = "/esperimento/{esperimentoId}/edit", method = RequestMethod.GET)
   	public String initUpdateForm(
   			@PathVariable("esperimentoId") long esperimentoId,
   			@PathVariable("esperimentoGroupId") long esperimentoGroupId
   			,ModelMap model) {

    	//EsperimentoGroup esperimentoGroup = this.esperimentoGroupService.findById(esperimentoGroupId);
    	Esperimento esperimento = this.esperimentoService.findById(esperimentoId);
    	//esperimento.setEsperimentoGroup(esperimentoGroup);
   		model.put("esperimento", esperimento);
   		return "esperimento/createOrUpdateEsperimentoForm";
       }
    
    
    @RequestMapping(value = "/esperimento/{esperimentoId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(
    		@Valid Esperimento esperimento, BindingResult result, 
    		ModelMap model,
    		@PathVariable("esperimentoGroupId") long esperimentoGroupId,
    		BindingResult bindingResult) {
    	
    	

		if (result.hasErrors()) {
            model.put("esperimento", esperimento);
            //esperimento.setEsperimentoGroup(esperimentoGroup);
            return "esperimento/createOrUpdateEsperimentoForm";
        } else {
        	
        	EsperimentoGroup esperimentoGroup = this.esperimentoGroupService.findById(esperimentoGroupId);
        	
        	esperimentoGroup.addEsperimento(esperimento);
        	esperimento.setEsperimentoGroup(esperimentoGroup);
			this.esperimentoService.save(esperimento);
            return "redirect:/esperimentoGroup/{esperimentoGroupId}";
        }
    }
	
   
 }
    
    
