package com.example.smabolt;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Experimental extends LinearLayout {

    private TextView nameView;
    private ImageView ratingView;

    // Конструктор для создания из кода
    public Experimental(Context context) {
        this(context, null);
    }

    // Конструктор для создания из XML
    public Experimental(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // Используем inflate, чтобы прикрепить XML к этому классу
        LayoutInflater.from(context).inflate(R.layout.elements, this, true);

        ratingView = findViewById(R.id.rating);
        nameView = findViewById(R.id.name);
    }

    public void setRating(int rating) {
        switch (rating) {
            case 1:
                Log.d("MY_TAG", ".  1 star");
                break;
            case 2:
                Log.d("MY_TAG", ".  2 star");
                break;
            case 3:
                Log.d("MY_TAG", ".  3 star");
                break;
        }
    }

}