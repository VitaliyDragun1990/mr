package com.revenat.myresume.application.service.profile;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.dto.SkillDTO;

public interface EditProfileService {

	@Nonnull MainInfoDTO getMainInfoFor(@Nonnull String profileId);
	
	void updateMainInfo(@Nonnull String profileId, @Nonnull MainInfoDTO updatedMainInfo);

	@Nonnull List<SkillDTO> getSkillsFor(@Nonnull String profileId);

	void updateSkills(@Nonnull String profileId, @Nonnull List<SkillDTO> updatedSkills);
	
	@Nonnull ContactsDTO getContactsFor(@Nonnull String profileId);
	
	void updateContacts(@Nonnull String profileId, @Nonnull ContactsDTO updatedContacts);
	
	@Nonnull List<PracticalExperienceDTO> getExperienceFor(@Nonnull String profileId);
	
	void updateExperience(@Nonnull String profileId, @Nonnull List<PracticalExperienceDTO> updatedExperience);
	
	@Nonnull List<CertificateDTO> getCertificatesFor(@Nonnull String profileId);
	
	void updateCertificates(@Nonnull String profileId, @Nonnull List<CertificateDTO> updatedCertificates, @Nullable SuccessCallback successCallback);
	
	@Nonnull List<CourseDTO> getCoursesFor(@Nonnull String profileId);
	
	void updateCourses(@Nonnull String profileId, @Nonnull List<CourseDTO> updatedCourses);
	
	@Nonnull List<EducationDTO> getEducationFor(@Nonnull String profileId);
	
	void updateEducation(@Nonnull String profileId, @Nonnull List<EducationDTO> updatedEducation);
	
	@Nonnull List<LanguageDTO> getLanguagesFor(@Nonnull String profileId);
	
	void updateLanguages(@Nonnull String profileId, @Nonnull List<LanguageDTO> updatedLanguages);
	
	@Nonnull List<HobbyDTO> getHobbiesFor(@Nonnull String profileId);
	
	void updateHobbies(@Nonnull String profileId, @Nonnull List<HobbyDTO> updatedHobbies);
	
	@Nonnull String getInfoFor(@Nonnull String profileId);
	
	void updateInfoFor(@Nonnull String profileId, @Nonnull String info);
	
	@FunctionalInterface
	interface SuccessCallback {
		void onSuccess();
	}

}
