package com.example.damataxi.domain.taxiPot.controller;

import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotInfoResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotListContentResponse;
import com.example.damataxi.domain.taxiPot.service.TaxiPotService;
import com.example.damataxi.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taxi-pot")
public class TaxiPotController {

    private final TaxiPotService taxiPotService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/info")
    public TaxiPotInfoResponse getTaxiPotInfo() {
        return taxiPotService.getTaxiPotInfo();
    }

    @GetMapping
    public List<TaxiPotListContentResponse> getTaxiPotList(@RequestParam("size") int size, @RequestParam("page") int page) {
        return taxiPotService.getTaxiPotList(authenticationFacade.getAuthentication(), size, page);
    }

    @GetMapping("/{id}")
    public TaxiPotContentResponse getTaxiPotContent(@PathVariable("id") int id) {
        return taxiPotService.getTaxiPotContent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void makeTaxiPot(@RequestBody TaxiPotContentRequest request) {
        taxiPotService.makeTaxiPot(authenticationFacade.getUser(), request);
    }

    @PatchMapping("/{id}")
    public void changeTaxiPotContent(@RequestBody TaxiPotContentRequest request, @PathVariable("id") int id) {
        taxiPotService.changeTaxiPotContent(authenticationFacade.getUser(), request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTaxiPot(@PathVariable("id") int id) {
        taxiPotService.deleteTaxiPot(authenticationFacade.getUser(), id);
    }

    @PostMapping("/sub/{id}")
    public void applyTaxiPot(@PathVariable("id") int id) {
        taxiPotService.applyTaxiPot(authenticationFacade.getUser(), id);
    }

    @DeleteMapping("/sub/{id}")
    public void cancleApplyTaxiPot(@PathVariable("id") int id) {
        taxiPotService.cancleApplyTaxiPot(authenticationFacade.getUser(), id);
    }
}
