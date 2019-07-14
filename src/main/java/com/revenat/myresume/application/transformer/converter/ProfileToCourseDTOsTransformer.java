package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.application.transformer.TypeConverter;
import com.revenat.myresume.domain.document.Course;
import com.revenat.myresume.domain.document.Profile;

@TypeConverter
class ProfileToCourseDTOsTransformer implements Converter<Profile, List<CourseDTO>> {

	@Override
	public List<CourseDTO> convert(Profile profile) {
		List<CourseDTO> dtoList = new ArrayList<>();
		if (profile != null) {
			for (Course course : profile.getCourses()) {
				dtoList.add(new CourseDTO(course));
			}
		}
		return dtoList;
	}

}
