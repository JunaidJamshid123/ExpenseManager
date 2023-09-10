package com.example.expencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expencemanager.ViewModels.MainViewModel;
import com.example.expencemanager.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Toolbar toolbar;
    //int isSelected=0;
   public static Calendar calendar;

    public static MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        getSupportActionBar().setTitle("Transaction");
        binding.Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottonFragmentsheet().show(getSupportFragmentManager(),null);
            }
        });

        calendar = Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyyy");
            binding.date.setText(simpleDateFormat.format(calendar.getTime()));
        }

        binding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HelpterFunctions.SELCTED_TAB==0){
                    calendar.add(Calendar.DATE,-1);
                }else{
                    calendar.add(Calendar.MONTH,-1);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyyy");
                    SimpleDateFormat simpleDateFormat;
                    if(HelpterFunctions.SELCTED_TAB==0){
                        simpleDateFormat = new SimpleDateFormat("dd MMM yyyyy");
                    }else{
                        simpleDateFormat = new SimpleDateFormat("MMM yyyyy");
                    }
                    binding.date.setText(simpleDateFormat.format(calendar.getTime()));
                    mainViewModel.getTransactions(calendar);

                }
                updateDate();
            }
        });

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HelpterFunctions.SELCTED_TAB==0){
                    calendar.add(Calendar.DATE,1);
                }else{
                    calendar.add(Calendar.MONTH,1);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    SimpleDateFormat simpleDateFormat;
                    if(HelpterFunctions.SELCTED_TAB==0){
                        simpleDateFormat = new SimpleDateFormat("dd MMM yyyyy");
                    }else{
                        simpleDateFormat = new SimpleDateFormat("MMM yyyyy");
                    }
                    binding.date.setText(simpleDateFormat.format(calendar.getTime()));
                    mainViewModel.getTransactions(calendar);

                }
                updateDate();


            }
        });
        HelpterFunctions.setCatagory();

        // Code Here
        binding.TransactioLayoutMain.setLayoutManager(new LinearLayoutManager(this));
       mainViewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
           @Override
           public void onChanged(RealmResults<Transaction> transactions) {
               TransactionAdapter transactionAdapter = new TransactionAdapter(MainActivity.this,transactions);

               binding.TransactioLayoutMain.setAdapter(transactionAdapter);
           }
       });
       mainViewModel.getTransactions(calendar);

       mainViewModel.totalAmount.observe(this, new Observer<Double>() {
           @Override
           public void onChanged(Double aDouble) {
               binding.totalTxt.setText(String.valueOf(aDouble));
           }
       });

       mainViewModel.totalIncome.observe(this, new Observer<Double>() {
           @Override
           public void onChanged(Double aDouble) {
               binding.incometxt.setText(String.valueOf(aDouble));
           }
       });

       mainViewModel.totalExpense.observe(this, new Observer<Double>() {
           @Override
           public void onChanged(Double aDouble) {
               binding.ExpenseTxt.setText(String.valueOf(aDouble));
           }
       });

       binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               Toast.makeText(MainActivity.this, tab.getText().toString(), Toast.LENGTH_SHORT).show();
               if(tab.getText().toString().equals("Monthly")){
                   //isSelected = 1;
                   HelpterFunctions.SELCTED_TAB = 1;
                   //updateDate();
               }else if(tab.getText().toString().equals("Daily")){
                   //isSelected = 0;
                   HelpterFunctions.SELCTED_TAB = 0;
                   //updateDate();
               }
               updateDate();
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }

    public void updateDate(){
        if(HelpterFunctions.SELCTED_TAB==HelpterFunctions.DAILY){
            binding.date.setText(HelpterFunctions.getDateFormate(calendar.getTime()));
        }else if(HelpterFunctions.SELCTED_TAB==HelpterFunctions.MONTHLY){
            binding.date.setText(HelpterFunctions.getDateFormateMonthly(calendar.getTime()));
        }
        mainViewModel.getTransactions(calendar);
    }

    public static void getTransactionns(){
        mainViewModel.getTransactions(calendar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}