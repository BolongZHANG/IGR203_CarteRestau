package com.carteresto.igr230.carteresto.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhufa on 20/03/2018.
 */

public class Command {
    static final String TAG = Command.class.getSimpleName();
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

    public Command(){

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

    public void addProductQuantity(String id){
        Log.d(TAG, "addProductQuantity: add 1 id:" + id);
        String mID = "ID_" + id;
        if(productList.containsKey(mID)){
            int qantity = productList.get(mID);
            productList.put(mID, qantity+1);
        }else{
            productList.put(mID, 1);
        }
    }



    public void minusProductQuantity(String id){
        String mID = "ID_" + id;
        Log.d(TAG, "minusProductQuantity: minus 1 id:" + id);
        if(productList.containsKey(mID)){
            Log.d(TAG, "minusProductQuantity: minus 1 id:" + id);
            int qantity = productList.get(mID);
            productList.put(mID, qantity>1 ? qantity-1: 0);
        }
    }

    public void setProductQuantity(String id, int quantity){
        String mID = "ID_" + id;
        productList.put(mID, quantity);
    }

    public int getProductQuantity(String id){
        String mID = "ID_" + id;
        Integer quantity = productList.get(mID);
        return quantity == null ? 0: quantity;
    }

}
