package com.revenat.myresume.presentation.security.adapter;

import java.util.Date;
import java.util.List;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.revenat.myresume.presentation.security.token.model.RememberMeToken;
import com.revenat.myresume.presentation.security.token.repository.RememberMeTokenRepository;

public class MongoPersistentTokenRepositoryAdapter implements PersistentTokenRepository {
	private final RememberMeTokenRepository rememberMeTokeRepo;
	
	public MongoPersistentTokenRepositoryAdapter(RememberMeTokenRepository rememberMeTokeRepo) {
		this.rememberMeTokeRepo = rememberMeTokeRepo;
	}

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		rememberMeTokeRepo.save(new RememberMeToken(token));
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken token = rememberMeTokeRepo.findBySeries(series);
		rememberMeTokeRepo.save(new RememberMeToken(token.getId(), token.getUsername(), token.getSeries(), tokenValue, lastUsed));
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return rememberMeTokeRepo.findBySeries(seriesId);
	}

	@Override
	public void removeUserTokens(String username) {
		List<RememberMeToken> list = rememberMeTokeRepo.findByUsername(username);
		if (!list.isEmpty()) {
			rememberMeTokeRepo.delete(list);
		}
	}

}
