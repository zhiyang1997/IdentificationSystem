package com.engineer.recognitionSystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(value = CarPKEntity.class)
@Table(name = "CARS")
public class CarEntity implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@Column(name = "MANUFACTURER")
private String manufacturer;

@Id
@Column(name = "TYPE")
private String type;

@Column(name = "MIN_PRICE")
private BigDecimal minPrice;

@Column(name = "PRICE")
private BigDecimal price;
}
