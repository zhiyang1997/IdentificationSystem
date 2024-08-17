package com.engineer.recognitionSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.recognitionSystem.dto.CarReq;
import com.engineer.recognitionSystem.dto.CarResp;
import com.engineer.recognitionSystem.dto.Response;
import com.engineer.recognitionSystem.entity.CarEntity;
import com.engineer.recognitionSystem.exception.CarDataNotFoundException;
import com.engineer.recognitionSystem.exception.CarErrorInputException;
import com.engineer.recognitionSystem.service.CarService;

import jakarta.validation.Valid;

@RestController
public class CarController {
    @Autowired
    CarService carService;

    // 無資料格式封裝
    @PostMapping("/queryAllCar")
    public List<CarEntity> queryAllCar() {
        return carService.queryAllCar();
    }

    // 資料格式封裝
    @PostMapping("/queryCarByManufacturerAndType")
    public Response<CarResp> queryCarByManufacturerAndType(@Valid @RequestBody CarReq carReq, Errors err)
            throws CarDataNotFoundException, CarErrorInputException {
        if (err.hasErrors()) {
            throw new CarErrorInputException();
        }
        return carService.queryCarByManufacturerAndType(carReq);
    }

}
