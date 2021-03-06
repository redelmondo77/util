
package it.applicazione.esperimento.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.applicazione.model.BaseEntity;
import it.applicazione.model.NamedEntity;
import it.applicazione.person.User;


@Entity
@Table(name = "esperimento")
public class Esperimento extends NamedEntity {

    
    @NotNull
    @Size(min=2, max=7)
    private String info;
    
    @NotNull
    @Size(min=2, max=8)
    private String fase1;
    
    @NotNull
    @Size(min=2, max=9)
    private String fase2;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	private State state;
    

	@ManyToOne
	@JoinColumn(name = "esperimentoGroup_id")
	private EsperimentoGroup esperimentoGroup;


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public String getFase1() {
		return fase1;
	}


	public void setFase1(String fase1) {
		this.fase1 = fase1;
	}


	public String getFase2() {
		return fase2;
	}


	public void setFase2(String fase2) {
		this.fase2 = fase2;
	}


	public EsperimentoGroup getEsperimentoGroup() {
		return esperimentoGroup;
	}


	public void setEsperimentoGroup(EsperimentoGroup esperimentoGroup) {
		this.esperimentoGroup = esperimentoGroup;
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}

	
	
	
	
}

