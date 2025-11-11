package com.militiariaapp.backend.appuser.service;

import com.militiariaapp.backend.appuser.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, UUID> {
}
