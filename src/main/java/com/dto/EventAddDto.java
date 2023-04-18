package com.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

public class EventAddDto {
    @NotNull(message = "Le nom d'évenement est nul !")
    private String name;

    private LocalityDto locality;

    private String description;

    @NotNull(message = "Type d'évenement est nul !")
    private Integer typeEvent;

    //@NotNull(message = "Type d'agri est nul !")
    //private Integer typeProject;

    private Set<Integer> commercials;


//    @NotNull(message = "date d'évenement est nul !")
//    private Date eventDate;

    private Date startEventDate;

    private Date endEventDate;

    private Date createAt;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalityDto getLocality() {
        return locality;
    }

    public void setLocality(LocalityDto locality) {
        this.locality = locality;
    }

    public Integer getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(Integer typeEvent) {
        this.typeEvent = typeEvent;
    }

//    public Date getEventDate() {
//        return eventDate;
//    }
//
//    public void setEventDate(Date eventDate) {
//        this.eventDate = eventDate;
//    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Integer> getCommercials() {
        return commercials;
    }

    public void setCommercials(Set<Integer> commercials) {
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
}
