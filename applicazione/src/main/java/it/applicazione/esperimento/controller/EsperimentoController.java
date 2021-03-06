package it.applicazione.esperimento.controller;

import java.util.Collection;

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
import it.applicazione.esperimento.model.State;
import it.applicazione.esperimento.repository.StateRepository;
import it.applicazione.esperimento.service.EmailServiceImpl;
import it.applicazione.esperimento.service.EsperimentoGroupService;
import it.applicazione.esperimento.service.EsperimentoService;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.User;

@Controller
@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('BACKOFFICE') ")
@RequestMapping("/esperimentoGroup/{esperimentoGroupId}")
class EsperimentoController {

    @Autowired
	EmailServiceImpl emailService;
    
    @Autowired
    EsperimentoGroupService esperimentoGroupService;
    
    @Autowired
    EsperimentoService esperimentoService;
    
    @Autowired
    StateRepository stateRepository;
    
    @PreAuthorize(EsperimentoGroupController.hasWritePermissionEsperimentoGroup)
    @RequestMapping(value = "/esperimento/new", method = RequestMethod.GET)
    public String initCreationForm(ModelMap model
    		,@PathVariable("esperimentoGroupId") long esperimentoGroupId
    		) {
    	model.put("states", stateRepository.findAll());
        model.put("esperimento", new Esperimento());
        return "esperimento/createOrUpdateEsperimentoForm";
        
    }
    
    @PreAuthorize(EsperimentoGroupController.hasWritePermissionEsperimentoGroup)
    @RequestMapping(value = "/esperimento/new", method = RequestMethod.POST)
    public String processCreationForm(ModelMap model,
    		@Valid Esperimento esperimento,BindingResult bindingResult,@PathVariable("esperimentoGroupId") long esperimentoGroupId
    		) {
    	//EsperimentoGroup esperimentoGroup = this.esperimentoGroupService.findById(esperimentoGroupId);
    		
        if (bindingResult.hasErrors()) {
        	model.put("states", stateRepository.findAll());
            model.put("esperimento", esperimento);
            //esperimento.setEsperimentoGroup(esperimentoGroup);
            return "esperimento/createOrUpdateEsperimentoForm";
        }else{
        	EsperimentoGroup esperimentoGroup = this.esperimentoGroupService.findById(esperimentoGroupId);
         	esperimento.setEsperimentoGroup(esperimentoGroup);
        	this.esperimentoService.save(esperimento);
        	//emailService.sendSimpleMessage("oggetto: inserimento","testo: "+esperimento.getId()+esperimento.getInfo());
            return "redirect:/esperimentoGroup/{esperimentoGroupId}";
        }
        
    }
        
    @PreAuthorize(EsperimentoGroupController.hasWritePermissionEsperimentoGroup)
    @RequestMapping(value = "/esperimento/{esperimentoId}/edit", method = RequestMethod.GET)
   	public String initUpdateForm(
   			@PathVariable("esperimentoId") long esperimentoId,
   			@PathVariable("esperimentoGroupId") long esperimentoGroupId,ModelMap model) {
    	model.put("states", stateRepository.findAll());
   		model.put("esperimento", esperimentoService.findById(esperimentoId));
   		return "esperimento/createOrUpdateEsperimentoForm";
    }
    
    
    @PreAuthorize(EsperimentoGroupController.hasWritePermissionEsperimentoGroup)
    @RequestMapping(value = "/esperimento/{esperimentoId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@Valid Esperimento esperimento, BindingResult result, 
    		ModelMap model,@PathVariable("esperimentoGroupId") long esperimentoGroupId,BindingResult bindingResult) {
    	if (result.hasErrors()) {
            model.put("esperimento", esperimento);
        	model.put("states", stateRepository.findAll());
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
    
    
