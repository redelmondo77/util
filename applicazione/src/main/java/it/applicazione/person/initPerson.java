package it.applicazione.person;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class initPerson {
	
	@Value("${configuration.applicazione.admin}")
	private String admin;
	
	@Value("${configuration.applicazione.admin-pwd}")
	private String adminPwd;
	
	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InternalPersonRepository internalPersonRepository;
	
	@PostConstruct
	public void firstTime() {
		
		it.applicazione.person.User user = userRepository.findByUsername(admin);
		
		if(user==null){
			
			Role role = new Role();
			role.setDescription("ADMIN");
			
			user = new it.applicazione.person.User();
			user.setUsername(admin);
			user.setPassword(passwordEncoder.encode(adminPwd));
			user.addRole(role);
			
			InternalPerson internalPerson = new InternalPerson();
			internalPerson.setFirstName(admin);
			internalPerson.setLastName(admin);
			internalPerson.addUser(user);
			
			internalPersonRepository.save(internalPerson);
			
			
		}

	}
	
	
	
	
	
	
	
	
	
	
}
