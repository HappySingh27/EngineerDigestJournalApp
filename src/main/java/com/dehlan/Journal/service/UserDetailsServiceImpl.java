package com.dehlan.Journal.service;

import com.dehlan.Journal.entity.User;
import com.dehlan.Journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/*
*
* UserDetailsServiceImpl is a custom implementation of Spring Security's UserDetailsService.
* Spring uses this class to load user data (username, password, roles) during authentication.
* We fetch the user from the database by username, and return it as a UserDetails object.
* If the user is not found, a UsernameNotFoundException is thrown, which stops authentication.
*
*/

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    /*
     *
     * This method does not directly authenticate (verify) the password.
     * Instead, it returns a UserDetails object containing:
     *   - username
     *   - encoded password from the database
     *   - user roles/authorities
     *
     * Spring Security internally compares:
     *   - The password provided in the login request
     *   - With the encoded password returned in this UserDetails object
     *
     * If they match (using the configured PasswordEncoder), authentication succeeds.
     * So yes, password authentication happens — but it's handled by Spring Security,
     * not manually inside this method.
     *
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles((user.getRoles().toArray(new String[0])))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with User Name" + username);
    }
}
