package com.adviters.challenge.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "weatherForecast")
@Entity
@Data
@Builder
public class WeatherForecast implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "temp")
    private double temp;

    @Column(name = "feels_like")
    private double feels_like;

    @Column(name = "temp_min")
    private double temp_min;

    @Column(name = "temp_max")
    private double temp_max;

    @Column(name = "pressure")
    private double pressure;

    @Column(name = "humidity")
    private double humidity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_country")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Country country;
}
