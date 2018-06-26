package it.applicazione.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

	private static final Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private static final String REQUIRED = "required";
    
    @Override
    public void validate(Object obj, Errors errors) {

		User user = (User) obj;

        if (StringUtils.hasLength(user.getUsername())) {
			if(user.getUsername().equals(user.getPassword()))
				errors.rejectValue("password","UserValidator.passwordEqualToUsername", "passwordEqualToUsername"); 
        }
        
		if (StringUtils.isEmpty(user.getPassword())) {
			errors.rejectValue("password", REQUIRED, REQUIRED);
		}else{
			if(!p.matcher(user.getPassword()).matches())
				errors.rejectValue("password", "UserValidator.password.notInPattern", "password notInPattern");
		}

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

}
