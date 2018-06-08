package it.applicazione.person;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    private static final String REQUIRED = "required";
	private static final String PASSWMIN = "passwordMin";

    @Override
    public void validate(Object obj, Errors errors) {

		User user = (User) obj;
		String name = user.getUsername();

        if (!StringUtils.hasLength(name)) {
			errors.rejectValue("username", REQUIRED, REQUIRED);
        }

		if (user.getPassword() == null || StringUtils.isEmpty(user.getPassword())) {
			errors.rejectValue("password", REQUIRED, REQUIRED);
		}

		int min = 4;
		String[] errorArgs = { Integer.toString(min) };
		if (user.getPassword().length() < min) {
			errors.rejectValue("password", PASSWMIN, errorArgs, PASSWMIN);
		}

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

}
