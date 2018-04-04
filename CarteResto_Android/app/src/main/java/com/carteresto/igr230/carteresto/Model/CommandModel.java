package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "commands",
        foreignKeys = @ForeignKey(entity = ProductModel.class,
                parentColumns = "id",
                childColumns = "productId",
                onDelete = CASCADE))

public class CommandModel {
    @NonNull
    @PrimaryKey
    private String productId;
    private Integer quantity;
    private String comment;

    @Ignore
    public CommandModel(String productId, Integer quantity, String comment) {
        this.productId = productId;
        this.quantity = quantity;
        this.comment = comment;
    }

    @Ignore
    public CommandModel(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


    @Ignore
    public CommandModel(SimpleProduct simpleProduct) {
        this.productId = simpleProduct.getId();
        this.quantity = simpleProduct.getQuantity();
        this.comment = simpleProduct.getComment();
    }


    public CommandModel() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public String toString() {
        return "CommandModel{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", comment='" + comment + '\'' +
                '}';
    }
}
