package com.sifast.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.sifast.common.ApiMessage;
import com.sifast.common.constants.ApiStatus;
import com.sifast.common.utils.HttpErrorResponse;
import com.sifast.common.utils.HttpMessageResponse;
import com.sifast.dto.role.RoleDto;
import com.sifast.dto.role.ViewRoleDto;
import com.sifast.dto.user.ViewUserDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

public interface IRoleApi {

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = RoleDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Add a new role", response = ViewUserDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @PostMapping(value = "/role")
    @PreAuthorize(value = "hasAuthority('AUTH_CREATE_ROLE')")
    ResponseEntity<Object> saveRole(RoleDto roleDto, BindingResult bindingResult);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewRoleDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "find role by id", response = ViewRoleDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @GetMapping(value = "/role/{id}")
    @PreAuthorize(value = "hasAuthority('AUTH_VIEW_ROLE')")
    ResponseEntity<Object> getRole(int id);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewRoleDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "find all role", response = ViewRoleDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @GetMapping(value = "/roles")
    @PreAuthorize(value = "hasAuthority('AUTH_VIEW_ROLE')")
    ResponseEntity<Object> getAllRoles();

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewRoleDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Update role", response = ViewRoleDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @PutMapping(value = "/role/{id}")
    @PreAuthorize(value = "hasAuthority('AUTH_UPDATE_ROLE')")
    ResponseEntity<Object> updateRole(RoleDto roleDto, int id, BindingResult bindingResult);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = HttpMessageResponse.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Delete role", response = HttpMessageResponse.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @DeleteMapping(value = "/role/{id}")
    @PreAuthorize(value = "hasAuthority('AUTH_DELETE_ROLE')")
    ResponseEntity<Object> delete(int id);
}
