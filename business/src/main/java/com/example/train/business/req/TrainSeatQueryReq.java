package com.example.train.business.req;

import com.example.train.common.req.PageReq;

public class TrainSeatQueryReq extends PageReq {

    private String TrainCode;

    public String getTrainCode() {
        return TrainCode;
    }

    public void setTrainCode(String trainCode) {
        TrainCode = trainCode;
    }

    @Override
    public String toString() {
        return "TrainSeatQueryReq{" +
                "} " + super.toString();
    }
}