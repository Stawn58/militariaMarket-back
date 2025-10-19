package com.militiariaapp.backend.seller.web;

import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import com.militiariaapp.backend.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping()
    public String getSellers() {
        return "Hello Sellers";
    }

    @PostMapping()
    public ResponseEntity<Void> saveSeller(@RequestBody SellerSummaryView sellerSummaryView) {
        sellerService.saveSeller(sellerSummaryView);
        
        return ResponseEntity.ok().build();
    }
}
