package com.example.rxjavaexample;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetResult {
    @SerializedName("coord")
    public Coord coord = new Coord();

    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<>();

    @SerializedName("base")
    public String base;

    @SerializedName("main")
    public Main main;

    @SerializedName("visibility")
    public double visibility;

    @SerializedName("wind")
    public Wind wind;

    @SerializedName("clouds")
    public Clouds clouds;

    @SerializedName("dt")
    public double dt;

    @SerializedName("sys")
    public Sys sys;

    @SerializedName("timezone")
    public int timezone;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("cod")
    public int cod;

    @NonNull
    @Override
    public String toString() {
        return "{\n" +
                "   \"coord\": {\n" +
                "       \"lon\": " + coord.lon + ",\n" +
                "       \"lat\": " + coord.lat + "\n" +
                "   },\n" +
                "   \"weather\": [\n" +
                "       {\n" +
                "           \"id\": " + weather.get(0).id + ",\n" +
                "           \"main\": " + weather.get(0).main + ",\n" +
                "           \"description\": " + weather.get(0).description + ",\n" +
                "           \"icon\": " + weather.get(0).icon + "\n" +
                "       }\n" +
                "   ],\n" +
                "   \"base\": " + base + ",\n" +
                "   \"main\": {\n" +
                "       \"temp\": " + main.temp + ",\n" +
                "       \"feels_like\": " + main.feelsLike + ",\n" +
                "       \"temp_min\": " + main.tempMin + ",\n" +
                "       \"temp_max\": " + main.tempMax + ",\n" +
                "       \"pressure\": " + main.pressure + ",\n" +
                "       \"humidity\": " + main.humidity + "\n" +
                "   },\n" +
                "   \"visibility\": " + visibility + ",\n" +
                "   \"wind\": {\n" +
                "       \"speed\": " + wind.speed + ",\n" +
                "       \"deg\": " + wind.deg + "\n" +
                "   },\n" +
                "   \"clouds\": {\n" +
                "       \"all\": " + clouds.all + "\n" +
                "   },\n" +
                "   \"dt\": " + dt + ",\n" +
                "   \"sys\": {\n" +
                "       \"type\": " + sys.type + ",\n" +
                "       \"id\": " + sys.id + ",\n" +
                "       \"country\": " + sys.country + ",\n" +
                "       \"sunrise\": " + sys.sunrise + ",\n" +
                "       \"sunset\": " + sys.sunset + "\n" +
                "   },\n" +
                "   \"timezone\": " + timezone + ",\n" +
                "   \"id\": " + id + ",\n" +
                "   \"name\": " + name + ",\n" +
                "   \"cod\": " + cod + "\n" +
                "}\n";
    }

    public class Coord {
        @SerializedName("lon")
        public double lon;

        @SerializedName("lat")
        public double lat;
    }

    public class Weather {
        @SerializedName("id")
        public int id;

        @SerializedName("main")
        public String main;

        @SerializedName("description")
        public String description;

        @SerializedName("icon")
        public String icon;
    }

    private class Main {
        @SerializedName("temp")
        public double temp;

        @SerializedName("feels_like")
        public double feelsLike;

        @SerializedName("temp_min")
        public double tempMin;

        @SerializedName("temp_max")
        public double tempMax;

        @SerializedName("pressure")
        public double pressure;

        @SerializedName("humidity")
        public double humidity;
    }

    private class Wind {
        @SerializedName("speed")
        public double speed;

        @SerializedName("deg")
        public double deg;
    }

    private class Clouds {
        @SerializedName("all")
        public double all;
    }

    private class Sys {
        @SerializedName("type")
        public int type;

        @SerializedName("id")
        public int id;

        @SerializedName("country")
        public String country;

        @SerializedName("sunrise")
        public double sunrise;

        @SerializedName("sunset")
        public double sunset;
    }
}
