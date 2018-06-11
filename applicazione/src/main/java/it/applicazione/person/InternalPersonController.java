package it.applicazione.person;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@PreAuthorize("hasRole('ADMIN')")
@Controller
class InternalPersonController {

	private static final String VIEWS_INTERNALPERSON_CREATE_OR_UPDATE_FORM = "internalPersons/createOrUpdateInternalPersonForm";
	private final InternalPersonRepository internalPersons;

	@Autowired
	InternalPersonService internalPersonService;


    @Autowired
	public InternalPersonController(InternalPersonRepository clinicService) {
		this.internalPersons = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

	@RequestMapping("/internalPersons/{internalPersonId}")
	public ModelAndView showInternalPerson(@PathVariable("internalPersonId") long internalPersonId) {
		ModelAndView mav = new ModelAndView("internalPersons/internalPersonDetails");
		mav.addObject(internalPersonService.findByIdFetchUsersAndRoles(internalPersonId));
		return mav;
	}

	@RequestMapping(value = "/internalPersons/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
		InternalPerson internalPerson = new InternalPerson();
		model.put("internalPerson", internalPerson);
		return VIEWS_INTERNALPERSON_CREATE_OR_UPDATE_FORM;
    }

	@RequestMapping(value = "/internalPersons/new", method = RequestMethod.POST)
	public String processCreationForm(@Valid InternalPerson internalPerson, BindingResult result) {
        if (result.hasErrors()) {
			return VIEWS_INTERNALPERSON_CREATE_OR_UPDATE_FORM;
        } else {
			this.internalPersons.save(internalPerson);
			return "redirect:/internalPersons/" + internalPerson.getId();
        }
    }

	@RequestMapping(value = "/internalPersons/find", method = RequestMethod.GET)
    public String initFindForm(Map<String, Object> model) {
		model.put("internalPerson", new InternalPerson());
		return "internalPersons/findInternalPersons";
    }

	@RequestMapping(value = "/internalPersons", method = RequestMethod.GET)
	public String processFindForm(InternalPerson internalPerson, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /internalPersons to return all
		// records
		if (internalPerson.getLastName() == null) {
			internalPerson.setLastName(""); // empty string signifies broadest
											// possible search
			//result.rejectValue("lastName", "notNull", "cannot be null!");
			//return "internalPersons/findInternalPersons";
        }

		// find internalPersons by last name
		Collection<InternalPerson> results = this.internalPersons.findByLastName(internalPerson.getLastName());
        if (results.isEmpty()) {
			// no internalPersons found
            result.rejectValue("lastName", "notFound", "not found");
			return "internalPersons/findInternalPersons";
        } else if (results.size() == 1) {
			// 1 internalPerson found
			internalPerson = results.iterator().next();
			return "redirect:/internalPersons/" + internalPerson.getId();
        } else {
			// multiple internalPersons found
            model.put("selections", results);
			return "internalPersons/internalPersonsList";
        }
    }

	@RequestMapping(value = "/internalPersons/{internalPersonId}/edit", method = RequestMethod.GET)
	public String initUpdateInternalPersonForm(@PathVariable("internalPersonId") long internalPersonId, Model model) {
		InternalPerson internalPerson = this.internalPersons.findById(internalPersonId);
		model.addAttribute(internalPerson);
		return VIEWS_INTERNALPERSON_CREATE_OR_UPDATE_FORM;
    }

	@RequestMapping(value = "/internalPersons/{internalPersonId}/edit", method = RequestMethod.POST)
	public String processUpdateInternalPersonForm(@Valid InternalPerson internalPerson, BindingResult result,
			@PathVariable("internalPersonId") long internalPersonId) {
        if (result.hasErrors()) {
			return VIEWS_INTERNALPERSON_CREATE_OR_UPDATE_FORM;
        } else {
			internalPerson.setId(internalPersonId);
			this.internalPersons.save(internalPerson);
			return "redirect:/internalPersons/{internalPersonId}";
        }
    }

    /**
	 * Custom handler for displaying an internalPerson.
	 *
	 * @param internalPersonId
	 *            the ID of the internalPerson to display
	 * @return a ModelMap with the model attributes for the view
	 */


}
