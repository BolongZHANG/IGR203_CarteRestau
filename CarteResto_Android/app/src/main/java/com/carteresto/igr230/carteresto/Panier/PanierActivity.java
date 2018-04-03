package com.carteresto.igr230.carteresto.Panier;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PanierActivity extends AppCompatActivity {
    PanierViewModel viewModel;
    int counter = 0;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(PanierViewModel.class);
        // Create and show the dialog.

        viewModel.getProduct().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {
                if(product != null)
                    text.setText(product.toString());
            }
        });


        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.getDao().insertCommand(new CommandModel("1", counter));
                    }
                }).start();
            }
        });
    }
}
