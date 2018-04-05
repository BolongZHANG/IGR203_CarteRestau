package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity(indices = @Index(value = {"productID"})
//        ,foreignKeys = {
//        @ForeignKey(
//                entity = ProductModel.class,
//                parentColumns = "id",
//                childColumns = "productID"
//        ),
//        @ForeignKey(
//                entity = ProductModel.class,
//                parentColumns = "id",
//                childColumns = "menuID"
        // )}
        , tableName = "menu_dishes")

public class MenuDishesModel {
    String comment;
    @NonNull
    @PrimaryKey
    private String productID;
    private String menuID;
    private int quantity;

    public MenuDishesModel() {


    }


    @Ignore
    public MenuDishesModel(String productID, String menuID, int quantity, String comment) {
        this.productID = productID;
        this.menuID = menuID;
        this.quantity = quantity;
        this.comment = comment;
    }


    @Ignore
    public MenuDishesModel(String productID, String menuID, int quantity) {
        this.productID = productID;
        this.menuID = menuID;
        this.quantity = quantity;
        this.comment = comment;
    }


    static public List<MenuDishesModel> getListByMenu(SimpleMenu simpleMenu) {
        Map<String, SimpleProduct> map = simpleMenu.getDishesList();
        List<MenuDishesModel> list = new ArrayList<>(map.size());
        for (SimpleProduct product : map.values()) {
            list.add(
                    new MenuDishesModel(product.getId()
                            , simpleMenu.getId()
                            , product.getQuantity()
                            , product.getComment()));
        }

        return list;

    }

    @Override
    public String toString() {
        return "MenuDishesModel{" +
                "productID='" + productID + '\'' +
                ", menuID='" + menuID + '\'' +
                ", quantity='" + quantity + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}