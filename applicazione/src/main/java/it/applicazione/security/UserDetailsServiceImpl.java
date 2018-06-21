package it.applicazione.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.applicazione.person.Privilege;
import it.applicazione.person.Role;
import it.applicazione.person.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository users;

	/*
	 * es 
	 * username: admin
	 * password: $2a$10$/6UlHydKXfvP098wheBG7OT0KJLGAJb0NQUVKcU27PAHxLoLVgeMe
	 * autorities: [ESPERIMENTO_READ_PRIVILEGE, ROLE_ADMIN] 
	 */
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		it.applicazione.person.User u = users.findByUsername(username);
		
		if(u==null) {
			throw new UsernameNotFoundException(
					"User non esiste");
		};
		
		
		Set<Role> roles = u.getRoles();
			
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		
		for(Role role: roles){
			String r = "ROLE_" + role.getDescription().toUpperCase();
			authList.add(new SimpleGrantedAuthority(r));
			
			for (Privilege privilege : role.getPrivileges()) {
				if(!authList.contains(privilege.getName()+"_PRIVILEGE")){
					authList.add(new SimpleGrantedAuthority(privilege.getName()+"_PRIVILEGE"));
				}
		    }
			
		}
			
		for (Privilege privilege : u.getPrivileges()) {
			if(!authList.contains(privilege.getName()+"_PRIVILEGE")){
				authList.add(new SimpleGrantedAuthority(privilege.getName()+"_PRIVILEGE"));
			}
	    }
			
		User user = new User(username, u.getPassword(), authList);

		return user;

		}
	
}
