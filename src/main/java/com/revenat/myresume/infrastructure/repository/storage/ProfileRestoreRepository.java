package com.revenat.myresume.infrastructure.repository.storage;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.revenat.myresume.domain.document.ProfileRestore;

public interface ProfileRestoreRepository extends CrudRepository<ProfileRestore, String> {

	Optional<ProfileRestore> findByToken(String token);
	
	Optional<ProfileRestore> findByProfileId(String id);
}
