package br.com.bossini.weatherforecastbycityfateccarapicuibanoite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ConversorDeDatas {

    public String convertToDayOfWeek (long dt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dt * 1000);
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND,
                tz.getOffset(calendar.getTimeInMillis()));
        return new SimpleDateFormat("EEEE HH:mm", Locale.getDefault()).
                format(calendar.getTime());
    }
}
