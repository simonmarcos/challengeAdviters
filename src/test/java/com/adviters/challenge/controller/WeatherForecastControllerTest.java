package com.adviters.challenge.controller;

import com.adviters.challenge.model.Country;
import com.adviters.challenge.model.WeatherForecast;
import com.adviters.challenge.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
class WeatherForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void getForecast_OK_Test() throws Exception {

        Mockito.when(weatherService.getWeatherForecast(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(Object.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/weather/forecast")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}