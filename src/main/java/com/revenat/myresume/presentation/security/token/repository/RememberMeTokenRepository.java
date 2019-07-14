package com.revenat.myresume.presentation.security.token.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.revenat.myresume.presentation.security.token.model.RememberMeToken;

public interface RememberMeTokenRepository extends CrudRepository<RememberMeToken, String> {

	RememberMeToken findBySeries(String series);
	
	List<RememberMeToken> findByUsername(String username);
}
