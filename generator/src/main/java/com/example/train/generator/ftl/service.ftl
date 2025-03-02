package com.example.train.${module}.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.example.train.common.resp.PageResp;
import com.example.train.common.util.SnowUtil;
import com.example.train.${module}.domain.${Domain};
import com.example.train.${module}.domain.${Domain}Example;
import com.example.train.${module}.mapper.${Domain}Mapper;
import com.example.train.${module}.req.${Domain}QueryReq;
import com.example.train.${module}.req.${Domain}SaveReq;
import com.example.train.${module}.resp.${Domain}QueryResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${Domain}Service {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);
    @Autowired
    private ${Domain}Mapper ${domain}Mapper;

    public void save(${Domain}SaveReq ${domain}Req) {
        DateTime now = DateTime.now();
        //从req拷贝到${Domain}
        ${Domain} ${domain} = BeanUtil.copyProperties(${domain}Req, ${Domain}.class);
        if (ObjectUtil.isNull(${domain}.getId())) {
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        } else {
            ${domain}.setUpdateTime(now);
            LOG.info("日期1：{}", ${domain}.getCreateTime());
            LOG.info("日期2：{}", ${domain}.getUpdateTime());
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }
//        LOG.info("乘客信息添加成功:{}", ${domain});
    }

    public PageResp<${Domain}QueryResp> queryList(${Domain}QueryReq req) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();


        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        //domain持久层生成的代码不能去动，返回的东西放入resp中，请求的东西放入req，model view controller
        //mapper与数据库完成映射，包含具体的方法
        //service处理业务具体逻辑
        PageHelper.startPage(req.getPage(), req.getSize());
        //执行SQL语句的上一行执行就行
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<${Domain}QueryResp> list = BeanUtil.copyToList(${domain}List, ${Domain}QueryResp.class);

        PageResp<${Domain}QueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}