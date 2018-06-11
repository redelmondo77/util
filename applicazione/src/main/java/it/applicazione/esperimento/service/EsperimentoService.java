package it.applicazione.esperimento.service;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.repository.EsperimentoRepository;

@Service
@Transactional
public class EsperimentoService {

	@Autowired
	EsperimentoRepository esperimentoRepository;

	// @Transactional(readOnly = true)
	public Esperimento findById(long id) {
		Esperimento esperimento = esperimentoRepository.findById(id);
		return esperimento;
	}

	public void save(Esperimento esperimento) {
		esperimentoRepository.save(esperimento);
	}
	
	

}
