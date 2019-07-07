package com.revenat.myresume.infrastructure.service;

import java.lang.annotation.Annotation;
import java.util.List;

import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileEntity;

/**
 * Manages search indexing functionality
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SearchIndexingService {

	void createNewProfileIndex(Profile profile);

	void removeProfileIndex(Profile profile);

	<T extends Annotation> void updateProfileIndex(Profile profile, Class<T> annotationClass);

	<E extends ProfileEntity> void updateProfileEntitiesIndex(long profileId, List<E> updatedData,
			Class<E> profileEntityClass);
}
