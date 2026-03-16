package com.example.smabolt;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.smabolt.R;
public class ExpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experimental_main); // Загружаем основной XML

        // Находим контейнер, куда будем добавлять элементы
        ConstraintLayout container = findViewById(R.id.mainBlock);

        // Создаем новый экземпляр вашего шаблона программно
        Experimental newItem = new Experimental(this);

        // Устанавливаем данные через ваши методы
        newItem.setRating(3);

        // Добавляем его на экран
        container.addView(newItem);
    }
}
