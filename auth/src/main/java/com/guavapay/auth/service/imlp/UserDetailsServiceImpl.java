package com.guavapay.auth.service.imlp;

import com.guavapay.auth.client.UsersClient;
import com.guavapay.auth.entity.User;
import com.guavapay.auth.exception.DataNotFoundException;
import com.guavapay.auth.security.jwt.JwtUserFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsServiceImpl")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersClient usersClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersClient.findUser(username).getBody();

        if (user == null) {
            throw new DataNotFoundException("Username not found");
        }

        System.out.println(user.toString());

        return JwtUserFactory.create(user);
    }
}
