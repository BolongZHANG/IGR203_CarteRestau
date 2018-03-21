package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhufa on 18/03/2018.
 */

@Entity(tableName = "products")
public class Product{

    static public final String VIN = "vin";
    static public final String MENU = "menu";
    static public final String APERO = "apero";
    static public final String ENTREE = "entree";
    static public final String PLAT = "plat";
    static public final String DESSERT = "dessert";

    @StringDef({VIN, APERO, ENTREE, PLAT, DESSERT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Types{}


    @NonNull
    @PrimaryKey
    private String id;
    private String image;
    @NonNull
    private String name;
    private double price;
    private String description;
    private String commentaire;
    @NonNull
    private String type;
    @Ignore
    private int quantity;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull @Types String type) {
        this.type = type;
    }


    public Product(String id, String name, int quantity, double price, String image){
        this.id = id;
        this.name = name;
        this.quantity  = quantity;
        this.price = price;
        this.image = image;
    }


    public Product(){

    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    /** path of image */

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
