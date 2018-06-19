package it.applicazione.person;

import java.util.Collection;
import org.springframework.data.repository.Repository;
import java.lang.String;
import it.applicazione.person.Privilege;
import java.util.List;

public interface PrivilegeRepository extends Repository<Privilege, Integer> {

	Privilege findById(Long id);
	
	Privilege save(Privilege privilege);
	
	Collection<Privilege> findAll();
	
	Privilege findByName(String name);
	

}

