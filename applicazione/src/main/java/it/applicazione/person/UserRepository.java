package it.applicazione.person;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends Repository<User, Integer> {

	User findById(Long id);
	
	@Query("SELECT user FROM User user left join fetch user.roles WHERE user.username LIKE :username%")
	// @Transactional(readOnly = true)
	User findByUsername(@Param("username") String username);

	User save(User user);


}

