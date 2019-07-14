package com.revenat.myresume.infrastructure.repository.storage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.revenat.myresume.domain.document.Profile;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, String> {

	Optional<Profile> findOneByUid(String uid);
	
	Optional<Profile> findOneById(String id);
	
	Optional<Profile> findByPhone(String phone);
	
	Optional<Profile> findByEmail(String email);
	
	Optional<Profile> findByUidOrEmailOrPhone(String uid, String email, String phone);
	
	int countByUid(String uid);
	
	Page<Profile> findAllByCompletedTrue(Pageable pageable);
	
	List<Profile> findByCompletedFalseAndCreatedBefore(LocalDateTime oldDate);
	
}
