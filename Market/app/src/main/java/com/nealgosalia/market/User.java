package com.nealgosalia.market;

/**
 * Created by kira on 29/1/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String uid;
    public String name;
    public String address;
    public String dist;


    public User() {
    }

    public User(String uid , String name,String address ,String dist ) {

        this.uid = uid;
        this.name =name;
        this.address=address;
        this.dist=dist;
    }

}