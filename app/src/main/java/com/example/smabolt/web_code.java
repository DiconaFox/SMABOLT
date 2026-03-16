package com.example.smabolt;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.ValueCallback;
import com.example.smabolt.databinding.WebLayoutBinding;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;


public class web_code extends AppCompatActivity implements View.OnClickListener{


    Button LoadBut;
    Button BackBut;

    private WebView webView;
    private String pageHtml; // Переменная для хранения HTML-кода страницы
    private boolean isBlockingEnabled = false; // Флаг блокировки
    String[] LinkMas;
    Subj[] Subjs;//Массив предметов
    String preUrl ="https://lk.samgtu.ru/distancelearning/distancelearning/view?id=";//Ссылка на АИС
    int n = 0;//идентификатор обрабатываемого предмета
//    Log.d("MY_TAG", ". (web_code.java:41) web_code worked");
    /*public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void sendData(String jsonData) {
            try {
                JSONArray data = new JSONArray(jsonData);
                for (int i = 0; i < data.length(); i++) {
                    String tooltipText = data.getString(i);
                    Log.d("ScheduleData", "Получены данные: " + tooltipText);

                    // Парсим данные (пример)
                    String[] parts = tooltipText.split("\n");
                    if (parts.length >= 4) {
                        String time = parts[0];
                        String teacher = parts[1];
                        String type = parts[2];
                        String group = parts[3];

                        Log.d("ParsedData",
                                "Время: " + time +
                                        "\nПреподаватель: " + teacher +
                                        "\nТип: " + type +
                                        "\nГруппа: " + group);
                    }

                    // Можно показать Toast или сохранить в БД
                    runOnUiThread(() -> Toast.makeText(mContext,
                            "Данные получены: " + tooltipText,
                            Toast.LENGTH_SHORT).show());
                }
            } catch (JSONException e) {
                Log.e("JSONError", "Ошибка парсинга JSON", e);
            }
        }
    }*///Блок выгрузки данных с личного кабинета с попыткой интерактивно выгрузить данные из раскрывающихся подсказок
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        LoadBut = (Button) findViewById(R.id.LoadBut);
        BackBut = (Button) findViewById(R.id.BackBut);
        webView = findViewById(R.id.webview);

        LoadBut.setOnClickListener(this);
        BackBut.setOnClickListener(this);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*if (isBlockingEnabled) {
                    // Блокируем загрузку, но сохраняем HTML
                    //String url = request.getUrl().toString();
                    //String url = "https://lk.samgtu.ru/distancelearning/distancelearning/view?id=1697474";
                    view.post(() -> {
                        //view.loadUrl(url); // Загружаем "в фоне"
                    });
                    return true; // Отменяем стандартную загрузку
                }
                return false; // Иначе работаем как обычно*/
                Log.d("MY_TAG", ". (web_code.java:109) shouldOverrideUrlLoading worked");
                return isBlockingEnabled;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // Получаем HTML-код страницы после её загрузки
                /*view.evaluateJavascript(
                        "(function() { return document.documentElement.outerHTML; })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                pageHtml = html;
                                // Теперь pageHtml содержит весь HTML-код страницы
                                // Можно использовать его дальше, например:
                                // Log.d("PAGE_HTML", pageHtml);
                                // или передать в другой метод
                            }
                        });*/
                // 2 выгружаем конкретный день с задержкой (иначе сервер не будет отвечать),
                // создаем бд и прочее
                if (isBlockingEnabled) {
                    // Получаем HTML только при активной блокировке
                    view.evaluateJavascript(
                            "(function() { return document.documentElement.outerHTML; })();",
                           html -> {
                                //pageHtml = html;
                                //Log.d("PAGE_HTML", pageHtml);
                                Log.d("BLOCKED_PAGE_HTML", "Сохранён HTML: " + url);
                                //  linkmass сохраняет номер html страницы, отвечающей за день как в дипломе указано
                                if(n<LinkMas.length){
                                   Subjs[n] = new Subj(html);
                                   n++;
                                   if (n==LinkMas.length){
                                       isBlockingEnabled=false;
                                       Log.d("SMA_OTL", "Начинается запись в базу данных");
                                       MainActivity.createBase(Subjs);
                                       Log.d("SMA_OTL", "Информация занесена в базу данных");
                                   }

                                   else {new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                       webView.loadUrl(preUrl + LinkMas[n]);
                                   }, 2000);//Задержка меньше секунды вызывает ошибки сервера
                                   }
                               }
                            }
                    );
                    Log.d("MY_TAG", ". (web_code.java:124) onPageFinished worked");
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        //String desktopUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
        //webSettings.setUserAgentString(desktopUserAgent);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
            // Код, который выполнится после задержки
            webView.loadUrl("https://lk.samgtu.ru/distancelearning/distancelearning/index");

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
        Log.d("MY_TAG", ". (web_code.java:124) onPageFinished worked");
    }
    /*private void loadScheduleData() {
        // Добавляем интерфейс для взаимодействия
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        // JavaScript код для сбора данных
        String jsCode =
                "var events = document.querySelectorAll('.fc-day-grid-event');" +
                        "var results = [];" +
                        "var clickHandler = function(event) {" +
                        "   event.click();" +
                        "   setTimeout(function() {" +
                        "       var tooltip = document.querySelector('.tooltip-inner');" +
                        "       if (tooltip) {" +
                        "           results.push(tooltip.innerText);" +
                        "       }" +
                        "       event.removeEventListener('click', clickHandler);" +
                        "   }, 1000);" + // Увеличенное время ожидания
                        "};" +
                        "events.forEach(function(event) {" +
                        "   event.addEventListener('click', clickHandler);" +
                        "   event.dispatchEvent(new MouseEvent('click'));" +
                        "});" +
                        "setTimeout(function() {" +
                        "   Android.sendData(JSON.stringify(results));" +
                        "}, events.length * 1500);"; // Общее время ожидания

        webView.evaluateJavascript(jsCode, null);
        Toast.makeText(this, "Загрузка данных...", Toast.LENGTH_SHORT).show();
    }*///Блок выгрузки данных с личного кабинета с попыткой интерактивно выгрузить данные из раскрывающихся подсказок

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.LoadBut:
                isBlockingEnabled = true;
                Toast.makeText(this, "Активировано извлечение данных", Toast.LENGTH_SHORT).show();
                // позволяет написанному коду взаимодействовать с вед страницей
                webView.evaluateJavascript(
                        "(function() { return document.documentElement.outerHTML; })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                //"(function() { return document.documentElement.outerHTML; })();",html -> {
                                pageHtml = html;
                                Log.d("SMA_OTL", "Начинается выгрузка списка ссылок на предметы");
                                // 1 выгрузка самих предметов списком со страницы всех предметов
                                LinkMas = TExtr.extract(pageHtml, "href=\\\"/distancelearning/distancelearning/view?id=","\\");
                                Toast.makeText(getApplicationContext(), "Загрузка ссылок завершена", Toast.LENGTH_SHORT).show();
                                if(LinkMas.length > 0) {
                                    Subjs = new Subj[LinkMas.length];
                                    n=0;
                                    webView.loadUrl(preUrl + LinkMas[0]);//Загрузка страниц с предметами
                                    Log.d("SMA_OTL", "Выгрузка списка ссылок на предметы закончена");
                                }

                            }
                        });

                //loadScheduleData();//Блок выгрузки данных с личного кабинета с попыткой интерактивно выгрузить данные из раскрывающихся подсказок

                break;
            case R.id.BackBut:

                webView.evaluateJavascript(
                        "(function() { return document.documentElement.outerHTML; })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                // Проверяем URL перед выполнением действий
                                String currentUrl = webView.getUrl(); // Получаем текущий URL WebView

                                if (currentUrl != null && currentUrl.equals("https://lk.samgtu.ru/distancelearning/distancelearning/index")) {
                                    webView.getSettings().setJavaScriptEnabled(false);
                                    // Удалить все WebView-клиенты
                                    webView.setWebViewClient(null);
                                    webView.setWebChromeClient(null);
                                    webView.stopLoading(); // Остановить загрузку
                                    webView.clearCache(true); // Очистить кеш
                                    webView.clearHistory(); // Очистить историю
                                    webView.destroy(); // Полное уничтожение WebView (после этого его нельзя использовать!)
                                    Intent intent = new Intent(web_code.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    webView.loadUrl("https://lk.samgtu.ru/distancelearning/distancelearning/index");
                                }
                            }
                        });


                break;

        }
    }
}