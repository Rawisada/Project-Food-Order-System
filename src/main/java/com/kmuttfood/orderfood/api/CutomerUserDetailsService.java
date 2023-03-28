package com.kmuttfood.orderfood.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kmuttfood.orderfood.entity.User;

public class CutomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CutomerUserDetails(user);

    }
}
