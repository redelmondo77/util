/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.applicazione.person;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@PreAuthorize("hasRole('ADMIN')")
@Controller
class RoleController {

	private final RoleRepository roles;
	private final UserRepository users;

	@Autowired
	UserService userService;

    @Autowired
	public RoleController(RoleRepository roles, UserRepository users) {
		this.roles = roles;
		this.users = users;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
	 * Called before each and every @RequestMapping annotated method. 2 goals: -
	 * Make sure we always have fresh data - Since we do not use the session
	 * scope, make sure that User object always has an id (Even though id is not
	 * part of the form fields)
	 *
	 * @param userId
	 * @return User
	 */
	@ModelAttribute("role")
	public Role loadUserWithRole(@PathVariable("userId") long userId, Map<String, Object> model) {
		User user = this.userService.findByIdFetchRoles(userId);
		model.put("user", user);
		Role role = new Role();
		user.addRole(role);
		return role;
    }

	// Spring MVC calls method loadUserWithRole(...) before initNewRoleForm is
	// called
	@RequestMapping(value = "/internalPersons/*/users/{userId}/roles/new", method = RequestMethod.GET)
	public String initNewRoleForm(@PathVariable("userId") long userId, Map<String, Object> model) {
		return "users/createOrUpdateRoleForm";
    }

	// Spring MVC calls method loadUserWithRole(...) before processNewRoleForm
	// is
	// called
	@RequestMapping(value = "/internalPersons/{ownerId}/users/{userId}/roles/new", method = RequestMethod.POST)
	public String processNewRoleForm(@Valid Role role, BindingResult result) {
        if (result.hasErrors()) {
			return "users/createOrUpdateRoleForm";
        } else {
			this.roles.save(role);
			return "redirect:/internalPersons/{ownerId}";
        }
    }

}
