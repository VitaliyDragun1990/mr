package com.revenat.myresume.application.transformer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.domain.entity.Course;

@TypeConverter
class CourseDTOsToCoursesTransformer implements Converter<List<CourseDTO>, List<Course>> {
	private CourseDTOToCourseTransformer dtoTransformer = new CourseDTOToCourseTransformer();

	@Override
	public List<Course> convert(List<CourseDTO> source) {
		List<Course> courses = new ArrayList<>();
		if (source != null) {
			for (CourseDTO dto : source) {
				courses.add(dtoTransformer.convert(dto));
			}
		}
		return courses;
	}

}
