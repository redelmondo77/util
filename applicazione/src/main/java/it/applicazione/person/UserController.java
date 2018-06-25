package it.applicazione.person;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/internalPersons/{internalPersonId}")
class UserController {

	private static final String VIEWS_USERS_CREATE_OR_UPDATE_FORM = "users/createOrUpdateUserForm";
    private final UserRepository users;
    private final InternalPersonRepository internalPersons;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;

    @Autowired
    public UserController(UserRepository users, InternalPersonRepository internalPersons) {
        this.users = users;
        this.internalPersons = internalPersons;
    }

	/*
	 * order: 1 @ModelAttribute , 2 initUserBinder, 3 UserValidator.validate,
	 * 4 @RequestMapping valorizza InternalPerson, poi lancia la UserValidator
	 * validate
	 */
	@ModelAttribute("internalPerson")
	public InternalPerson findInternalPerson(@PathVariable("internalPersonId") long internalPersonId) {
		InternalPerson internalPerson = this.internalPersons.findById(internalPersonId);
		return internalPerson;
	}

    @InitBinder("internalPerson")
    public void initInternalPersonBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
		// dataBinder.setDisallowedFields("password");
    }

    @InitBinder("user")
    public void initUserBinder(WebDataBinder dataBinder) {

		Validator userValidator = new UserValidator();

		// custom validator only
		// dataBinder.setValidator(userValidator);

		// webMvcValidator + userValidator
		dataBinder.addValidators(userValidator);
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.GET)
    public String initCreationForm(InternalPerson internalPerson, ModelMap model) {
		
    	
    	Collection<Role> allRole = roleService.findAll();
		model.put("allroles", allRole);
    	
    	User user = new User();
		user.setInternalPerson(internalPerson);
        model.put("user", user);
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    public String processCreationForm(InternalPerson internalPerson, @Valid User user, BindingResult result, ModelMap model) {

		if (StringUtils.hasLength(user.getUsername()) && user.isNew()
				&& users.findByUsername(user.getUsername()) != null) {
			result.rejectValue("username", "duplicate", "already exists");
        
		
		}
        if (result.hasErrors()) {
        	
        	Collection<Role> allRole = roleService.findAll();
			model.put("allroles", allRole);
        	
            model.put("user", user);
            user.setInternalPerson(internalPerson);
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
        } else {
			user.setInternalPerson(internalPerson);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			this.userService.save(user);
            return "redirect:/internalPersons/{internalPersonId}";
        }
    }

    @RequestMapping(value = "/users/{userId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(InternalPerson internalPerson, @PathVariable("userId") long userId, ModelMap model) {

    	Collection<Role> allRole = roleService.findAll();
		model.put("allroles", allRole);
    	
		User user = this.users.findById(userId);
		user.setInternalPerson(internalPerson);
		user.setPassword(null);
        model.put("user", user);
		return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/users/{userId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@Valid User user, BindingResult result, InternalPerson internalPerson, ModelMap model) {

    	
    	if( CollectionUtils.isEmpty(user.getRoles()) ){
    		result.rejectValue("roleids", "UserController.role.missing", "non hai messo ruolo");
    	}
    	if (result.hasErrors()) {
			Collection<Role> allRole = roleService.findAll();
			model.put("allroles", allRole);
            user.setInternalPerson(internalPerson);
            model.put("user", user);
			return VIEWS_USERS_CREATE_OR_UPDATE_FORM;
        } else {
        	User userDb = users.findByUsername(user.getUsername());
        	userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        	userDb.setRoles(user.getRoles());
       	internalPerson.addUser(userDb);
            this.userService.save(userDb);
            return "redirect:/internalPersons/{internalPersonId}";
        }
    }

}
