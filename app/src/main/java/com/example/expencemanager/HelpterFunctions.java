package com.example.expencemanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelpterFunctions {
    public static String getDateFormate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getDateFormateMonthly(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy");
        return simpleDateFormat.format(date);
    }
    public static int DAILY = 0;
    public static int MONTHLY = 0;
    public static int CALANDER = 0;
    public static int SUMMARY = 0;
    public static int NOTES = 0;
    public static int SELCTED_TAB =0;
    public static ArrayList<CatagoryModel> list;
    public static void setCatagory(){
        list = new ArrayList<>();
        list.add(new CatagoryModel("Salary",R.drawable.ic_salary,R.color.catagory1));
        list.add(new CatagoryModel("Business",R.drawable.ic_business,R.color.catagory2));
        list.add(new CatagoryModel("Investment",R.drawable.ic_investment,R.color.catagory3));
        list.add(new CatagoryModel("Loan",R.drawable.ic_loan,R.color.catagory4));
        list.add(new CatagoryModel("Rent",R.drawable.ic_rent,R.color.catagory5));
        list.add(new CatagoryModel("Other",R.drawable.ic_other,R.color.catagory6));
    }

    public static CatagoryModel getCatagoryDetails(String name){
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCatagoryName().equals(name)) {
                    return list.get(i);
                }
            }
        }
        return null;
    }
}
