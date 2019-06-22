package com.revenat.myresume.application.service.profile;

import java.util.List;

import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.dto.ExperienceDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.dto.SkillDTO;

public interface EditProfileService {

	ProfileDTO create(String firstName, String lastName, String password);

	MainInfoDTO getMainInfoFor(long profileId);
	
	void updateMainInfo(long profileId, MainInfoDTO updatedMainInfo);

	List<SkillDTO> getSkillsFor(long profileId);

	void updateSkills(long profileId, List<SkillDTO> updatedSkills);
	
	ContactsDTO getContactsFor(long profileId);
	
	void updateContacts(long profileId, ContactsDTO updatedContacts);
	
	List<ExperienceDTO> getExperienceFor(long profileId);
	
	void updateExperience(long profileId, List<ExperienceDTO> updatedExperience);
	
	List<CertificateDTO> getCertificatesFor(long profileId);
	
	void updateCertificates(long profileId, List<CertificateDTO> updatedCertificates);
	
	List<CourseDTO> getCoursesFor(long profileId);
	
	void updateCourses(long profileId, List<CourseDTO> updatedCourses);
	
	List<EducationDTO> getEducationFor(long profileId);
	
	void updateEducation(long profileId, List<EducationDTO> updatedEducation);
	
	List<LanguageDTO> getLanguagesFor(long profileId);
	
	void updateLanguages(long profileId, List<LanguageDTO> updatedLanguages);
	
	List<HobbyDTO> getHobbiesFor(long profileId);
	
	void updateHobbies(long profileId, List<HobbyDTO> updatedHobbies);
	
	String getInfoFor(long profileId);
	
	void updateInfoFor(long profileId, String info);

	List<String> getAllSkillCategories();
	
	List<String> getLanguageTypes();
	
	List<String> getLanguageLevels();
}
