package com.dto;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class TypeEventUpdateDto {

    private Integer id;

    @NotNull(message = "nom du type est nul !")
    private String name;

    private String abbreviation;

    private String observation;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


}
