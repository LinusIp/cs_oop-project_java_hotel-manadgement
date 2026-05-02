package org.example;

public class Account {
    private int ID;
    private String id;
    private String username;
    private String password;
    private AccountStatus status;

    public Account(String id, String password, AccountStatus status) {
        this.id = id;
        this.username = id;
        this.password = password;
        this.status = status;
    }

    public Account(int ID, String username, String password) {
        this.ID = ID;
        this.id = String.valueOf(ID);
        this.username = username;
        this.password = password;
        this.status = AccountStatus.ACTIVE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
        this.id = String.valueOf(ID);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if (this.username == null || this.username.isBlank()) {
            this.username = id;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (status != AccountStatus.ACTIVE) {
            return false;
        }
        this.password = newPassword;
        return true;
    }
}
