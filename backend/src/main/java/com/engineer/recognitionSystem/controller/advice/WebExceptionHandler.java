package com.engineer.recognitionSystem.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.engineer.recognitionSystem.dto.CarResp;
import com.engineer.recognitionSystem.dto.Response;
import com.engineer.recognitionSystem.exception.CarDataNotFoundException;
import com.engineer.recognitionSystem.exception.CarErrorInputException;

@ControllerAdvice
public class WebExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CarErrorInputException.class)
    public Response<CarResp> handleCarErrorInputException() {
        Response<CarResp> response = new Response<>();
        response.setDatas(null);
        response.setMessage("ErrorInputException");
        return response;
    }

    @ResponseBody
    @ExceptionHandler(CarDataNotFoundException.class)
    public Response<CarResp> handleDataNotFoundException() {
        Response<CarResp> response = new Response<>();
        response.setDatas(null);
        response.setMessage("DataNotFoundException");
        return response;
    }

}
