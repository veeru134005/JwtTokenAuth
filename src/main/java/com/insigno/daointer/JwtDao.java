package com.insigno.daointer;

import java.util.List;
import java.util.Optional;

import com.insigno.entity.TokensEntity;
import com.insigno.model.AuthenticationRequest;
import com.insigno.model.AuthenticationResponse;

public interface JwtDao {
	
	public Optional<Long> findBySecNum(AuthenticationRequest authRequest, AuthenticationResponse authRes);

	public Optional<List<TokensEntity>> fetchTokendetails(Long seqNo);

	public void updateTokenDetails(AuthenticationRequest authRequest, AuthenticationResponse authRes);

	public void deleteTokenDetails(Long seqNo);

	public void createTokenDetails(AuthenticationRequest authRequest, AuthenticationResponse authRes);

}
