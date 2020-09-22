package com.sifast.web.service.api;

import org.springframework.http.MediaType;
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
import com.sifast.dto.user.ChangePasswordDto;
import com.sifast.dto.user.CreateUserDto;
import com.sifast.dto.user.ForceUpdatePassword;
import com.sifast.dto.user.UserDto;
import com.sifast.dto.user.UserInfoDto;
import com.sifast.dto.user.UserPasswordDto;
import com.sifast.dto.user.ViewUserDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

public interface IUserApi {

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewUserDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Add a new user", response = ViewUserDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @PostMapping(value = "/user")
    @PreAuthorize(value = "hasAuthority('AUTH_CREATE_USER')")
    ResponseEntity<Object> saveUser(CreateUserDto userDto, BindingResult bindingResult);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewUserDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "find user by id", response = ViewUserDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @GetMapping(value = "/user/{id}")
    @PreAuthorize(value = "hasAuthority('AUTH_VIEW_USER')")
    ResponseEntity<Object> getUser(int id);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewUserDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "find all users", response = ViewUserDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @GetMapping(value = "/users")
    @PreAuthorize(value = "hasAuthority('AUTH_VIEW_USER')")
    ResponseEntity<Object> getAllUsers();

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = ViewUserDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Update user", response = ViewUserDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @PutMapping(value = "/user/{id}")
    @PreAuthorize(value = "hasAuthority('AUTH_UPDATE_USER')")
    ResponseEntity<Object> updateUser(UserDto userDto, int id, BindingResult bindingResult);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_CREATED, message = ApiMessage.CREATED_SUCCESSFULLY, response = HttpMessageResponse.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Delete user", response = HttpMessageResponse.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @DeleteMapping(value = "/user/{id}")
    @PreAuthorize(value = "hasAuthority('AUTH_DELETE_USER')")
    ResponseEntity<Object> delete(int id);

    @PutMapping(value = "/user/update-password")
    @ApiOperation(value = "Update password", authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "trust", description = "") }) })
    ResponseEntity<Object> updatePassword(ChangePasswordDto changePasswordDto, BindingResult bindingResult);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.CREATED_SUCCESSFULLY, response = UserInfoDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "get Connected User", response = UserInfoDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @GetMapping(value = "/user")
    ResponseEntity<Object> getConnectedUser();

    @PutMapping(value = "/user/force-update-password")
    @ApiOperation(value = "Update password", authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "trust", description = "") }) })
    @PreAuthorize(value = "hasAuthority('AUTH_CREATE_USER') or hasAuthority('AUTH_UPDATE_USER')")
    ResponseEntity<Object> forceUpdatePassword(ForceUpdatePassword forceUpdatePassword, BindingResult bindingResult);

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.CREATED_SUCCESSFULLY, response = UserPasswordDto.class) })
    @ApiOperation(value = "compare passwords of connected user", response = UserPasswordDto.class, authorizations = {
            @Authorization(value = ApiMessage.OAUTH2SCHEMA, scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @PostMapping(value = "/user/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> comparePasswords(String password);
}
