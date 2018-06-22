
package it.applicazione.esperimento.repository;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.esperimento.model.State;

public interface StateRepository extends Repository<State, Long> {


	Esperimento findById(@Param("id") Long id);

	Collection<State> findAll();

	State save(State state);

	

}
