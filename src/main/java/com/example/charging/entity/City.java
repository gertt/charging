package com.example.charging.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "CITY")
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "City_code")
    private Long CityCode;

    @Column(name = "city_name")
    private String CityName;
}



