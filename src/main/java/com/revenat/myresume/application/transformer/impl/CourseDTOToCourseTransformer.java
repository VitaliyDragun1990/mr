package com.revenat.myresume.application.transformer.impl;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.domain.entity.Course;

@TypeConverter
class CourseDTOToCourseTransformer implements Converter<CourseDTO, Course> {

	@Override
	public Course convert(CourseDTO dto) {
		Course c = new Course();
		c.setEndDate(dto.getEndDate());
		c.setId(dto.getId());
		c.setName(dto.getName());
		c.setSchool(dto.getSchool());
		return c;
	}

}
