package com.carteresto.igr230.carteresto.MenuDetail;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DishDataModel {
    private Bitmap preview ;
    private String title ;
    private Integer number ;

    public DishDataModel(Bitmap preview, String title, Integer number) {
        this.preview = preview ;
        this.title = title ;
        this.number = number ;
    }

    public Bitmap getPreview() {
        return preview ;
    }

    public String getTitle() {
        return title ;
    }

    public Integer getNumber() {
        return number;
    }

    public void decrementNumber() {
        if(number > 0)
            number-- ;
    }

    public void incrementNumber() {
        number++ ;
    }
}
