package com.weatherapp.sensor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.weatherapp.sensor.model.Sensor;
import com.weatherapp.sensor.service.SensorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(SensorController.class)
public class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorService sensorService;

    List<Integer> s1Temps = Stream.of(12, 13, 14, 10).collect(Collectors.toList());
    Sensor sensor1 = new Sensor(1, "Ireland", "Limerick", s1Temps);

    List<Integer> s1TempsUpdated = Stream.of(12, 13, 14, 10, 15).collect(Collectors.toList());
    Sensor sensor1Updated = new Sensor(1, "Ireland", "Limerick", s1TempsUpdated);

    List<Sensor> sensorList = Stream.of(sensor1).collect(Collectors.toList());

    @Test
    public void getAllSensorsTest() throws Exception {
        when(sensorService.findAllSensors()).thenReturn(sensorList);

        String expectedResult = "[{\"id\":1,\"countryName\":\"Ireland\",\"cityName\":\"Limerick\",\"temperatures\":[12,13,14,10]}]";
        this.mockMvc.perform(get("/sensors/all")).andDo(print()).andExpect(content().string(expectedResult));
    }

    @Test
    public void getSpecificSensorTest() throws Exception {
        when(sensorService.findSensorById(1)).thenReturn(Optional.of(sensor1));

        String expectedResult = "{\"id\":1,\"countryName\":\"Ireland\",\"cityName\":\"Limerick\",\"temperatures\":[12,13,14,10]}";
        this.mockMvc.perform(get("/sensors/1")).andDo(print()).andExpect(content().string(expectedResult));
    }

    @Test
    public void addNewSensorTest() throws Exception {
        when(sensorService.addNewSensor(any())).thenReturn(sensor1);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/sensors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(getSensorJson());
        String expectedResult = getSensorJson(); // "{\"id\":1,\"countryName\":\"Ireland\",\"cityName\":\"Limerick\",\"temperatures\":[12,13,14,10]}";
        this.mockMvc.perform(builder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResult));

    }

    @Test
    public void addTempToSensor() throws Exception {
        when(sensorService.addTemperatureToSensor(anyInt(), anyInt())).thenReturn(sensor1Updated);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/sensors/1/addtemp?temp=15")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
        // .content(getUpdatedSensorJson());
        String expectedResult = getUpdatedSensorJson();
        this.mockMvc.perform(builder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResult));

    }

    @Test
    public void getLast3DaysAverageTemp() throws Exception {
        when(sensorService.findAverageTemp(anyInt(), anyInt())).thenReturn(12);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/sensors/1/gettemp?days=3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Integer.toString(12)));
    }

    private String getSensorJson() {
        return "{\"id\":1,\"countryName\":\"Ireland\",\"cityName\":\"Limerick\",\"temperatures\":[12,13,14,10]}";
    }

    private String getUpdatedSensorJson() {
        return "{\"id\":1,\"countryName\":\"Ireland\",\"cityName\":\"Limerick\",\"temperatures\":[12,13,14,10,15]}";
    }

}
