package com.example.smabolt;

import android.util.Log;

public class Functions {
//старый класс для работы c excel
    public static StringBuilder getName (String input)
    {
        StringBuilder name = new StringBuilder();

        input = input.replaceAll("[^A-Za-zА-Яа-я0-9.,-/:]", " ");
        String firstCheck = "ассистент|доцент|профессор|старший|практика|лекция|лабораторные";
        String[] output = input.split(" ");

        for (String s : output) {
            if (!s.replaceAll(" ", "").toLowerCase().matches(firstCheck)) {
                name.append(s).append(" ");
            } else break;
        }
        Log.d("MY_TAG", ". (Functions.java:3) StringBuilder getName worked");
        return name;
    }

    public static StringBuilder getTeacher (String input)
    {
        StringBuilder teacher = new StringBuilder();
        input = input.replaceAll("[^A-Za-zА-Яа-я0-9.,-/:]", " ");
        String[] output = input.split(" ");

        for (int index = 0; index < output.length; index++) {
            switch (output[index].replaceAll(" ", "").toLowerCase()) {
                case "доцент":
                case "ассистент":
                case "профессор": {
                    teacher.append(output[index]).append(" ").append(output[index + 1]).append(" ").append(output[index + 2]);
                    break;
                }
                case "старший": {
                    teacher.append(output[index]).append(" ").append(output[index + 1]).append(" ").append(output[index + 2]).append(" ").append(output[index + 3]);
                }
                default: {
                    if (index == output.length - 1) break;
                }
            }
        }
        Log.d("MY_TAG", ". (Functions.java:22) StringBuilder getTeacher worked");
        return teacher;
    }

    public static StringBuilder getType (String input)
    {
        StringBuilder type = new StringBuilder();
        input = input.replaceAll("[^A-Za-zА-Яа-я0-9.,-/:]", " ");
        String[] output = input.split(" ");

        for (int index = 0; index < output.length; index++) {
            switch (output[index].replaceAll(" ", "").toLowerCase()) {
                case "практика":
                case "лекция": {
                    type.append(output[index]);
                    break;
                }
                case "лабораторные":
                case "лабораторная": {
                    type.append(output[index]).append(" ").append(output[index + 1]);
                    break;
                }
                default: {
                    if (index == output.length - 1) break;
                }
            }
        }
        Log.d("MY_TAG", ". (Functions.java:48) StringBuilder getType worked");
        return type;
    }

    public static StringBuilder getWeeks (String input)
    {
        StringBuilder weeks = new StringBuilder();
        input = input.replaceAll("[^A-Za-zА-Яа-я0-9.,-/:]", " ");
        String[] output = input.split(" ");

        for (int index = 0; index < output.length; index++) {
            if (output[index].replaceAll("[^А-яа-я]", "").equalsIgnoreCase("недели")) {
                if (!"недели".equalsIgnoreCase(output[index].replaceAll("[^А-яа-я0-9]", "")))
                    weeks.append(output[index].replaceAll("[^0-9,]", ""));
                else weeks.append(output[index + 1]);
                break;
            }
            if (index == output.length - 1) break;
        }
        Log.d("MY_TAG", ". (Functions.java:75) StringBuilder getWeeks worked");
        return weeks;
    }

    public static StringBuilder getGroup (String input)
    {
        StringBuilder group = new StringBuilder();
        input = input.replaceAll("[^A-Za-zА-Яа-я0-9.,-/:]", " ");
        String[] output = input.split(" ");

        for (int index = 0; index < output.length; index++) {
            if (output[index].replaceAll(" ", "").equalsIgnoreCase("подгруппа")) {
                group.append(output[index - 1]);
                break;
            }
            if (index == output.length - 1) break;
        }
        Log.d("MY_TAG", ". (Functions.java:94) StringBuilder getGroup worked");
        return group;
    }

    public static StringBuilder getCab (String input)
    {
        StringBuilder cab = new StringBuilder();
        input = input.replaceAll("[^A-Za-zА-Яа-я0-9.,-/:]", " ");
        String[] output = input.split(" ");

        for (int index = 0; index < output.length; index++) {
            if (output[index].regionMatches(0,"ауд.",0,4))
            {
                if (output[index].length() > 4) cab.append(output[index].substring(4));
                else cab.append(output[index+1]);
            }
            if ("спортивный".replaceAll("[^А-яа-я]", "").equals(output[index].toLowerCase())) {
                cab.append(output[index]).append(" ").append(output[index + 1]);
                break;
            }
            if (index == output.length - 1) break;
        }
        Log.d("MY_TAG", ". (Functions.java:111) StringBuilder getCab worked");
        return cab;
    }

    public static void getWeeksArray (String input, int[] weeks)
    {
        String[] spl = input.replaceAll(" ","").split(",");
        for (int i = 0; i < weeks.length; i++)
        {
            if (i < spl.length) weeks[i] = Integer.parseInt(spl[i]);
        }
        Log.d("MY_TAG", ". (Functions.java:133) getWeeksArray worked");
    }

    public static boolean getWeekIndex (int num, String input)
    {
        boolean check = false;
        String[] spl = input.split(",");
        for (int i = 0; i < spl.length; i++)
        {
            if (spl[i] == null ? Integer.toString(num) == null : spl[i].equals(Integer.toString(num))) check = true;
        }
        Log.d("MY_TAG", ". (Functions.java:143) StringBuilder getWeeksIndex worked");
        return check;
    }

}