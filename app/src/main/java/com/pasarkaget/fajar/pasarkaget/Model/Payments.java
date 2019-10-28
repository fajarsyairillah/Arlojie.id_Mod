package com.pasarkaget.fajar.pasarkaget.Model;

public class Payments
{
    private String uid, bank, buyerBank, numberBuyerBank, buyerAccount, nominal, metods, date, time;

    public Payments()
    {

    }

    public Payments(String uid, String bank, String buyerBank, String numberBuyerBank, String buyerAccount, String nominal, String metods, String date, String time)
    {
        this.uid = uid;
        this.bank = bank;
        this.buyerBank = buyerBank;
        this.numberBuyerBank = numberBuyerBank;
        this.buyerAccount = buyerAccount;
        this.nominal = nominal;
        this.metods = metods;
        this.date = date;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBuyerBank() {
        return buyerBank;
    }

    public void setBuyerBank(String buyerBank) {
        this.buyerBank = buyerBank;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getMetods() {
        return metods;
    }

    public void setMetods(String metods) {
        this.metods = metods;
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

    public String getNumberBuyerBank() {
        return numberBuyerBank;
    }

    public void setNumberBuyerBank(String numberBuyerBank) {
        this.numberBuyerBank = numberBuyerBank;
    }
}
