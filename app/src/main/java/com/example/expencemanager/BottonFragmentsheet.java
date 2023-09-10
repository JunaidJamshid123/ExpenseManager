package com.example.expencemanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.expencemanager.databinding.FragmentBottonFragmentsheetBinding;
import com.example.expencemanager.databinding.ListLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BottonFragmentsheet extends BottomSheetDialogFragment {


    public BottonFragmentsheet(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    FragmentBottonFragmentsheetBinding binding;
    Transaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottonFragmentsheetBinding.inflate(inflater);
        transaction = new Transaction();
        binding.expencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.expencebtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.incomebtn.setTextColor(getContext().getColor(R.color.black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.expencebtn.setTextColor(getContext().getColor(R.color.black));
                }

                transaction.setType("Expense");
            }
        });
        binding.incomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expencebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.incomebtn.setTextColor(getContext().getColor(R.color.black));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.expencebtn.setTextColor(getContext().getColor(R.color.black));
                }
                transaction.setType("income");
            }
        });
        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar calendar= Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_MONTH,view.getDayOfMonth());
                            calendar.set(Calendar.MONTH,view.getMonth());
                            calendar.set(Calendar.YEAR,view.getYear());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM, yyyy");
                            String datetoshow = simpleDateFormat.format(calendar.getTime());
                            binding.date.setText(datetoshow);
                            transaction.setDate(calendar.getTime());
                            transaction.setId(calendar.getTime().getTime());
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });
        binding.catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListLayoutBinding listLayoutBinding = ListLayoutBinding.inflate(inflater);
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setView(listLayoutBinding.getRoot());
                ArrayList<CatagoryModel> list = new ArrayList<>();
                list.add(new CatagoryModel("Salary",R.drawable.ic_salary,R.color.catagory1));
                list.add(new CatagoryModel("Business",R.drawable.ic_business,R.color.catagory2));
                list.add(new CatagoryModel("Investment",R.drawable.ic_investment,R.color.catagory3));
                list.add(new CatagoryModel("Loan",R.drawable.ic_loan,R.color.catagory4));
                list.add(new CatagoryModel("Rent",R.drawable.ic_rent,R.color.catagory5));
                list.add(new CatagoryModel("Other",R.drawable.ic_other,R.color.catagory6));
                CatagoryAdapter catagoryAdapter = new CatagoryAdapter(getContext(), list, new CatagoryAdapter.CatagoryClickListner() {
                    @Override
                    public void onCatagoryClicked(CatagoryModel catagoryModel) {
                        binding.catagory.setText(catagoryModel.getCatagoryName());
                        transaction.setCatagory(catagoryModel.getCatagoryName());
                        alertDialog.dismiss();
                    }
                });
                listLayoutBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                listLayoutBinding.recyclerView.setAdapter(catagoryAdapter);
                alertDialog.show();
            }
        });

        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListLayoutBinding listLayoutBinding = ListLayoutBinding.inflate(inflater);
                AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
                accountDialog.setView(listLayoutBinding.getRoot());

                ArrayList<Account> accountArrayList = new ArrayList<>();
                accountArrayList.add(new Account("Cash",500));
                accountArrayList.add(new Account("EasyPaisa",500));
                accountArrayList.add(new Account("JazzCash",500));
                accountArrayList.add(new Account("Bank",500));
                accountArrayList.add(new Account("PayTM",500));
                accountArrayList.add(new Account("Other",500));

                AccuntAdapter accuntAdapter = new AccuntAdapter(getContext(), accountArrayList, new AccuntAdapter.AccountClickListner() {
                    @Override
                    public void onCatagoryClicked(Account account) {
                        binding.account.setText(account.getAccountName());
                        transaction.setAccount(account.getAccountName());
                        accountDialog.dismiss();
                    }
                });
                listLayoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                listLayoutBinding.recyclerView.setAdapter(accuntAdapter);
                accountDialog.show();

            }
        });
        binding.SaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(binding.amount.getText().toString());
                String note = binding.Notes.getText().toString();
                if(transaction.getType().equals("Expense")){
                    transaction.setAmount(amount*(-1));
                }else{
                    transaction.setAmount(amount);
                }
                transaction.setNote(note);
                MainActivity.mainViewModel.AddTransactions(transaction);
                MainActivity.mainViewModel.getTransactions(MainActivity.calendar);
                dismiss();
            }
        });
        return binding.getRoot();
    }
}