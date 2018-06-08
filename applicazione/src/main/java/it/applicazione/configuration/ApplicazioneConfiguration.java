package it.applicazione.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("configuration.applicazione")
public class ApplicazioneConfiguration {
	

	private String admin;
	private String adminPwd;
	private String[] destinatari;

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String[] getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String[] destinatari) {
		this.destinatari = destinatari;
	}
	
	
}
