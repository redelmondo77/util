package it.applicazione.configuration;

import java.util.Collection;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import it.applicazione.esperimento.model.Esperimento;
import it.applicazione.esperimento.model.EsperimentoGroup;
import it.applicazione.esperimento.model.State;
import it.applicazione.esperimento.repository.StateRepository;
import it.applicazione.esperimento.service.EsperimentoGroupService;
import it.applicazione.esperimento.service.EsperimentoService;
import it.applicazione.person.InternalPerson;
import it.applicazione.person.InternalPersonService;
import it.applicazione.person.Privilege;
import it.applicazione.person.PrivilegeService;
import it.applicazione.person.Role;
import it.applicazione.person.RoleService;
import it.applicazione.person.UserService;

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
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    StateRepository stateRepository;
	
	@PostConstruct
	public void firstTime() {
		
		
		Collection<Privilege> privilegeList = privilegeService.findAll();
		if(privilegeList.isEmpty())	initPrivileges();
		
		Role role = roleService.findByName(admin.toUpperCase());
		if(role==null) initRole(admin);
		
		Role role2 = roleService.findByName("USER".toUpperCase());
		if(role2==null) initRole("USER");
		
		Role role3 = roleService.findByName("BACKOFFICE".toUpperCase());
		if(role3==null) initRole("BACKOFFICE");
		
		it.applicazione.person.User user = userService.cacheFindByUsernameFetchPersonAndRoles(admin.toLowerCase());
		if(user==null)initUsers(admin);
		
		user = userService.cacheFindByUsernameFetchPersonAndRoles("USER".toLowerCase());
		if(user==null) initUsers("USER");
		
		user = userService.cacheFindByUsernameFetchPersonAndRoles("BACKOFFICE".toLowerCase());
		if(user==null) initUsers("BACKOFFICE");
		
		Collection<EsperimentoGroup> esperimentoGroupList = esperimentoGroupService.findByInfo("");
		if(esperimentoGroupList.isEmpty()) initEsperimentoGroupList();
		
	}
	
	
	private void initRole(String name) {
		
		Role role = new Role();
		role.setName(name.toUpperCase());
		
	    Privilege privilegeRole1 = privilegeService.findByName("ESPERIMENTOGROUP_READ");
	    Privilege privilegeRole2 = privilegeService.findByName("ESPERIMENTOGROUP_WRITE");
	    Privilege privilegeRole3 = privilegeService.findByName("ESPERIMENTOGROUP_READ_OWNED");
	    Privilege privilegeRole4 = privilegeService.findByName("ESPERIMENTOGROUP_WRITE_OWNED");
		
	    if("BACKOFFICE".equals(name.toUpperCase())){
	    	role.addPrivilege(privilegeRole1);
	    	role.addPrivilege(privilegeRole4);
	    }
	    if("USER".equals(name.toUpperCase())){
	    	role.addPrivilege(privilegeRole3);
	    	role.addPrivilege(privilegeRole4);
	    }
	    if("ADMIN".equals(name.toUpperCase())){
	    	role.addPrivilege(privilegeRole1);
	    	role.addPrivilege(privilegeRole2);
	    }
	    roleService.save(role);
	}
	
	
	
	private void initUsers(String name) {
		
		logger.warn("init user: "+name.toLowerCase());
		
	    
		Role role = roleService.findByName(name.toUpperCase());
		
		it.applicazione.person.User user = new it.applicazione.person.User();
		user.setUsername(name.toLowerCase());
		user.setPassword(passwordEncoder.encode(adminPwd));
		user.addRole(role);
		
		
	    Privilege privilege1 = privilegeService.findByName("ESPERIMENTOGROUP_READ");
	    Privilege privilege2 = privilegeService.findByName("ESPERIMENTOGROUP_WRITE");
	    Privilege privilege3 = privilegeService.findByName("ESPERIMENTOGROUP_READ_OWNED");
	    Privilege privilege4 = privilegeService.findByName("ESPERIMENTOGROUP_WRITE_OWNED");
	    
	    if("BACKOFFICE".equals(name.toUpperCase())){
	    	// Privilege from role
	    	//user.addPrivilege(privilege1);
	    }
	    if("USER".equals(name.toUpperCase())){
	    	// Privilege from role
	    	//user.addPrivilege(privilege3);
	    	//user.addPrivilege(privilege4);
	    }
	    if("ADMIN".equals(name.toUpperCase())){
	    	// Privilege from role
	    	//user.addPrivilege(privilege1);
	    	//user.addPrivilege(privilege2);
	    }
			
		InternalPerson internalPerson = new InternalPerson();
		internalPerson.setFirstName(name.toLowerCase());
		internalPerson.setLastName(name.toLowerCase());
		internalPerson.addUser(user);
		
		logger.warn("init internalPerson: "+name.toLowerCase());
		internalPersonService.save(internalPerson);
	}
	
	private void initPrivileges() {
		logger.warn("initPrivileges: ESPERIMENTOGROUP_READ");
	    Privilege privilege1 = new Privilege("ESPERIMENTOGROUP_READ");
	    privilegeService.save(privilege1);
	    logger.warn("initPrivileges: ESPERIMENTOGROUP_WRITE");
	    Privilege privilege2 = new Privilege("ESPERIMENTOGROUP_WRITE");
	    privilegeService.save(privilege2);
		logger.warn("initPrivileges: ESPERIMENTOGROUP_READ_SELF");
	    Privilege privilege3 = new Privilege("ESPERIMENTOGROUP_READ_OWNED");
	    privilegeService.save(privilege3);
	    logger.warn("initPrivileges: ESPERIMENTOGROUP_WRITE_SELF");
	    Privilege privilege4 = new Privilege("ESPERIMENTOGROUP_WRITE_OWNED");
	    privilegeService.save(privilege4);
	}
	
	
	private void initEsperimentoGroupList(){
		
		
		State sPrealpha = new State("Pre-alpha");
		stateRepository.save(sPrealpha);
		stateRepository.save(new State("Alpha"));
		stateRepository.save(new State("Beta"));
		stateRepository.save(new State("RC"));
		stateRepository.save(new State("Release"));
		
		
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
		esperimento.setState(sPrealpha);
		esperimento.setEsperimentoGroup(esperimentoGroup);
		
		esperimentoGroup.addEsperimento(esperimento);
		
		logger.warn("initPrivileges: ESPERIMENTO_WRITE");
		esperimentoGroupService.save(esperimentoGroup);
		
	}
	
	

}
