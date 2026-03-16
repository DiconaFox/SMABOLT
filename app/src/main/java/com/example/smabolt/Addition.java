package com.example.smabolt;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class Addition {
    // номер недели
    public static int getWeekNumberSpecification(LocalDate currentDate){
        //определяем данные на вход год и месяц
        int currentYear = currentDate.getYear(); //
        int year = LocalDate.now().getYear();

        Month currentMonth = currentDate.getMonth();
        LocalDate start_date;
// сравниваю, больше ли месяц по числу и равен ли год текущему
        if (currentMonth.getValue() >= Month.SEPTEMBER.getValue() && currentYear == year) {
            start_date =  LocalDate.of(currentYear, 9, 2);
        } else {
            start_date = LocalDate.of(currentYear-1, 9, 2);
        }
        // Если дата раньше начала семестра
        if (currentDate.isBefore(start_date)) {
            Log.d("Checking", "week info update");
            return 0; // выбросить ошибку

        }

        // Считаем количество полных дней между датами
        long daysBetween = ChronoUnit.DAYS.between(start_date, currentDate);

        // Делим на 7 (дней в неделе) и прибавляем 1 (так как первая неделя - это номер 1)
        // +1 нужен, потому что в день старта разница 0 дней, а неделя должна быть 1-й
        int weekNumber = (int) (daysBetween / 7) + 1;

        return weekNumber;
    }
    // четность нечетность
    public static String getWeekChetnost (int weekNumber){
        return weekNumber % 2 == 0 ? "Четная" : "Нечетная";
    }




//    public static boolean isDarkThemeEnabled(Context context) {
//        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
//
//        if (uiModeManager == null) return false;
//
//        int nightModeFlags = uiModeManager.getNightMode();
//
//        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
//            return true; // Темная тема
//        } else {
//            return false; // Светлая тема
//        }
//    }


}
