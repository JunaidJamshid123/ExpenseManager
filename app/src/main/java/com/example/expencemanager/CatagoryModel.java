package com.example.expencemanager;
public class CatagoryModel {
    String catagoryName;
    int catagoryImg;
    private int catagoryColor;

    public int getCatagoryColor() {
        return catagoryColor;
    }

    public void setCatagoryColor(int catagoryColor) {
        this.catagoryColor = catagoryColor;
    }

    CatagoryModel(){

    }
    public CatagoryModel(String catagoryName, int catagoryImg,int catagoryColor) {
        this.catagoryName = catagoryName;
        this.catagoryImg = catagoryImg;
        this.catagoryColor = catagoryColor;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public int getCatagoryImg() {
        return catagoryImg;
    }

    public void setCatagoryImg(int catagoryImg) {
        this.catagoryImg = catagoryImg;
    }
}
