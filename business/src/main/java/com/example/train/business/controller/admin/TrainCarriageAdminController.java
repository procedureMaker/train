package com.example.train.business.controller.admin;

import com.example.train.common.context.LoginMemberContext;
import com.example.train.common.resp.CommonResp;
import com.example.train.common.resp.PageResp;
import com.example.train.business.req.TrainCarriageQueryReq;
import com.example.train.business.req.TrainCarriageSaveReq;
import com.example.train.business.resp.TrainCarriageQueryResp;
import com.example.train.business.service.TrainCarriageService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-carriage")
public class TrainCarriageAdminController {

    private static final Logger LOG = LoggerFactory.getLogger(TrainCarriageAdminController.class);
    @Autowired
    private TrainCarriageService trainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody TrainCarriageSaveReq trainCarriageReq) {
        trainCarriageService.save(trainCarriageReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainCarriageQueryResp>> queryList(@Valid TrainCarriageQueryReq req) {
        PageResp<TrainCarriageQueryResp> list = trainCarriageService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        trainCarriageService.delete(id);
        return new CommonResp<>();
    }
}