package com.example.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.example.train.common.exception.BusinessException;
import com.example.train.common.exception.BusinessExceptionEnum;
import com.example.train.common.resp.PageResp;
import com.example.train.common.util.SnowUtil;
import com.example.train.business.domain.Station;
import com.example.train.business.domain.StationExample;
import com.example.train.business.mapper.StationMapper;
import com.example.train.business.req.StationQueryReq;
import com.example.train.business.req.StationSaveReq;
import com.example.train.business.resp.StationQueryResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);
    @Autowired
    private StationMapper stationMapper;

    public void save(StationSaveReq stationReq) {
        DateTime now = DateTime.now();
        //从req拷贝到Station
        Station station = BeanUtil.copyProperties(stationReq, Station.class);
        if (ObjectUtil.isNull(station.getId())) {

            StationExample stationExample = new StationExample();
            stationExample.createCriteria().andNameEqualTo(stationReq.getName());
            List<Station> list = stationMapper.selectByExample(stationExample);
            if (CollUtil.isNotEmpty(list)){
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);
            }


                station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        } else {
            station.setUpdateTime(now);
            LOG.info("日期1：{}", station.getCreateTime());
            LOG.info("日期2：{}", station.getUpdateTime());
            stationMapper.updateByPrimaryKey(station);
        }
//        LOG.info("乘客信息添加成功:{}", station);
    }

    public PageResp<StationQueryResp> queryList(StationQueryReq req) {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("id desc");
        StationExample.Criteria criteria = stationExample.createCriteria();


        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        //domain持久层生成的代码不能去动，返回的东西放入resp中，请求的东西放入req，model view controller
        //mapper与数据库完成映射，包含具体的方法
        //service处理业务具体逻辑
        PageHelper.startPage(req.getPage(), req.getSize());
        //执行SQL语句的上一行执行就行
        List<Station> stationList = stationMapper.selectByExample(stationExample);

        PageInfo<Station> pageInfo = new PageInfo<>(stationList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<StationQueryResp> list = BeanUtil.copyToList(stationList, StationQueryResp.class);

        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        stationMapper.deleteByPrimaryKey(id);
    }

    //获取全部车站
    public List<StationQueryResp> queryAll() {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("name_pinyin asc");
        List<Station> StationList = stationMapper.selectByExample(stationExample);
        return BeanUtil.copyToList(StationList, StationQueryResp.class);
    }
}