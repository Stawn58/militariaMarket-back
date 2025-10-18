package com.militiariaapp.backend.user.service;

import com.militiariaapp.backend.user.model.view.UserSummaryView;

import java.util.UUID;

public interface UserService {
    UUID saveUser(UserSummaryView user);
}
