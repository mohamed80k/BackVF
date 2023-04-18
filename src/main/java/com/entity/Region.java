package com.entity;

import javax.persistence.*;
import java.util.Set;

    @Entity

    public class Region {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id ;

        private String regionName;
        private String abbreviation;
        @OneToMany(mappedBy = "region")
        private Set<Commercial> commercials;
        @OneToMany(mappedBy = "regions" ,fetch = FetchType.EAGER)
        private Set<Site> sites;

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

        public Set<Commercial> getCommercials() {
            return commercials;
        }

        public void setCommercials(Set<Commercial> commercials) {
            this.commercials = commercials;
        }

        public Set<Site> getSites() {
            return sites;
        }

        public void setSites(Set<Site> sites) {
            this.sites = sites;
        }
    }

