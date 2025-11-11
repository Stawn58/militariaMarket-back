package com.militiariaapp.backend.appuser.web;

import com.militiariaapp.backend.appuser.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app-users")
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("")
    public ResponseEntity<Void> createAppUser() {

        return ResponseEntity.ok().build();
    }
}
