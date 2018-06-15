package it.applicazione.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.model.EsperimentoGroup;


@Entity
@Table(name = "persons")
public class InternalPerson extends Person {

	@Size(min=2, max=100)
	@Column(name = "address")
    private String address;

	@Size(min=2, max=100)
    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    @Digits(fraction = 0, integer = 10)
    private String telephone;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "internalPerson", fetch = FetchType.LAZY)
	private Set<User> users;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "internalPerson", fetch = FetchType.LAZY )
	private Set<EsperimentoGroup> esperimentoGroups;


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

	protected Set<User> getUsersInternal() {
		if (this.users == null) {
			this.users = new HashSet<>();
        }
		return this.users;
    }

	protected void setUsersInternal(Set<User> users) {
		this.users = users;
    }

	public List<User> getUsers() {
		List<User> sortedUsers = new ArrayList<>(getUsersInternal());
		PropertyComparator.sort(sortedUsers, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedUsers);
	}

	public void addUser(User user) {
		if (user.isNew()) {
			getUsersInternal().add(user);
        }
		user.setInternalPerson(this);
    }

	// public User getUser(String name) {
	// return getUser(name, false);
	// }
	/*
	 * public User getUser(String name, boolean ignoreNew) { name =
	 * name.toLowerCase(); for (User user : getUsersInternal()) { if (!ignoreNew
	 * || !user.isNew()) { String compName = user.getUsername(); compName =
	 * compName.toLowerCase(); if (compName.equals(name)) { return user; } } }
	 * return null; }
	 */

}
