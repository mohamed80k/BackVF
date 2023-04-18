package com.entity;


import com.entity.type.TypeEtablissement;

import javax.persistence.*;

@Entity
@Table(name = "event_tiers")
@IdClass(EventTiersId.class)
public class EventTiers {

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name="EventId", referencedColumnName = "event_id")
    private Event event;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "TiersId", referencedColumnName = "tiers_id")
    private Tiers tiers;

    private String etablissement;

    private String typeEtablissement;

    private String typeTiers;

    private String sample;

    private String ville;

    private String fonction;

    private String observation;



    public EventTiers() {
    }

    public EventTiers(Event event, Tiers tiers, String etablissement, String typeEtablissement, String typeTiers, String sample, String ville, String fonction, String observation) {
        this.event = event;
        this.tiers = tiers;
        this.etablissement = etablissement;
        this.typeEtablissement = typeEtablissement;
        this.typeTiers = typeTiers;
        this.sample = sample;
        this.ville = ville;
        this.fonction = fonction;
        this.observation = observation;
    }

    public String getTypeTiers() {
        return typeTiers;
    }

    public void setTypeTiers(String typeTiers) {
        this.typeTiers = typeTiers;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Tiers getTiers() {
        return tiers;
    }

    public void setTiers(Tiers tiers) {
        this.tiers = tiers;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
