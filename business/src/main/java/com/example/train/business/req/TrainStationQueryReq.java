package com.example.train.business.req;

import com.example.train.common.req.PageReq;

public class TrainStationQueryReq extends PageReq {

    private String trainCode;

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }


//    没有学不会的，没有难不难，只有见没见过，熟悉不熟悉

    @Override
    public String toString() {
        return "TrainStationQueryReq{" +
                "trainCode='" + trainCode + '\'' +
                "} " + super.toString();
    }
}