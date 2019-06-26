package com.revenat.myresume.application.service.profile;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.revenat.myresume.application.dto.ProfileDTO;

public interface SearchProfileService {

	ProfileDTO getByUid(String uid);
	
	Optional<ProfileDTO> findByEmail(String email);
	
	Page<ProfileDTO> findAll(Pageable pageable);
	
	Page<ProfileDTO> findBySearchQuery(String query, Pageable pageable);
}
