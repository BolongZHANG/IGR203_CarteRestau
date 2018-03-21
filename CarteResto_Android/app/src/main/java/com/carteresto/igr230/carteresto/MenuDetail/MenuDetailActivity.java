package com.carteresto.igr230.carteresto.MenuDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carteresto.igr230.carteresto.R;

public class MenuDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        btnMenu = findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            btnMenu.setSelected(!btnMenu.isSelected());

    }
}
