package com.sifast.web.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.common.ApiMessage;
import com.sifast.common.Constants;
import com.sifast.common.enums.HttpCostumCode;
import com.sifast.common.enums.HttpCostumMessage;
import com.sifast.common.utils.HttpErrorResponse;
import com.sifast.common.utils.HttpMessageResponse;
import com.sifast.common.utils.IValidatorError;
import com.sifast.common.utils.IWebServicesValidators;
import com.sifast.dto.role.RoleDto;
import com.sifast.dto.role.ViewRoleDto;
import com.sifast.dto.authority.AuthorityDto;
import com.sifast.model.Authority;
import com.sifast.model.Role;
import com.sifast.service.IAuthorityService;
import com.sifast.service.IRoleService;
import com.sifast.service.config.ConfiguredModelMapper;
import com.sifast.web.service.api.IRoleApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin("*")
@Api(value = "role api", tags = "role-api")
@RequestMapping(value = "/api/")
public class RoleApi implements IRoleApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleApi.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAuthorityService authorityService;

    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

    @Autowired
    private ConfiguredModelMapper modelMapper;

    private Object httpResponseBody;

    private HttpStatus httpStatus;

    @Override
    public ResponseEntity<Object> saveRole(
            @ApiParam(required = true, value = "role", name = "role") @RequestBody @Validated(value = { IWebServicesValidators.class }) RoleDto roleDto,
            BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            Role mappedRole = modelMapper.map(roleDto, Role.class);
            roleDto.getAuthoritiesId().add(getDefaultAuthorityByDesignation(Constants.DEFAULT_AUTHORITY));
            List<Authority> authorities = authorityService.findByIdIn(roleDto.getAuthoritiesId());
            mappedRole.setAuthorities(new HashSet<>(authorities));
            Role savedRole = roleService.save(mappedRole);
            httpStatus = HttpStatus.CREATED;
            httpResponseBody = savedRole;
            LOGGER.info("INFO level message: Role saved {}", savedRole);
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), IValidatorError.getValidatorErrors(bindingResult));
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    private int getDefaultAuthorityByDesignation(String designation) {
        return authorityService.findByDesignation(designation).getId();
    }

    @Override
    public ResponseEntity<Object> getRole(@ApiParam(value = "Id of User that will be fetched", required = true) @PathVariable("id") int id) {
        Optional<Role> role = roleService.findById(id);
        if (role.isPresent()) {
            ViewRoleDto viewRoleDto = modelMapper.map(role.get(), ViewRoleDto.class);
            Set<AuthorityDto> authorities = role.get().getAuthorities().stream().map(authority -> modelMapper.map(authority, AuthorityDto.class)).collect(Collectors.toSet());
            viewRoleDto.setAuthorities(authorities);
            httpResponseBody = viewRoleDto;
            httpStatus = HttpStatus.OK;
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), HttpCostumMessage.EMPTY_RESPONSE_MSG.getValue());
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<Object> getAllRoles() {
        httpStatus = HttpStatus.OK;
        List<Role> roles = roleService.findAll();
        if (roles.isEmpty()) {
            httpResponseBody = Collections.emptyList();
        } else {
            httpResponseBody = roles.stream().map(role -> modelMapper.map(role, ViewRoleDto.class)).collect(Collectors.toList());
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<Object> updateRole(
            @ApiParam(required = true, value = "role", name = "role") @RequestBody @Validated(value = { IWebServicesValidators.class }) RoleDto roleDto,
            @ApiParam(required = true, value = "id", name = "id") @PathVariable("id") int id, BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            Optional<Role> role = roleService.findById(id);
            if (role.isPresent()) {
                roleDto.getAuthoritiesId().add(getDefaultAuthorityByDesignation(Constants.DEFAULT_AUTHORITY));
                forceRoleLogout(role.get(), roleDto);
                Role preUpdatedRole = modelMapper.map(roleDto, Role.class);
                List<Authority> authorities = authorityService.findByIdIn(roleDto.getAuthoritiesId());
                preUpdatedRole.setAuthorities(new HashSet<>(authorities));
                preUpdatedRole.setId(id);
                Role updatedRole = roleService.save(preUpdatedRole);

                httpResponseBody = modelMapper.map(updatedRole, ViewRoleDto.class);
                httpStatus = HttpStatus.OK;
            } else {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.NO_DATA);
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = httpErrorResponse;
            }
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(), IValidatorError.getValidatorErrors(bindingResult));
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    private void forceRoleLogout(Role updatedRole, RoleDto roleDto) {
        Set<Integer> authoritiesId = updatedRole.getAuthorities().stream().map(authoritie -> authoritie.getId()).collect(Collectors.toSet());
        if (!roleDto.getAuthoritiesId().equals(authoritiesId)) {
            roleService.forceRoleLogout(updatedRole);
        }
    }

    @Override
    public ResponseEntity<Object> delete(@ApiParam(required = true, value = "id", name = "id") @PathVariable("id") int id) {
        Optional<Role> preDeleteRole = roleService.findById(id);
        if (!preDeleteRole.isPresent()) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), "No role with the requested identifier, Check your entry please.");
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            if (preDeleteRole.get().getId() == 1) {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(), ApiMessage.ROLE_IS_SUPER_ADMIN);
                httpResponseBody = httpErrorResponse;
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                roleService.deleteById(preDeleteRole.get().getId());
                LOGGER.debug("INFO level message: Role with id = {} deleted ", id);
                httpResponseBody = new HttpMessageResponse("Role deleted successfully");
                httpStatus = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

}
