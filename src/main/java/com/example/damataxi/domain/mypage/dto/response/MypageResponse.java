package com.example.damataxi.domain.mypage.dto.response;

import com.example.damataxi.domain.auth.domain.User;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class MypageResponse {
    private String tel;
    private String email;
    private Double latitude;
    private Double longitude;
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
