package com.dg.mdsrose.user;

import com.dg.mdsrose.user.model.User;

public class UserSession {
    private static UserSession instance;
    private Long userId;
    private String username;

    private UserSession() { }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void clearSession() {
        userId = null;
        username = null;
    }

    public void init(User user) {
        userId = user.getId();
        username = user.getUsername();
    }
}