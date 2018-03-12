package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carteresto.igr230.carteresto.R;

/**
 * Ce Activity affichir tous les list du plat à la cart et la liste du menu du jour
 * **/
public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }
}
