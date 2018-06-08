
package it.applicazione.person;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Owner</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
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
