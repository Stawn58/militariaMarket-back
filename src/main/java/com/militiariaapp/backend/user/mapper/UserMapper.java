package com.militiariaapp.backend.user.mapper;

import com.militiariaapp.backend.user.model.User;
import com.militiariaapp.backend.user.model.view.UserSummaryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    UserSummaryView toSummaryView(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserSummaryView userSummaryView);
}
