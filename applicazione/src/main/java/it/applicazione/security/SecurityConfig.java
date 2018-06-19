package it.applicazione.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import it.applicazione.configuration.ApplicazioneConfiguration;
import it.applicazione.person.InternalPersonRepository;
import it.applicazione.person.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
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
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new CustomDaoAuthenticationProvider();
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
						, "/about/**", "/contact/**"
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
		}else{
			logger.warn("console h2 disabled");
		}
		
	}
	

}
