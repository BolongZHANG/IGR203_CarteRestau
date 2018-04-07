package com.carteresto.igr230.carteresto.Panier;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.MenuDetail.MenuDetailActivity;
import com.carteresto.igr230.carteresto.MenuDetail.NoteDialog;
import com.carteresto.igr230.carteresto.MenuDetail.NoteListener;
import com.carteresto.igr230.carteresto.MenuPrincipale.ProductListFragment;
import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.SimpleMenu;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Ce activity affihier tous les info sur le panier et une fragment pour montrer des recommentaion.
 */

public class PanierActivity extends AppCompatActivity implements CommandItemModifyListener,ProductListFragment.OnListFragmentInteractionListener{

    private static final String TAG = PanierActivity.class.getSimpleName() ;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.tx_set_meal)
    TextView txSetMeal;
    @BindView(R.id.menu_list)
    RecyclerView menuListView;
    @BindView(R.id.tx_apero)
    TextView txApero;
    @BindView(R.id.apero_list)
    RecyclerView aperoListView;
    @BindView(R.id.tx_starters)
    TextView txStarters;
    @BindView(R.id.starter_list)
    RecyclerView starterListView;
    @BindView(R.id.tx_main_dishes)
    TextView txMainDishes;
    @BindView(R.id.main_meal_list)
    RecyclerView mainMealListView;
    @BindView(R.id.deserts)
    TextView deserts;
    @BindView(R.id.dessert_list)
    RecyclerView dessertListView;
    @BindView(R.id.tx_wine)
    TextView txWine;
    @BindView(R.id.wine_list)
    RecyclerView wineListView;
    @BindView(R.id.cmd_list)
    NestedScrollView cmdListView;
    @BindView(R.id.tx_total)
    TextView txTotal;
    @BindView(R.id.tx_total_price)
    TextView txTotalPrice;
    @BindView(R.id.valide)
    Button valide;
    @BindView(R.id.panier_layout)
    ConstraintLayout panierLayout;
    @BindView(R.id.tx_recommendation)
    TextView txRecommendation;

    PanierViewModel viewModel;
    private SimpleMenuAdapter menusAdapter;
    private SimpleProductAdapter aperoApapter;
    private SimpleProductAdapter starterApdapter;
    private SimpleProductAdapter dessertApdapter;
    private SimpleProductAdapter mainMealApdapter;
    private SimpleProductAdapter wineApdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        ButterKnife.bind(this);


        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this, VERTICAL, false);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this, VERTICAL, false);
        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(this, VERTICAL, false);
        LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(this, VERTICAL, false);
        LinearLayoutManager mLayoutManager5 = new LinearLayoutManager(this, VERTICAL, false);
        LinearLayoutManager mLayoutManager6 = new LinearLayoutManager(this, VERTICAL, false);

//            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menuListView.setLayoutManager(mLayoutManager1);
        aperoListView.setLayoutManager(mLayoutManager2);
        starterListView.setLayoutManager(mLayoutManager3);
        mainMealListView.setLayoutManager(mLayoutManager4);
        dessertListView.setLayoutManager(mLayoutManager5);
        wineListView.setLayoutManager(mLayoutManager6);

        menusAdapter = new SimpleMenuAdapter(this);
        aperoApapter = new SimpleProductAdapter(this);
        starterApdapter = new SimpleProductAdapter(this);
        mainMealApdapter = new SimpleProductAdapter(this);
        dessertApdapter = new SimpleProductAdapter(this);
        wineApdapter = new SimpleProductAdapter(this);

        menuListView.setAdapter(menusAdapter);
        aperoListView.setAdapter(aperoApapter);
        starterListView.setAdapter(starterApdapter);
        mainMealListView.setAdapter(mainMealApdapter);
        dessertListView.setAdapter(dessertApdapter);
        wineListView.setAdapter(wineApdapter);

        viewModel = ViewModelProviders.of(this).get(PanierViewModel.class);

        viewModel.getCommandLiveData().observe(this, new Observer<Command>() {
            @Override
            public void onChanged(@Nullable Command command) {
                if(command != null){
                    Log.e(TAG, "onChanged: " + command );
                    updateUI(command);
                }
            }
        });



    }

    private void updateUI(@NonNull Command command) {
        Log.d(TAG, "updateUI: " + command);
        float totalPrice = 0;
        for(SimpleProduct product: command.getProductMap().values()){
            totalPrice += product.getPrice() * product.getQuantity();
        }

        for(SimpleMenu menu: command.getMenuMap().values()){
            totalPrice += menu.getPrice() * menu.getQuantity();
        }

        txTotalPrice.setText( totalPrice + " €");
        menusAdapter.setData(command.transTOMenuList());
        List<SimpleProduct> aperoList = new ArrayList<>();
        List<SimpleProduct> starterList = new ArrayList<>();
        List<SimpleProduct> mainMealList = new ArrayList<>();
        List<SimpleProduct> desertList = new ArrayList<>();
        List<SimpleProduct> wineList = new ArrayList<>();

        for( SimpleProduct product: command.getProductMap().values()){
            if(product.getType().equals(Product.APERO)){
                aperoList.add(product);
            }else if(product.getType().equals(Product.ENTREE)){
                starterList.add(product);
            }else if(product.getType().equals(Product.PLAT)){
                mainMealList.add(product);
            }else if(product.getType().equals(Product.DESSERT)){
                desertList.add(product);
            }else if(product.getType().equals(Product.VIN)){
                wineList.add(product);
            }else{
                Log.e(TAG, "updateUI: Find menu in product list:" + product);
            }
        }

        txApero.setVisibility(aperoList.size() == 0 ? View.GONE : View.VISIBLE);
        txStarters.setVisibility(starterList.size() == 0 ? View.GONE : View.VISIBLE);
        txMainDishes.setVisibility(mainMealList.size() == 0 ? View.GONE : View.VISIBLE);
        deserts.setVisibility(desertList.size() == 0 ? View.GONE : View.VISIBLE);
        txWine.setVisibility(wineList.size() == 0 ? View.GONE : View.VISIBLE);

        aperoApapter.setData(aperoList);
        starterApdapter.setData(starterList);
        mainMealApdapter.setData(mainMealList);
        dessertApdapter.setData(desertList);
        wineApdapter.setData(wineList);


        FragmentManager mFragmentMan = this.getSupportFragmentManager();
        ProductListFragment fragment = ProductListFragment.getInstance(Product.VIN);
        mFragmentMan.beginTransaction().add(R.id.recommandationList, fragment,Product.VIN).commit();

    }


    @Override
    public void editMenu(String id) {
        Intent intent = new Intent(this, MenuDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void editNote(SimpleProduct product) {
        NoteDialog dialog = new NoteDialog(new NoteListener() {
            @Override
            public String getCurNote() {
                return product.getComment();
            }

            @Override
            public void setNote(String note) {
                product.setComment(note);
                viewModel.updateNote(product);
            }
        });

        dialog.show(getFragmentManager(),getString(R.string.menu_detail_note_dialog_title));
    }

    @Override
    public void add(String id) {
        Log.e(TAG, "add: add quantity for " + id);
        viewModel.addProductQuantity(id);
    }

    @Override
    public void minus(String id) {
        Log.e(TAG, "minus: minus quantity for " + id);
        viewModel.minusProductQuantity(id);

    }

    @Override
    public void onListFragmentInteraction(Product item) {

    }
}
