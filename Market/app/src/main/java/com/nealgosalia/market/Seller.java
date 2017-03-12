package com.nealgosalia.market;

/**
 * Created by kira on 29/1/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Seller {
    public String uid;
    public String product;
    public String price;
    public String quantity;
    public String booktime;


    public Seller() {
    }

    public Seller(String uid , String product,String price, String quantity,String booktime ) {

        this.uid = uid;
        this.product =product;
        this.price=price;
        this.quantity=quantity;
        this.booktime=booktime;
    }


}