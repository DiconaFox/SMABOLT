package com.example.smabolt;

import static com.example.smabolt.MainActivity.mDb;

import android.annotation.SuppressLint;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.SharedPreferences;


import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Option extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> Groups = new ArrayList<String>();
    // расписание на неделю
    private CalendarView calendarView; // CalendarView вместо DatePicker
    //поток чтения файла
    //ReadExcel RE = new ReadExcel();//Тут и далее ME - модуль чтения данных из эксель. В данной версии работы программы не используется, но может быть активирован при некоторых усилях

    Button loadButton;
    Button saveButton;
    Button BackMain;
    Spinner groupSpinner;
    Spinner subgroupSpinner;
    Spinner CourseSpinner;

    EditText WeekBox;
    public static String[][][] InputData = new String[4][12][6];//Входная строка для функции разбивки
    public static ArrayList<String> GrpSpis = new ArrayList<String>();
    public static String[] UgrSpis = {"1 подгруппа", "2 подгруппа"};
    public static String[] CourseSpis = {"1", "2", "3", "4"};//Потом выгружать из Экселя список страниц

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Инициализация элементов настройки
        loadButton = findViewById(R.id.loadButton);
        saveButton = findViewById(R.id.saveButton);
        groupSpinner = findViewById(R.id.groupSpinner);
        CourseSpinner = findViewById(R.id.CourseSpinner);
        subgroupSpinner = findViewById(R.id.subgroupSpinner);
        BackMain = findViewById(R.id.BackMain);
        calendarView = findViewById(R.id.calendarView);

        BackMain.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Месяц в CalendarView начинается с 0 (январь = 0), а в LocalDate с 1, поэтому month + 1
                MainActivity.day = LocalDate.of(year, month + 1, dayOfMonth);



                // Можно также показать Toast
                Toast.makeText(Option.this, "Дата изменена: " + MainActivity.day, Toast.LENGTH_SHORT).show();
            }
        });

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner//ME
        /*ArrayAdapter<String> adapterCourse = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CourseSpis);
        // Определяем разметку для использования при выборе элемента
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        CourseSpinner.setAdapter(adapterCourse);
        //загрузка значения спиннера








        /*String FILE_NAME = "etf_vesna24.xlsx";//ME
        try {
            RE = new ReadExcel(getAssets().open(FILE_NAME));  // передача файла
        } catch (Exception e) {

        }

        ArrayList<String> names = RE.getSheetsNames();        // выдача имен страниц
        RE.setSheetIndex(MainActivity.findCourse);         // установка курса
        //RE.setSheetIndex(2);         // установка курса
        Groups = RE.getGroupNames(); // получение имен групп

        GrpSpis = Groups;*/

        /*        Spinner GrpSpin = (Spinner) findViewById(R.id.groupSpinner);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> GroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GrpSpis);
        // Определяем разметку для использования при выборе элемента
        GroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        GrpSpin.setAdapter(GroupAdapter);
        //загрузка значения спиннера

        //Получение значение выбранного в списке поля
        String Grp = GrpSpin.getSelectedItem().toString();

        Spinner UgrSpin = (Spinner) findViewById(R.id.subgroupSpinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapterU = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UgrSpis);
        // Определяем разметку для использования при выборе элемента
        GroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        UgrSpin.setAdapter(adapterU);
        //загрузка значения спиннера
        groupSpinner.setSelection(0);




        CourseSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                // Получаем выбранный элемент
                MainActivity.findCourse = position;
                // Здесь можно выполнить действия, связанные с выбранным элементом.
                // Например, вывести сообщение или выполнить другие действия в зависимости от выбранного элемента.
                /*String FILE_NAME = "etf12s_osen24.xlsx";
                try {
                    RE = new ReadExcel(getAssets().open(FILE_NAME));  // передача файла
                } catch (Exception e) {

                }
                ArrayList<String> names = RE.getSheetsNames();        // выдача имен страниц
                RE.setSheetIndex(MainActivity.findCourse);         // установка курса
                Groups = RE.getGroupNames(); // получение имен групп
                // Обновляем данные в массиве, который используется для адаптера
                GrpSpis = Groups;

                // Затем устанавливаем новые данные в адаптере
                GroupAdapter.clear(); // Очищаем существующие данные
                GroupAdapter.addAll(GrpSpis); // Добавляем новые данные

                // Уведомляем адаптер об изменениях
                GroupAdapter.notifyDataSetChanged();

            }
            /*@Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*///ME

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.BackMain:
                MainActivity.Ugr = subgroupSpinner.getSelectedItemPosition()+1;;
                Intent intent = new Intent(Option.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.loadButton: {
                Intent intentWeb = new Intent(this, web_code.class);
                startActivity(intentWeb);
                Toast.makeText(getApplicationContext(), "Нажата кнопка загрузки ", Toast.LENGTH_SHORT).show();
            }
            case R.id.saveButton: {                /*InputData = new String[4][12][6];
                int Grp = groupSpinner.getSelectedItemPosition();
                RE.setGroup(Grp);              // установка группы ME
                ArrayList<ArrayList<String>> shedule = new ArrayList<ArrayList<String>>();
                ArrayList<ArrayList<String>> Nshedule = new ArrayList<ArrayList<String>>();
                shedule = RE.WeakShedule(0);
                Nshedule = RE.WeakShedule(1);
                String Example = shedule.get(1).get(1);//УДАЛИ!!!!
                //заполнение массива исходными данными (для примера)
                for (int x = 0; x < 4; x++)
                    for (int y = 0; y < 12; y++){
                        int numba = 0;
                        int le = Nshedule.size();//длина нечётной недели
                        if (y > 5) numba = shedule.get(y-le).size();
                        if (y < 6) numba = Nshedule.get(y).size();
                        int choice = 0;//переменная номера выбранного предмета
                        if(numba==0) InputData[x][y][0] = " ";
                        for (int z = 0; z < numba; z++) {

                            if (y > 5){
                                choice = (int) shedule.get(y - le).get(z).charAt(0) - (int) '0';
                                InputData[x][y][z] = shedule.get(y - le).get(z);// + x + y + choice;
                            }
                            if (y < 6) {
                                choice = (int)Nshedule.get(y).get(z).charAt(0) - (int) '0';
                                InputData[x][y][z] = Nshedule.get(y).get(z);// + x + y + choice;
                            }
                        }
                            /*
                            if(disc<numba) {
                                int tch = choice;//Переменные для определения двух предметов, которые претендуют на одну пару пару
                                int td = disc;//Реализовать код если диск изменился, а чойс нет, прибавить чойсу +1. Возможно закинуть в цикл. Существует на случай, если не успеется обработка одинаковых строк от Некита подъехать

                            }
                            if(choice == z+1){
                                if (y > 5) {

                                    if(disc < numba)disc++;
                                }

                                if (y < 6) {

                                    if(disc < numba)disc++;
                                }
                            }else {
                                // InputData[x][y][z] = " ";
                                }

                            }

                            //if(choice < z+1){z--;}

                            /*
                            if (z == name){

                            }else {InputData[x][y][z] = " ";
                                name++;
                                z--;
                            }
                           if (choice < numba){

                                if(choice<=z+1) {
                                    if (y > 5) {
                                        InputData[x][y][z] = shedule.get(y - 6).get(choice - 1) + x + y + z;
                                    }

                                    if (y < 6) {
                                        InputData[x][y][z] = Nshedule.get(y).get(choice - 1) + x + y + z;
                                    }
                                }else
                                { choice = z+1;
                                    InputData[x][y][z] = " ";}


                        }else InputData[x][y][z] = " ";

                    }*/
                /*mDb.delete("excel", null, null);

                //"Массив создан, база удалена");}
                MainActivity.createBase(InputData);/////////////ATENTION/////////////////////////////
                Toast.makeText(getApplicationContext(), "Информация из Excel выгружена в базу данных", Toast.LENGTH_SHORT).show();*/
                mDb.delete("web", null, null);
            }
        }
    }
}
