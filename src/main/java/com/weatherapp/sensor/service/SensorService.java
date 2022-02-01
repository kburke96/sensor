package com.weatherapp.sensor.service;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.weatherapp.sensor.model.Sensor;
import com.weatherapp.sensor.repository.SensorRepository;

@Service
@AllArgsConstructor
public class SensorService {
    
    private SensorRepository sensorRepo;

    public Sensor addNewSensor(Sensor sensor) {
        return sensorRepo.save(sensor);
    }

    public List<Sensor> findAllSensors() {
        return sensorRepo.findAll();
    }

    public Optional<Sensor> findSensorById(int id) {
        return sensorRepo.findById(id);
    }

    public Integer findAverageTemp(int sensorId, int days) {
        Optional<Sensor> sensor = sensorRepo.findById(sensorId);

        List<Integer> sensorTemps = sensor.get().getTemperatures();
        int averageTemp = 0;

        System.out.println("SensorTemps is: " + sensorTemps.toString());
        for (int i=sensorTemps.size()-1; i>sensorTemps.size()-days-1; i--) {
            System.out.println("Adding this to averageTemp: " + sensorTemps.get(i));
            averageTemp+=sensorTemps.get(i);
        }
        System.out.println("Average temp is: " + averageTemp);
        return averageTemp/days;
    }

    public Sensor addTemperatureToSensor(int sensorId, int newTemp) {
        Optional<Sensor> sensor = sensorRepo.findById(sensorId);
        List<Integer> currentSensorTemps = sensor.get().getTemperatures();
        currentSensorTemps.add(newTemp);
        sensor.get().setTemperatures(currentSensorTemps);
        return sensorRepo.save(sensor.get());
    }
}
