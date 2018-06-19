package it.applicazione.person;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	
	@Bean(name = "bCryptPasswordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	

	public User findByIdFetchRoles(long id) {
		User user = userRepository.findById(id);
		List<Role> roles = user.getRoles();
		return user;
	}
	
	
	@Cacheable("usersCache")
	public User cacheFindByUsernameFetchPersonAndRoles(String username) {
		User user = userRepository.findByUsername(username);
		if(user!=null){
			List<Role> roles = user.getRoles();
			InternalPerson internalPerson = user.getInternalPerson();
		}
		return user;
	}
	
	

	@Transactional(readOnly = true)
	public User findByUsernameFetchPerson(String username) {
		User user = userRepository.findByUsername(username);
		user.getInternalPerson();
		return user;
	}

	@CacheEvict(value="usersCache", allEntries=true)
	public User save(User user) {
		return userRepository.save(user);
	}
	
	
	
	
	

}
