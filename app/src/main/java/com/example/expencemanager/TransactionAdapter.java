package com.example.expencemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencemanager.databinding.RowAccountsBinding;
import com.example.expencemanager.databinding.TransactionlayoutBinding;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;
    RealmResults<Transaction> transactionArrayList;
    public  TransactionAdapter(Context context, RealmResults<Transaction> transactionArrayList){
        this.context = context;
        this.transactionArrayList = transactionArrayList;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.transactionlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionArrayList.get(position);
        if(transaction!=null)
        holder.binding.AmountLayout.setText(String.valueOf(transaction.getAmount()));

        holder.binding.AccountTypelayout.setText(transaction.getAccount());
        holder.binding.dateLayout.setText(HelpterFunctions.getDateFormate(transaction.getDate()));
        holder.binding.catagoryTypeLayout.setText(transaction.getCatagory());
        CatagoryModel catagoryModel = HelpterFunctions.getCatagoryDetails(transaction.getCatagory());

        if (catagoryModel != null) {
            holder.binding.catagoryyImglayout.setImageResource(catagoryModel.getCatagoryImg());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.catagoryyImglayout.setBackgroundTintList(context.getColorStateList(catagoryModel.getCatagoryColor()));
            }
        } else {
            holder.binding.catagoryyImglayout.setImageResource(R.drawable.ic_investment);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.binding.AccountTypelayout.setBackgroundTintList(context.getColorStateList(catagoryModel.getCatagoryColor()));
        }

        if (transaction.getType().equals("income")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.AmountLayout.setTextColor(context.getColor(R.color.catagory1));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.binding.AmountLayout.setTextColor(context.getColor(R.color.catagory2));
            }
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Delete Transaction");
                alertDialog.setMessage("Are You sure to delete this Transaction");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.mainViewModel.deleteTransaction(transaction);
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return false;
            }
        });

    }
    @Override
    public int getItemCount() {
        if(transactionArrayList!=null){
            return transactionArrayList.size();
        }
        return 0;
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{
        TransactionlayoutBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransactionlayoutBinding.bind(itemView);
        }
    }
}
