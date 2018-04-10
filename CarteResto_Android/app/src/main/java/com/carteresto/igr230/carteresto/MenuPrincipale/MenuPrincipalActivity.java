package com.carteresto.igr230.carteresto.MenuPrincipale;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Panier.PanierActivity;
import com.carteresto.igr230.carteresto.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Ce Activity affichir tous les list du plat à la cart et la liste du menu du jour
 **/
public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener, ProductListFragment.OnListFragmentInteractionListener {

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
    @BindView(R.id.btn_panier)
    ImageButton btnPanier;
    @BindView(R.id.btn_waiter)
    ImageButton btnWaiter;
    @BindView(R.id.bean_list)
    LinearLayout beanList;
    @BindView(R.id.tbNb)
    TextView tbNb;
    @BindView(R.id.circle)
    ImageView circle;
    private FragmentManager mFragmentMan;
    private ProductViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideVirtualKey();
        setContentView(R.layout.activity_menu_principal);
        ButterKnife.bind(this);
        mFragmentMan = this.getSupportFragmentManager();
        btnMap = new HashMap<>();
        btnMap.put(btnMenu, ProductListFragment.getInstance(Product.MENU, 1));
        btnMap.put(btnVin, ProductListFragment.getInstance(Product.VIN, 4));
        btnMap.put(btnPlat, ProductListFragment.getInstance(Product.PLAT,4));
        btnMap.put(btnApero, ProductListFragment.getInstance(Product.APERO,4));
        btnMap.put(btnEntree, ProductListFragment.getInstance(Product.ENTREE,4));
        btnMap.put(btnDessert, ProductListFragment.getInstance(Product.DESSERT,4));

        for (Map.Entry<Button, Fragment> entry : btnMap.entrySet()) {
            entry.getKey().setSelected(false);
            entry.getKey().setOnClickListener(this);
        }

        selectButton(btnApero);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        viewModel.getCmd().observe(this, new Observer<Command>() {
            @Override
            public void onChanged(@Nullable Command command) {
                if (command == null) return;
                tbNb.setText(command.getTableNb());
                if(command.getMenuMap().size() >0 || command.getProductMap().size() >0){
                    circle.setVisibility(View.VISIBLE);
                }else{
                    circle.setVisibility((View.GONE));
                }
            }
        });

        btnWaiter.setOnClickListener(view ->{
            Snackbar snackbar = Snackbar.make(beanList, R.string.call_witer, Snackbar.LENGTH_LONG);

            snackbar.setAction(R.string.valide, v -> Snackbar.make(beanList, R.string.waiter_comming, Snackbar.LENGTH_LONG));
            snackbar.show();

        });
    }


    void selectButton(Button button) {
        Fragment from = null;
        for (Map.Entry<Button, Fragment> entry : btnMap.entrySet()) {
            entry.getKey().setSelected(false);
            entry.getKey().setOnClickListener(this);
            if (entry.getValue().isVisible()) from = entry.getValue();
        }
        button.setSelected(true);

        Fragment to = btnMap.get(button);
        Log.i(TAG, "selectButton: From " + from + "to " + to);
        switchContent(from, to);
    }

    public void switchContent(Fragment from, Fragment to) {
        if (to.isVisible() || from == to) return;

        FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (!to.isAdded()) {
            if (from != null)
                transaction.hide(from).add(R.id.bean_list, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            else transaction.add(R.id.bean_list, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            if (from != null)
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            else transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    @Override
    public void onClick(View v) {
        selectButton((Button) v);
    }

    @Override
    public void onListFragmentInteraction(Product item) {

    }


    @OnClick(R.id.btn_panier)
    public void onPainier() {
        Intent intent = new Intent(this, PanierActivity.class);
        startActivity(intent);
    }

    private void hideVirtualKey(){
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

}
