package com.militiariaapp.backend.seller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    @GetMapping()
    public String getSellers() {
        return "Hello Sellers";
    }
}
