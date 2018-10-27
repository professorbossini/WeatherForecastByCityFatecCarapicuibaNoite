package br.com.bossini.weatherforecastbycityfateccarapicuibanoite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class PrevisaoAdapter extends ArrayAdapter <Previsao> {
    public PrevisaoAdapter (Context context, List <Previsao> previsoes){
        super (context, -1, previsoes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Previsao caraDaVez = getItem(position);
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.list_item, parent, false);
        ImageView conditionImageView =
                raiz.findViewById(R.id.conditionImageView);

        new BaixaImagem(conditionImageView).
                execute(context.getString(R.string.img_download_url,
                        caraDaVez.getIconName()));
        TextView dayTextView =
                raiz.findViewById(R.id.dayTextView);
        TextView lowTextView =
                raiz.findViewById(R.id.lowTextView);
        TextView highTextView =
                raiz.findViewById(R.id.highTextView);
        TextView humidityTextView =
                raiz.findViewById(R.id.humidityTextView);
        dayTextView.setText(caraDaVez.getDiaDaSemana());
        lowTextView.setText(caraDaVez.getLowTemp());
        highTextView.setText(caraDaVez.getHighTemp());
        humidityTextView.setText(caraDaVez.getHumidity());
        return raiz;
    }

    private class BaixaImagem extends
            AsyncTask <String, Void, Bitmap>{

        private ImageView conditionImageView;
        public BaixaImagem (ImageView conditionImageView){
            this.conditionImageView = conditionImageView;
        }
        @Override
        protected void onPostExecute(Bitmap figura) {
            conditionImageView.setImageBitmap(figura);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream =
                        connection.getInputStream();
                Bitmap figura =
                        BitmapFactory.decodeStream(inputStream);
                return figura;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
