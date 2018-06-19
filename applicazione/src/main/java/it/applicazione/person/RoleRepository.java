package it.applicazione.person;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

public interface RoleRepository extends Repository<Role, Integer> {

	void save(Role role) throws DataAccessException;

	List<User> findByUserId(Long userId);

}
