package it.applicazione.security;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import it.applicazione.model.OwnedEntityInterface;
import it.applicazione.person.UserService;
/*
 * UserDetails(admin):  ESPERIMENTO_READ_PRIVILEGE, ROLE_ADMIN
 * 
 * hasRole('ADMIN')	or hasAuthority('ROLE_ADMIN')	:	
 *  SecurityExpressionRoot:  
 *   [ESPERIMENTOGROUP_READ_PRIVILEGE, ROLE_ADMIN] contains ROLE_ADMIN
 * 
 * privilege with:
 * hasAuthority('ESPERIMENTOGROUP_READ_PRIVILEGE')
 * 
 * hasPermission(#esperimentoGroupId,'it.applicazione.esperimento.model.EsperimentoGroup','read_owned_privilege')
 * 
 * 
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	UserService userService;
	
	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		
		if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
			return false;
		}
		
		String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

		return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
		
	}

	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		
		if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}
		
		String entityClass = null;
		try {
			entityClass = Class.forName(targetType).getName();
			targetType =  Class.forName(targetType).getSimpleName();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		boolean  hasPrivilege = hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
	
		if(hasPrivilege){
			if( permission.toString().toUpperCase().contains("OWNED") ){
				hasPrivilege = testOwned(auth, targetId, entityClass, permission);
			}
		}
		
		return hasPrivilege;
	}

	private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
		
		for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
			if (grantedAuth.getAuthority().startsWith(targetType)) {
				if (grantedAuth.getAuthority().contains(permission)) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	private boolean testOwned(Authentication auth, Serializable targetId, String targetType, Object permission){
		
		Class<?> c;
		try {
			c = Class.forName(targetType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		Object entity = em.find(c, targetId);
		
		if(entity==null){return false;}
			
		if( OwnedEntityInterface.class.isInstance(entity) ){
			
			OwnedEntityInterface ownedEntity = (OwnedEntityInterface) entity;
		  	Long idAuth = userService.cacheFindByUsernameFetchPersonAndRoles(auth.getName()).getInternalPerson().getId();
		  	Long idEntity =  ownedEntity.getInternalPerson().getId();
			
			if( idAuth!= idEntity){
				return false;
			}
		}

		return true;
		
		
	}
	

}