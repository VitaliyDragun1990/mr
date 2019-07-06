package com.revenat.myresume.infrastructure.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.revenat.myresume.domain.entity.Profile;

public interface ProfileSearchRepository extends ElasticsearchRepository<Profile, Long> {

	/**
	 * Example query if you're not using fuzzy elasticsearch 
	 */
	Page<Profile> findByObjectiveLikeOrSummaryLikeOrInfoLikeOrCertificatesNameLikeOrLanguagesNameLikeOrExperiencesCompanyLikeOrExperiencesPositionLikeOrExperiencesResponsibilitiesLikeOrSkillsValueLike(
			String objective, String summary, String info, String certificateName, String languageName, String experienceCompany,
			String experiencePosition, String experienceResponsibities, String skillValue, Pageable pageable);
}
