package br.com.bossini.weatherforecastbycityfateccarapicuibanoite;

import java.text.NumberFormat;

public class ConversorDeNumeros {

    public String removeParteDecimal (double numero){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(numero);
    }

    public String convertePercentual (int numero){
        NumberFormat numberFormat
                = NumberFormat.getPercentInstance();
        return numberFormat.format(numero / 100.0);
    }
}
