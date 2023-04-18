package com.entity.type;

public enum TypeEtablissement {
    publique("publique"), prive("prive");

    private String value;

    TypeEtablissement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
