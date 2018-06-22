package it.applicazione.person;

import java.util.Collection;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@PreAuthorize("hasRole('ADMIN')")
@Controller
class RoleController {

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;


	@RequestMapping(value = "/internalPersons/*/users/{userId}/roles/new", method = RequestMethod.GET)
	public String initNewRoleForm(@PathVariable("userId") long userId, ModelMap model) {
		
		User user = this.userService.findByIdFetchRoles(userId);
		model.put("user", user);
		
		Collection<Role> allRole = roleService.findAll();
		model.put("roles", allRole);
		
		Role role = new Role();
		model.put("role", role);
		
		return "users/addRoleForm";
    }


	@RequestMapping(value = "/internalPersons/{internalPersonId}/users/{userId}/roles/new", method = RequestMethod.POST)
	public String processNewRoleForm(Role role, @PathVariable("userId") long userId, BindingResult result, ModelMap model) {
        
		User user = this.userService.findByIdFetchRoles(userId);
		Set<Role> userRoles = user.getRoles();

		boolean exists = false;
		for(Role urole: userRoles){
			if( urole.getId()==role.getId() ){
				exists = true;
			}
		}
		
		if(exists){
			result.rejectValue("id", "duplicate", "already exists");
		}

		if (result.hasErrors()) {
			Collection<Role> allRole = roleService.findAll();
			model.put("roles", allRole);
			model.put("user", user);
			model.put("role", role);
			return "users/addRoleForm";
        } else {
        	user.addRole(roleService.findById(role.getId()));
        	userService.save(user);
			return "redirect:/internalPersons/{internalPersonId}";
        }
    }

}
