package it.applicazione.person;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface InternalPersonRepository extends Repository<InternalPerson, Integer> {

	@Query("SELECT DISTINCT internalPerson FROM InternalPerson internalPerson left join fetch internalPerson.users WHERE internalPerson.lastName LIKE :lastName%")
	// @Transactional(readOnly = true)
	Collection<InternalPerson> findByLastName(@Param("lastName") String lastName);

	// @Transactional(readOnly = true)
	InternalPerson findById(@Param("id") Long id);
    
	// @PreAuthorize("hasRole('ADMIN')")
	// @Transactional(readOnly = true)
	Collection<InternalPerson> findAll();

	void save(InternalPerson internalPerson);

	// @Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE
	// owner.id =:id")
	// @Transactional(readOnly = true)
	// Owner findById(@Param("id") Integer id);

}
