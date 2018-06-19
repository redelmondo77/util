package it.applicazione.model;

import it.applicazione.person.InternalPerson;

public interface OwnedEntityInterface {
	
	
	public Long getId();
	
	public void setId(Long id);
	
	public InternalPerson getInternalPerson();
	
	public void setInternalPerson(InternalPerson internalPerson);
	

}
