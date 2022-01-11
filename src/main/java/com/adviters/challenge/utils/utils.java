package com.adviters.challenge.utils;

import com.adviters.challenge.dto.AverageDTO;
import com.adviters.challenge.dto.CountryDTO;
import com.adviters.challenge.model.Country;
import com.adviters.challenge.model.WeatherForecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class utils {

    final static String URI_OPENWEATHER = "https://api.openweathermap.org/data/2.5/weather?q=city&appid=apiKey&units=metric";
    final static String REGEX_DATE = "^\\d{4}-\\d{2}-\\d{2}$";

    public static String getURL(String city, String apiKey) {
        return URI_OPENWEATHER.replace("city", city).replace("apiKey", apiKey);
    }

    public static CountryDTO parseCountryToDTO(Country country) {
        CountryDTO countryDTO = CountryDTO
                .builder()
                .id(country.getId())
                .name(country.getName())
                .build();
        return countryDTO;
    }

    public static String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static AverageDTO calculateAverageForecast(List<WeatherForecast> listWeatherForecast, String startDate, String endDate) {

        String rangeDate = startDate + " - " + endDate;

        double averageTemp = listWeatherForecast.stream().mapToDouble(WeatherForecast::getTemp).average().orElse(0.0);
        double averageFeelsLike = listWeatherForecast.stream().mapToDouble(WeatherForecast::getFeels_like).average().orElse(0.0);
        double averageTempMin = listWeatherForecast.stream().mapToDouble(WeatherForecast::getTemp_min).average().orElse(0.0);
        double averageTempMax = listWeatherForecast.stream().mapToDouble(WeatherForecast::getTemp_max).average().orElse(0.0);
        double averagePressure = listWeatherForecast.stream().mapToDouble(WeatherForecast::getPressure).average().orElse(0.0);
        double averageHumidity = listWeatherForecast.stream().mapToDouble(WeatherForecast::getHumidity).average().orElse(0.0);

        AverageDTO averageDTO = AverageDTO
                .builder()
                .temp(averageTemp)
                .feelsLike(averageFeelsLike)
                .tempMin(averageTempMin)
                .tempMax(averageTempMax)
                .pressure(averagePressure)
                .humidity(averageHumidity)
                .build();

        return averageDTO;
    }

    public static boolean isDateValid(String date) {
        if (date == null)
            return false;
        return (date.matches(REGEX_DATE)) ? true : false;
    }

    public static boolean isCityValid(String city) {
        return (city == null || city.length() == 0) ? false : true;
    }

    public static boolean isListValid(List<WeatherForecast> list) {
        return (list == null || list.size() == 0) ? false : true;
    }

}
