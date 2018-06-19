package it.applicazione.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
 
	@Autowired
	private CustomPermissionEvaluator permissionEvaluator;
	
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		// GlobalMethodSecurityConfiguration
		 DefaultMethodSecurityExpressionHandler m = (DefaultMethodSecurityExpressionHandler) super.createExpressionHandler();
		 m.setPermissionEvaluator(permissionEvaluator);
		 return m;
	}
	
	/*
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
    	DefaultMethodSecurityExpressionHandler expressionHandler = 
          new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }
    */
}