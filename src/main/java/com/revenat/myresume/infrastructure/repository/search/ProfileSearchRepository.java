package com.revenat.myresume.infrastructure.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.revenat.myresume.domain.entity.Profile;

public interface ProfileSearchRepository extends ElasticsearchRepository<Profile, Long> {

	Page<Profile> findByObjectiveLikeOrSummaryLikeOrExperienceCompanyLikeOrExperiencePositionLike(
			String objective, String summary, String experienceCompany, String experiencePosition, Pageable pageable);
}
