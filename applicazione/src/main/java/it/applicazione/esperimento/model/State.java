package it.applicazione.esperimento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import it.applicazione.model.NamedEntity;

@Entity
@Table(name = "states")
public class State extends NamedEntity {
	
	public State() {
		super();
	}

	public State(String name) {
		super(name);
	}
   
	
}
