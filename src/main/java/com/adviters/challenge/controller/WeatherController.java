package com.adviters.challenge.controller;

import com.adviters.challenge.dto.AverageDTO;
import com.adviters.challenge.model.WeatherForecast;
import com.adviters.challenge.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/forecast", produces = "application/json")
    public ResponseEntity<Object> getWeatherByOpenweather(@RequestParam String name, @RequestHeader("appid") String apiKey) {
        Object openWeather = weatherService.getWeatherForecast(name, apiKey);
        ResponseEntity responseEntity = new ResponseEntity<>(openWeather, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity save(@RequestBody WeatherForecast weatherForecast) {
        WeatherForecast weatherForecastResponse = weatherService.save(weatherForecast);
        ResponseEntity responseEntity = new ResponseEntity<>(weatherForecastResponse, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(value = "/search/city/date", produces = "application/json")
    public ResponseEntity<WeatherForecast> findByCityAndDate(@RequestParam String name, @RequestParam String date) {
        WeatherForecast weatherForecastResponse = weatherService.findByCityAndDate(name, date);
        ResponseEntity responseEntity = new ResponseEntity<>(weatherForecastResponse, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/search/city", produces = "application/json")
    public ResponseEntity<List<WeatherForecast>> findByCity(@RequestParam String name) {
        List<WeatherForecast> listWeatherForecastResponse = weatherService.findByCity(name);
        ResponseEntity responseEntity = new ResponseEntity<>(listWeatherForecastResponse, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/search/country", produces = "application/json")
    public ResponseEntity<List<WeatherForecast>> findByCountry(@RequestParam Long idCountry) {
        List<WeatherForecast> listWeatherForecastResponse = weatherService.findByCountry(idCountry);
        ResponseEntity responseEntity = new ResponseEntity<>(listWeatherForecastResponse, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/average/city", produces = "application/json")
    public ResponseEntity<AverageDTO> findByRangeDate(@RequestParam String name, @RequestParam String startDate, @RequestParam String endDate) {
        AverageDTO listAverageDTO = weatherService.calculateAverageByCity(name, startDate, endDate);
        ResponseEntity responseEntity = new ResponseEntity<>(listAverageDTO, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/average/country", produces = "application/json")
    public ResponseEntity<AverageDTO> findByRangeDate(@RequestParam Long idCountry, @RequestParam String startDate, @RequestParam String endDate) {
        AverageDTO listAverageDTO = weatherService.calculateAverageByCountry(idCountry, startDate, endDate);
        ResponseEntity responseEntity = new ResponseEntity<>(listAverageDTO, HttpStatus.OK);
        return responseEntity;
    }

}
