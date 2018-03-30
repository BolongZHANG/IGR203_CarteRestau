package com.carteresto.igr230.carteresto.Panier;


import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carteresto.igr230.carteresto.MenuPrincipale.ProductShowFragment;

import com.carteresto.igr230.carteresto.R;

public class PanierActivity extends AppCompatActivity {
    PanierViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        viewModel = ViewModelProviders.of(this).get(PanierViewModel.class);
        // Create and show the dialog.
        String cmdId = viewModel.getCmdNumber();


        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DialogFragment.show() will take care of adding the fragment
                // in a transaction.  We also want to remove any currently showing
                // dialog, so make our own transaction and take care of that here.
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment newFragment = ProductShowFragment.newInstance("27");
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }
}
