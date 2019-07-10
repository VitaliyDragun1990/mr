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

	@Nonnull MainInfoDTO getMainInfoFor(long profileId);
	
	void updateMainInfo(long profileId, @Nonnull MainInfoDTO updatedMainInfo);

	@Nonnull List<SkillDTO> getSkillsFor(long profileId);

	void updateSkills(long profileId, @Nonnull List<SkillDTO> updatedSkills);
	
	@Nonnull ContactsDTO getContactsFor(long profileId);
	
	void updateContacts(long profileId, @Nonnull ContactsDTO updatedContacts);
	
	@Nonnull List<PracticalExperienceDTO> getExperienceFor(long profileId);
	
	void updateExperience(long profileId, @Nonnull List<PracticalExperienceDTO> updatedExperience);
	
	@Nonnull List<CertificateDTO> getCertificatesFor(long profileId);
	
	void updateCertificates(long profileId, @Nonnull List<CertificateDTO> updatedCertificates, @Nullable SuccessCallback successCallback);
	
	@Nonnull List<CourseDTO> getCoursesFor(long profileId);
	
	void updateCourses(long profileId, @Nonnull List<CourseDTO> updatedCourses);
	
	@Nonnull List<EducationDTO> getEducationFor(long profileId);
	
	void updateEducation(long profileId, @Nonnull List<EducationDTO> updatedEducation);
	
	@Nonnull List<LanguageDTO> getLanguagesFor(long profileId);
	
	void updateLanguages(long profileId, @Nonnull List<LanguageDTO> updatedLanguages);
	
	@Nonnull List<HobbyDTO> getHobbiesFor(long profileId);
	
	void updateHobbies(long profileId, @Nonnull List<HobbyDTO> updatedHobbies);
	
	@Nonnull String getInfoFor(long profileId);
	
	void updateInfoFor(long profileId, @Nonnull String info);
	
	@FunctionalInterface
	interface SuccessCallback {
		void onSuccess();
	}

}
