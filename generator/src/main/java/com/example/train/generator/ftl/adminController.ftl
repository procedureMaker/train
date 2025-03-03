package com.example.train.${module}.controller.admin;

import com.example.train.common.context.LoginMemberContext;
import com.example.train.common.resp.CommonResp;
import com.example.train.common.resp.PageResp;
import com.example.train.${module}.req.${Domain}QueryReq;
import com.example.train.${module}.req.${Domain}SaveReq;
import com.example.train.${module}.resp.${Domain}QueryResp;
import com.example.train.${module}.service.${Domain}Service;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/${do_main}")
public class ${Domain}AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}AdminController.class);
    @Autowired
    private ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp<Object> register(@Valid @RequestBody ${Domain}SaveReq ${domain}Req) {
        ${domain}Service.save(${domain}Req);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req) {
        PageResp<${Domain}QueryResp> list = ${domain}Service.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }
}