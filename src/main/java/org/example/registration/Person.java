package org.example.registration;

import org.example.enums.AccountType;
import org.example.hotel.Address;

public abstract class Person {
    private String name;
    private Address address;
    private String email;
    private String phone;
    private AccountType accountType;
    private Account account;

    public Person(String name, Address address, String email, String phone, AccountType accountType, Account account) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.accountType = accountType;
        this.account = account;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
