package it.applicazione.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.applicazione.person.Role;
import it.applicazione.person.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository users;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		it.applicazione.person.User u = users.findByUsername(username);
		
		if(u==null) {
			throw new UsernameNotFoundException(
					"User non esiste");
		};
		
		List<Role> roles = u.getRoles();
			
			List<SimpleGrantedAuthority> authList = new ArrayList<>();
			for(Role role: roles){
			String r = "ROLE_" + role.getDescription().toUpperCase();
			authList.add(new SimpleGrantedAuthority(r));
			}
		User user = new User(username, u.getPassword(), authList);

		return user;
			
		}
	
}
