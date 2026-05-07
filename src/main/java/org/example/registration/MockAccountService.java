package org.example.registration;

import org.example.enums.AccountStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockAccountService {
    
    private static Map<String, String> accounts = new HashMap<>();
    private static int nextId = 1;
    
    static {
        // Pre-populate with test account
        accounts.put("admin", "admin123");
        accounts.put("test", "test123");
    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        int id = 1;
        for (Map.Entry<String, String> entry : accounts.entrySet()) {
            accountList.add(new Account(id++, entry.getKey(), entry.getValue()));
        }
        return accountList;
    }

    public void insertAccount(String userName, String password) {
        accounts.put(userName, password);
        System.out.println("Mock: Account created - " + userName);
    }

    public void deleteAccount(int accountID) {
        System.out.println("Mock: Account deleted - ID: " + accountID);
    }

    public void updateAccount(int accountID, String username, String password) {
        accounts.put(username, password);
        System.out.println("Mock: Account updated - " + username);
    }

    public boolean isValidLogin(String username, String password) {
        String storedPassword = accounts.get(username);
        boolean valid = storedPassword != null && storedPassword.equals(password);
        System.out.println("Mock: Login attempt - " + username + " - " + (valid ? "SUCCESS" : "FAILED"));
        return valid;
    }
}
