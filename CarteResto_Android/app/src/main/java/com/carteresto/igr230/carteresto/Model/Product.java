package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhufa on 18/03/2018.
 */


public class Product extends ProductModel{

    static public final String VIN = "vin";
    static public final String MENU = "menu";
    static public final String APERO = "apero";
    static public final String ENTREE = "entree";
    static public final String PLAT = "plat";
    static public final String DESSERT = "dessert";

    @StringDef({VIN, APERO, ENTREE, PLAT, DESSERT, MENU})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Types{}

    @Ignore
    private int quantity;
    @Ignore
    private String commentaire;


    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }



    public Product(){

    }


    @Override
    public String toString() {

        return "Product{" +
                "quantity=" + quantity +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }

    /** path of image */


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Product)) return false;
        Product product = (Product) obj;
        return this.quantity == product.quantity
                && super.equals(obj);
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int add(){
        quantity ++;
        return quantity;
    }

    public int minus(){
        quantity = (quantity == 0) ? 0 : quantity-1;
        return quantity;
    }


}
