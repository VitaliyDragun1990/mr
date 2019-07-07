package com.revenat.myresume.application.service.profile;

import javax.annotation.Nonnull;

import com.revenat.myresume.application.dto.ProfileDTO;

public interface CreateProfileService {

	long createProfile(@Nonnull ProfileDTO newProfileData);
}
