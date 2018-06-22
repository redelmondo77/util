package it.applicazione.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class NamedEntity extends BaseEntity {

    public NamedEntity(String name) {
		super();
		this.name = name;
	}
    
 	public NamedEntity() {
		super();
	}

	@Column(name = "name")
	@Size(min=3, max=100)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
