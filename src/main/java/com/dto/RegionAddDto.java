package com.dto;

public class RegionAddDto {

    //    @Size(min = 3, max = 90, message = "La nom doit être comprise entre 3 et 50 caractères !")
    private String regionName;

    //    @Size(max = 50, message = "L'abbreviation dépasse 50 caractères !")
    private String abbreviation;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public RegionAddDto(String regionName, String abbreviation) {
        this.regionName = regionName;
        this.abbreviation = abbreviation;
    }
}