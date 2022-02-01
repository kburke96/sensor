package com.weatherapp.sensor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.weatherapp.sensor.model.Sensor;
import com.weatherapp.sensor.repository.SensorRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SensorServiceTest {
    
    @Mock
    private SensorRepository sensorRepo;

    private SensorService sensorService;

    List<Integer> s1Temps = Stream.of(12,13,14,10).collect(Collectors.toList());
    Sensor sensor1 = new Sensor(1, "Ireland", "Limerick", s1Temps);

    List<Integer> s1TempsUpdated = Arrays.asList(12, 13, 14, 10, 15);
    Sensor sensor1Updated = new Sensor(1, "Ireland", "Limerick", s1Temps);

    List<Integer> s2Temps = Arrays.asList(110, 34, 132, 25);
    Sensor sensor2 = new Sensor(1, "Ireland", "Galway", s2Temps);

    List<Sensor> sensorList;

    @BeforeEach
    public void setUp() {
        sensorService = new SensorService(sensorRepo);

        sensorList = new ArrayList<Sensor>();
        sensorList.add(sensor1);
        sensorList.add(sensor2);
    }

    @AfterEach
    public void tearDown() {
        sensorService = null;
        sensorList = null;
        sensor1 = sensor2 = null;
    }

    @Test
    public void addNewSensorTest() {
        when(sensorRepo.save(sensor1)).thenReturn(sensor1);
        Sensor savedSensor = sensorService.addNewSensor(sensor1);

        assertEquals(sensor1, savedSensor);
    }

    @Test
    public void findAllSensorsTest() {
        when(sensorRepo.findAll()).thenReturn(sensorList);
        List<Sensor> returnedSensors = sensorService.findAllSensors();

        assertEquals(sensorList, returnedSensors);
    }

    @Test
    public void findSensorByIdTest() {
        when(sensorRepo.findById(1)).thenReturn(Optional.of(sensor1));
        Optional<Sensor> returnedSensor = sensorService.findSensorById(1);

        assertEquals(Optional.of(sensor1), returnedSensor);
    }

    @Test
    public void findDefaultAverageTempTest() {
        when(sensorRepo.findById(1)).thenReturn(Optional.of(sensor1));
        int returnedTemp = sensorService.findAverageTemp(1, 1);

        assertEquals(10, returnedTemp);
    }

    @Test
    public void findAverageTempLast3DaysTest() {
        when(sensorRepo.findById(1)).thenReturn(Optional.of(sensor1));
        int returnedTemp = sensorService.findAverageTemp(1, 3);

        assertEquals(12, returnedTemp);
    }
    
    @Test
    public void addTempToSensor() {
        when(sensorRepo.findById(1)).thenReturn(Optional.of(sensor1));
        when(sensorRepo.save(sensor1)).thenReturn(sensor1Updated);

        Sensor returnedSensor = sensorService.addTemperatureToSensor(1, 15);

        assertEquals(sensor1Updated, returnedSensor);
    }
}
