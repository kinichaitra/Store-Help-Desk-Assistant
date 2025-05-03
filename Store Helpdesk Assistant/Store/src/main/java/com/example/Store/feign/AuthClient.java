package com.example.Store.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="authorization-service", url="http://localhost:8091/authorization")

public interface AuthClient {
    @GetMapping("/validate")
    public Boolean validate(@RequestHeader(name = "Authorization") String token1);

    @GetMapping("/isHelpdesk")
    public Boolean isHelpdesk(@RequestHeader(name = "Authorization") String token1);

    @GetMapping("/isAdmin")
    public Boolean isAdmin(@RequestHeader(name = "Authorization") String token1);

}
