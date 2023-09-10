package com.example.expencemanager;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.zip.DataFormatException;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject {
    private String type,account,catagory,note;
    private double amount;
    private Date date;
    @PrimaryKey
    private long id;

    public Transaction(){

    }
    public Transaction(String type, String catagory, String account, String note, double amount, Date date,long id) {
        this.type = type;
        this.account = account;
        this.catagory = catagory;
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
