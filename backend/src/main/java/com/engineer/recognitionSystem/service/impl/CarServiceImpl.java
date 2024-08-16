package com.engineer.recognitionSystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineer.recognitionSystem.dto.CarReq;
import com.engineer.recognitionSystem.dto.CarResp;
import com.engineer.recognitionSystem.dto.Response;
import com.engineer.recognitionSystem.entity.CarEntity;
import com.engineer.recognitionSystem.exception.CarDataNotFoundException;
import com.engineer.recognitionSystem.repository.CarRepository;
import com.engineer.recognitionSystem.service.CarService;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public List<CarEntity> queryAllCar() {
        return carRepository.findAll();
    }

    @Override
    public Response<CarResp> queryCarByManufacturerAndType(CarReq carReq) throws CarDataNotFoundException {
        String manufacturer = carReq.getManufacturer();
        String type = carReq.getType();
        List<CarEntity> carEntityList = carRepository.findByManufacturerAndType(manufacturer, type);
        if (carEntityList == null || carEntityList.isEmpty()) {
            throw new CarDataNotFoundException();
        }

        CarEntity carEntity = carEntityList.get(0); // 因為只會有一筆
        CarResp carResp = new CarResp();
        carResp.setManufacturer(manufacturer);
        carResp.setType(type);
        carResp.setMinPrice(carEntity.getMinPrice());
        carResp.setPrice(carEntity.getPrice());
        
        List<CarResp> carResplist = new ArrayList<>();
        carResplist.add(carResp);

        Response<CarResp> response = new Response<>();
        response.setDatas(carResplist);
        response.setMessage("success");

        return response;
    }
}
