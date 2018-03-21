package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

/**
 * Created by zhufa on 20/03/2018.
 */

public class SimpleProduct {
    @NonNull
    private String id;

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    private String image;

    @NonNull
    public String getId() {
        return this.id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull

    private String name;
    private double price;
    @Ignore
    private int quantity;

    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
