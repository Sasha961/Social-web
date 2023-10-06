package com.skillbox.socialnetwork.services;

import com.skillbox.socialnetwork.dto.user.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public interface UserService extends UserDetailsService {

	@Override
	@Transactional
	CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

//	@Autowired
//	void setRoleService(RoleService roleService);

//	@Autowired
//	void setPasswordEncoder(PasswordEncoder passwordEncoder);

//	@Autowired
//	void setUserRepository(UserRepository userRepository);

//	Optional<User> findByUsername(String username);
}
