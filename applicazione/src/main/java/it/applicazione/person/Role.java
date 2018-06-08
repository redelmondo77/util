package it.applicazione.person;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import it.applicazione.model.BaseEntity;


@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

	@NotNull
    @Column(name = "description")
    private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
