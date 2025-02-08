package com.example.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PassengerSaveReq {

    private Long id;

    //@NotNull(message = "【会员ID】不能为空")
    private Long memberId;


    @NotBlank(message = "【名字】不能为空")
    private String name;

    @NotBlank(message = "【身份证】不能为空")
    private String idCard;

    @NotBlank(message = "【旅客类型】不能为空")
    private String type;

    private Date createTime;

    private Date updateTime;
}