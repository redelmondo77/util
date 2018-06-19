package it.applicazione.person;

import java.util.Collection;
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
public class PrivilegeService {

	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@CacheEvict(value="privilegeCache", allEntries=true)
	public Privilege save(Privilege privilege) {
		return privilegeRepository.save(privilege);
	}
	
	@Cacheable("privilegeCache")
	public Privilege findById(Long id){
		return privilegeRepository.findById(id);
	}
	
	public Collection<Privilege> findAll(){
		return privilegeRepository.findAll();
	}
	
	public Privilege findByName(String name){
		return privilegeRepository.findByName(name);
	}
	

}
