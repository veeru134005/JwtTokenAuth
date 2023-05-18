package com.insigno.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigno.daointer.JwtDao;
import com.insigno.entity.TokensEntity;
import com.insigno.model.AuthenticationRequest;
import com.insigno.model.AuthenticationResponse;
import com.insigno.service.IJwtService;

@Service
public class IJwtServiceImpl implements IJwtService {

	@Autowired
	private JwtDao jwtdao;

	public AuthenticationResponse fetchExistTokenIfPresent(AuthenticationResponse authRes,
			AuthenticationRequest authReq) {

		Optional<List<TokensEntity>> tokenDetails = jwtdao.fetchTokendetails(authRes.getCustomerSeqNumber());

		if (tokenDetails.isPresent()) {
			Optional<TokensEntity> isLongLivedToken = tokenDetails.get().stream().filter(p -> p.getIs_long_lived_token() && p.getToken_revoked_at() == null).findFirst();
			
			if (isLongLivedToken.isPresent()) {
				
				longLivedToken(isLongLivedToken.get(), authRes);
				
				return authRes;
			
			} else {
				
				Optional<TokensEntity> activeJwtToken = tokenDetails.get().stream().filter(p -> !p.getIs_token_expired()
						&& p.getToken_revoked_at() == null && p.getToken_expires_at().compareTo(new Date()) == 1)
						.findFirst();

				activeOrExpiredToken(activeJwtToken, authRes, authReq);
				
				return authRes;

			}
		}else {
			createTokenDetails(authReq, authRes);
			authRes.setTokenStatus("NewToken");
			return authRes;
		}
	}

	private void activeOrExpiredToken(Optional<TokensEntity> activeJwtToken, AuthenticationResponse authRes,
			AuthenticationRequest authReq) {
		if (activeJwtToken.isPresent()) {
			authRes.setExpirationTime(activeJwtToken.get().getToken_expires_at());
			authRes.setType(activeJwtToken.get().getToken_type());
			authRes.setToken(activeJwtToken.get().getToken_details());
			authRes.setTokenStatus("TokenNotExpired");
		} else {
			deleteTokenDetails(authRes.getCustomerSeqNumber());
			createTokenDetails(authReq, authRes);
			authRes.setCustomerSeqNumber(authRes.getCustomerSeqNumber());
			authRes.setTokenStatus("created");
		}
	}

	private void longLivedToken(TokensEntity tokensEntity, AuthenticationResponse authRes) {
		authRes.setExpirationTime(tokensEntity.getToken_expires_at());
		authRes.setType(tokensEntity.getToken_type());
		authRes.setToken(tokensEntity.getToken_details());
		authRes.setTokenStatus("Token Is Long Lived");
	}

	public Optional<Long> findByUserSecNum(AuthenticationRequest authRequest, AuthenticationResponse authRes) {
		return jwtdao.findBySecNum(authRequest, authRes);
	}

	@Transactional
	public void updateTokenDetails(AuthenticationRequest authRequest, AuthenticationResponse authRes) {

		jwtdao.updateTokenDetails(authRequest, authRes);
	}

	@Transactional
	public void deleteTokenDetails(Long seqNo) {
		jwtdao.deleteTokenDetails(seqNo);
	}

	@Transactional
	public void createTokenDetails(AuthenticationRequest authRequest, AuthenticationResponse authRes) {
		jwtdao.createTokenDetails(authRequest, authRes);
	}

}
