package com.revenat.myresume.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.revenat.myresume.domain.entity.ProfileRestore;

public interface ProfileRestoreRepository extends CrudRepository<ProfileRestore, Long> {

	Optional<ProfileRestore> findByToken(String token);
}
