package it.applicazione.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import it.applicazione.person.InternalPerson;

@MappedSuperclass
public class OwnedEntity extends NamedEntity implements OwnedEntityInterface {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id")
	private InternalPerson internalPerson;
	
	public InternalPerson getInternalPerson() {
		return internalPerson;
	}

	public void setInternalPerson(InternalPerson internalPerson) {
		this.internalPerson = internalPerson;
	}

}
