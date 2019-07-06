package com.revenat.myresume.infrastructure.repository.storage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revenat.myresume.domain.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findOneByUid(String uid);
	
	Optional<Profile> findOneById(Long id);
	
	Optional<Profile> findByPhone(String phone);
	
	Optional<Profile> findByEmail(String email);
	
	Optional<Profile> findByUidOrEmailOrPhone(String uid, String email, String phone);
	
	int countByUid(String uid);
	
	Page<Profile> findAllByCompletedTrue(Pageable pageable);
	
	List<Profile> findByCompletedFalseAndCreatedBefore(LocalDateTime oldDate);
	
	@Modifying
	@Query("delete from Profile p where p.completed=false and p.created < ?1")
	int deleteNotCompleted(LocalDateTime oldDate);
	
	@Query("select p.id from Profile p where p.completed=false and p.created < ?1")
	List<Long> fetchNotCompletedProfileIds(LocalDateTime oldDate);
}
