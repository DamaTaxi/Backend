package com.example.damataxi.domain.auth.util.api.client;

import com.example.damataxi.domain.auth.util.api.dto.CodeReqeust;
import com.example.damataxi.domain.auth.util.api.dto.CodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "codeClient", url = "https://developer-api.dsmkr.com")
public interface CodeClient {

    @PostMapping("/dsmauth/token")
    CodeResponse getUserToken(@RequestBody CodeReqeust reqeust);

}
