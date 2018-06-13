
package it.applicazione.esperimento.repository;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.person.InternalPerson;

public interface EsperimentoGroupRepository extends Repository<EsperimentoGroup, Integer> {

	// @Transactional(readOnly = true)
	EsperimentoGroup findById(@Param("id") Long id);
    
	// @PreAuthorize("hasRole('ADMIN')")
	// @Transactional(readOnly = true)
	Collection<EsperimentoGroup> findAll();

	void save(EsperimentoGroup esperimentoGroup);
	
	
	@Query("SELECT DISTINCT esperimentoGroup FROM EsperimentoGroup esperimentoGroup left join fetch esperimentoGroup.esperimentos WHERE esperimentoGroup.info LIKE :info%")
	// @Transactional(readOnly = true)
	Collection<EsperimentoGroup> findByInfo(@Param("info") String info);


	Collection<EsperimentoGroup> findByInternalPerson(InternalPerson InternalPerson);
	

	

}
