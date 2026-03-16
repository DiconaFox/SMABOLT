package com.example.smabolt;

import static com.example.smabolt.TExtr.extract;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subj {
    private String subjectName;       // Название предмета
    private String teacher;          // Преподаватель
    private String activityType;     // Вид занятия
    private String date;             // Дата проведения
    private String Time;        // Время
    private String location;         // Место проведения
    private String groups;            // Группа(ы)

    // Конструктор расписания с парсером html страницы
    public Subj(String html) {
        this.subjectName = exs(html,"Дисциплина\\u003C/th>\\u003Ctd>","\\u003C/td>");
        this.teacher = exs(html,"u003Cth>Преподаватель\\u003C/th>\\u003Ctd>","\\u003C/td>");
        this.activityType = exs(html,"Вид занятия\\u003C/th>\\u003Ctd>","\\u003C/td>");
        this.date = exs(html,"Дата проведения занятия\\u003C/th>\\u003Ctd>","\\u003C/td>");
        this.Time = exs(html,"Время проведения занятия\\u003C/th>\\u003Ctd>","\\u003C/td>");
        this.location = exs(html,"Место проведения занятия\\u003C/th>\\u003Ctd>","\\u003C/td>");
//        Log.d("MyTag");
        if (location != null){
            boolean isCorpusNew = location.contains("№5");
            boolean isCorpusT = location.contains("№&nbsp;2");
            boolean isCorpusOsn = location.contains("№&nbsp;1");
            boolean isGym = location.contains("Спортзал");
            // Извлекаем числовую часть (последнее число в строке)
            //String number = location.replaceAll(".*?(\\d+)\\D*$", "$1");
            String number = location;
            // Формируем результат - полноценный номер кабинете с указаниями абревиатуры


            if (isCorpusNew) {
                location = location.replace("Корпус №5 (СФ), ", "") + "н";
            }
            if (isCorpusT) {
                location = location.replace("Корпус №&nbsp;2 (СФ), ", "");
            }
            if (isGym && isCorpusOsn){
                location = location.replace("Корпус №&nbsp;1 (СФ), ", "");
            }
            if (isCorpusOsn && !isGym) {
                location = location.replace("Корпус №&nbsp;1 (СФ), ", "") + " осн";
            }


            if (!isCorpusNew && !isCorpusT && !isCorpusOsn && !isGym){
                location = location.replaceAll(".*?(\\d+)\\D*$", "$1");
            }

//            } else if (isCorpusT) {
//                finalLocation = "Т-" + number;
//            } else if (isCorpusOsn) {
//                finalLocation = number + " осн";
//            } else {
//                finalLocation = number;
//            }


           // location = isCorpusNew ? number + "н" : number;
//            location = number;
//             location = isCorpusT ? "Т-" + number : number;
            //location = finalLocation;
            Log.d("MY_TAG", ". (DatabaseHelper.java:112)" + location);
        }
        this.groups = exs(html,"Группы\\u003C/th>\\u003Ctd>","\\u003C/td>");
    }

    // Геттеры и сеттеры
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGroup() {
        return groups;
    }

    public void setGroup(String group) {
        this.groups = group;
    }

    @Override
    public String toString() {
        Log.d("MY_TAG", ". (Subj.java:91) toString worked");
        return "INSERT OR IGNORE INTO 'web' VALUES ('" + subjectName + "', '"
                + teacher + "', '"
                + activityType + "', '"
                + date + "', '"
                + Time + "', '"
                + location + "', '"
                + groups + "');";
    }
    public static String exs(String text, String startDelimiter, String endDelimiter) {
        // Экранируем специальные символы в разделителях
        String escapedStart = Pattern.quote(startDelimiter);
        String escapedEnd = Pattern.quote(endDelimiter);
        // Создаем шаблон регулярного выражения
        String regex = escapedStart + "(.*?)" + escapedEnd;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        // Ищем первое совпадение
        if (matcher.find()) {
            return matcher.group(1);
        }
        // Если совпадений не найдено, возвращаем null
        return null;
    }
}