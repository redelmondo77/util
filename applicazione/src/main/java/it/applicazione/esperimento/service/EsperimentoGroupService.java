package it.applicazione.esperimento.service;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.esperimento.repository.EsperimentoGroupRepository;
import it.applicazione.person.InternalPerson;

@Service
@Transactional
public class EsperimentoGroupService {

	@Autowired
	EsperimentoGroupRepository esperimentoGroupRepository;
	

	// @Transactional(readOnly = true)
	public EsperimentoGroup findById(long id) {
		EsperimentoGroup esperimentoGroup = esperimentoGroupRepository.findById(id);
		return esperimentoGroup;
	}

	public void save(EsperimentoGroup esperimentoGroup) {
		esperimentoGroupRepository.save(esperimentoGroup);
	}
	
	public Collection<EsperimentoGroup> findByInfo(String info){
		return esperimentoGroupRepository.findByInfo(info);
	}
	
	
	public Collection<EsperimentoGroup> findByInternalPerson(InternalPerson InternalPerson){
		return esperimentoGroupRepository.findByInternalPerson(InternalPerson);
	}


}
