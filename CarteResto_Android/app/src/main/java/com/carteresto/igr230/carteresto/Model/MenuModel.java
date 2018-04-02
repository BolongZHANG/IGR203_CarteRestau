package com.carteresto.igr230.carteresto.Model;

import java.util.Map;

public class MenuModel extends Product {
    Map<String, Integer> entreeMap;
    Map<String, Integer> platMapMap;
    Map<String, Integer> dessertMap;


    public Map<String, Integer> getEntreeMap() {
        return entreeMap;
    }

    public void setEntreeMap(Map<String, Integer> entreeMap) {
        this.entreeMap = entreeMap;
    }

    public Map<String, Integer> getPlatMapMap() {
        return platMapMap;
    }

    public void setPlatMapMap(Map<String, Integer> platMapMap) {
        this.platMapMap = platMapMap;
    }

    public Map<String, Integer> getDessertMap() {
        return dessertMap;
    }

    public void setDessertMap(Map<String, Integer> dessertMap) {
        this.dessertMap = dessertMap;
    }
}
