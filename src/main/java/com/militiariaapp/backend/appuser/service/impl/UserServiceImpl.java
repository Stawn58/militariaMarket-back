package com.militiariaapp.backend.appuser.service.impl;

import com.militiariaapp.backend.appuser.mapper.UserMapper;
import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;
import com.militiariaapp.backend.appuser.service.UserRepository;
import com.militiariaapp.backend.appuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UUID saveUser(AppUserSummaryView user) {
        var mapper = Mappers.getMapper(UserMapper.class);
        return repository.save(mapper.toEntity(user)).getId();

    }
}
