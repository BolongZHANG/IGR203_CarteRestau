package com.carteresto.igr230.carteresto.Panier;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.SimpleMenu;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;

public interface CommandItemModifyListener {
        void editMenu(String id);
        void editNote(SimpleProduct simpleProduct);
        void add(String id);
        void minus(String id);
    void showProduct(String id, boolean complet);
}
