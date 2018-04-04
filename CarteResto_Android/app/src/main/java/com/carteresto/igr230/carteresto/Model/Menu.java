package com.carteresto.igr230.carteresto.Model;

/***
 * This class is use to save all the information about a menus
 * @version 1.0
 * @author ZHUF angda
 * ***/
public class Menu extends ProductModel {
    private int quantity;
    private String comment;
//    List<SimpleProduct> dishes;
//
//    public List<SimpleProduct> getDishes() {
//        return dishes;
//    }
//
//    public void setDishes(List<SimpleProduct> dishes) {
//        this.dishes = dishes;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public void copyWithoutList(Menu menu) {
        super.copyOf(menu);
        quantity = menu.quantity;
        comment = menu.comment;


    }
}
