package com.hotel;
public class Account {
    private String id;
    private String password;
    private AccountStatus status;

    public Account(String id, String password, AccountStatus status) {
        this.id = id;
        this.password = password;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public boolean resetPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        if(status != AccountStatus.ACTIVE){
            return false;
        }
        this.password = newPassword;
        return true;
    }
}

