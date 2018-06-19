
package it.applicazione.esperimento.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import it.applicazione.model.OwnedEntity;

@Entity
@Table(name = "esperimentoGroup")
public class EsperimentoGroup extends OwnedEntity {

	
    @NotNull
    @Size(min=10, max=99)
    private String info;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "esperimentoGroup", fetch = FetchType.EAGER )
	private Set<Esperimento> esperimentos;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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