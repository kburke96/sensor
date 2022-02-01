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
        int totalTemp = 0;

        /*
         * Assuming the List of temps is in choronological order
         * So start at the end of the list to get most recent temp
         * and add required number of days to find total temp
         * Return the average (total/days).
         */ 
        for (int i=sensorTemps.size()-1; i>sensorTemps.size()-days-1; i--) {
            totalTemp+=sensorTemps.get(i);
        }
        return totalTemp/days;
    }

    public Sensor addTemperatureToSensor(int sensorId, int newTemp) {
        Optional<Sensor> sensor = sensorRepo.findById(sensorId);
        List<Integer> currentSensorTemps = sensor.get().getTemperatures();
        currentSensorTemps.add(newTemp);
        sensor.get().setTemperatures(currentSensorTemps);
        return sensorRepo.save(sensor.get());
    }
}
