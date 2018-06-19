package it.applicazione.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.applicazione.model.BaseEntity;

@Entity
@Table(name = "privileges")
public class Privilege extends BaseEntity {
	
    @Column(name = "name",nullable = false, unique = true)
    private String name;
    
   	public Privilege(String name) {
		super();
		this.name = name;
	}
	public Privilege() {
		super();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
	

}
