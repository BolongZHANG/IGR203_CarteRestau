package com.carteresto.igr230.carteresto.Model;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhufa on 20/03/2018.
 */

public class Command {

    static final String INIT = "initialization";
    static final String PREPA = "preparation";
    static final String COMPLET = "complete";
    static final String RESERVE = "reserve";
    @NonNull
    private String id;
    @NonNull
    private String tableNb;
    private Map<String, Integer> productList = new HashMap<>();
    private Map<String, Integer> menuList = new HashMap<>();
    private double totalPrice;

    @Override
    public String toString() {
        return "Command{" +
                "id='" + id + '\'' +
                ", tableNb='" + tableNb + '\'' +
                ", productList=" + productList +
                ", menuList=" + menuList +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }

    private String status;

    public Command(@NonNull String cmdID, @NonNull String table){
        this.id = checkNotNull(cmdID);
        this.tableNb = checkNotNull(table);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableNb() {
        return tableNb;
    }

    public void setTableNb(String tableNb) {
        this.tableNb = tableNb;
    }

    public Map<String, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<String, Integer> productList) {
        this.productList = productList;
    }

    public Map<String, Integer> getMenuList() {
        return menuList;
    }

    public void setMenuList(Map<String, Integer> menuList) {
        this.menuList = menuList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
