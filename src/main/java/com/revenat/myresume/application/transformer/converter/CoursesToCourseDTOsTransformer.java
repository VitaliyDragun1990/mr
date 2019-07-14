package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Course;

@TypeConverter
class CoursesToCourseDTOsTransformer implements Converter<List<Course>, List<CourseDTO>> {

	@Override
	public List<CourseDTO> convert(List<Course> source) {
		List<CourseDTO> dtoList = new ArrayList<>();
		if (source != null) {
			for (Course c : source) {
				dtoList.add(new CourseDTO(c));
			}
		}
		return dtoList;
	}

}
