package com.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.dto.account.AccountDto;
import com.user.dto.response.AccountResponseDto;
import com.user.dto.secure.AccountSecureDto;
import com.user.exception.EmailIsBlank;
import com.user.exception.EmailNotUnique;
import com.user.model.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AccountResponseDto getUserByEmail(String email) {
        try {
            User user = userRepository.findUserByEmail(email);
            if (user != null) {
                return new AccountResponseDto(new AccountSecureDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                        user.getPassword(), user.getRoles()), true);
            } else {
                throw new UsernameNotFoundException("user with email: " + email + " not found");
            }
        } catch (Exception ex) {
            throw new UsernameNotFoundException("user with email: " + email + " not found");
        }
    }

    @Override
    public AccountResponseDto createUser(AccountSecureDto accountSecureDto) {
        try {
            if (accountSecureDto.getEmail().isEmpty() || accountSecureDto.getEmail().isBlank()) {
                throw new EmailIsBlank("email is blank");
            }

            Optional<com.user.model.User> tempUser = Optional.ofNullable(userRepository.findUserByEmail(accountSecureDto.getEmail()));
            if (tempUser.isEmpty()) {
                System.out.println(accountSecureDto);
                User user = new User(accountSecureDto);
                user.setRoles("ROLE_USER");
                userRepository.save(user);
                System.out.println(user);
                return new AccountResponseDto(new AccountSecureDto(user.getId(), user.getFirstName(),
                        user.getLastName(), user.getEmail(), user.getPassword(), user.getRoles()), true);
            } else {
                throw new EmailNotUnique("email " + accountSecureDto.getEmail() + " not unique");
            }
        } catch (EmailNotUnique exception) {
            throw new EmailNotUnique("email " + accountSecureDto.getEmail() + " not unique");
        } catch (EmailIsBlank exception) {
            throw new EmailIsBlank("email is blank");
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Transactional
    public AccountResponseDto editUser(AccountDto accountDto) {
        Optional <User> oldUser = Optional.ofNullable(userRepository.getReferenceById(Long.parseLong(accountDto.getId())));
        User tempUser = new User();
        if (oldUser.isPresent() &&oldUser.get().getEmail().equalsIgnoreCase(tempUser.getEmail())
                && oldUser.get().getId().equals(tempUser.getId())) {
            //Изменение юзера мепим все один к 1.

            //what we can change?
            userRepository.save(oldUser.get());
        }
        return null;
    }

    @Override
    public ResponseEntity<List<AccountDto>> searchUser(String username, String offset, String limit) {
        if (username == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Pageable page = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));

        String[] fullName = username.toLowerCase().split(" ");
        String firstName = null;
        String lastName = null;

        if (fullName.length < 2) {
            firstName = fullName[0];
            lastName = fullName[0];
        } else {
            firstName = fullName[0];
            lastName = fullName[1];
        }
        Specification<User> specification = Specification.where(null);
        String finalFirstName = firstName;
        String finalLastName = lastName;
        specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), String.format("%%%s%%", finalFirstName))));
        specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), String.format("%%%s%%", finalLastName))));

        ObjectMapper objectMapper = new ObjectMapper();
        Page<User> userList = userRepository.findAll(specification, page);
        List<AccountDto> accountList = userList.stream().map(user -> objectMapper.convertValue(user, AccountDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @Override
    public User getUserById(Long id) {
        Optional <User> user = Optional.ofNullable(userRepository.getReferenceById(id));
        return user.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Long deleteUserById(Long id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public void blockUser(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        user.ifPresent(() -> {
//            user.get().setBlocked(!user.get().isBlocked());
//            userRepository.save(user.get());
//        });
    }
}