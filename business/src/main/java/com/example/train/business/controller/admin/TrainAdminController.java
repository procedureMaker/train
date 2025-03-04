package com.example.train.business.controller.admin;

import com.example.train.common.context.LoginMemberContext;
import com.example.train.common.resp.CommonResp;
import com.example.train.common.resp.PageResp;
import com.example.train.business.req.TrainQueryReq;
import com.example.train.business.req.TrainSaveReq;
import com.example.train.business.resp.TrainQueryResp;
import com.example.train.business.service.TrainService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {

    private static final Logger LOG = LoggerFactory.getLogger(TrainAdminController.class);
    @Autowired
    private TrainService trainService;

    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody TrainSaveReq trainReq) {
        trainService.save(trainReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        PageResp<TrainQueryResp> list = trainService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        trainService.delete(id);
        return new CommonResp<>();
    }
}