package it.applicazione.model;
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
	
	public OwnedEntity() {
		super();
	}

	public OwnedEntity(String name) {
		super(name);
	}

	public OwnedEntity(String name, InternalPerson internalPerson) {
		super(name);
		this.internalPerson = internalPerson;
	}

	public InternalPerson getInternalPerson() {
		return internalPerson;
	}

	public void setInternalPerson(InternalPerson internalPerson) {
		this.internalPerson = internalPerson;
	}

}
