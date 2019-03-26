package model;

public class PlainCurrentWeather {
    private String city;
    private String country;
    private double temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private String windDirection;
    private int visibility;
    private String weatherName;

    public PlainCurrentWeather() {
    }

    @Override
    public int hashCode() {
        return city.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlainCurrentWeather && city.equals(((PlainCurrentWeather) obj).city);
    }

    @Override
    public String toString() {
        return city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return String.format("%.2f°C",temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return "" + humidity + "%";
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return "" + pressure + "hPa";
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getWindSpeed() {
        return String.format("%.1fm/s",windSpeed);
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }
}
