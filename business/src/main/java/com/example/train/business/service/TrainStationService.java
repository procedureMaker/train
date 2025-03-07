package com.example.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.example.train.business.domain.*;
import com.example.train.common.exception.BusinessException;
import com.example.train.common.exception.BusinessExceptionEnum;
import com.example.train.common.resp.PageResp;
import com.example.train.common.util.SnowUtil;
import com.example.train.business.mapper.TrainStationMapper;
import com.example.train.business.req.TrainStationQueryReq;
import com.example.train.business.req.TrainStationSaveReq;
import com.example.train.business.resp.TrainStationQueryResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainStationService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainStationService.class);
    @Autowired
    private TrainStationMapper trainStationMapper;

    public void save(TrainStationSaveReq trainStationReq) {
        DateTime now = DateTime.now();
        //从req拷贝到TrainStation
        TrainStation trainStation = BeanUtil.copyProperties(trainStationReq, TrainStation.class);
        if (ObjectUtil.isNull(trainStation.getId())) {

            TrainStation trainStationDB = selectByUnique(trainStationReq.getTrainCode(),trainStation.getIndex());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR);
            }
            trainStationDB = selectByUnique(trainStationReq.getTrainCode(),trainStation.getName());
            if (ObjectUtil.isNotEmpty(trainStationDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR);
            }

            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        } else {
            trainStation.setUpdateTime(now);
            LOG.info("日期1：{}", trainStation.getCreateTime());
            LOG.info("日期2：{}", trainStation.getUpdateTime());
            trainStationMapper.updateByPrimaryKey(trainStation);
        }
//        LOG.info("乘客信息添加成功:{}", trainStation);
    }

    public PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq req) {
        TrainStationExample trainStationExample = new TrainStationExample();
//        trainStationExample.setOrderByClause("id desc");
        /**
         *  index是mysql关键字加反引号  ``
         */
        trainStationExample.setOrderByClause("train_code asc, `index` asc");
        TrainStationExample.Criteria criteria = trainStationExample.createCriteria();

        /**
         *  code不为空，走条件查询   null 和 ""空字符的区别  NotNull对空字符串无效
         */
        if (ObjectUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }


        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        //domain持久层生成的代码不能去动，返回的东西放入resp中，请求的东西放入req，model view controller
        //mapper与数据库完成映射，包含具体的方法
        //service处理业务具体逻辑
        PageHelper.startPage(req.getPage(), req.getSize());
        //执行SQL语句的上一行执行就行
        List<TrainStation> trainStationList = trainStationMapper.selectByExample(trainStationExample);

        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainStationQueryResp> list = BeanUtil.copyToList(trainStationList, TrainStationQueryResp.class);

        PageResp<TrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainStationMapper.deleteByPrimaryKey(id);
    }

    private TrainStation selectByUnique(String trainCode, Integer index) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria().andTrainCodeEqualTo(trainCode).andIndexEqualTo(index);
        List<TrainStation> list = trainStationMapper.selectByExample(trainStationExample);

        if(CollUtil.isNotEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }
    }

    private TrainStation selectByUnique(String trainCode, String name) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria().andTrainCodeEqualTo(trainCode).andNameEqualTo(name);
        List<TrainStation> list = trainStationMapper.selectByExample(trainStationExample);

        if(CollUtil.isNotEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }
    }
}