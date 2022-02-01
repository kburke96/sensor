package com.weatherapp.sensor.repository;

import com.weatherapp.sensor.model.Sensor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer>{
    
}
