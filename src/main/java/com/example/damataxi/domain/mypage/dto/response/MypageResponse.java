package com.example.damataxi.domain.mypage.dto.response;

import com.example.damataxi.domain.auth.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class MypageResponse {

    @ApiModelProperty(value = "유저 전화번호", example = "01012030149")
    private String tel;

    @ApiModelProperty(value = "이메일", example = "xxxxxx@dsm.hs.kr")
    private String email;

    @ApiModelProperty(value = "자주가는 곳 위도", example = "12.3456")
    private Double latitude;

    @ApiModelProperty(value = "자주가는 곳 경도", example = "34.5678")
    private Double longitude;

    @ApiModelProperty(value = "현재 예약한 팟 아이디", example = "1")
    private Integer potId;

    public static MypageResponse from(User user){
        Integer potId = null;
        if(user.getReservation() != null){
            potId = user.getReservation().getId().getPotId();
        }
        return MypageResponse.builder()
                .tel(user.getTel())
                .email(user.getEmail())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .potId(potId)
                .build();
    }
}
