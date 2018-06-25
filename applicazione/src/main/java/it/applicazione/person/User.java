
package it.applicazione.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.model.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "username", unique = true)
	@NotNull
	@Size(min = 4, max = 10)
	private String username;

	@Column(name = "password")
	@NotNull
	@Size(min = 4, max = 80)
	private String password;

	@Column(name = "email")
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	private String email;
	
	@Column(name = "created")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date created = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private InternalPerson internalPerson;


    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(name = "users_privileges", 
    joinColumns = 
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
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

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(name = "users_roles", 
    joinColumns = 
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = 
    @JoinColumn(name = "role_id", referencedColumnName = "id")) 
    private Set<Role> roles;
	
    public void addRole(Role role) {
		if(!getRoleInternal().contains(role)){
			getRoleInternal().add(role);
		}
    }
	protected Set<Role> getRoleInternal() {
		if (this.roles == null) {
			this.roles = new HashSet<>();
        }
		return this.roles;
    }
    public Set<Role> getRoles() {
		return roles;
	}
    public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
    public List<Long> getRoleids() {
    	List<Long> l = new ArrayList<Long>(); 
    	if(roles==null)return null;
    	for(Role role: roles){
    		l.add(role.getId());
    	}
		return l;
	}
    public void setRoleids(List<Long> l) {
    	for(Long id: l){
    		Role role = new Role();
        	role.setId(id);
    		addRole(role);
    	}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public InternalPerson getInternalPerson() {
		return internalPerson;
	}

	public void setInternalPerson(InternalPerson internalPerson) {
		this.internalPerson = internalPerson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
