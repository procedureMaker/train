package com.example.train.member.controller;

import com.example.train.common.resp.CommonResp;
import com.example.train.member.req.PassengerSaveReq;
import com.example.train.member.service.PassengerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerController.class);
    @Autowired
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody PassengerSaveReq passengerReq) {
        passengerService.save(passengerReq);
        return new CommonResp<>();
    }

}