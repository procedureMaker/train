package com.example.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.example.train.common.context.LoginMemberContext;
import com.example.train.common.util.SnowUtil;
import com.example.train.member.domain.Passenger;
import com.example.train.member.mapper.PassengerMapper;
import com.example.train.member.req.PassengerSaveReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);
    @Autowired
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq passengerReq) {
        DateTime now = DateTime.now();
        //从req拷贝到Passenger
        Passenger passenger = BeanUtil.copyProperties(passengerReq,Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
        LOG.info("乘客信息添加成功:{}",passenger);
    }
}