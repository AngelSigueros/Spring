package com.certidevs.service;

import com.certidevs.model.User;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserService {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {

        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public User findById(Long id) {
        return User.builder().id(id).name("name1").lastName("lastName1").email("email1").password("password1").build();
    }
}
