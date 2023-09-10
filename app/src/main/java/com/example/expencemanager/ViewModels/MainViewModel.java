package com.example.expencemanager.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expencemanager.HelpterFunctions;
import com.example.expencemanager.Transaction;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setUpDatabase();
    }
    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    Realm realm;
    Calendar calendar;
    void setUpDatabase(){
        realm = Realm.getDefaultInstance();
    }

    public void getTransactions(Calendar calendar){
        this.calendar = calendar;
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        double total = 0;
        double income = 0;
        double expense = 0;
        RealmResults<Transaction> transactionArrayList = null;

        if(HelpterFunctions.SELCTED_TAB==HelpterFunctions.DAILY) {
           transactionArrayList = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .findAll();
          // transactions.setValue(transactionArrayList);

             total = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .sum("amount").doubleValue();
            income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", "income")
                    .sum("amount").doubleValue();
            expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", "expense")
                    .sum("amount").doubleValue();
        }else if(HelpterFunctions.SELCTED_TAB==HelpterFunctions.MONTHLY){
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();
            transactionArrayList = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .findAll();

            total = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .sum("amount").doubleValue();
            income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .equalTo("type", "income")
                    .sum("amount").doubleValue();
            expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .equalTo("type", "expense")
                    .sum("amount").doubleValue();

        }
        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);
        transactions.setValue(transactionArrayList);

    }
    public void AddTransactions(Transaction transaction){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transaction);
        realm.commitTransaction();
    }
    public void deleteTransaction(Transaction transaction){
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
        getTransactions(calendar);
    }
    public void AddTransactions(){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new Transaction("income","Business","Bank","Amount Received",500.00,new Date(),new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction("income","Salary","PayTM","Amount Received",9000.00,new Date(),new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction("Expense","Rent","EasyPaisa","Amount Received",250.00,new Date(),new Date().getTime()));
        // TO here
        realm.commitTransaction();
    }


}
