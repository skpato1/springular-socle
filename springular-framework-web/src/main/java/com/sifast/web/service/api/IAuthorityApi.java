package com.sifast.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.common.ApiMessage;
import com.sifast.common.constants.ApiStatus;
import com.sifast.common.utils.HttpErrorResponse;
import com.sifast.dto.authority.ViewAuthorityDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

public interface IAuthorityApi {

	@ApiResponses(value = {
			@ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewAuthorityDto.class),
			@ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
	@ApiOperation(value = "find all authorities", response = ViewAuthorityDto.class, authorizations = {
			@Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = {
					@AuthorizationScope(scope = "read", description = "") }) })
	@RequestMapping(value = "/authorities", method = RequestMethod.GET)
	ResponseEntity<Object> getAllAuthorities();
}
