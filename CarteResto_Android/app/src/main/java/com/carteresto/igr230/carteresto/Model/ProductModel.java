package com.carteresto.igr230.carteresto.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "products")
public class ProductModel {
    @NonNull
    @PrimaryKey
    private String id;
    @NonNull
    private String type;
    private String name;
    private double price;
    private String description;
    private String ingredients;


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "ProductModel{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductModel that = (ProductModel) o;

        if (!id.equals(that.id)) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @NonNull


    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull @Product.Types String type) {
        this.type = type;
    }


    public void copyOf(ProductModel model) {
        this.id = model.getId();
        this.name = model.getName();
        this.type = model.getType();
        this.price = model.getPrice();
        this.description = model.getDescription();
        this.ingredients = model.getIngredients();

    }
}
