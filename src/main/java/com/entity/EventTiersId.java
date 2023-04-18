package com.entity;

import java.io.Serializable;
import java.util.Objects;

public class EventTiersId implements Serializable {

    private Integer event;
    private Integer tiers;

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getTiers() {
        return tiers;
    }

    public void setTiers(Integer tiers) {
        this.tiers = tiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTiersId that = (EventTiersId) o;
        return event.equals(that.event) && tiers.equals(that.tiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, tiers);
    }
}
