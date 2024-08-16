package com.engineer.recognitionSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineer.recognitionSystem.entity.CarEntity;
import com.engineer.recognitionSystem.entity.CarPKEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, CarPKEntity> {
    List<CarEntity> findByManufacturerAndType(String manu, String type);
}
