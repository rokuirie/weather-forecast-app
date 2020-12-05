package com.plugin.awesomejava.UIApp;

import com.plugin.awesomejava.Forecast.FeedEntry;
import com.plugin.awesomejava.Forecast.ForecastValues;
import com.plugin.awesomejava.Forecast.WeatherHttpRest;
import com.plugin.awesomejava.Location.Error;
import com.plugin.awesomejava.Location.LocationInfo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class BackgroundWorker extends SwingWorker<String, Void> {

    private final FeedEntry entrys;
    private Error ErrorCode;
    private final DynamicJLabelList DynJLabelList;

    private final JLabel CurrentTempLabel;
    private final JLabel DayLambel;
    private final JLabel DescriptionLabel;
    private final JLabel DetailedLabel;
    private final JLabel HumidityLabel;
    private final JLabel PressureJLabel;
    private final JLabel MaxTempLabel;
    private final JLabel MinTempLabel;
    private final JLabel WindLabel;
    private final JLabel WeatherDayIcon;
    private final JLabel RefreshIconLabel;

    public BackgroundWorker(FeedEntry entrys, JLabel CurrentTempLabel, JLabel DayLambel, JLabel DescriptionLabel, JLabel DetailedLabel,
            JLabel HumidityLabel, JLabel MaxTempLabel, JLabel MinTempLabel, JLabel WindLabel) {
        this.entrys = entrys;
        this.CurrentTempLabel = CurrentTempLabel;
        this.DayLambel = DayLambel;
        this.DescriptionLabel = DescriptionLabel;
        this.DetailedLabel = DetailedLabel;
        this.HumidityLabel = HumidityLabel;
        this.MaxTempLabel = MaxTempLabel;
        this.MinTempLabel = MinTempLabel;
        this.WindLabel = WindLabel;
        this.PressureJLabel = null;
        this.DynJLabelList = null;
        this.WeatherDayIcon = null;
        this.RefreshIconLabel = null;
    }

    public BackgroundWorker(FeedEntry entrys, DynamicJLabelList DynJLabelList, JLabel CurrentTempLabel, JLabel DayLambel,
            JLabel DescriptionLabel, JLabel DetailedLabel, JLabel HumidityLabel, JLabel PressureJLabel, JLabel MaxTempLabel,
            JLabel MinTempLabel, JLabel WindLabel, JLabel WeatherDayIcon, JLabel RefreshIconLabel) {
        this.entrys = entrys;
        this.DynJLabelList = DynJLabelList;
        this.CurrentTempLabel = CurrentTempLabel;
        this.DayLambel = DayLambel;
        this.DescriptionLabel = DescriptionLabel;
        this.DetailedLabel = DetailedLabel;
        this.HumidityLabel = HumidityLabel;
        this.PressureJLabel = PressureJLabel;
        this.MaxTempLabel = MaxTempLabel;
        this.MinTempLabel = MinTempLabel;
        this.WindLabel = WindLabel;
        this.WeatherDayIcon = WeatherDayIcon;
        this.RefreshIconLabel = RefreshIconLabel;
    }

    @Override
    protected String doInBackground() throws Exception {
        this.RefreshIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ring.gif")));

        final WeatherHttpRest doRequest = new WeatherHttpRest(entrys, DynJLabelList);

        final HashMap<DynJLabelObject, ForecastValues> ForecastMap = doRequest.HttpRestRequest();
        if (ForecastMap.isEmpty()) {
            ErrorCode = Error.LISTVALUES;
            return "";
        }
        UpdateStatus(ForecastMap);
        return "";
    }

    private void SetForecastValuesForCurrentDay(final ForecastValues ForecastValue) {
        this.CurrentTempLabel.setText(ForecastValue.getDateTemperature());

//        this.DescriptionLabel.setText(ForecastValue.getDescription());
        if(ForecastValue.getDescription().equals("moderate rain")){
//            obukuba bugenda kufuyirira leero for moderate rain
            this.DescriptionLabel.setText("Enkuba nga ssi nyingi");
        } else if(ForecastValue.getDescription().equals("light rain")){
            this.DescriptionLabel.setText("Enkuba entono-tono");
        } else if(ForecastValue.getDescription().equals("freezing rain")){
            this.DescriptionLabel.setText("Enkuba elimu o'buyogovu");
        } else if(ForecastValue.getDescription().equals("shower rain")){
            this.DescriptionLabel.setText("Enkuba");
        } else if(ForecastValue.getDescription().equals("heavy intensity rain")){
            this.DescriptionLabel.setText("Enkuba namutikwa");
        } else if(ForecastValue.getDescription().equals("overcast clouds")){
            this.DescriptionLabel.setText("Embile e'bikwaafu");
        } else if(ForecastValue.getDescription().equals("few clouds")){
            this.DescriptionLabel.setText("Embile ebitono-tono");
        } else if(ForecastValue.getDescription().equals("scattered clouds")){
            this.DescriptionLabel.setText("Embile ebyeetade");
        } else if(ForecastValue.getDescription().equals("broken clouds")){
            this.DescriptionLabel.setText("Embile e'bikutufu-kutufu");
        } else if(ForecastValue.getDescription().equals("clear sky")){
            this.DescriptionLabel.setText("Eggulu okutali bile");
        } else if(ForecastValue.getDescription().equals("thunderstorm with light rain")){
            this.DescriptionLabel.setText("Embuyaga ya laddu n'enkuba entono");
        } else if(ForecastValue.getDescription().equals("thunderstorm with rain")){
            this.DescriptionLabel.setText("Embuyaga ya laddu elimu enkuba");
        } else if(ForecastValue.getDescription().equals("thunderstorm with heavy rain")){
            this.DescriptionLabel.setText("Embuyaga elimu enkuba ennyingi");
        } else if(ForecastValue.getDescription().equals("light thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga entono-tono");
        } else if(ForecastValue.getDescription().equals("thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga");
        } else if(ForecastValue.getDescription().equals("heavy thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga eya maanyi");
        } else if(ForecastValue.getDescription().equals("Ragged thunderstorm")){
            this.DescriptionLabel.setText("Embuyaga eyeetadde-tadde");
        } else if(ForecastValue.getDescription().equals("Thunderstorm with light drizzle")){
            this.DescriptionLabel.setText("Embuyaga elimu obukuba obutono");
        } else if(ForecastValue.getDescription().equals("light intensity drizzle")){
            this.DescriptionLabel.setText("Obukuba obutono");
        } else if(ForecastValue.getDescription().equals("ragged shower rain")){
            this.DescriptionLabel.setText("Enkuba etali ya mujilano");
        } else if(ForecastValue.getDescription().equals("light intensity drizzle rain")){
            this.DescriptionLabel.setText("Obukuba obutono");
        } else if(ForecastValue.getDescription().equals("heavy intensity drizzle rain")){
            this.DescriptionLabel.setText("Enkuba ey'amaanyi");
        } else if(ForecastValue.getDescription().equals("shower rain and drizzle")){
            this.DescriptionLabel.setText("Enkuba etali ya mujilano");
        } else if(ForecastValue.getDescription().equals("heavy shower rain and drizzle")){
            this.DescriptionLabel.setText("Enkuba ey'amaanyi nga yetadde-tadde");
        } else if(ForecastValue.getDescription().equals("shower drizzle")){
            this.DescriptionLabel.setText("Obukuba obuwelako");
        }
        else {
            this.DescriptionLabel.setText(ForecastValue.getDescription());
        }
        
//        this.DetailedLabel.setText(ForecastValue.getDateInformations());
        this.HumidityLabel.setText(ForecastValue.getHumidity());
        this.PressureJLabel.setText(ForecastValue.getPressure());
        this.WeatherDayIcon.setIcon(ForecastValue.getMainWeatherIcon());
        this.MaxTempLabel.setText(ForecastValue.getMaxTemperature());
        this.MinTempLabel.setText(ForecastValue.getMinTemperature());
        this.WindLabel.setText(ForecastValue.getWind_Speed());
    }

    private void UpdateStatus(final HashMap<DynJLabelObject, ForecastValues> ForecastMap) {

        Set set = ForecastMap.entrySet();
        Iterator iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            final Map.Entry MapEntry = (Map.Entry) iterator2.next();
            final ForecastValues ForecastValue = (ForecastValues) MapEntry.getValue();
            final DynJLabelObject DynLabel = (DynJLabelObject) MapEntry.getKey();

            if (ForecastValue.isCurrentDay()) {
                SetForecastValuesForCurrentDay(ForecastValue);
            }
            DynLabel.getTemperatureJLabel().setText(ForecastValue.getDateTemperature());
            if (ForecastValue.getWeatherIcon() != null) {
                DynLabel.getIconJLabel().setIcon(ForecastValue.getWeatherIcon());
            }
        }
    }

    @Override
    protected void done() {
        if (ErrorCode == Error.LISTVALUES) {
            new JFxBuilder(new DialogObject(Alert.AlertType.ERROR, "Error Dialog", "Ooops, there was an error!",
                    "Something goes wrong. Please check your internet connection first or try again later")).Invoke();
        }
        this.RefreshIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Refresh_15px.png")));
    }
}
