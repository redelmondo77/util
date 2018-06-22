package it.applicazione.person;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import it.applicazione.model.BaseEntity;
import it.applicazione.model.NamedEntity;


@Entity
@Table(name = "roles")
public class Role extends NamedEntity {

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(name = "roles_privileges", 
    joinColumns = 
    @JoinColumn(name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = 
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")) 
    private Set<Privilege> privileges;
	
	
    public void addPrivilege(Privilege privilege) {
		if(!getPrivilegeInternal().contains(privilege)){
			getPrivilegeInternal().add(privilege);
		}
    }
	protected Set<Privilege> getPrivilegeInternal() {
		if (this.privileges == null) {
			this.privileges = new HashSet<>();
        }
		return this.privileges;
    }
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}


}
