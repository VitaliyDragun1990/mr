package com.revenat.myresume.infrastructure.service;

import java.lang.annotation.Annotation;
import java.util.List;

import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.domain.document.ProfileDocument;

/**
 * Provides search indexing facilities
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SearchIndexingService {

	void createNewProfileIndex(Profile profile);

	void removeProfileIndex(Profile profile);

	<T extends Annotation> void updateProfileIndex(Profile updatedProfile, Class<T> annotationClass);

	<E extends ProfileDocument> void updateProfileDataIndex(String profileId, List<E> updatedData,
			Class<E> profileEntityClass);
}
