package com.militiariaapp.backend.appuser.service;

import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;

import java.util.UUID;

public interface UserService {
    UUID saveUser(AppUserSummaryView user);
}
