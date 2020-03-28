package com.sifast.web.config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("userName", user.getUsername());
		additionalInfo.put("refreshTokenValidity",
				clientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId())
						.getRefreshTokenValiditySeconds());
		additionalInfo.put("authorities", StringUtils.join(
				user.getAuthorities().stream().map(a -> a.getAuthority().toLowerCase()).collect(Collectors.toList()),
				";"));
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

}
