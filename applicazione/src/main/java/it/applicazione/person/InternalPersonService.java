package it.applicazione.person;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InternalPersonService {
	
	@Value("${configuration.applicazione.admin}")
	private String admin;

	@Autowired
	InternalPersonRepository internalPersonRepository;

	// @Transactional(readOnly = true)
	public InternalPerson findByIdFetchUsersAndRoles(long id) {
		InternalPerson internalPerson = internalPersonRepository.findById(id);
		List<User> users = internalPerson.getUsers();
		for (User user : users) {
			user.getRoles();
		}
		return internalPerson;
	}

	public void save(InternalPerson internalPerson){
		internalPersonRepository.save(internalPerson);
	}
	
}
