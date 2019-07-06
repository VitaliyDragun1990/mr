package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CertificateDTO;
import com.revenat.myresume.application.dto.ContactsDTO;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.dto.EducationDTO;
import com.revenat.myresume.application.dto.PracticalExperienceDTO;
import com.revenat.myresume.application.dto.HobbyDTO;
import com.revenat.myresume.application.dto.LanguageDTO;
import com.revenat.myresume.application.dto.MainInfoDTO;
import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.dto.SkillDTO;
import com.revenat.myresume.domain.entity.Certificate;
import com.revenat.myresume.domain.entity.Contacts;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Education;
import com.revenat.myresume.domain.entity.PracticalExperience;
import com.revenat.myresume.domain.entity.Hobby;
import com.revenat.myresume.domain.entity.Language;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.Skill;

@TypeConverter
class ProfileToProfileDTOTransformer implements Converter<Profile, ProfileDTO> {

	@Override
	public ProfileDTO convert(Profile profile) {
		ProfileDTO dto = new ProfileDTO();
		
		dto.setId(profile.getId());
		dto.setUid(profile.getUid());
		dto.setFirstName(profile.getFirstName());
		dto.setLastName(profile.getLastName());
		dto.setPassword(profile.getPassword());
		dto.setCompleted(profile.isCompleted());
		dto.setCreated(profile.getCreated());
		dto.setInfo(profile.getInfo());
		
		dto.setMainInfo(getMainInfo(profile));
		dto.setContacts(getContacts(profile));
		dto.setCertificates(getCertificates(profile));
		dto.setCourses(getCourses(profile));
		dto.setEducations(getEducations(profile));
		dto.setExperience(getExperience(profile));
		dto.setLanguages(getLanguages(profile));
		dto.setSkills(getSkills(profile));
		dto.setHobbies(getHobbies(profile));
		
		return dto;
	}

	private List<HobbyDTO> getHobbies(Profile profile) {
		List<HobbyDTO> dtoList = new ArrayList<>();
		List<Hobby> hobbies = profile.getHobbies();
		if (hobbies != null) {
			for (Hobby hobby : hobbies) {
				HobbyDTO dto = new HobbyDTO(hobby);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private List<SkillDTO> getSkills(Profile profile) {
		List<SkillDTO> dtoList = new ArrayList<>();
		List<Skill> skills = profile.getSkills();
		if (skills != null) {
			for (Skill skill : skills) {
				SkillDTO dto = new SkillDTO(skill);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private List<LanguageDTO> getLanguages(Profile profile) {
		List<LanguageDTO> dtoList = new ArrayList<>();
		List<Language> languages = profile.getLanguages();
		if (languages != null) {
			for (Language language : languages) {
				LanguageDTO dto = new LanguageDTO(language);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private List<PracticalExperienceDTO> getExperience(Profile profile) {
		List<PracticalExperienceDTO> dtoList = new ArrayList<>();
		List<PracticalExperience> exp = profile.getExperience();
		if (exp != null) {
			for (PracticalExperience experience : exp) {
				PracticalExperienceDTO dto = new PracticalExperienceDTO(experience);
				dtoList.add(dto);
			} 
		}
		return dtoList;
	}

	private List<EducationDTO> getEducations(Profile profile) {
		List<EducationDTO> dtoList = new ArrayList<>();
		List<Education> educations = profile.getEducations();
		if (educations != null) {
			for (Education education : educations) {
				EducationDTO dto = new EducationDTO(education);
				dtoList.add(dto);
			} 
		}
		return dtoList;
	}

	private List<CourseDTO> getCourses(Profile profile) {
		List<CourseDTO> dtoList = new ArrayList<>();
		List<Course> courses = profile.getCourses();
		if (courses != null) {
			for (Course course : courses) {
				CourseDTO dto = new CourseDTO(course);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private List<CertificateDTO> getCertificates(Profile profile) {
		List<CertificateDTO> dtoList = new ArrayList<>();
		List<Certificate> certificates = profile.getCertificates();
		if (certificates != null) {
			for (Certificate certificate : certificates) {
				CertificateDTO dto = new CertificateDTO(certificate);				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private ContactsDTO getContacts(Profile profile) {
		Contacts contacts = profile.getContacts();
		return new ContactsDTO(contacts);
	}

	private MainInfoDTO getMainInfo(Profile profile) {
		return new MainInfoDTO(profile);
	}

}
