package com.example.expencemanager;

public class Account {
    private String accountName;
    private double accountAmount;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public Account(){

    }
    public Account(String accountName, double accountAmount) {
        this.accountName = accountName;
        this.accountAmount = accountAmount;
    }
}
