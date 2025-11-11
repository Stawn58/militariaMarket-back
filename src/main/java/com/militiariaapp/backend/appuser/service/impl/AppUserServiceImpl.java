package com.militiariaapp.backend.appuser.service.impl;

import com.militiariaapp.backend.appuser.mapper.AppUserMapper;
import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;
import com.militiariaapp.backend.appuser.service.AppUserRepository;
import com.militiariaapp.backend.appuser.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository repository;

    @Override
    public UUID saveUser(AppUserSummaryView user) {
        var mapper = Mappers.getMapper(AppUserMapper.class);
        return repository.save(mapper.toEntity(user)).getId();

    }
}
