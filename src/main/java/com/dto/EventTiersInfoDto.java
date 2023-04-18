package com.dto;


public class EventTiersInfoDto {

    private TiersInfoDto tiers;
    private EventDto event;
    /*private EventTierDto eventTiers;*/
    private String etablissement;

    private String sample;

    private String ville;

    private String fonction;

    private String typeEtablissement;

    private String typeTiers;

    private String observation;

    public TiersInfoDto getTiers() {
        return tiers;
    }

    public void setTiers(TiersInfoDto tiers) {
        this.tiers = tiers;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
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

    public String getTypeTiers() {
        return typeTiers;
    }

    public void setTypeTiers(String typeTiers) {
        this.typeTiers = typeTiers;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
