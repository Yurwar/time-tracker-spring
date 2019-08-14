package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.dto.UpdateUserDTO;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameFail() {
        doReturn(null).when(userRepository).findByUsername(ArgumentMatchers.anyString());

        userService.loadUserByUsername("MissingUser");
    }

    @Test
    public void updateUser() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("UpdatedFirstName");
        updateUserDTO.setLastName("UpdatedLastName");
        updateUserDTO.setUsername("UpdatedUsername");
        updateUserDTO.setPassword("UpdatedPassword");
        updateUserDTO.setAuthorities(Set.of(Authority.USER, Authority.ADMIN));

        doReturn("old encrypted password")
                .when(passwordEncoder)
                .encode("OldPassword");

        doReturn("updated encrypted password")
                .when(passwordEncoder)
                .encode("UpdatedPassword");

        User user = User.builder()
                .id(1L)
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .username("OldUsername")
                .password(passwordEncoder.encode("OldPassword"))
                .enabled(true)
                .authorities(Collections.singleton(Authority.USER))
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .username("UpdatedUsername")
                .password(passwordEncoder.encode("UpdatedPassword"))
                .enabled(true)
                .authorities(Set.of(Authority.USER, Authority.ADMIN))
                .build();

        doReturn(Optional.of(user))
                .when(userRepository)
                .findById(1L);

        userService.updateUser(1L, updateUserDTO);

        verify(userRepository, times(1)).save(expectedUser);
    }
}