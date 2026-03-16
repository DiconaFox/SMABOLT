package com.example.smabolt;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.content.SharedPreferences;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;


//import android.appcompat.app.AlertDialog;
import java.nio.charset.Charset; //для вывода русского в лог

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
    }


    //addition объявления


    //Блок первый. Здесь нужно объявлять каждый элемент так, как вы назвали его в XML файле, тобишь в дизайне окна.
    ImageView ButDay;
    ImageView ButWeek;
    ImageView ButBell;
    ImageView ButSettings;
    ImageView ButLeft;
    ImageView ButRight;

    TextView name1;
    TextView name2;
    TextView name3;
    TextView name4;
    TextView name5;
    TextView name6;
    TextView tech1;
    TextView tech2;
    TextView tech3;
    TextView tech4;
    TextView tech5;
    TextView tech6;
    TextView type1;
    TextView type2;
    TextView type3;
    TextView type4;
    TextView type5;
    TextView type6;
    TextView aud1;
    TextView aud2;
    TextView aud3;
    TextView aud4;
    TextView aud5;
    TextView aud6;
    TextView DayBox;
    TextView WeekBox;

    //время
    TextView times;
    String chetnost;



    static SharedPreferences Syst;
    public static int findGroup;
    public static int findDay;
    public static int findCourse;
    public static int findWeek;
    public static int Ugr;
    public static LocalDate day;
    Toolbar toolbar1;
    //Конец первого блока
    //Массив данных для спиннера. Если ваша задача загрузить или выгрузить что-то из спиннера, можете передать информацию в этот массив прямо в этой строке
    public static ArrayList<String> GrpSpis = new ArrayList<String>();
    //Массив данных для спиннера.
    ArrayList<String> Groups = new ArrayList<String>();
    // расписание на неделю
    ArrayList<ArrayList<String>> shedule = new ArrayList<ArrayList<String>>();
    //поток чтения файла
    //ReadExcel RE = new ReadExcel();//Тут и далее ME - модуль чтения данных из эксель. В данной версии работы программы не используется, но может быть активирован при некоторых усилях
    public static String[] WeekNames = {"Понедельник, неч. Неделя №", "Вторник, неч. Неделя №", "Среда, неч. Неделя №", "Четверг, неч. Неделя №", "Пятница, неч. Неделя №", "Суббота, неч. Неделя №", "Понедельник, чёт. Неделя №", "Вторник, чёт. Неделя №", "Среда, чёт. Неделя №", "Четверг, чёт. Неделя №", "Пятница, чёт. Неделя №", "Суббота, чёт. Неделя №"};


    //строка для всех входных строк [группа 0-2][день недели 0-13][номер предмета 0-5]

    private DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;
    Spinner GrpSpin;//Потом снести тут и на мейнактивити

    public static void createBase(Subj[] Subjs) {
        //очистка бд
        //mDb.delete("web", null, null);/////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////
        //нереально жесткий цикл заполнения
//создание и занесение в бд данных

                for (int i = 0; i < Subjs.length; i++) {
                    if (Subjs != null) {
                        String cell = Subjs[i].toString();
                        mDb.execSQL(cell);
                    }

        Log.d("WOW", "Database created");
    }}///////////////////////////////////////////////////

    @SuppressLint("SuspiciousIndentation")
    //заменить на создание text view возм
    public void loadBase()
    {
        //findGroup = DataHolder.DHfindGroup;
        //findDay = DataHolder.DHfindDay;/
        //запрос sql для поиска по группе и дню
        name1.setText(" ");
        aud1.setText(" ");
        tech1.setText(" ");
        type1.setText(" ");


        name2.setText(" ");
        aud2.setText(" ");
        tech2.setText(" ");
        type2.setText(" ");

        name3.setText(" ");
        aud3.setText(" ");
        tech3.setText(" ");
        type3.setText(" ");

        name4.setText(" ");
        aud4.setText(" ");
        tech4.setText(" ");
        type4.setText(" ");

        name5.setText(" ");
        aud5.setText(" ");
        tech5.setText(" ");
        type5.setText(" ");

        name6.setText(" ");
        aud6.setText(" ");
        tech6.setText(" ");
        type6.setText(" ");

//////////////////////////////////////////////////////
        Log.d("SMA_OTL", "Начинается выгрузка данных");
        Log.d("WOW", "Database started to load");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formDate = day.format(formatter);
        Cursor query1 = mDb.rawQuery("SELECT * FROM web WHERE date = '" + formDate + "'", null);

        //загрузка всех 6 предметов на экран по времени предмета
        while (query1.moveToNext()) {
            //int[] filtr = new int[5];
            //if (query1.getString(4) != "")Functions.getWeeksArray(query1.getString(4),filtr);
            //if (query1.getStкring(4) != "")if(findWeek==filtr[0] ||

            //if(Functions.getWeekIndex(findWeek, query1.getString(3)))

            // реализовать массив с 6 query - расписание звонков

            switch (query1.getString(4)) {
                case "08:30-10:05":
                    name1.setText(query1.getString(0));
                    aud1.setText(query1.getString(5));
                    tech1.setText(query1.getString(1));
                    type1.setText(query1.getString(2));

                    /*if(type1.getText().toString().equalsIgnoreCase("Лабораторные работы")){
                        name2.setText(name1.getText());
                        aud2.setText(aud1.getText());
                        tech2.setText(tech1.getText());
                        type2.setText(type1.getText());
                    }*///Для лаб
                    break;
                case "10:15-11:50":
            name2.setText(query1.getString(0));
                    aud2.setText(query1.getString(5));
                    tech2.setText(query1.getString(1));
                    type2.setText(query1.getString(2));
                    break;
                case "12:50-14:25":
                        name3.setText(query1.getString(0));
                    aud3.setText(query1.getString(5));
                    tech3.setText(query1.getString(1));
                    type3.setText(query1.getString(2));
                    break;
                case "14:35-16:10":
                        name4.setText(query1.getString(0));
                    aud4.setText(query1.getString(5));
                    tech4.setText(query1.getString(1));
                    type4.setText(query1.getString(2));
                    break;
                case "16:20-17:55":
                        name5.setText(query1.getString(0));
                    aud5.setText(query1.getString(5));
                    tech5.setText(query1.getString(1));
                    type5.setText(query1.getString(2));
                    break;
                case "18:00-19:25":
                        name6.setText(query1.getString(0));
                    aud6.setText(query1.getString(5));
                    tech6.setText(query1.getString(1));
                    type6.setText(query1.getString(2));
                    break;
}//}
        }
                query1.close();
        Log.d("SMA_OTL", "Выгрузка данных закончилась");
        Log.d("WOW", "Database loaded");
                ////////////////////////////////////////////////////////
                }

    @SuppressLint("CutPasteId") //возм отключает предупр
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Отображение окна главной активности(главного окна)
        //Блок второй. Здесь также нужно объявить каждый элемент второй раз, чтобы он был виден внутри кода, можно элементам дать другое название, иное, нежели в файле дизайна
        ButDay = (ImageView) findViewById(R.id.ButDay);
        ButWeek = (ImageView) findViewById(R.id.ButWeek);
        ButBell = (ImageView) findViewById(R.id.ButBell);
        ButSettings = (ImageView) findViewById(R.id.ButSettings);
        ButLeft = (ImageView) findViewById(R.id.ButLeft);
        ButRight = (ImageView) findViewById(R.id.ButRight);
        DayBox = (TextView) findViewById(R.id.DayBox);
        WeekBox=(TextView) findViewById(R.id.WeekBox);

        //времячко
//        times=(TextView) findViewById(R.id.dialogWindow);

        name1 = (TextView) findViewById(R.id.name1);//Название предмета
        tech1 = (TextView) findViewById(R.id.tech1);//Фамилия ИО преподавателя
        type1 = (TextView) findViewById(R.id.type1);//Тип предмета(Лекция, практика, лабораторная работа)
        aud1 = (TextView) findViewById(R.id.aud1); //Аудитория, в которой проходят занятия

        name2 = (TextView) findViewById(R.id.name2);
        tech2 = (TextView) findViewById(R.id.tech2);
        type2 = (TextView) findViewById(R.id.type2);
        aud2 = (TextView) findViewById(R.id.aud2);

        name3 = (TextView) findViewById(R.id.name3);
        tech3 = (TextView) findViewById(R.id.tech3);
        type3 = (TextView) findViewById(R.id.type3);
        aud3 = (TextView) findViewById(R.id.aud3);

        name4 = (TextView) findViewById(R.id.name4);
        tech4 = (TextView) findViewById(R.id.tech4);
        type4 = (TextView) findViewById(R.id.type4);
        aud4 = (TextView) findViewById(R.id.aud4);

        name5 = (TextView) findViewById(R.id.name5);
        tech5 = (TextView) findViewById(R.id.tech5);
        type5 = (TextView) findViewById(R.id.type5);
        aud5 = (TextView) findViewById(R.id.aud5);

        name6 = (TextView) findViewById(R.id.name6);
        tech6 = (TextView) findViewById(R.id.tech6);
        type6 = (TextView) findViewById(R.id.type6);
        aud6 = (TextView) findViewById(R.id.aud6);

// установление шрифта
        Typeface typeface = ResourcesCompat.getFont(this , R.font.lapsuspro);
        DayBox.setTypeface(typeface);
        WeekBox.setTypeface(typeface);
        name1.setTypeface(typeface);
        type1.setTypeface(typeface);
        name2.setTypeface(typeface);
        type2.setTypeface(typeface);
        name3.setTypeface(typeface);
        type3.setTypeface(typeface);
        name4.setTypeface(typeface);
        type4.setTypeface(typeface);
        name5.setTypeface(typeface);
        type5.setTypeface(typeface);
        name6.setTypeface(typeface);
        type6.setTypeface(typeface);





        ButDay.setOnClickListener(this);//Для вывода на день? Нереализ
        ButWeek.setOnClickListener(this); //Для вывода на неделю? Нереализ
        ButBell.setOnClickListener(this);//Для вывода в отдельном окне расписания звонков? Нереализ
        ButSettings.setOnClickListener(this);
        ButLeft.setOnClickListener(this);
        ButRight.setOnClickListener(this);

        //индекс группы и дня для поиска
        Syst = getSharedPreferences("Syst", MODE_PRIVATE);
        findGroup = Syst.getInt("G", 2);//2;//Предзагрузка даты и группы
        findDay = Syst.getInt("D", 0);//0;
        findCourse = Syst.getInt("C", 2);//2;
        findWeek = Syst.getInt("W", 1);//25;
        findWeek = Syst.getInt("U", 2);//25;




        //новые введения из класса addition
        //Создание диалогового окна
        Activity intent = new Activity();
//        creDialog(intent);


        /*String FILE_NAME = "etf12s_osen24.xlsx";//ME
        try {
            RE = new ReadExcel(getAssets().open(FILE_NAME));  // передача файла
        }
        catch (Exception e){

        }

        ArrayList<String> names = RE.getSheetsNames();        // выдача имен страниц
        RE.setSheetIndex(2);         // установка курса
        Groups = RE.getGroupNames(); // получение имен групп

        GrpSpis = Groups;
        GrpSpin = (Spinner) findViewById(R.id.GrpSpin);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Groups);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        GrpSpin.setAdapter(adapter);*/
        //загрузка значения спиннера
        //SQLite метод
        ////////////////////////////////////////////////////////////////////////////////
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        if (day == null)day = LocalDate.now();
        if (day.getDayOfWeek().getValue() == 7) day = day.plusDays(1);
        DayBox.setText(formatDate(day));

        int weekNum = Addition.getWeekNumberSpecification(day);
        WeekBox.setText("№ "+weekNum);
        loadBase();//Включить, выключить.*//////////////////////////////////////////???
    }


    @Override
    public void onClick(View v) {
//        // НЕТ делаем нечетность или четность
//        LocalDate today = LocalDate.now();
//        int weekNum = Addition.getWeekNumber(today);
//        String weekString = Integer.toString(weekNum);
//        LocalDate today = LocalDate.now();
        Log.d("MayTag", "dddd"+day);
        int weekNum = Addition.getWeekNumberSpecification(day);

        switch (v.getId()) {
            case R.id.ButDay:
                int Grp = GrpSpin.getSelectedItemPosition();///Раздолбанная кнопка. Нулевой функционал
                /*RE.setGroup(Grp);              // установка группы ME пример
                shedule = RE.WeakShedule(0);
                String Example = shedule.get(1).get(1);

                StringBuilder name = Functions.getName(Example);
                StringBuilder teacher = Functions.getTeacher(Example);
                StringBuilder type = Functions.getType(Example);

                // с новой версией разбивки строк
                //StringBuilder cab = Functions.getCab(Example);

                name1.setText(name.toString());
                tech1.setText(teacher.toString());
                type1.setText(type.toString());

                // с новой версией разбивки строк
                //ud1.setText(cab.toString());*/
                break;
            case R.id.ButSettings:
                Intent intent = new Intent(MainActivity.this, Option.class);
                startActivity(intent);
                break;
            case R.id.ButRight:
                day = day.plusDays(1);
                if (day.getDayOfWeek().getValue() == 7) day = day.plusDays(1);
                if (findDay < 11) findDay++;
                else {findDay = 0; findWeek++;}
                if (findDay == 6)findWeek++;
                DayBox.setText(formatDate(day));
                WeekBox.setText("№ "+weekNum);

                SharedPreferences.Editor ed = Syst.edit();//Внесение изменений в настройки даты
                ed.putInt("G", findGroup);//Выпилить
                ed.putInt("D", findDay);
                ed.putInt("C", findCourse);
                ed.putInt("W", findWeek);
                ed.putInt("U", Ugr);//Выпилить
                ed.commit();
                loadBase();///////////////////////////////////////////////////////////////////////////////////
                break;
            case R.id.ButLeft:

                day = day.minusDays(1);
                if (day.getDayOfWeek().getValue() == 7) day = day.minusDays(1);
                if (findDay > 0) findDay--;
                else {findDay = 11; findWeek--;}
                if (findDay == 5)findWeek--;
                DayBox.setText(formatDate(day));
                WeekBox.setText("№ "+weekNum);





//                if (d%2==0) {
//                    chet = Boolean.FALSE;
//                } else{
//                    chet= Boolean.TRUE;
//                }

                //раньше отвечало за заполнение данных в файл
//                SharedPreferences.Editor ed2 = Syst.edit();//Внесение изменений в настройки даты
//                ed2.putInt("G", findGroup);//Выпилить?
//                ed2.putInt("D", findDay);
//                ed2.putInt("C", findCourse);
//                ed2.putInt("W", findWeek);
//                ed2.putInt("U", Ugr);//Выпилить
//                ed2.commit();
                loadBase();//////////////////////////////////////////
                Log.d("MY_TAG", ". (MainActivity.java:321) onClick worked");
                break;
            case R.id.ButBell:
                //day вроде как отвечает за номер дня конкретного дня
                int dayOfWeek=day.getDayOfWeek().getValue();
                times.setText("day");
                times.setVisibility(View.VISIBLE);
                times.setText("Верхний");
                times.setX(100f);
                times.setY(100f);
                times.setTranslationZ(5f); // Поднимаем над первым
//                times.setLayoutParams(params);
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////                String dayOfWeek = getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru"));
////                int dayOfMonth = LocalDate.now().getDayOfMonth();
//
//                if (dayOfWeek == 6){
//                    builder.setTitle("Расписание звонков")
//                            .setMessage(dayOfWeek);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }

        }
    }
    public static String formatDate(LocalDate date) {
        // Форматируем день и месяц
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("ru"));
        String dayAndMonth = date.format(monthFormatter);

        // Получаем название дня недели
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("ru"));
        Log.d("MY_TAG", ". (MainActivity.java:383) formatDate worked");
        // Собираем итоговую строку
        return dayAndMonth + ", " + dayOfWeek;
    }


//    public void creDialog(Activity activity) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("Диалог")
//                .setMessage("Текст в диалоге")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(activity,"Нажата кнопка 'OK'",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(activity,"Нажата кнопка 'Отмена'",Toast.LENGTH_SHORT).show();
//                    }
//                });
//        builder.create().show();
//    }
}