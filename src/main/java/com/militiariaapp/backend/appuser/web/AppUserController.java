package com.militiariaapp.backend.appuser.web;

import com.militiariaapp.backend.appuser.model.view.AppUserCreationView;
import com.militiariaapp.backend.appuser.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app-users")
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("")
    public ResponseEntity<UUID> createAppUser(@RequestBody AppUserCreationView appUserCreationView) {
        var id = appUserService.saveUserFromCreationView(appUserCreationView);
        return ResponseEntity.ok().body(id);
    }
}
