package com.example.demo.service.security;

import com.example.demo.model_example.User;
//import com.example.demo.repository_example.UserRepository;
import com.example.demo.service.security.JWT.JwtUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

//    private final UserRepository userRepository;

//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

    @Override
//    @Transactional
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("'%s' not found ", username)));
        JwtUser jwtUser = new JwtUser();
        jwtUser.setUsername("admin");
        //пароль 12345
        jwtUser.setPassword("$2a$12$vkVa/f9AJoBDxk7Qw0ZZuuq0g8SheHdLa4M4TFheXnWwFaTxJ4es6");
        jwtUser.setUserId("1");
        jwtUser.setEnabled(true);

        return jwtUser;
    };
}
