package com.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_type_id")
    private TypeEvent typeEvent;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "event_commercial", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {
            @JoinColumn(name = "commercial_id")})
    private Set<Commercial> commercials;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventTiers> eventTiers;

//    @Column(name = "event_date")
//    private Date eventDate;

    @Column(name = "start_event_date")
    private Date startEventDate;

    @Column(name = "end_event_date")
    private Date endEventDate;

    @Column(name = "create_at")
    private Date createAt;

    public Event() {
    }

    public Event(Integer id, String name, Locality locality, String description, TypeEvent typeEvent, Set<Commercial> commercials, List<EventTiers> eventTiers, Date eventDate, Date startEventDate, Date endEventDate, Date createAt) {
        this.id = id;
        this.name = name;
        this.locality = locality;
        this.description = description;
        this.typeEvent = typeEvent;
        this.commercials = commercials;
        this.eventTiers = eventTiers;
      //  this.eventDate = eventDate;
        this.startEventDate = startEventDate;
        this.endEventDate = endEventDate;
        this.createAt = createAt;
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

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeEvent getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(TypeEvent typeEvent) {
        this.typeEvent = typeEvent;
    }

    public Set<Commercial> getCommercials() {
        return commercials;
    }

    public void setCommercials(Set<Commercial> commercials) {
        this.commercials = commercials;
    }

    public List<EventTiers> getEventTiers() {
        return eventTiers;
    }

    public void setEventTiers(List<EventTiers> eventTiers) {
        this.eventTiers = eventTiers;
    }

//    public Date getEventDate() {
//        return eventDate;
//    }
//
//    public void setEventDate(Date eventDate) {
//        this.eventDate = eventDate;
//    }

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
