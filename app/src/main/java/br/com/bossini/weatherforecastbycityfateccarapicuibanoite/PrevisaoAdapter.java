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
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.conditionImageView =
                    convertView.findViewById(R.id.conditionImageView);
            viewHolder.dayTextView =
                    convertView.findViewById(R.id.dayTextView);
            viewHolder.lowTextView =
                    convertView.findViewById(R.id.lowTextView);
            viewHolder.highTextView =
                    convertView.findViewById(R.id.highTextView);
            viewHolder.humidityTextView =
                    convertView.findViewById(R.id.humidityTextView);

        }
        viewHolder = (ViewHolder) convertView.getTag();
        new BaixaImagem(viewHolder.conditionImageView).
                execute(context.getString(R.string.img_download_url,
                        caraDaVez.getIconName()));

        viewHolder.dayTextView.setText(caraDaVez.getDiaDaSemana());
        viewHolder.lowTextView.setText(caraDaVez.getLowTemp());
        viewHolder.highTextView.setText(caraDaVez.getHighTemp());
        viewHolder.humidityTextView.setText(caraDaVez.getHumidity());
        return convertView;
    }
    private class ViewHolder{
        public ImageView conditionImageView;
        public TextView dayTextView, lowTextView, highTextView, humidityTextView;
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
