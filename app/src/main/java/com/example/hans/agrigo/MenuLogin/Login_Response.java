package com.example.hans.agrigo.MenuLogin;

import com.example.hans.agrigo.Storage.User;

public class Login_Response {
    private boolean error;
    private String message;
    private User user;

    public Login_Response (boolean app, String message, User user) {
        this.error = app;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }


}
