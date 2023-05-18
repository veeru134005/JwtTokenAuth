package com.insigno.daoimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insigno.daointer.JwtDao;
import com.insigno.entity.TokensEntity;
import com.insigno.model.AuthenticationRequest;
import com.insigno.model.AuthenticationResponse;
import com.insigno.util.DatabaseQueryConstant;

@Repository
@SuppressWarnings("unchecked")
public class JwtDaoImpl implements JwtDao {

	@Autowired
	private EntityManager entityManager;

	public Optional<Long> findBySecNum(AuthenticationRequest authRequest, AuthenticationResponse authRes) {

		String fetchUserDetailsQuery = DatabaseQueryConstant.fetchUserDetailsQuery;

		List<Long> resultList = entityManager.createNativeQuery(fetchUserDetailsQuery)
				.setParameter("applicationId", authRequest.getApplicationId())
				.setParameter("tenentId", authRequest.getTenantId()).setParameter("custId", authRequest.getUserId())
				.setParameter("custPassword", authRequest.getPassword()).getResultList();

		return Optional.ofNullable(resultList.isEmpty() ? null : resultList.get(0));
	}
	
	public Optional<Long> findByUserName(AuthenticationRequest authRequest, AuthenticationResponse authRes) {

		String fetchUserDetailsQuery = DatabaseQueryConstant.fetchUserDetailsQuery;

		List<Long> resultList = entityManager.createNativeQuery(fetchUserDetailsQuery)
				.setParameter("applicationId", authRequest.getApplicationId())
				.setParameter("tenentId", authRequest.getTenantId()).setParameter("custId", authRequest.getUserId())
				.setParameter("custPassword", authRequest.getPassword()).getResultList();

		return Optional.ofNullable(resultList.isEmpty() ? null : resultList.get(0));
	}

	public Optional<List<TokensEntity>> fetchTokendetails(Long seqNo) {

		String fetQueryForTOken = DatabaseQueryConstant.fetQueryForTOken;

		List<TokensEntity> resultList1 = entityManager.createNativeQuery(fetQueryForTOken).setParameter("seqNo", seqNo).getResultList();

		return Optional.ofNullable(resultList1.isEmpty() ? null : resultList1);
	}

	public void updateTokenDetails(AuthenticationRequest authRequest,
			AuthenticationResponse authRes) {

		String updateToken = DatabaseQueryConstant.updateToken;

		entityManager.createNativeQuery(updateToken)
				.setParameter("customer_sequence_number", authRes.getCustomerSeqNumber())
				.setParameter("token_details", authRes.getJwt())
				.setParameter("token_expires_at",
						new Date(System.currentTimeMillis() + 1000 * 60 * authRequest.getExpirationTime()))
				.setParameter("token_created_at", new Date()).setParameter("token_revoked_at", null)
				.setParameter("is_token_expired", false)
				.setParameter("is_long_lived_token", authRequest.isRememberMeSelected()).executeUpdate();
		authRes.setTokenStatus("Update");

	}

	public void deleteTokenDetails(Long seqNo) {
		String tokenDeleteQuery = DatabaseQueryConstant.deleteToken;

		entityManager.createNativeQuery(tokenDeleteQuery).setParameter("customer_sequence_number", seqNo)
				.executeUpdate();
	}

	public void createTokenDetails(AuthenticationRequest authRequest, AuthenticationResponse authRes) {

		String storeToken = DatabaseQueryConstant.createToken;

		entityManager.createNativeQuery(storeToken)
				.setParameter("customer_sequence_number", authRes.getCustomerSeqNumber())
				.setParameter("token_type", "JWT").setParameter("token_details", "HS256")
				.setParameter("token_expires_at",
						new Date(System.currentTimeMillis() + 1000 * 60 * authRequest.getExpirationTime()))
				.setParameter("token_created_at", new Date()).setParameter("token_revoked_at", null)
				.setParameter("is_token_expired", false)
				.setParameter("is_long_lived_token", authRequest.isRememberMeSelected()).executeUpdate();
		authRes.setCustomerSeqNumber(authRes.getCustomerSeqNumber());
		authRes.setTokenStatus("Created");

	}

}
