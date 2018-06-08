
package it.applicazione.esperimento.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Esperimento {

	
    @NotNull
    @Min(100)
    private Integer id;
    
    @NotNull
    @Size(min=2, max=10)
    private String info;
    
    @NotNull
    @Size(min=2, max=10)
    private String fase1;
    
    @NotNull
    @Size(min=2, max=10)
    private String fase2;
    

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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

   

}