package com.carteresto.igr230.carteresto.Model;

import android.util.ArrayMap;

import java.util.Map;

/**
 * Created by zhufa on 20/03/2018.
 */

public class SimpleMenu extends SimpleProduct {

    Map<String, SimpleProduct> dishesList;

    public Map<String, SimpleProduct> getDishesList() {
        return dishesList;
    }

    public void setDishesList(ArrayMap<String, SimpleProduct> dishesList) {
        this.dishesList = dishesList;
    }

}
