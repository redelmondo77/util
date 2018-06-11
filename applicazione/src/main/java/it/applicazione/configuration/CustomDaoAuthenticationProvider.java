package it.applicazione.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) 
			throws AuthenticationException {

		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		if (username != null) { if(username.length()<3)	
		{
			
			String[] errorArgs = { ""+username.length() };
			
			throw new BadCredentialsException(
				messages.getMessage("CustomDaoAuthenticationProvider.usersmall", errorArgs, "troppo piccola user")
			);}
		
		}
		//UsernamePasswordAuthenticationToken
		String pwd = (authentication.getCredentials() == null) ? "NONE_PROVIDED"
				: (String) authentication.getCredentials();

		if (pwd != null) { if(pwd.length()<3)	{
			
			String[] errorArgs = { ""+pwd.length() };
			
			throw new BadCredentialsException(
				messages.getMessage("CustomDaoAuthenticationProvider.pwdsmall", errorArgs, "troppo piccola pwd")
				);}
		
		}
	//SimpleUrlAuthenticationFailureHandler
	return super.authenticate(authentication);
	
	}
	
}	
	
	
	