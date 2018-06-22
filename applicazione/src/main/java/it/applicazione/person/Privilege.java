package it.applicazione.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import it.applicazione.model.NamedEntity;

@Entity
@Table(name = "privileges")
public class Privilege extends NamedEntity {
	
	public Privilege() {
		super();
	}

	public Privilege(String name) {
		super(name);
	}
   
	
}
