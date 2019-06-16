package com.revenat.myresume.application.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Profile;

@TypeConverter
public class CourseDTOToCourseComverter implements Converter<CourseDTO, Course> {

	@Override
	public Course convert(CourseDTO dto) {
		Course c = new Course();
		c.setEndDate(dto.getEndDate());
		c.setId(dto.getId());
		c.setName(dto.getName());
		c.setSchool(dto.getSchool());
		if (dto.getProfileId() != null) {
			Profile p = new Profile();
			p.setId(dto.getProfileId());
			c.setProfile(p);
		}
		return c;
	}

}
