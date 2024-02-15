package it.appventurers.taskflow.model;

public class Weather {

    private String city;
    private String temperature;
    private String code;
    private String day;

    public Weather() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature + " °C";
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
