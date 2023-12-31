package ru.skillbox.group39.socialnetwork.authorization.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skillbox.group39.socialnetwork.authorization.security.model.Person;

public interface UserService extends UserDetailsService {

	@Override
	Person loadUserByUsername(String email) throws UsernameNotFoundException;

}
