package com.example.damataxi.domain.mypage.dto.response;

import com.example.damataxi.domain.auth.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class MypageResponse {

    @ApiModelProperty(value = "유저 학번", example = "1234")
    private String gcn;

    @ApiModelProperty(value = "유저 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(value = "유저 전화번호", example = "01012030149")
    private String tel;

    @ApiModelProperty(value = "이메일", example = "xxxxxx@dsm.hs.kr")
    private String email;

    @ApiModelProperty(value = "자주가는 곳 주소", example = "대전광역시 유성구 장동 가정북로 76")
    private String address;

    @ApiModelProperty(value = "현재 예약한 팟 아이디", example = "1")
    private Integer potId;

    public static MypageResponse from(User user){
        Integer potId = null;
        if(user.getReservation() != null){
            potId = user.getReservation().getId().getPotId();
        }
        return MypageResponse.builder()
                .gcn(user.getGcn())
                .name(user.getUsername())
                .tel(user.getTel())
                .email(user.getEmail())
                .address(user.getAddress())
                .potId(potId)
                .build();
    }
}
