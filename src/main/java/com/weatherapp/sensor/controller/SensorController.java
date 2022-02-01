package com.weatherapp.sensor.controller;

import java.util.List;
import java.util.Optional;

import com.weatherapp.sensor.model.Sensor;
import com.weatherapp.sensor.service.SensorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value="/sensors")
@AllArgsConstructor
public class SensorController {
    
    private SensorService sensorService;

    @PostMapping(value="/add")
    public ResponseEntity<Sensor> addNewSensor(@RequestBody Sensor sensor) {
        final Sensor newSensor = sensorService.addNewSensor(sensor);
        return ResponseEntity.ok(newSensor);
    }

    @GetMapping(value="/all")
    public ResponseEntity<List<Sensor>> findAllSensors() {
        final List<Sensor> allSensors = sensorService.findAllSensors();
        return ResponseEntity.ok(allSensors);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<Sensor>> findSensor(@PathVariable int id) {
        return ResponseEntity.ok(sensorService.findSensorById(id));
    }


    /**
     * Need a PUT mapping to add data to sensor
     * uri could be /sensors/{id}/addtemp/{temp}
     *              /sensors/{id}/addhumidity/{humidity}
     */

    @PutMapping(value="/{id}/addtemp", params="temp")
    public ResponseEntity<Sensor> addTempToStation(@PathVariable int id, 
                                                   @RequestParam("temp") int temp) {
        return ResponseEntity.ok(sensorService.addTemperatureToSensor(id, temp));
    }

    /**
     * Need a GET mapping to retrieve sensor data for time period
     * uri could be /sensors/{id}/gettemp/?days={days}
     *              /sensors/{id}/gethumidity/?days={days}
     */
    @GetMapping(value="/{id}/gettemp")
    public ResponseEntity<Integer> getAverageTemp(@PathVariable int id, 
                                                  @RequestParam(defaultValue = "1") int days) {
        return ResponseEntity.ok(sensorService.findAverageTemp(id, days));
    }

}
