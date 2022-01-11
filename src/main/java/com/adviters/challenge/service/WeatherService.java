package com.adviters.challenge.service;

import com.adviters.challenge.config.ApplicationConfiguration;
import com.adviters.challenge.dto.AverageDTO;
import com.adviters.challenge.exceptions.RequestException;
import com.adviters.challenge.model.WeatherForecast;
import com.adviters.challenge.repository.IWeather;
import com.adviters.challenge.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IWeather iWeather;

    @Autowired
    private ApplicationConfiguration app;

    public Object getWeatherForecast(String city, String apikey) {
        if (!utils.isCityValid(city))
            throw new RequestException(app.getProperty("cityIsRequired"), "C-400", HttpStatus.BAD_REQUEST);

        Object openWeather = restTemplate.getForObject(utils.getURL(city, apikey), Object.class);
        return openWeather;
    }

    public WeatherForecast save(WeatherForecast weatherForecast) {
        if (weatherForecast.getDate() == null)
            weatherForecast.setDate(utils.getTodayDate());

        WeatherForecast weatherForecastByDate = this.findByCityAndDate(weatherForecast.getName(), weatherForecast.getDate());
        if (weatherForecastByDate != null)
            throw new RequestException(app.getProperty("duplicateRecord"), "C-400", HttpStatus.BAD_REQUEST);

        return iWeather.save(weatherForecast);
    }

    public WeatherForecast findByCityAndDate(String city, String date) {
        if (!utils.isDateValid(date))
            throw new RequestException(app.getProperty("dateIsInvalid"), "C-400", HttpStatus.BAD_REQUEST);

        if (!utils.isCityValid(city))
            throw new RequestException(app.getProperty("cityIsRequired"), "C-400", HttpStatus.BAD_REQUEST);

        WeatherForecast weatherForecast = iWeather.findByNameAndDate(city, date);
        if (weatherForecast == null)
            return null;

        return weatherForecast;
    }

    public List<WeatherForecast> findByCity(String city) {
        if (!utils.isCityValid(city))
            throw new RequestException(app.getProperty("cityIsRequired"), "C-400", HttpStatus.BAD_REQUEST);

        List<WeatherForecast> listWeatherForecast = iWeather.findByName(city);

        if (!utils.isListValid(listWeatherForecast))
            throw new RequestException(app.getProperty("noRecordsFoundCity"), "C-400", HttpStatus.BAD_REQUEST);

        return listWeatherForecast;
    }

    public List<WeatherForecast> findByCountry(Long idCountry) {
        List<WeatherForecast> listWeatherForecast = iWeather.findByCountry(idCountry);

        if (!utils.isListValid(listWeatherForecast))
            throw new RequestException(app.getProperty("noRecordsFoundCountry"), "C-400", HttpStatus.BAD_REQUEST);

        return listWeatherForecast;
    }

    public AverageDTO calculateAverageByCity(String city, String startDate, String endDate) {
        if (!utils.isDateValid(startDate) || !utils.isDateValid(endDate))
            throw new RequestException(app.getProperty("dateIsInvalid"), "C-400", HttpStatus.BAD_REQUEST);

        if (!utils.isCityValid(city))
            throw new RequestException(app.getProperty("cityIsRequired"), "C-400", HttpStatus.BAD_REQUEST);

        List<WeatherForecast> listWeatherForecast = iWeather.findDateRangeByCity(city, startDate, endDate);

        if (!utils.isListValid(listWeatherForecast))
            throw new RequestException(app.getProperty("noRecordsFoundCity"), "C-400", HttpStatus.BAD_REQUEST);

        return utils.calculateAverageForecast(listWeatherForecast, startDate, endDate);
    }

    public AverageDTO calculateAverageByCountry(Long idCountry, String startDate, String endDate) {
        if (!utils.isDateValid(startDate) || !utils.isDateValid(endDate))
            throw new RequestException(app.getProperty("dateIsInvalid"), "C-400", HttpStatus.BAD_REQUEST);

        List<WeatherForecast> listWeatherForecast = iWeather.findDateRangeByCountry(idCountry, startDate, endDate);

        if (!utils.isListValid(listWeatherForecast))
            throw new RequestException(app.getProperty("noRecordsFoundCountry"), "C-400", HttpStatus.BAD_REQUEST);

        return utils.calculateAverageForecast(listWeatherForecast, startDate, endDate);
    }
}















