package com.revenat.myresume.infrastructure.service;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.Nonnull;

import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.domain.document.ProfileDocument;

/**
 * Provides search indexing facilities
 * 
 * @author Vitaliy Dragun
 *
 */
public interface SearchIndexingService {

	/**
	 * Creates new search index for profied {@code profile}
	 * 
	 * @param profile {@link Profile} to create index for
	 */
	void createNewProfileIndex(@Nonnull Profile profile);

	/**
	 * Removes search index for profided {@code profile} if any
	 * 
	 * @param profile {@link Profile} to remove index for
	 */
	void removeProfileIndex(@Nonnull Profile profile);

	/**
	 * Updates index for provided {@code updatedProfile}, in particular for it's
	 * properties annotated with {@code dataTypeAnnotationClass} annotation
	 * 
	 * @param <T>                     type of the annotation which designates
	 *                                profile data that should be updated
	 * @param updatedProfile          holder with updated data
	 * @param dataTypeAnnotationClass class of the annotation which designates
	 *                                profile data that should be updated
	 */
	<T extends Annotation> void updateProfileIndex(@Nonnull Profile updatedProfile,
			@Nonnull Class<T> dataTypeAnnotationClass);

	/**
	 * Updates data for some kind of profile aggregates
	 * 
	 * @param <E>                       type of profile aggregates to update
	 * @param profileId                 id of the profile to update data for
	 * @param updatedData               updated profile data
	 * @param profileAggregateDataClass precise class of the profile aggregate data
	 *                                  to update
	 */
	<E extends ProfileDocument> void updateProfileAggregateDataIndex(@Nonnull String profileId,
			@Nonnull List<E> updatedData, @Nonnull Class<E> profileAggregateDataClass);
}
