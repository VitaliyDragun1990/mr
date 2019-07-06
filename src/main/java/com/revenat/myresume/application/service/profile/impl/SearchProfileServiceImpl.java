package com.revenat.myresume.application.service.profile.impl;

import java.util.List;
import java.util.Optional;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.revenat.myresume.application.dto.ProfileDTO;
import com.revenat.myresume.application.exception.NotFoundException;
import com.revenat.myresume.application.service.cache.CacheService;
import com.revenat.myresume.application.service.profile.SearchProfileService;
import com.revenat.myresume.application.transformer.Transformer;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.search.ProfileSearchRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;

@Service
class SearchProfileServiceImpl implements SearchProfileService {
	
	private final ProfileRepository profileRepo;
	private final ProfileSearchRepository searchRepository;
	private final CacheService cacheService;
	private final Transformer transformer;
	
	@Autowired
	public SearchProfileServiceImpl(ProfileRepository profileRepo, ProfileSearchRepository searchRepository,
			CacheService cacheService, Transformer transformer) {
		this.profileRepo = profileRepo;
		this.searchRepository = searchRepository;
		this.cacheService = cacheService;
		this.transformer = transformer;
	}

	@Override
	public ProfileDTO getByUid(String uid) {
		Optional<Profile> profile = cacheService.findProfileByUid(uid);
		if (profile.isPresent()) {
			return transformer.transform(profile.get(), ProfileDTO.class);
		} else {
			throw new NotFoundException(Profile.class, "username", uid);
		}
	}
	
	@Override
	public Optional<ProfileDTO> findByEmail(String email) {
		Optional<Profile> optional = profileRepo.findByEmail(email);
		if (optional.isPresent()) {
			return Optional.of(transformer.transform(optional.get(), ProfileDTO.class));
		}
		return Optional.empty();
	}

	@Override
	public Page<ProfileDTO> findAll(Pageable pageable) {
		Page<Profile> page = profileRepo.findAll(pageable);
		List<Profile> content = page.getContent();
		List<ProfileDTO> dtoList = transformer.transformToList(content, Profile.class, ProfileDTO.class);
		return new PageImpl<>(dtoList, pageable, page.getTotalElements());
	}
	
	@Override
	public Page<ProfileDTO> findBySearchQuery(String query, Pageable pageable) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(query)
						.field("objective")
						.field("summary")
						.field("info")
						.field("certificates.name")
						.field("languages.name")
						.field("experience.company")
						.field("experience.position")
						.field("experience.responsibilities")
						.field("skills.value")
						.field("courses.name")
						.field("courses.school")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.operator(MatchQueryBuilder.Operator.AND))
				.withSort(SortBuilders.fieldSort("uid").order(SortOrder.DESC))
				.build();
		searchQuery.setPageable(pageable);
		Page<Profile> page = searchRepository.search(searchQuery);
		List<Profile> content = page.getContent();
		List<ProfileDTO> dtoList = transformer.transformToList(content, Profile.class, ProfileDTO.class);
		return new PageImpl<>(dtoList, pageable, page.getTotalElements());
	}

}
