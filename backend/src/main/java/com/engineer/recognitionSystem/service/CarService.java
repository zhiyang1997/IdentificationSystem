package com.engineer.recognitionSystem.service;

import java.util.List;

import com.engineer.recognitionSystem.dto.CarReq;
import com.engineer.recognitionSystem.dto.CarResp;
import com.engineer.recognitionSystem.dto.Response;
import com.engineer.recognitionSystem.entity.CarEntity;
import com.engineer.recognitionSystem.exception.CarDataNotFoundException;

public interface CarService {
    List<CarEntity> queryAllCar();

    Response<CarResp> queryCarByManufacturerAndType(CarReq carReq) throws CarDataNotFoundException;
}
