package com.militiariaapp.backend.appuser.mapper;

import com.militiariaapp.backend.appuser.model.AppUser;
import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    AppUserSummaryView toSummaryView(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    AppUser toEntity(AppUserSummaryView appUserSummaryView);
}
