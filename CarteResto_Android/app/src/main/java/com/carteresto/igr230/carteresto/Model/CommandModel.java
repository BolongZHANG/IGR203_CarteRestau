package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.carteresto.igr230.carteresto.Model.Product;

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

    @Ignore
    public CommandModel(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CommandModel() {
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String commentaire;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }


    @Override
    public String toString() {
        return "CommandModel{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
