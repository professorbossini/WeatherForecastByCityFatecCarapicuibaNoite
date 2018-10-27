package br.com.bossini.weatherforecastbycityfateccarapicuibanoite;

public class Previsao {
    private String diaDaSemana;
    private String descricao;
    private String lowTemp;
    private String highTemp;
    private String iconName;
    private String humidity;

    public Previsao(String diaDaSemana, String descricao, String lowTemp, String highTemp, String iconName, String humidity) {
        this.diaDaSemana = diaDaSemana;
        this.descricao = descricao;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
        this.iconName = iconName;
        this.humidity = humidity;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
