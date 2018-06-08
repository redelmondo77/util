
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import it.applicazione.model.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "username", unique = true)
	@NotNull
	private String username;

	@Column(name = "password")
	@NotNull
	// @Size(min = 4, max = 10)
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "created")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date created = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private InternalPerson internalPerson;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Role> roles = new LinkedHashSet<>();


	protected Set<Role> getRolesInternal() {
		if (this.roles == null) {
			this.roles = new HashSet<>();
		}
		return this.roles;
	}

	protected void setRolesInternal(Set<Role> Roles) {
		this.roles = Roles;
	}

	public List<Role> getRoles() {
		List<Role> sortedRoles = new ArrayList<>(getRolesInternal());
		PropertyComparator.sort(sortedRoles, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedRoles);
	}

	public void addRole(Role role) {
		getRolesInternal().add(role);
		role.setUser(this);
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

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}