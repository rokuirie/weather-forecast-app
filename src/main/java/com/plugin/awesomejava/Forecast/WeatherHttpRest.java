package com.plugin.awesomejava.Forecast;

import com.plugin.awesomejava.Location.LocationInfo;
import java.util.ArrayList;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import com.plugin.awesomejava.UIApp.DynJLabelObject;
import com.plugin.awesomejava.UIApp.DynamicJLabelList;
import java.util.HashMap;
import javax.swing.ImageIcon;


public class WeatherHttpRest {
    private static final boolean isMetric = true;
    private static final String DEGREE = "\u00b0";

    private final FeedEntry entry;
    private final WeatherImp weather;
    private ForecastValues forecastValue;

    private HashMap<DynJLabelObject, ForecastValues> WeatherMaphm;

    public WeatherHttpRest(FeedEntry entry, final DynamicJLabelList DynJLabelList) {
        this.entry = entry;
        this.weather = new WeatherImp(DynJLabelList);
        WeatherMaphm = new HashMap<DynJLabelObject, ForecastValues>();
    }

    public HashMap<DynJLabelObject, ForecastValues> HttpRestRequest() {

        final ArrayList<ForecastValues> list = new ArrayList<ForecastValues>();
        final OpenWeatherMap.Units units = (isMetric) ? OpenWeatherMap.Units.METRIC : OpenWeatherMap.Units.IMPERIAL;
        final OpenWeatherMap owm = new OpenWeatherMap(units, entry.getApiKey());

        final byte forecastDays = Byte.valueOf(String.valueOf(entry.getDays()));
        try {

            final DailyForecast forecast = owm.dailyForecastByCityName(entry.getLocation(), entry.getCountryCode(), forecastDays);

            System.out.println("Raw Response: " + forecast.getRawResponse());

            int numForecasts = forecast.getForecastCount();
            for (int i = 0; i < numForecasts; i++) {
                final DailyForecast.Forecast dayForecast = forecast.getForecastInstance(i);

                IntializeForecastValues(forecast.getForecastInstance(i),
                        dayForecast.getTemperatureInstance(),
                        dayForecast.getWeatherInstance(0));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            WeatherMaphm.clear();
        }
        return WeatherMaphm;//epistrofi listas
    }

    private void IntializeForecastValues(final DailyForecast.Forecast dayForecast,
            final DailyForecast.Forecast.Temperature temperature,
            final DailyForecast.Forecast.Weather weather) {
        forecastValue = new ForecastValues();

        forecastValue.setDateInformations("Apudeti e'yasembayo: " + String.valueOf(dayForecast.getDateTime()));

        forecastValue.setMinTemperature(TemperatureRoundSplit.SplitStringValue(temperature.getMinimumTemperature()) + DEGREE + "C");
        forecastValue.setMaxTemperature(TemperatureRoundSplit.SplitStringValue(temperature.getMaximumTemperature()) + DEGREE + "C");
        forecastValue.setDateTemperature(TemperatureRoundSplit.SplitStringValue(temperature.getDayTemperature()) + DEGREE + "C");
//        Humidity to amazzi agali mu mpewo
        forecastValue.setHumidity("Amazzi agali mu mpewo : " + String.valueOf(dayForecast.getHumidity()) + "%");
//        Pressure to Puleesa to Amanyi g'empewo to puleesa
        forecastValue.setPressure("Puleesa: " + String.valueOf(dayForecast.getPressure()) + " mbar ");
        forecastValue.setClouds(String.valueOf(dayForecast.getPercentageOfClouds()) + "%");
//        Wind Speed to Obungi bw'empewo
        forecastValue.setWind_Speed("Obungi bw'empewo : " + String.valueOf(dayForecast.getWindSpeed()) + "m/s");

        forecastValue.setDescription(weather.getWeatherDescription());
        forecastValue.setDayofWeek(dayForecast.getDateTime());

        this.weather.setForecastValue(forecastValue);
        final int day = this.weather.GetDayCode();

        if (day == LocationInfo.DayCode()) {
            forecastValue.setCurrentDay(true);
            forecastValue.setMainWeatherIcon(this.weather
                    .WeatherIcon(forecastValue.getDescription(), true));
        }

        ImageIcon WeatherIcon = this.weather.WeatherIcon(forecastValue.getDescription());
        forecastValue.setWeatherIcon(WeatherIcon);

        PutMapValues(this.weather.DayOfWeekWeather(day));

        System.out.println(weather.getWeatherDescription());
        System.out.println(forecastValue.toString());


    }

//    These two lines of code commented out below are what are supposed to make the app speak.
//    You can see the method below called SaySth for an idea of what I want to happen.

//    TextToSpeech tts = new TextToSpeech();
//    tts.speak("Obukuba bugenda kufuyirira leero", 1.0f, false, false);

//    private void SaySth(final DailyForecast.Forecast.Weather weather){
//        if(weather.getWeatherDescription().equals("moderate rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Obukuba bugenda kufuyirira leero", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("heavy intensity rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Enkuba egenda kufudemba leero", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("light rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Enkuba entono tono", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("very heavy rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("extreme rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("freezing rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("light intensity shower rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("shower rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("heavy intensity rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("ragged shower rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        }
////        clouds section
//        else if(weather.getWeatherDescription().equals("overcast clouds")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Kazimeera", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("few clouds")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Obuleerere", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("scattered clouds")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Kibisse", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("broken clouds")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Kibisse", 1.0f, false, false);
//        }
////        clear sky section
//        else if(weather.getWeatherDescription().equals("clear sky")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Butukula", 1.0f, false, false);
//        }
////        thunderstorm section
//        else if(weather.getWeatherDescription().equals("thunderstorm with light rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("thunderstorm with rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("thunderstorm with heavy rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("light thunderstorm")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("thunderstorm")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("heavy thunderstorm")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("ragged thunderstorm")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("thunderstorm with light drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("thunderstorm with drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("thunderstorm with heavy drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        }
////        drizzle
//        else if(weather.getWeatherDescription().equals("light intensity drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("Ekimpoowooze", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("heavy intensity drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("ragged shower rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("light intensity drizzle rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("drizzle rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("heavy intensity drizzle rain")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("shower rain and drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("heavy shower rain and drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        } else if(weather.getWeatherDescription().equals("shower drizzle")){
//            TextToSpeech tts = new TextToSpeech();
//            tts.speak("", 1.0f, false, false);
//        }
//    }

    private void PutMapValues(final DynJLabelObject labelObj) {
        WeatherMaphm.put(labelObj, forecastValue);
    }
}
