package com.example.expencemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencemanager.databinding.RowAccountsBinding;

import java.util.ArrayList;

public class AccuntAdapter extends RecyclerView.Adapter<AccuntAdapter.AccountViewHolder> {

    Context context;
    ArrayList<Account> accountArrayList;
    public interface AccountClickListner{
        void onCatagoryClicked(Account account);
    }

    AccountClickListner AccountClickListner;

    AccuntAdapter(Context context, ArrayList<Account> accountArrayList,AccountClickListner accountClickListner){
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.AccountClickListner = accountClickListner;
    }
    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.row_accounts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accounttxt.setText(account.getAccountName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountClickListner.onCatagoryClicked(account);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{
        RowAccountsBinding binding;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountsBinding.bind(itemView);
        }
    }
}
