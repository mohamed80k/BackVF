package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "type_evenement")
public class TypeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "observation")
    private String observation;

    @OneToMany(mappedBy = "typeEvent", fetch = FetchType.LAZY)
    private Set<Event> events;

    public TypeEvent() {
    }

    public TypeEvent(Integer id, String name, String abbreviation, String observation, Set<Event> events) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.observation = observation;
        this.events = events;
    }

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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
