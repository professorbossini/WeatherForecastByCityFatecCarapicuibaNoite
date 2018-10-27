package br.com.bossini.weatherforecastbycityfateccarapicuibanoite;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private List <Previsao> previsoes;
    private PrevisaoAdapter adapter;
    private ListView weatherListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherListView =
                findViewById(R.id.weatherListView);
        previsoes = new ArrayList<>();
        adapter = new PrevisaoAdapter(this, previsoes);
        weatherListView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade = locationEditText.getEditableText().toString();
                String chave = getString(R.string.api_key);
                String end = getString(R.string.web_service_url, cidade, chave);
                new ObtemTemperaturas().execute(end);
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();*/
            }
        });
    }
    private class ObtemTemperaturas extends
            AsyncTask <String, Void, String >{

        @Override
        protected String doInBackground(String... urls) {

            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                InputStreamReader inputStreamReader =
                        new InputStreamReader(stream);
                StringBuilder stringBuilder = new StringBuilder("");
                //try with resources
                try (BufferedReader reader = new BufferedReader(inputStreamReader)){
                    String linha = null;
                    String json = "";
                    while ((linha = reader.readLine()) != null)
                        stringBuilder.append(linha);
                }
                connection.disconnect();
                return stringBuilder.toString();
            }
            catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonS) {
            //Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();
            try {
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("list");
                ConversorDeDatas convDatas =
                        new ConversorDeDatas();
                ConversorDeNumeros convNumeros =
                        new ConversorDeNumeros();
                for (int i = 0; i < list.length(); i++){
                    JSONObject previsao = list.getJSONObject(i);
                    long dt = previsao.getLong("dt");
                    JSONObject main = previsao.getJSONObject("main");
                    double temp_min = main.getDouble("temp_min");
                    double temp_max = main.getDouble("temp_max");
                    int humidity = main.getInt("humidity");
                    JSONArray weather = previsao.getJSONArray("weather");
                    JSONObject unicoDoWeather = weather.getJSONObject(0);
                    String description =
                            unicoDoWeather.getString("description");
                    String icon =
                            unicoDoWeather.getString("icon");
                    Previsao novo =
                            new Previsao(convDatas.
                                    convertToDayOfWeek(dt), description,
                                    convNumeros.
                                            removeParteDecimal(temp_min),
                                    convNumeros.
                                            removeParteDecimal(temp_max),
                                    icon, convNumeros.
                                    convertePercentual(humidity));
                    previsoes.add(novo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
