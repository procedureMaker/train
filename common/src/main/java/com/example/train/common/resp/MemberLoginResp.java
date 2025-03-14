package com.example.train.common.resp;

import lombok.Data;

@Data
public class MemberLoginResp {

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemberLoginResp{");
        sb.append("id=").append(id);
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private Long id;
    private String mobile;
    private String token;

}