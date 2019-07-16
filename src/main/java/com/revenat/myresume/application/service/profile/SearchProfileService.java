package com.revenat.myresume.application.service.profile;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.revenat.myresume.application.dto.ProfileDTO;

public interface SearchProfileService {

	@Nonnull Page<ProfileDTO> findAll(@Nonnull Pageable pageable);
	
	@Nonnull ProfileDTO getByUid(@Nonnull String uid);
	
	@Nonnull Optional<ProfileDTO> findByEmail(@Nonnull String email);
	
	@Nonnull Page<ProfileDTO> findBySearchQuery(@Nonnull String query, @Nonnull Pageable pageable);
	
}
