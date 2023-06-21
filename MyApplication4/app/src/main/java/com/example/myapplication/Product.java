package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String ten,gia,loai,sl,mau;

    public Product() {
    }

    public Product(String ten, String gia, String loai,String sl,String mau) {
        this.ten = ten;
        this.gia = gia;
        this.loai = loai;
        this.sl= sl;
        this.mau=mau;
    }

    public Product(int id, String ten, String gia, String loai,String sl,String mau) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.loai = loai;
        this.sl=sl;
        this.mau = mau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
    public String getSl(){
        return sl;
    }
    public void setSl(String sl){
        this.sl = sl;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }
}
