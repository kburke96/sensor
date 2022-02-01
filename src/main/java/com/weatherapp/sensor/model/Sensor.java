package com.weatherapp.sensor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    private int id;
    private String countryName;
    private String cityName;
    @ElementCollection
    private List<Integer> temperatures;

    public Sensor(int id) {
        this.id=id;
        this.countryName="";
        this.cityName="";
        this.temperatures=new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Sensor [cityName=" + cityName + ", countryName=" + countryName + ", id=" + id + ", temperatures="
                + temperatures.toString() + "]";
    }

    

    
}
