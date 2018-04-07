package com.carteresto.igr230.carteresto.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zhufa on 20/03/2018.
 */

public class SimpleProduct {
    @NonNull
    private String id;
    @NonNull
    private String name;
    private String type;
    private double price;
    private int quantity;
    private String comment;


    public SimpleProduct() {

    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public void add() {
        quantity++;
    }

    public void minus() {
        quantity = (quantity == 0) ? 0 : quantity - 1;
    }

}
