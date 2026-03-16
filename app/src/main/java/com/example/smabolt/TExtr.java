package com.example.smabolt;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TExtr {
    public static String[] extract(String text, String startDelimiter, String endDelimiter) {
        // Создаем список для хранения найденных подстрок
        List<String> matchesList = new ArrayList<>();

        // Экранируем специальные символы в разделителях для использования в регулярном выражении
        String escapedStart = Pattern.quote(startDelimiter);
        String escapedEnd = Pattern.quote(endDelimiter);

        // Создаем шаблон регулярного выражения
        String regex = escapedStart + "(.*?)" + escapedEnd;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // Ищем все совпадения
        while (matcher.find()) {
            matchesList.add(matcher.group(1));
        }
        Log.d("MY_TAG", ". (TExtr.java:9) String[] worked");
        // Преобразуем список в массив и возвращаем
        return matchesList.toArray(new String[0]);
    }

}