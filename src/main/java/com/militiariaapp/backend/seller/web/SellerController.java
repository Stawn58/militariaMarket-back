package com.militiariaapp.backend.seller.web;

import com.militiariaapp.backend.seller.model.view.SellerCreationView;
import com.militiariaapp.backend.seller.model.view.SellerSummaryView;
import com.militiariaapp.backend.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("{id}")
    public ResponseEntity<SellerSummaryView> getSellers(@PathVariable UUID id) {
        var sellerSummary = sellerService.getSellerById(id);

        return ResponseEntity.ok(sellerSummary);
    }

    @PostMapping()
    public ResponseEntity<Void> saveSeller(@RequestBody SellerCreationView sellerCreationView) {
        sellerService.saveSeller(sellerCreationView);

        // maybe return a "created" response with the location of the new resource (seller)
        return ResponseEntity.ok().build();
    }
}
