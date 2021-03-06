package com.example.damataxi.domain.mypage.dto.response;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.taxiPot.domain.Reservation;
import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.example.damataxi.domain.taxiPot.dto.response.UserContentResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class MyTaxiPotContentResponse {

    @ApiModelProperty(value = "택시 팟 아이디", example = "1")
    private int id;

    @ApiModelProperty(value = "택시 팟 도착지 이름", example = "가나다라마바사노래방")
    private String title;

    @ApiModelProperty(value = "택시 팟 도착지 주소", example = "주소주소주소주소")
    private String address;

    @ApiModelProperty(value = "택시 팟 생성자", example = "김재현")
    private CreatorContentResponse creator;

    @ApiModelProperty(value = "택시 팟 대상자", example = "ALL")
    private String target;

    @ApiModelProperty(value = "택시 팟 예약자 수", example = "3")
    private int reserve;

    @ApiModelProperty(value = "택시 팟 모집할 사람 수", example = "4")
    private int all;

    @ApiModelProperty(value = "택시 팟 도착지 위도", example = "12.3456")
    private double latitude;

    @ApiModelProperty(value = "택시 팟 도착지 경도", example = "34.5678")
    private double longitude;

    @ApiModelProperty(value = "택시 팟 약속 장소", example = "사감실")
    private String place;

    @ApiModelProperty(value = "택시 팟 약속 날짜 및 시간", example = "2020-12-03-03:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-mm:ss")
    private LocalDateTime meetingAt;

    @ApiModelProperty(value = "택시 팟 생성 날짜 및 시간", example = "2020-06-21-09:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-mm:ss")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "택시 팟 상세 정보", example = "몰래 노래방 가실래요..? 그런 사람들만 신청해주세요")
    private String content;

    @ApiModelProperty(value = "택시 팟 신청한 유저 정보", example = "2101 권민정 010-2809-3338")
    private List<UserContentResponse> users;

    public static MyTaxiPotContentResponse from(TaxiPot taxiPot, List<Reservation> reservations, String email) {

        List<UserContentResponse> users = reservations.stream().map(
                (reservation)-> {
                    User user = reservation.getUser();
                    return new UserContentResponse(user.getGcn(), user.getUsername(), user.getTel());
                }
        ).collect(Collectors.toList());

        User creator = taxiPot.getCreator();

        return MyTaxiPotContentResponse.builder()
                .id(taxiPot.getId())
                .title(taxiPot.getTitle())
                .address(taxiPot.getAddress())
                .creator(new CreatorContentResponse(creator.getGcn(), creator.getUsername(), creator.getTel(), creator.getEmail().equals(email)))
                .target(taxiPot.getTarget().name())
                .reserve(taxiPot.getReservations().size())
                .all(taxiPot.getAmount())
                .latitude(taxiPot.getDestinationLatitude())
                .longitude(taxiPot.getDestinationLongitude())
                .place(taxiPot.getPlace())
                .meetingAt(taxiPot.getMeetingAt())
                .createdAt(taxiPot.getCreatedAt())
                .content(taxiPot.getContent())
                .users(users)
                .build();
    }
}

