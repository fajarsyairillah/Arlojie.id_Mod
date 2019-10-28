package com.pasarkaget.fajar.pasarkaget.Model;

public class Delivery {
    private String uid, name, address, resi, delivery, date, time, ongkir;

    public Delivery()
    {

    }

    public Delivery(String uid, String name, String address, String resi, String delivery, String date, String time, String ongkir)
    {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.resi = resi;
        this.delivery = delivery;
        this.date = date;
        this.time = time;
        this.ongkir = ongkir;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }
}
