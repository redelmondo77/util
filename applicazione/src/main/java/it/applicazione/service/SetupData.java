package it.applicazione.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.esperimento.service.EsperimentoGroupService;
import it.applicazione.esperimento.service.EsperimentoService;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.InternalPersonRepository;
import it.applicazione.person.InternalPersonService;
import it.applicazione.person.Privilege;
import it.applicazione.person.PrivilegeRepository;
import it.applicazione.person.PrivilegeService;
import it.applicazione.person.Role;
import it.applicazione.person.UserRepository;
import it.applicazione.person.UserService;


//@Service
@Component
public class SetupData {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Value("${configuration.applicazione.admin}")
	private String admin;
	
	@Value("${configuration.applicazione.admin-pwd}")
	private String adminPwd;
	
	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;
	
	@Autowired
	PrivilegeService privilegeService;
	
	@Autowired
	InternalPersonService internalPersonService;
	
    @Autowired
    EsperimentoGroupService esperimentoGroupService;
    
    @Autowired
    EsperimentoService esperimentoService;
	
	@PostConstruct
	public void firstTime() {
		
		
		
		Collection<Privilege> privilegeList = privilegeService.findAll();
		
		if(privilegeList.isEmpty()){
			initPrivileges();
		}
		
		
		it.applicazione.person.User user = userService.cacheFindByUsernameFetchPersonAndRoles(admin.toLowerCase());
		
		if(user==null){
	        initUsers(admin);
		}
		
		
		user = userService.cacheFindByUsernameFetchPersonAndRoles("USER".toLowerCase());
		
		if(user==null){
	        initUsers("USER");
		}
		
		user = userService.cacheFindByUsernameFetchPersonAndRoles("BACKOFFICE".toLowerCase());
		
		if(user==null){
	        initUsers("BACKOFFICE");
		}
		
		
		Collection<EsperimentoGroup> esperimentoGroupList = esperimentoGroupService.findByInfo("");
		
		if(esperimentoGroupList.isEmpty()){
			initEsperimentoGroupList();
		}
		
		

	}
	
	
	
	
	
	
	private void initUsers(String name) {
		
		logger.warn("init user: "+name.toLowerCase());
		Role role = new Role();
		role.setDescription(name.toUpperCase());
		
		

		
		it.applicazione.person.User user = new it.applicazione.person.User();
		user.setUsername(name.toLowerCase());
		user.setPassword(passwordEncoder.encode(adminPwd));
		user.addRole(role);
		
		
	    Privilege privilege1 = privilegeService.findByName("ESPERIMENTO_READ");
	    //user.setPrivileges(new HashSet<Privilege>(Arrays.asList(privilege1)));
	    user.addPrivilege(privilege1);
	    if(!("BACKOFFICE".equals(name.toUpperCase())  ||  "ADMIN".equals(name.toUpperCase()))){
	    	Privilege privilege2 = privilegeService.findByName("ESPERIMENTO_WRITE");
	    	//user.setPrivileges(new HashSet<Privilege>(Arrays.asList(privilege2)));
	    	user.addPrivilege(privilege2);
	    }
		
		InternalPerson internalPerson = new InternalPerson();
		internalPerson.setFirstName(name.toLowerCase());
		internalPerson.setLastName(name.toLowerCase());
		internalPerson.addUser(user);
		
		logger.warn("init internalPerson: "+name.toLowerCase());
		internalPersonService.save(internalPerson);
	}
	
	private void initPrivileges() {
		logger.warn("initPrivileges: ESPERIMENTO_READ");
	    Privilege privilege1 = new Privilege("ESPERIMENTO_READ");
	    privilegeService.save(privilege1);
	    logger.warn("initPrivileges: ESPERIMENTO_WRITE");
	    Privilege privilege2 = new Privilege("ESPERIMENTO_WRITE");
	    privilegeService.save(privilege2);
	}
	
	
	private void initEsperimentoGroupList(){
		
		EsperimentoGroup esperimentoGroup = new EsperimentoGroup();
		logger.warn("initEsperimentoGroupList : test esperim 1");
		esperimentoGroup.setInfo("testes12345678");
		esperimentoGroup.setName("testes12345678");
		
		it.applicazione.person.User user = userService.cacheFindByUsernameFetchPersonAndRoles(admin);
		InternalPerson internalPerson = user.getInternalPerson();
		esperimentoGroup.setInternalPerson(internalPerson);
		
		Esperimento esperimento = new Esperimento();
		esperimento.setFase1("fa1info");
		esperimento.setFase2("fainfo");
		esperimento.setInfo("infoe");
		esperimento.setEsperimentoGroup(esperimentoGroup);
		
		esperimentoGroup.addEsperimento(esperimento);
		
		logger.warn("initPrivileges: ESPERIMENTO_WRITE");
		esperimentoGroupService.save(esperimentoGroup);
		
	}
	
	

}
