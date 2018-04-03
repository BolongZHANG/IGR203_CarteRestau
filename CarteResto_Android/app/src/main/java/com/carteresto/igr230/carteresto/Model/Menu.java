package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by zhufa on 20/03/2018.
 */

@Entity
public class Menu {

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
    @Ignore
    private Map<String, Integer> aperoMap;
    @Ignore
    private Map<String, Integer> entreeMap;
    @Ignore
    private Map<String, Integer> platMap;
    @Ignore
    private Map<String, Integer> dessertMap;


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

    public void setType(@NonNull String type) {
        this.type = type;
    }


    public Menu(){

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
