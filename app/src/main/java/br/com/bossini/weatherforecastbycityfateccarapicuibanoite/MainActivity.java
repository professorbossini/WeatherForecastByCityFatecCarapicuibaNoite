package br.com.bossini.weatherforecastbycityfateccarapicuibanoite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String cidade = locationEditText.getEditableText().toString();
                        String chave = getString(R.string.api_key);
                        String end = getString(R.string.web_service_url, cidade, chave);
                        try{
                            URL url = new URL(end);
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
                            runOnUiThread(()->{
                                Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
                            });

                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
