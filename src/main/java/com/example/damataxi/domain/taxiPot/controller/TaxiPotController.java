package com.example.damataxi.domain.taxiPot.controller;

import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotInfoResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotPage;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotSlidePage;
import com.example.damataxi.domain.taxiPot.service.TaxiPotService;
import com.example.damataxi.global.security.AuthenticationFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taxi-pot")
public class TaxiPotController {

    private final TaxiPotService taxiPotService;
    private final AuthenticationFacade authenticationFacade;

    @ApiOperation(value = "택시 팟 현황 받아오기", notes = "현재 택시 팟의 개수와 예약 완료된 택시 팟의 개수를 가져옵니다 (ALL)")
    @GetMapping("/info")
    public TaxiPotInfoResponse getTaxiPotInfo() {
        return taxiPotService.getTaxiPotInfo();
    }

    @ApiOperation(value = "택시 팟 슬라이드 리스트 받아오기", notes = "모든 택시 팟 리스트를 받아옵니다 (ALL, 유저)")
    @GetMapping("/slide")
    public TaxiPotSlidePage getTaxiPotSlide(@RequestParam("size") int size, @RequestParam("page") int page) {
        try {
            return taxiPotService.getTaxiPotSlideList(authenticationFacade.getUser(), size, page);
        } catch (Exception e) {
            return taxiPotService.getTaxiPotSlideList(size, page);
        }
    }

    @ApiOperation(value = "택시 팟 리스트 받아오기", notes = "모든 택시 팟 리스트를 받아옵니다 (ALL, 유저)")
    @GetMapping
    public TaxiPotPage getTaxiPotList(@RequestParam("size") int size, @RequestParam("page") int page) {
        try {
            return taxiPotService.getTaxiPotList(authenticationFacade.getUser(), size, page);
        } catch (Exception e) {
            return taxiPotService.getTaxiPotList(size, page);
        }
    }

    @ApiOperation(value = "택시 팟 내용 받아오기", notes = "택시 팟 아이디를 받고 택시 팟 내용을 반환합니다 (ALL)")
    @GetMapping("/{id}")
    public TaxiPotContentResponse getTaxiPotContent(@PathVariable("id") int id) {
        return taxiPotService.getTaxiPotContent(id);
    }

    @ApiOperation(value = "택시 팟 만들기", notes = "택시 팟을 만듭니다 (유저)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTaxiPot(@RequestBody TaxiPotContentRequest request) {
        taxiPotService.makeTaxiPot(authenticationFacade.getUser(), request);
    }

    @ApiOperation(value = "택시 팟 내용 변경하기", notes = "택시 팟 내용을 변경합니다 (유저)")
    @PatchMapping("/{id}")
    public void changeTaxiPotContent(@RequestBody TaxiPotContentRequest request, @PathVariable("id") int id) {
        taxiPotService.changeTaxiPotContent(authenticationFacade.getUser(), request, id);
    }

    @ApiOperation(value = "택시 팟 삭제하기", notes = "택시 팟 아이디를 받고 택시 팟을 삭제합니다 (유저)")
    @DeleteMapping("/{id}")
    public void deleteTaxiPot(@PathVariable("id") int id) {
        taxiPotService.deleteTaxiPot(authenticationFacade.getUser(), id);
    }

    @ApiOperation(value = "택시 팟 신청하기", notes = "택시 팟을 신청합니다 (유저)")
    @PostMapping("/sub/{id}")
    public void applyTaxiPot(@PathVariable("id") int id) {
        taxiPotService.applyTaxiPot(authenticationFacade.getUser(), id);
    }

    @ApiOperation(value = "택시 팟 신청 취소하기", notes = "택시 팟 신청을 취소합니다 (유저)")
    @DeleteMapping("/sub/{id}")
    public void cancelApplyTaxiPot(@PathVariable("id") int id) {
        taxiPotService.cancelApplyTaxiPot(authenticationFacade.getUser(), id);
    }
}
