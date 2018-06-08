package it.applicazione.esperimento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import it.applicazione.configuration.ApplicazioneConfiguration;

@Service
public class EmailServiceImpl {
  
    @Autowired
    public JavaMailSender emailSender;
    
	@Autowired
	ApplicazioneConfiguration applicazioneConfiguration;
 
    public void sendSimpleMessage(
      String subject, String text) {
 
        for(String to:  applicazioneConfiguration.getDestinatari()  ){
        	 SimpleMailMessage message = new SimpleMailMessage(); 
        	 message.setFrom("noreply");
             message.setTo(to); 
             message.setSubject(subject); 
             message.setText(text);
        	 emailSender.send(message);
        }
    
    }
}