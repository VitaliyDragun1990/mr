package com.revenat.myresume.application.transformer.converter;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Course;

@TypeConverter
class CourseDTOToCourseTransformer implements Converter<CourseDTO, Course> {

	@Override
	public Course convert(CourseDTO dto) {
		Course c = new Course();
		c.setEndDate(dto.getEndDate());
		c.setName(dto.getName());
		c.setSchool(dto.getSchool());
		return c;
	}

}
