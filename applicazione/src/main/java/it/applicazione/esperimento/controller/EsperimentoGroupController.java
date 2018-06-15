package it.applicazione.esperimento.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.esperimento.repository.EsperimentoGroupRepository;
import it.applicazione.esperimento.service.EsperimentoGroupService;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.User;
import it.applicazione.person.UserRepository;
import it.applicazione.person.UserService;

/*
 * https://docs.spring.io/autorepo/docs/spring-security/3.0.x/reference/el-access.html
 */
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
@Controller
class EsperimentoGroupController {

	
	private static final String VIEWS_EsperimentoGroup_CREATE_OR_UPDATE_FORM = "esperimentoGroup/createOrUpdateEsperimentoGroupForm";
	
	public static final String  preInvocationAuthCheck
	= "hasRole('ADMIN') or ( hasRole('USER') and @myEsperimentoSecurityService.hasAccess(#esperimentoGroupId) )";
	
	@Autowired
	EsperimentoGroupService esperimentoGroupService;
	
	@Autowired
	UserService userService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    
    
	@PreAuthorize(preInvocationAuthCheck)
	@RequestMapping("/esperimentoGroup/{esperimentoGroupId}")
	public ModelAndView showInternalPerson(@PathVariable("esperimentoGroupId") long esperimentoGroupId) {
		ModelAndView mav = new ModelAndView("esperimentoGroup/esperimentoGroupDetails");
		
		mav.addObject("esperimentoGroup",esperimentoGroupService.findById(esperimentoGroupId));
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//User user = userService.findByUsernameFetchPerson(auth.getName());
		//InternalPerson internalPerson = user.getInternalPerson();
		//mav.addObject("internalPerson",internalPerson);
		
		InternalPerson internalPerson = userService.cacheFindByUsernameFetchPersonAndRoles(SecurityContextHolder.getContext().getAuthentication().getName()).getInternalPerson();
		mav.addObject("internalPerson",internalPerson);
		
		return mav;
	}

	@RequestMapping(value = "/esperimentoGroup/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
		EsperimentoGroup esperimentoGroup = new EsperimentoGroup();
		model.put("esperimentoGroup", esperimentoGroup);
		return VIEWS_EsperimentoGroup_CREATE_OR_UPDATE_FORM;
    }

	
	@RequestMapping(value = "/esperimentoGroup/new", method = RequestMethod.POST)
	public String processCreationForm(@Valid EsperimentoGroup esperimentoGroup, BindingResult result) {
        if (result.hasErrors()) {
			return VIEWS_EsperimentoGroup_CREATE_OR_UPDATE_FORM;
        } else {
			
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User user = userService.findByUsernameFetchPerson(auth.getName());
    		InternalPerson internalPerson = user.getInternalPerson();
    		esperimentoGroup.setInternalPerson(internalPerson);	
         	
        	this.esperimentoGroupService.save(esperimentoGroup);
			return "redirect:/esperimentoGroup/" + esperimentoGroup.getId();
        }
    }
	
	
	
	
	@RequestMapping(value = "/esperimentoGroup/list", method = RequestMethod.GET)
	public String esperimentoGroupList(EsperimentoGroup esperimentoGroup, 
			BindingResult result, Map<String, Object> model) {
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//User user = userService.findByUsernameFetchPerson(auth.getName());
		//InternalPerson internalPerson = user.getInternalPerson();
		
		InternalPerson internalPerson = 
		userService.cacheFindByUsernameFetchPersonAndRoles(SecurityContextHolder.getContext().getAuthentication().getName()).getInternalPerson();
		
		Collection<EsperimentoGroup> results = 
				this.esperimentoGroupService.findByInternalPerson(internalPerson);
		
        if (results.isEmpty()) {
			// no internalPersons found
            result.rejectValue("info", "notFound", "not found");
			//return "esperimentoGroup/findEsperimentoGroup";
			return "esperimentoGroup/esperimentoGroupList";
        //} else if (results.size() == 1) {
		//	// 1 internalPerson found
       // 	esperimentoGroup = results.iterator().next();
		//	return "redirect:/esperimentoGroup/" + esperimentoGroup.getId();
        } else {
			// multiple internalPersons found
            model.put("selections", results);
			return "esperimentoGroup/esperimentoGroupList";
        }
		
		
    }
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/esperimentoGroup/find", method = RequestMethod.GET)
    public String initFindForm(Map<String, Object> model) {
		model.put("esperimentoGroup", new EsperimentoGroup());
		return "esperimentoGroup/findEsperimentoGroup";
    }

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/esperimentoGroup", method = RequestMethod.GET)
	public String processFindForm(EsperimentoGroup esperimentoGroup, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /internalPersons to return all
		// records
		if (esperimentoGroup.getInfo() == null) {
			esperimentoGroup.setInfo(""); 
        }

		// find internalPersons by last name
		Collection<EsperimentoGroup> results = 
				this.esperimentoGroupService.findByInfo(
				esperimentoGroup.getInfo());
        if (results.isEmpty()) {
			// no internalPersons found
            result.rejectValue("info", "notFound", "not found");
			return "esperimentoGroup/findEsperimentoGroup";
        } else if (results.size() == 1) {
			// 1 internalPerson found
        	esperimentoGroup = results.iterator().next();
			return "redirect:/esperimentoGroup/" + esperimentoGroup.getId();
        } else {
			// multiple internalPersons found
            model.put("selections", results);
			return "esperimentoGroup/esperimentoGroupList";
        }
    }

	@PreAuthorize(preInvocationAuthCheck)
	@RequestMapping(value = "/esperimentoGroup/{esperimentoGroupId}/edit", method = RequestMethod.GET)
	public String initUpdateEsperimentoGroupForm(@PathVariable("esperimentoGroupId") long esperimentoGroupId, Model model) {
		EsperimentoGroup esperimentoGroup = this.esperimentoGroupService.findById(esperimentoGroupId);
		model.addAttribute(esperimentoGroup);
		return VIEWS_EsperimentoGroup_CREATE_OR_UPDATE_FORM;
    }

	@PreAuthorize(preInvocationAuthCheck)
	@RequestMapping(value = "/esperimentoGroup/{esperimentoGroupId}/edit", method = RequestMethod.POST)
	public String processUpdateInternalPersonForm(@Valid EsperimentoGroup esperimentoGroup, BindingResult result,
			@PathVariable("esperimentoGroupId") long internalPersonId) {
        if (result.hasErrors()) {
			return VIEWS_EsperimentoGroup_CREATE_OR_UPDATE_FORM;
        } else {
        	esperimentoGroup.setId(internalPersonId);
			this.esperimentoGroupService.save(esperimentoGroup);
			return "redirect:/esperimentoGroup/{esperimentoGroupId}";
        }
    }

 

}
