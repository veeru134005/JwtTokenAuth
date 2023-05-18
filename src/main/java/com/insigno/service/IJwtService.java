package com.insigno.service;

import java.util.Optional;

import com.insigno.model.AuthenticationRequest;
import com.insigno.model.AuthenticationResponse;

public interface IJwtService {

	Optional<Long> findByUserSecNum(AuthenticationRequest authRequest, AuthenticationResponse authRes);

	void updateTokenDetails(AuthenticationRequest authRequest,AuthenticationResponse authRes);

	void deleteTokenDetails(Long seqNo);

	public void createTokenDetails(AuthenticationRequest authRequest, AuthenticationResponse authRes);

	public AuthenticationResponse fetchExistTokenIfPresent(AuthenticationResponse authRes, AuthenticationRequest authReq);

}
