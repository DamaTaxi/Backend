package com.example.damataxi.domain.mypage.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatorContentResponse {

    @ApiModelProperty(value = "택시 팟 생성자 학번", example = "2101")
    private final String gcn;
    @ApiModelProperty(value = "택시 팟 생성자 이름", example = "박성현")
    private final String name;
    @ApiModelProperty(value = "택시 팟 생성자 번호", example = "010-2222-2222")
    private final String number;
    @ApiModelProperty(value = "택시 팟 생성자가 자신인지", example = "true")
    private final boolean me;

}
