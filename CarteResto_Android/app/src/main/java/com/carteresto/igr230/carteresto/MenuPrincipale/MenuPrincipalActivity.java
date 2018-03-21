package com.carteresto.igr230.carteresto.MenuPrincipale;


import android.app.Fragment;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Ce Activity affichir tous les list du plat à la cart et la liste du menu du jour
 **/
public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener , PlatFragment.OnListFragmentInteractionListener {

    @BindView(R.id.top_toolbar)
    LinearLayout topToolbar;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.btn_vin)
    Button btnVin;
    @BindView(R.id.space)
    Space space;
    @BindView(R.id.btn_apero)
    Button btnApero;
    @BindView(R.id.btn_entree)
    Button btnEntree;
    @BindView(R.id.btn_plat)
    Button btnPlat;
    @BindView(R.id.btn_dessert)
    Button btnDessert;
    @BindView(R.id.menu_list)
    ConstraintLayout menuList;

    ArrayList<Button> btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ButterKnife.bind(this);

        btnList = new ArrayList<>();
        btnList.add(btnMenu);
        btnList.add(btnVin);
        btnList.add(btnPlat);
        btnList.add(btnApero);
        btnList.add(btnEntree);
        btnList.add(btnDessert);

        for(Button btn: btnList){
            btn.setSelected(false);
            btn.setOnClickListener(this);
        }


        // Create new fragment and transaction
        PlatFragment newFragment = new PlatFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.bean_list, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();


    }


    void selectButton(Button button){
        for(Button btn: btnList){
            btn.setSelected(false);
        }

        button.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        selectButton((Button) v);
        //TODO change the fragment
    }

    @Override
    public void onListFragmentInteraction(Product item) {

    }
}
