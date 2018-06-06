package it.applicazione.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ApplicazioneConfiguration applicazioneConfiguration;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {


		http
            .authorizeRequests()
				.antMatchers("/", "/home", "/resources/**", "/welcome/**", "/webjars/**"
						).permitAll()
				.anyRequest()
				.authenticated()
				.and().formLogin().loginPage("/login").permitAll()
                .and()
				.logout().logoutSuccessUrl("/logout")
				.permitAll();


	}
	

	
	@Bean
    @Override
    public UserDetailsService userDetailsService() {
       
		Collection<UserDetails> users = new ArrayList<UserDetails>();
		
		for(String utenti:  applicazioneConfiguration.getUtenti()  ){
			
			String user = (utenti.split("/"))[0];
			String pwd = (utenti.split("/"))[1];
			String ruolo = (utenti.split("/"))[2];
			
			UserDetails userDetails =
		             User.withDefaultPasswordEncoder()
		                .username(user)
		                .password(pwd)
		                .roles(ruolo)
		                .build();
		    			
		    users.add(userDetails);
			
		}

        return new InMemoryUserDetailsManager(users);
    }
	
	
	
}
