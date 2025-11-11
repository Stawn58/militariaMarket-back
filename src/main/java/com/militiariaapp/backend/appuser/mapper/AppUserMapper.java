package com.militiariaapp.backend.appuser.mapper;

import com.militiariaapp.backend.appuser.model.AppUser;
import com.militiariaapp.backend.appuser.model.view.AppUserCreationView;
import com.militiariaapp.backend.appuser.model.view.AppUserSummaryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppUserMapper {

    AppUserSummaryView toSummaryView(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    AppUser toEntity(AppUserSummaryView appUserSummaryView);

    @Mapping(target = "id", ignore = true)
    AppUser toEntityFromCreationView(AppUserCreationView appUserCreationView);

}
