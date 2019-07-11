package com.revenat.myresume.application.transformer.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revenat.myresume.application.config.annotation.TypeConverter;
import com.revenat.myresume.application.dto.CourseDTO;
import com.revenat.myresume.domain.entity.Course;
import com.revenat.myresume.domain.entity.Profile;

@Deprecated
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
