package com.carteresto.igr230.carteresto.Model;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhufa on 20/03/2018.
 */

public class SimpleMenu extends SimpleProduct {

    Map<String, SimpleProduct> dishesList;


    public SimpleMenu(){

    }


    public SimpleMenu(@NonNull  MenuDuCarte menu){
        super.setId(menu.getId());
        setName(menu.getName());
        setComment(menu.getComment());
        setPrice(menu.getPrice());
        setQuantity(menu.getQuantity());
        setType(menu.getType());
        dishesList = new HashMap<String, SimpleProduct>();
    }

    public Map<String, SimpleProduct> getDishesList() {
        return dishesList;
    }

    public void setDishesList(Map<String, SimpleProduct> dishesList) {
        this.dishesList = dishesList;
    }


    public void putDish(String id, SimpleProduct simpleProduct) {
        if(simpleProduct.getQuantity() >0){
            dishesList.put(id, simpleProduct);
        }
    }
}
