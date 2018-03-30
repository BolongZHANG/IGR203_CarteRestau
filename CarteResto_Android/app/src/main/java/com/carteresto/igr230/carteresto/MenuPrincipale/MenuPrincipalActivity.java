package com.carteresto.igr230.carteresto.MenuPrincipale;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Ce Activity affichir tous les list du plat à la cart et la liste du menu du jour
 **/
public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener , ProductListFragment.OnListFragmentInteractionListener {

    private static final String TAG = MenuPrincipalActivity.class.getSimpleName();
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

    Map<Button, Fragment> btnMap;

    ProductListFragment platFragment;
    private FragmentManager mFragmentMan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ButterKnife.bind(this);
        mFragmentMan = this.getSupportFragmentManager();
        btnMap = new HashMap<>();
        btnMap.put(btnMenu, ProductListFragment.getInstance(Product.VIN));
        btnMap.put(btnVin, ProductListFragment.getInstance(Product.VIN));
        btnMap.put(btnPlat,ProductListFragment.getInstance(Product.PLAT));
        btnMap.put(btnApero, ProductListFragment.getInstance(Product.APERO));
        btnMap.put(btnEntree, ProductListFragment.getInstance(Product.ENTREE));
        btnMap.put(btnDessert, ProductListFragment.getInstance(Product.DESSERT));

        for(Map.Entry<Button, Fragment> entry: btnMap.entrySet()){
            entry.getKey().setSelected(false);
            entry.getKey().setOnClickListener(this);
        }

        selectButton(btnApero);


    }


    void selectButton(Button button){
        Fragment from = null;
        for(Map.Entry<Button, Fragment> entry: btnMap.entrySet()){
            entry.getKey().setSelected(false);
            entry.getKey().setOnClickListener(this);
            if(entry.getValue().isVisible()) from = entry.getValue();
        }
        button.setSelected(true);

        Fragment to = btnMap.get(button);
        Log.i(TAG, "selectButton: From " + from + "to " + to );
        switchContent(from, to);
    }

    public void switchContent(Fragment from, Fragment to) {
        if(to.isVisible() || from == to) return;

        FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.slide_out_right);
        if (!to.isAdded()) {
            if(from != null) transaction.hide(from).add(R.id.bean_list, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            else transaction.add(R.id.bean_list, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            if(from != null) transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            else transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
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
