package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.dto.UpdateUserDTO;
import com.yurwar.trainingcourse.entity.Authority;
import com.yurwar.trainingcourse.entity.User;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import com.yurwar.trainingcourse.repository.UserRepository;
import com.yurwar.trainingcourse.util.exception.LoginNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       ActivityRepository activityRepository,
                       MessageSource messageSource,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by("id"));
    }

    public Page<User> findAllUsersPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id: " + id));
    }

    public void saveUser(RegistrationUserDTO userDTO) {
        try {
            User user = User
                    .builder()
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .enabled(true)
                    .authorities(Collections.singleton(Authority.USER))
                    .build();
            userRepository.save(user);
            log.info("New user " + user);
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getUsername());
            throw new LoginNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + userDTO.getUsername(), e);
        }
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(long id, UpdateUserDTO userDTO) {
        User user = findUserById(id);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        if (Objects.nonNull(userDTO.getPassword()) && userDTO.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user.setAuthorities(userDTO.getAuthorities());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getUsername());
            throw new LoginNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + userDTO.getUsername(), e);
        }
    }

}
