package com.carteresto.igr230.carteresto.MenuDetail;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.carteresto.igr230.carteresto.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Affichier la presentation du menu du jour et les option associé à ce menu.
 * **/
public class MenuDetailActivity extends AppCompatActivity {

    // Left panel widgets
    private TextView menuNameView, priceView ;
    private Button menuNumberLessBtn, menuNumberMoreBtn ;
    private TextView menuNumberView ;
    private ImageButton noteBtn ;
    private int lastQuantity = 0 ;

    // Dishes ListView
    private DishesListAdapter listAdapter ;
    private ExpandableListView dishesListView ;
    private List<String> listDataHeader ;
    private HashMap<String, List<DishDataModel>> listDataChild ;

    // Validation button
    private Button validateBtn ;
    AlertDialog.Builder confirmDialogBuilder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        // Left panel presenting the menu
        menuNameView = (TextView) findViewById(R.id.menu_detail_menu_title) ;
        priceView = (TextView) findViewById(R.id.menu_detail_price) ;
        menuNumberLessBtn = (Button) findViewById(R.id.menu_detail_number_less) ;
        menuNumberMoreBtn = (Button) findViewById(R.id.menu_detail_number_more) ;
        menuNumberView = (TextView) findViewById(R.id.menu_detail_number) ;
        noteBtn = (ImageButton) findViewById(R.id.menu_detail_add_note) ;
        prepareMenuData() ;
        // Setting listeners to change the number of menus
        menuNumberLessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastQuantity == 0) return ;
                boolean success = listAdapter.decrementMenusNumber() ;
                if(success) {
                    lastQuantity-- ;
                    menuNumberView.setText(String.valueOf(lastQuantity)) ;
                }
            }
        });
        menuNumberMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastQuantity++ ;
                menuNumberView.setText(String.valueOf(lastQuantity));
            }
        });
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        validateBtn = (Button) findViewById(R.id.menu_detail_validate_btn) ;
        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastQuantity == 0) {
                    Toast.makeText(MenuDetailActivity.this, R.string.menu_detail_validate_no_menu_added, Toast.LENGTH_SHORT).show() ;
                    return ;
                }
                if(listAdapter.isChoiceComplete()) {
                    AlertDialog confirmDialog = confirmDialogBuilder.create() ;
                    confirmDialog.show() ;
                }
            }
        });

        // Right panel with the list of the dishes for each step of the meal
        dishesListView = (ExpandableListView) findViewById(R.id.menu_detail_dishes_list) ;
        prepareListData() ;
        listAdapter = new DishesListAdapter(this, listDataHeader, listDataChild) ;
        dishesListView.setAdapter(listAdapter);
        dishesListView.expandGroup(0) ;

        confirmDialogBuilder = new AlertDialog.Builder(this) ;
        confirmDialogBuilder.setPositiveButton(R.string.menu_detail_validate_accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MenuDetailActivity.this, R.string.menu_detail_validate_ok, Toast.LENGTH_LONG).show() ;
                finish() ;
            }
        }) ;
        confirmDialogBuilder.setNegativeButton(R.string.menu_detail_validate_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing special to do there
            }
        }) ;
        confirmDialogBuilder.setTitle(R.string.menu_detail_validate_confirmation_title)
                            .setMessage(R.string.menu_detail_validate_confirmation_message) ;
    }

    private void prepareMenuData() {
        String menuName = getString(R.string.menu_detail_titre) ;
        String price = getString(R.string.menu_detail_default_price) ;
        menuNameView.setText(menuName) ;
        priceView.setText(price);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>() ;
        listDataChild = new HashMap<String, List<DishDataModel>>() ;

        //Adding lunch steps :
        listDataHeader.add(getString(R.string.menu_detail_starters_header)) ;
        listDataHeader.add(getString(R.string.menu_detail_main_dishes_header)) ;
        listDataHeader.add(getString(R.string.menu_detail_deserts_header)) ;


        // Adding child data : these are example to show how to add dishes to each category.
        List<DishDataModel> starters = new ArrayList<DishDataModel>() ;
        String[] startersTitlesList = getResources().getStringArray(R.array.menu_detail_starters_list) ;
        Bitmap start1Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.starter_saint_jacques) ;
        DishDataModel starter1 = new DishDataModel(start1Bitmap, startersTitlesList[0], 0) ;
        Bitmap start2Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.starter_religieuse_tomates) ;
        DishDataModel starter2 = new DishDataModel(start2Bitmap, startersTitlesList[1], 0) ;
        starters.add(starter1) ;
        starters.add(starter2) ;

        List<DishDataModel> mainDishes = new ArrayList<DishDataModel>() ;
        String[] mainDishesTitlesList = getResources().getStringArray(R.array.menu_detail_main_dishes_list) ;
        Bitmap dish1Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.main_dish_taboule_quinoa) ;
        DishDataModel dish1 = new DishDataModel(dish1Bitmap, mainDishesTitlesList[0], 0) ;
        mainDishes.add(dish1) ;

        List<DishDataModel> deserts = new ArrayList<DishDataModel>() ;
        String[] desertsTitlesList = getResources().getStringArray(R.array.menu_detail_deserts_list) ;
        Bitmap desert1Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desert_pavlova) ;
        DishDataModel desert1 = new DishDataModel(desert1Bitmap, desertsTitlesList[0], 0) ;
        Bitmap desert2Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desert_tartelette) ;
        DishDataModel desert2 = new DishDataModel(desert2Bitmap, desertsTitlesList[1], 0) ;
        deserts.add(desert1) ;
        deserts.add(desert2) ;

        listDataChild.put(listDataHeader.get(0), starters) ;
        listDataChild.put(listDataHeader.get(1), mainDishes) ;
        listDataChild.put(listDataHeader.get(2), deserts) ;
    }

    public int getMaxQuantity() {
        return lastQuantity ;
    }
}
