package com.engineer.recognitionSystem.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CarPKEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "MANUFACTURER")
    private String manufacturer;
    
    @Column(name = "TYPE")
    private String type;

    public CarPKEntity() {
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CarPKEntity)) {
            return false;
        }
        CarPKEntity castOther = (CarPKEntity) other;
        return this.manufacturer.equals(castOther.manufacturer)
                && this.type.equals(castOther.type);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.manufacturer.hashCode();
        hash = hash * prime + this.type.hashCode();
        return hash;
    }
}
