package com.carteresto.igr230.carteresto.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Map<String, SimpleProduct> productMap = new HashMap<>();
    private Map<String, SimpleMenu> menuMap = new HashMap<>();
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
                ", productMap=" + productMap +
                ", menuMap=" + menuMap +
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

    public Map<String, SimpleProduct> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, SimpleProduct> productMap) {
        this.productMap = productMap;
    }

    public Map<String, SimpleMenu> getMenuMap() {
        return menuMap;
    }

    public List<SimpleMenu> transTOMenuList() {
        return new ArrayList<SimpleMenu>(menuMap.values());
    }

    public void setMenuMap(Map<String, SimpleMenu> menuMap) {
        this.menuMap = menuMap;
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
        if (productMap.containsKey(mID)) {
            SimpleProduct product = productMap.get(mID);
            int qantity = productMap.get(mID).getQuantity();
            productMap.put(mID, product);
        } else {
            throw new IllegalArgumentException("The product id is not in the command, please add it firstly!");
        }
    }

    public void updateProduct(SimpleProduct product) {
        Log.d(TAG, "update product quantity");
        String mID = "ID_" + product.getId();
        if (product.getQuantity() > 0) productMap.put(mID, product);
        else productMap.remove(mID);
    }


    public void minusProduct(SimpleProduct product) {
        String mID = "ID_" + id;
        if (!productMap.containsKey(mID)) return;
        Log.d(TAG, "minusProductQuantity: minus quantity to " + product.getQuantity()
                + "id:" + id);
        product.minus();
        if (product.getQuantity() == 0) productMap.remove(mID);
        else productMap.put(mID, product);
    }


    public void updateMenu(@NonNull SimpleMenu menu) {
        Log.d(TAG, "update menu quantity");
        String mID = "ID_" + menu.getId();
        if (menu.getQuantity() > 0) menuMap.put(mID, menu);
        else menuMap.remove(mID);
    }

    public void removeMenu(@NonNull MenuDuCarte menu) {
        String mID = "ID_" + menu.getId();
        menuMap.remove(menu.getId());
    }

    public int getProductQuantity(String id) {
        String mID = "ID_" + id;
        if (productMap.containsKey(mID))
            return productMap.get(mID).getQuantity();
        else
            return 0;
    }

}

