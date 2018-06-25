package it.applicazione.person;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	//@CacheEvict(value="rolesCache", allEntries=true)
	@CacheEvict(value = { "rolesCache", "rolesCacheId" }, allEntries = true)
	public Role save(Role role) {
		return roleRepository.save(role);
	}
	
	@Cacheable("rolesCacheId")
	public Role findById(long id){
		return roleRepository.findById(id);
	}
	
	@Cacheable("rolesCache")
	public Role findByName(String name){
		return roleRepository.findByName(name);
	}
	
	@Cacheable("allrolesCache")
	public Collection<Role> findAll(){
		return roleRepository.findAll();
	}
	
	
	
}
