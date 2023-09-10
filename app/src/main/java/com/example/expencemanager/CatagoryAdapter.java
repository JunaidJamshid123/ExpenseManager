package com.example.expencemanager;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencemanager.databinding.SimpleCatagoryLayoutBinding;

import java.util.ArrayList;

public class CatagoryAdapter extends RecyclerView.Adapter<CatagoryAdapter.catagoryViewHolder> {
    Context context;
    ArrayList<CatagoryModel> catagoryModelArrayList;
    public interface CatagoryClickListner{
       void onCatagoryClicked(CatagoryModel catagoryModel);
    }

    CatagoryClickListner catagoryClickListner;

    public CatagoryAdapter(Context context, ArrayList<CatagoryModel> catagoryModelArrayList,CatagoryClickListner catagoryClickListner){
        this.context = context;
        this.catagoryModelArrayList=catagoryModelArrayList;
        this.catagoryClickListner = catagoryClickListner;
    }

    @NonNull
    @Override
    public catagoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new catagoryViewHolder(LayoutInflater.from(context).inflate(R.layout.simple_catagory_layout,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull catagoryViewHolder holder, int position) {
        CatagoryModel catagoryModel = catagoryModelArrayList.get(position);
        holder.binding.catagoryy.setText(catagoryModel.getCatagoryName());
        holder.binding.catagoryyImg.setImageResource(catagoryModel.getCatagoryImg());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.binding.catagoryyImg.setBackgroundTintList(context.getColorStateList(catagoryModel.getCatagoryColor()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagoryClickListner.onCatagoryClicked(catagoryModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return catagoryModelArrayList.size();
    }

    public class catagoryViewHolder extends RecyclerView.ViewHolder{

        SimpleCatagoryLayoutBinding binding;
        public catagoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SimpleCatagoryLayoutBinding.bind(itemView);
        }
    }
}