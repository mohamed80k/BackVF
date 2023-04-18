package com.dto;

import java.util.Date;
import java.util.List;

public class EventDto {
    private Integer id;

    private String name;

    private String description;

    private LocalityDto locality;

    private TypeEventInfoDto typeEvent;

    private TypeProjectInfoDto typeProject;

    private List<CommercialInfoDto> commercials;

    private Date startEventDate;

    private Date endEventDate;

    private Date createAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalityDto getLocality() {
        return locality;
    }

    public void setLocality(LocalityDto locality) {
        this.locality = locality;
    }

    public TypeEventInfoDto getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(TypeEventInfoDto typeEvent) {
        this.typeEvent = typeEvent;
    }

    public TypeProjectInfoDto getTypeProject() {
        return typeProject;
    }

    public void setTypeProject(TypeProjectInfoDto typeProject) {
        this.typeProject = typeProject;
    }

    public List<CommercialInfoDto> getCommercials() {
        return commercials;
    }

    public void setCommercials(List<CommercialInfoDto> commercials) {
        this.commercials = commercials;
    }

    public Date getStartEventDate() {
        return startEventDate;
    }

    public void setStartEventDate(Date startEventDate) {
        this.startEventDate = startEventDate;
    }

    public Date getEndEventDate() {
        return endEventDate;
    }

    public void setEndEventDate(Date endEventDate) {
        this.endEventDate = endEventDate;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
