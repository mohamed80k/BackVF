package com.dto;

public class RegionInfoDto {

    private Integer id ;
    private String regionName;
    private String abbreviation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "RegionInfoDto{" +
                "id=" + id +
                ", regionName='" + regionName + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                '}';
    }
}
