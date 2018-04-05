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
    private Map<String, SimpleProduct> productList = new HashMap<>();
    private Map<String, SimpleMenu> menuList = new HashMap<>();
    private double totalPrice;
    private String status;

    public Command(@NonNull String cmdID, @NonNull String table) {
        this.id = checkNotNull(cmdID);
        this.tableNb = checkNotNull(table);
    }

    public Command() {

    }

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

    public Map<String, SimpleProduct> getProductList() {
        return productList;
    }

    public void setProductList(Map<String, SimpleProduct> productList) {
        this.productList = productList;
    }

    public Map<String, SimpleMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(Map<String, SimpleMenu> menuList) {
        this.menuList = menuList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addProductQuantity(SimpleProduct simpleProduct) {
        Log.d(TAG, "addProductQuantity: add 1 id:" + id);
        String mID = "ID_" + id;
        if (productList.containsKey(mID)) {
            SimpleProduct product = productList.get(mID);
            int qantity = productList.get(mID).getQuantity();
            productList.put(mID, product);
        } else {
            throw new IllegalArgumentException("The product id is not in the command, please add it firstly!");
        }
    }

    public void updateProduct(SimpleProduct product) {
        Log.d(TAG, "update product quantity");
        String mID = "ID_" + product.getId();
        if (product.getQuantity() > 0) productList.put(mID, product);
        else productList.remove(mID);
    }


    public void minusProduct(SimpleProduct product) {
        String mID = "ID_" + id;
        if (!productList.containsKey(mID)) return;
        Log.d(TAG, "minusProductQuantity: minus quantity to " + product.getQuantity()
                + "id:" + id);
        product.minus();
        if (product.getQuantity() == 0) productList.remove(mID);
        else productList.put(mID, product);
    }


    public void updateMenu(@NonNull SimpleMenu menu) {
        Log.d(TAG, "update menu quantity");
        String mID = "ID_" + menu.getId();
        if (menu.getQuantity() > 0) menuList.put(mID, menu);
        else menuList.remove(mID);
    }

    public void removeMenu(@NonNull MenuDuCarte menu) {
        String mID = "ID_" + menu.getId();
        menuList.remove(menu.getId());
    }

    public int getProductQuantity(String id) {
        String mID = "ID_" + id;
        if (productList.containsKey(mID))
            return productList.get(mID).getQuantity();
        else
            return 0;
    }

}

