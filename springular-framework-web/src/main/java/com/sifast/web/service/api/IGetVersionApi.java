package com.sifast.web.service.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.sifast.common.ApiMessage;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

public interface IGetVersionApi {

	@GetMapping(value = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Get Application version", response = String.class, authorizations = {
			@Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {
					@AuthorizationScope(scope = "read", description = "") }) })
	ResponseEntity<?> getVersion();
}