package com.example.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.example.train.business.enums.SeatColEnum;
import com.example.train.common.resp.PageResp;
import com.example.train.common.util.SnowUtil;
import com.example.train.business.domain.TrainCarriage;
import com.example.train.business.domain.TrainCarriageExample;
import com.example.train.business.mapper.TrainCarriageMapper;
import com.example.train.business.req.TrainCarriageQueryReq;
import com.example.train.business.req.TrainCarriageSaveReq;
import com.example.train.business.resp.TrainCarriageQueryResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainCarriageService.class);
    @Autowired
    private TrainCarriageMapper trainCarriageMapper;

    public void save(TrainCarriageSaveReq trainCarriageReq) {
        DateTime now = DateTime.now();
        //自动计算出车座总数和列数
        List<SeatColEnum> seatColEnums = SeatColEnum.getColsByType(trainCarriageReq.getSeatType());
        trainCarriageReq.setColCount(seatColEnums.size());
        trainCarriageReq.setSeatCount(seatColEnums.size() * trainCarriageReq.getRowCount());


        //从req拷贝到TrainCarriage
        TrainCarriage trainCarriage = BeanUtil.copyProperties(trainCarriageReq, TrainCarriage.class);
        LOG.info("计算得出车座总数：{},列数:{}", trainCarriageReq.getSeatCount(), trainCarriageReq.getColCount());
        if (ObjectUtil.isNull(trainCarriage.getId())) {
            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        } else {
            trainCarriage.setUpdateTime(now);
            LOG.info("日期1：{}", trainCarriage.getCreateTime());
            LOG.info("日期2：{}", trainCarriage.getUpdateTime());
            trainCarriageMapper.updateByPrimaryKey(trainCarriage);
        }
//        LOG.info("乘客信息添加成功:{}", trainCarriage);
    }

    public PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq req) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("train_code asc, `index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();

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
        List<TrainCarriage> trainCarriageList = trainCarriageMapper.selectByExample(trainCarriageExample);

        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryResp> list = BeanUtil.copyToList(trainCarriageList, TrainCarriageQueryResp.class);

        PageResp<TrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainCarriageMapper.deleteByPrimaryKey(id);
    }

    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("`index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);
        return trainCarriageMapper.selectByExample(trainCarriageExample);
    }
}