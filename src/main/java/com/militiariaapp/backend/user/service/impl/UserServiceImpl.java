package com.militiariaapp.backend.user.service.impl;

import com.militiariaapp.backend.user.mapper.UserMapper;
import com.militiariaapp.backend.user.model.view.UserSummaryView;
import com.militiariaapp.backend.user.service.UserRepository;
import com.militiariaapp.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UUID saveUser(UserSummaryView user) {
        var mapper = Mappers.getMapper(UserMapper.class);
        return repository.save(mapper.toEntity(user)).getId();

    }
}
