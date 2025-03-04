package com.example.train.business.controller.admin;

import com.example.train.common.context.LoginMemberContext;
import com.example.train.common.resp.CommonResp;
import com.example.train.common.resp.PageResp;
import com.example.train.business.req.StationQueryReq;
import com.example.train.business.req.StationSaveReq;
import com.example.train.business.resp.StationQueryResp;
import com.example.train.business.service.StationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/station")
public class StationAdminController {

    private static final Logger LOG = LoggerFactory.getLogger(StationAdminController.class);
    @Autowired
    private StationService stationService;

    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody StationSaveReq stationReq) {
        stationService.save(stationReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<StationQueryResp>> queryList(@Valid StationQueryReq req) {
        PageResp<StationQueryResp> list = stationService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        stationService.delete(id);
        return new CommonResp<>();
    }
    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryAll() {
        List<StationQueryResp> stationResps = stationService.queryAll();
        return new CommonResp<>(stationResps);
    }

}