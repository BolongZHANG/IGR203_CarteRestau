package com.carteresto.igr230.carteresto.Panier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carteresto.igr230.carteresto.R;

/**
 * Ce activity affihier tous les info sur le panier et une fragment pour montrer des recommentaion.
 * */
public class PanierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
    }
}
