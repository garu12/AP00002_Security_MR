package co.com.example.authentication_security.service;

import co.com.example.authentication_security.entity.User;

public interface UserService {

    void saveUser(User user);

    User authenticate(String username, String password);

}
