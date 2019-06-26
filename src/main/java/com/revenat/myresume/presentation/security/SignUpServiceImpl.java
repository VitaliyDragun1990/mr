package com.revenat.myresume.presentation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.revenat.myresume.application.generator.UidGenerator;
import com.revenat.myresume.application.util.DataUtil;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.service.SearchIndexingService;
import com.revenat.myresume.infrastructure.util.CommonUtils;

@Service
class SignUpServiceImpl implements SignUpService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SignUpServiceImpl.class);
	
	private final ProfileRepository profileRepo;
	private final SearchIndexingService searchIndexingService;
	private final UidGenerator uidGenerator;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SignUpServiceImpl(ProfileRepository profileRepo, SearchIndexingService searchIndexingService,
			UidGenerator uidGenerator, PasswordEncoder passwordEncoder) {
		this.profileRepo = profileRepo;
		this.searchIndexingService = searchIndexingService;
		this.uidGenerator = uidGenerator;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public AuthenticatedUser signUp(String firstName, String lastName, String password) {
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setPassword(password);
		return signUp(profile);
	}

	@Override
	@Transactional
	public AuthenticatedUser signUp(Profile newProfile) {
		Profile profile = new Profile();
		profile.setUid(uidGenerator.generateUid(
				newProfile.getFirstName(),
				newProfile.getLastName(),
				uid -> profileRepo.countByUid(uid) > 0));
		profile.setFirstName(DataUtil.capitalizeName(newProfile.getFirstName()));
		profile.setLastName(DataUtil.capitalizeName(newProfile.getLastName()));
		profile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
		profile.setLargePhoto(newProfile.getLargePhoto());
		profile.setSmallPhoto(newProfile.getSmallPhoto());
		profile.setBirthDay(newProfile.getBirthDay());
		profile.setCountry(newProfile.getCountry());
		profile.setCity(newProfile.getCity());
		profile.setEmail(newProfile.getEmail());
		profile.setPhone(newProfile.getPhone());
		profile.setObjective(newProfile.getObjective());
		profile.setSummary(newProfile.getSummary());
		
		boolean completedRegistration = isRegistrationCompleted(profile);
		profile.setCompleted(completedRegistration);
		
		Profile savedProfile = profileRepo.save(profile);
		
		LOGGER.info("New profile created: {}", profile.getUid());
		executeIfTransactionSuccess(() -> searchIndexingService.createIndexProfile(savedProfile));

		return SecurityUtil.authenticate(savedProfile);
	}
	
	private boolean isRegistrationCompleted(Profile profile) {
		boolean hasPhoto = !CommonUtils.isBlank(profile.getLargePhoto()) && !CommonUtils.isBlank(profile.getSmallPhoto());
		boolean hasAddress = !CommonUtils.isBlank(profile.getCountry()) && !CommonUtils.isBlank(profile.getCity());
		boolean hasBirthday = profile.getBirthDay() != null;
		boolean hasPhoneAndEmail = CommonUtils.isBlank(profile.getPhone()) && !CommonUtils.isBlank(profile.getEmail());
		boolean hasInfo = CommonUtils.isBlank(profile.getObjective()) && !CommonUtils.isBlank(profile.getSummary());
		return hasPhoto && hasAddress && hasBirthday && hasPhoneAndEmail && hasInfo;
	}

	private void executeIfTransactionSuccess(Runnable action) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				action.run();
			}
		});
	}

}
