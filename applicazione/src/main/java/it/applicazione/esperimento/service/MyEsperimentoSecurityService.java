package it.applicazione.esperimento.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.User;
import it.applicazione.person.UserService;

@Service
public class MyEsperimentoSecurityService {
	
	@Autowired
	EsperimentoGroupService esperimentoGroupService;
	
	@Autowired
	UserService userService;
	/*
	 * see stack PreInvocationAuthorizationAdviceVoter
	 */
	public boolean hasAccess(long esperimentoGroupId) {
	      
		try{
	      EsperimentoGroup esperimentoGroup = esperimentoGroupService.findById(esperimentoGroupId);
			
		  	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsernameFetchPerson(auth.getName());
			InternalPerson internalPerson = user.getInternalPerson();
			
			// info
			Collection<? extends GrantedAuthority> grantedAuthList = auth.getAuthorities();
			for (GrantedAuthority grantedAuth : grantedAuthList) {
				System.out.println("MyEsperimentoSecurityService:"+ auth.getName()+" "+grantedAuth.getAuthority()  );
		    }
			// info
			
			if( esperimentoGroup.getInternalPerson().getId()!= internalPerson.getId()){
				return false;
			}else{
				return true;
			}
	       
		}catch(Exception e){
			return false;
		}			
	   }

}
