package com.adviters.challenge.service;

import com.adviters.challenge.config.ApplicationConfiguration;
import com.adviters.challenge.exceptions.RequestException;
import com.adviters.challenge.model.Country;
import com.adviters.challenge.model.WeatherForecast;
import com.adviters.challenge.repository.IWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private IWeather iWeatherMock;

    @Mock
    private ApplicationConfiguration app;

    @InjectMocks
    private WeatherService weatherService;

    private WeatherForecast weatherForecastMock = null;
    final String URL_OPENWEATHER = "https://api.openweathermap.org/data/2.5/weather?q=Salta&appid=123456789&units=metric";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        Country country = Country
                .builder()
                .id(1L)
                .name("Argentina")
                .build();

        weatherForecastMock = WeatherForecast
                .builder()
                .name("Salta")
                .date("2022-08-12")
                .country(country)
                .build();
    }

    @Test
    public void getWeatherWithCity_OK_Test() {
        when(restTemplateMock.getForObject(URL_OPENWEATHER, Object.class)).thenReturn(new Object());
        assertNotNull(weatherService.getWeatherForecast("Salta", "123456789"));
    }

    @Test
    public void getWeatherWithCity_WithCityNull_Test() {
        assertThrows(RequestException.class, () -> {
            assertNull(weatherService.getWeatherForecast(null, ""));
        });
    }

    @Test
    public void saveWeather_OK_Test() {
        when(iWeatherMock.save(Mockito.any(WeatherForecast.class))).thenAnswer(i -> i.getArguments()[0]);
        assertNotNull(iWeatherMock.save(weatherForecastMock));
        assertEquals("Salta", weatherForecastMock.getName());
    }

    @Test
    public void findByCityAndDate_WithDataOK_Test() {

        when(iWeatherMock.findByNameAndDate(any(String.class), any(String.class))).thenReturn(weatherForecastMock);
        assertNotNull(weatherService.findByCityAndDate("Salta", "2022-08-12"));
    }

    @Test
    public void findByCityAndDate_WithNameNull_Test() {
        when(iWeatherMock.findByNameAndDate(any(String.class), any(String.class))).thenReturn(weatherForecastMock);
        assertThrows(RequestException.class, () -> {
            assertNull(weatherService.findByCityAndDate(null, "2022-08-12"));
        });
    }

    @Test
    public void findByCityAndDate_WithDateNull_Test() {
        when(iWeatherMock.findByNameAndDate(any(String.class), any(String.class))).thenReturn(weatherForecastMock);
        assertThrows(RequestException.class, () -> {
            assertNull(weatherService.findByCityAndDate("Salta", null));
        });
    }

    @Test
    public void findByCityAndDate_WithDateIncorrect_Test() {
        when(iWeatherMock.findByNameAndDate(any(String.class), any(String.class))).thenReturn(weatherForecastMock);
        assertThrows(RequestException.class, () -> {
            assertNull(weatherService.findByCityAndDate("Salta", "2022/10/12"));
        });
    }

    @Test
    public void findByCity_WithDataOK_Test() {
        List<WeatherForecast> listWeatherForecast = new ArrayList<>();
        listWeatherForecast.add(weatherForecastMock);

        when(iWeatherMock.findByName(any(String.class))).thenReturn(listWeatherForecast);
        assertNotNull(weatherService.findByCity("Salta"));
        assertEquals(listWeatherForecast.size(), 1);
    }

    @Test
    public void findByCity_WithNameNull_Test() {
        List<WeatherForecast> listWeatherForecast = new ArrayList<>();
        when(iWeatherMock.findByName(any(String.class))).thenReturn(listWeatherForecast);
        assertThrows(RequestException.class, () -> {
            assertNull(weatherService.findByCity(null));
        });
    }

    @Test
    public void findByCountry_WithIdExistent_Test() {
        List<WeatherForecast> listWeatherForecast = new ArrayList<>();
        listWeatherForecast.add(weatherForecastMock);

        when(iWeatherMock.findByCountry(any(Long.class))).thenReturn(listWeatherForecast);
        assertNotNull(weatherService.findByCountry(1L));
    }

    @Test
    public void findByCountry_WithIdNonExistent_Test() {
        List<WeatherForecast> listWeatherForecast = new ArrayList<>();

        when(iWeatherMock.findByCountry(any(Long.class))).thenReturn(listWeatherForecast);
        assertThrows(RequestException.class, () -> {
            assertNull(weatherService.findByCountry(1L));
        });
    }
}