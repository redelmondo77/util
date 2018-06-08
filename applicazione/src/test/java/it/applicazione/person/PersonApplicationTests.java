package it.applicazione.person;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonApplicationTests {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserRepository users;

	@Test
	public void contextLoads() {
		
		logger.info("start test person");
		
		it.applicazione.person.User user = users.findByUsername("admin");
		
		if(user==null){
			
			logger.warn("no admin");
			System.out.println("no admin");
			
			user = new it.applicazione.person.User();
			user.setUsername("admin");
			user.setEmail("admin@noemail.it");
			user.setPassword("admin");
			
		}else{
			
			logger.info("admin "+user.getUsername());
			logger.info("admin "+user.getPassword());
			logger.info("admin "+user.getEmail());
			logger.info("admin "+user.getId());
			
		}
		
		
		logger.info("done test person");
		
	}

}
