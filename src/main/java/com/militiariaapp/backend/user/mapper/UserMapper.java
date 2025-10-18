package com.militiariaapp.backend.user.mapper;

import com.militiariaapp.backend.user.model.User;
import com.militiariaapp.backend.user.model.view.UserSummaryView;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserSummaryView toSummaryView(User user);

    User toEntity(UserSummaryView userSummaryView);
}
