package pl.coderslab.login.service;

//package com.gpch.login.service;

////import com.gpch.login.model.User;
////import com.gpch.login.repository.RoleRepository;
////import com.gpch.login.repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import pl.coderslab.login.repository.RoleRepository;
//import pl.coderslab.login.repository.UserRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.login.entity.Role;
import pl.coderslab.login.entity.User;
import pl.coderslab.login.repository.RoleRepository;
import pl.coderslab.login.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository,
                mockRoleRepository,
                mockBCryptPasswordEncoder);

        user = User.builder()
                .id(1)
                .name("Gustavo")
                .lastName("Ponce")
                .email("czarny@gmail.com")
                .active(1)
//                .roles(new HashSet<>(Arrays.asList(mockRoleRepository.findByRole("ADMIN"))))
                .build();

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(user);
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        final User result = userServiceUnderTest.findUserByEmail(email);
        System.out.println(result);

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    //
    @Test
    public void testSaveUser() {
        // Setup
        final String email = "test@test.com";

        final int active = 1;
//tu wskazuje na userService
        // Run the test
        User result = userServiceUnderTest.saveUser(User.builder().build());
//                User result = userServiceUnderTest.saveUser(user);
        System.out.println("saveUser" + result);
        System.out.println(User.builder().build());
//        User result = userServiceUnderTest.saveUser(user);


        // Verify the results
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getActive(), result.getActive());

    }
}