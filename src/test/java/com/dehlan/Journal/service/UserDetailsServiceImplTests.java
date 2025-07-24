package com.dehlan.Journal.service;

import java.util.*;
import com.dehlan.Journal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.dehlan.Journal.entity.User;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/*
 * UserDetailsServiceImplTests
 * ----------------------------
 * ✅ Purpose:
 * Unit test class for the UserDetailsServiceImpl class which implements
 * Spring Security's UserDetailsService. It ensures that user details
 * are loaded correctly from the UserRepository.
 *
 * ✅ Tools/Frameworks Used:
 * - JUnit 5: For structuring test methods
 * - Mockito: For mocking dependencies (UserRepository)
 * - Lombok: Used in User entity for builder() method
 *
 * ✅ Annotations:
 * - @InjectMocks: Injects mocks into the class under test (UserDetailsServiceImpl)
 * - @Mock: Creates mock instance of UserRepository
 * - @BeforeEach: Initializes mocks before each test
 * - @Test: Marks the method as a unit test
 * | Annotation     | Purpose                       | Used On          | Creates                         |
| -------------- | ----------------------------- | ---------------- | ------------------------------- |
| `@Mock`        | Create mock object            | Dependencies     | Fake object                     |
| `@InjectMocks` | Inject mocks into real object | Class under test | Real object with mocks injected |

 *
 * ✅ Test Method:
 * 1. loadUserByUsernameTest():
 *    - Mocks the behavior of userRepository.findByUserName()
 *    - Uses builder() method from Lombok to create a mock User object
 *    - Validates that the service can load user details without throwing errors
 *
 * ⚠️ Notes:
 * - MockitoAnnotations.initMocks(this) is used to initialize @Mock and @InjectMocks fields.
 * - The test currently lacks assertions — it sets up behavior but doesn't verify output.
 *   You can improve this test by calling `userDetailsService.loadUserByUsername(...)`
 *   and asserting the returned UserDetails.
 *
 * ✅ Sample Improvement:
 *     UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
 *     assertEquals("ram", userDetails.getUsername());
 *
 */

//@ActiveProfiles("") - name of profile we want to use for tests can be passed here.
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram")
                .password("dsds").build());
    }

    /*
     * ✅ loadUserByUsernameTest2() — Mockito-Focused Test
     * ---------------------------------------------------
     * 🔸 Purpose:
     *   Unit test for userDetailsService.loadUserByUsername() using Mockito.

     * 🔸 Mockito Concepts Used:
     *   - when(...).thenReturn(...):
     *     Mocks the behavior of userRepository.findByUserName("ram")
     *     so it returns a custom-built User object.

     *   - Mocked Dependency:
     *     userRepository is mocked to isolate the service logic from DB layer.

     * 🔸 Flow:
     *   1. Arrange: Create mock User and set up repository behavior
     *   2. Act:     Call the actual service method
     *   3. Assert:  Verify expected username and password are returned
     */
    @Test
    void loadUserByUsernameTest2(){
        // Arrange
        User mockUser = User.builder()
                .userName("ram")
                .password("charan")
                .roles(new ArrayList<>(Arrays.asList("admin")))
                .build();

        when(userRepository.findByUserName("ram")).thenReturn(mockUser);

        // Act
        UserDetails user = userDetailsService.loadUserByUsername("ram");

        // Assert
        assertEquals("ram", user.getUsername());
        assertEquals("charan", user.getPassword());

        System.out.println("Happy Singh");
    }

}
