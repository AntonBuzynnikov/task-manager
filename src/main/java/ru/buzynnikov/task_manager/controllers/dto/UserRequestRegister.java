package ru.buzynnikov.task_manager.controllers.dto;

public class UserRequestRegister {
    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRequestRegister(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRequestRegister() {
    }
}
