package it.applicazione.person;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import java.lang.String;
import it.applicazione.person.Role;

public interface RoleRepository extends Repository<Role, Integer> {

	Role save(Role role) throws DataAccessException;

	Role findByName(String name);
	
	Role findById(long id);
	
	Collection<Role> findAll();

}
