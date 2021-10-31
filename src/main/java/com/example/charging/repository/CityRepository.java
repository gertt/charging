package com.example.charging.repository;

import com.example.charging.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  CityRepository  extends JpaRepository<City, Long> { }
