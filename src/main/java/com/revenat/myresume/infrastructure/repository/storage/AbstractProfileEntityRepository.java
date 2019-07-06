package com.revenat.myresume.infrastructure.repository.storage;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.revenat.myresume.domain.entity.ProfileEntity;

@NoRepositoryBean
public interface AbstractProfileEntityRepository<T extends ProfileEntity> extends Repository<T, Long> {

	void deleteByProfileId(Long profileId);
	
	List<T> findByProfileIdOrderByIdAsc(Long profileId);
	
	T saveAndFlush(T entity);
	
	void flush();
}
