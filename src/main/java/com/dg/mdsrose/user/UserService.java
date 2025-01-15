package com.dg.mdsrose.user;

import com.dg.mdsrose.exception.UserAlreadyExistsException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserService {
    private static UserService instance;

    private final UserDAO userDAO = UserDAO.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userDAO.findByUsername(username);
        if(user.isPresent()) {
            if(BCrypt.checkpw(password,user.get().getPassword())) {
                UserSession userSession = UserSession.getInstance();
                userSession.setUserId(user.get().getId());
                userSession.setUsername(user.get().getUsername());
                return user;
            }
        }
        return Optional.empty();
    }

    public void signup(String username, String password, String firstname, String lastname) {
        if(userDAO.existsByUsername(username)){
            throw new UserAlreadyExistsException(username);
        }
        User userToInsert = new User(username, password, firstname, lastname);
        userDAO.insert(userToInsert);
    }
}
