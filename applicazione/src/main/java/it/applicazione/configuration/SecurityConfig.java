package it.applicazione.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.applicazione.person.InternalPerson;
import it.applicazione.person.InternalPersonRepository;
import it.applicazione.person.Person;
import it.applicazione.person.Role;
import it.applicazione.person.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ApplicazioneConfiguration applicazioneConfiguration;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InternalPersonRepository internalPersonRepository;
	
	@Value("${spring.h2.console.enabled}")
	private boolean h2Console;
	
	@Value("${configuration.applicazione.admin}")
	private String admin;
	
	@Value("${configuration.applicazione.admin-pwd}")
	private String adminPwd;
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}
	
	private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
	
	
    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
            .authorizeRequests()
				.antMatchers("/", "/home", "/resources/**", "/welcome/**", "/webjars/**"
						).permitAll()
				
				.and().authorizeRequests().antMatchers("/h2").permitAll()
				.and().authorizeRequests().antMatchers("/h2/**").permitAll()
				
				.anyRequest()
				.authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and()
				.logout().logoutSuccessUrl("/logoutDone")
				.permitAll();
		
		
		//Disable CRSF (Cross-Site Request Forgery). By default, Spring Security will protect against CRSF attacks.
		//Since the H2 database console runs inside a frame, you need to enable this in in Spring Security.
		if(h2Console){
			logger.warn("console h2 attiva, csrf protection disabled");
			http.csrf().disable();
			http.headers().frameOptions().disable();
			firstTime();
		}else{
			logger.warn("console h2 disabled");
		}
		
	}
	

	public void firstTime() {
		
		it.applicazione.person.User user = userRepository.findByUsername(admin);
		
		if(user==null){
			logger.warn("insert new admin user: "+admin);
			
			Role role = new Role();
			role.setDescription("ADMIN");
			
			user = new it.applicazione.person.User();
			user.setUsername(admin);
			user.setPassword(passwordEncoder.encode(adminPwd));
			user.addRole(role);
			
			InternalPerson internalPerson = new InternalPerson();
			internalPerson.setFirstName(admin);
			internalPerson.setLastName(admin);
			internalPerson.addUser(user);
			
			internalPersonRepository.save(internalPerson);
			
		}else{
			logger.warn("admin user: "+user.getUsername()+" "+user.getEmail());
		}

	}
	
}
