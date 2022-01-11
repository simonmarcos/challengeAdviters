package com.adviters.challenge.repository;

import com.adviters.challenge.model.WeatherForecast;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IWeather extends CrudRepository<WeatherForecast, Long> {

    @Query("SELECT m FROM WeatherForecast m WHERE m.name = :name AND m.date = :date")
    WeatherForecast findByNameAndDate(@Param("name") String name, @Param("date") String date);

    List<WeatherForecast> findByName(String name);

    @Query("SELECT m FROM WeatherForecast m WHERE m.name = :name AND m.date BETWEEN :start AND :end")
    List<WeatherForecast> findDateRangeByCity(@Param("name") String name, @Param("start") String startDate, @Param("end") String endDate);

    @Query("SELECT m FROM WeatherForecast m WHERE m.country.id = :id")
    List<WeatherForecast> findByCountry(@Param("id") Long idCountry);

    @Query("SELECT m FROM WeatherForecast m WHERE m.country.id = :id AND m.date BETWEEN :start AND :end")
    List<WeatherForecast> findDateRangeByCountry(@Param("id") Long idCountry, @Param("start") String startDate, @Param("end") String endDate);

}
