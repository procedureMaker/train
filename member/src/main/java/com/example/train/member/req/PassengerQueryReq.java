package com.example.train.member.req;

import com.example.train.common.req.PageReq;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PassengerQueryReq extends PageReq {

    //@NotNull(message = "【会员ID】不能为空")
    private Long memberId;

}