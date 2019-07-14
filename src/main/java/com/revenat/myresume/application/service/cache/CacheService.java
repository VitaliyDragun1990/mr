package com.revenat.myresume.application.service.cache;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.revenat.myresume.domain.document.Profile;

/**
 * Cache service for {@link Profile} entities.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface CacheService {

	Optional<Profile> findProfileByUid(@Nonnull String uid);

	void deleteProfileByUid(@Nonnull String uid);
}
