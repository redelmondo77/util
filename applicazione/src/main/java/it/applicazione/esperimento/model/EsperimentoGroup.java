
package it.applicazione.esperimento.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.applicazione.model.BaseEntity;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.User;

@Entity
@Table(name = "esperimentoGroup")
public class EsperimentoGroup extends BaseEntity {

	
    @NotNull
    @Size(min=10, max=99)
    private String info;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id")
	private InternalPerson internalPerson;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "esperimentoGroup", fetch = FetchType.EAGER)
	private Set<Esperimento> esperimentos;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public InternalPerson getInternalPerson() {
		return internalPerson;
	}

	public void setInternalPerson(InternalPerson internalPerson) {
		this.internalPerson = internalPerson;
	}

	public Set<Esperimento> getEsperimentos() {
		return esperimentos;
	}

	public void setEsperimentos(Set<Esperimento> esperimentos) {
		this.esperimentos = esperimentos;
	}
	
	
	protected Set<Esperimento> getEsperimentoInternal() {
		if (this.esperimentos == null) {
			this.esperimentos = new HashSet<>();
        }
		return this.esperimentos;
    }
    
	public void addEsperimento(Esperimento esperimento) {
		if (esperimento.isNew()) {
			getEsperimentoInternal().add(esperimento);
        }
		esperimento.setEsperimentoGroup(this);
    }
    

   

}