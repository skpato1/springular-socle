package com.sifast.web.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.common.ApiMessage;
import com.sifast.common.enums.HttpCostumCode;
import com.sifast.common.mapper.UserMapper;
import com.sifast.common.utils.HttpErrorResponse;
import com.sifast.common.utils.HttpMessageResponse;
import com.sifast.common.utils.IValidatorError;
import com.sifast.common.utils.IWebServicesValidators;
import com.sifast.dto.user.ChangePasswordDto;
import com.sifast.dto.user.CreateUserDto;
import com.sifast.dto.user.ForceUpdatePassword;
import com.sifast.dto.user.UserDto;
import com.sifast.dto.user.UserInfoDto;
import com.sifast.dto.user.UserPasswordDto;
import com.sifast.dto.user.ViewUserDto;
import com.sifast.exception.CustomException;
import com.sifast.model.Role;
import com.sifast.model.User;
import com.sifast.service.IRoleService;
import com.sifast.service.IUserService;
import com.sifast.validator.UserValidator;
import com.sifast.web.config.ConfiguredModelMapper;
import com.sifast.web.service.api.IUserApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin("*")
@Api(value = "user api", tags = "user-api")
@RequestMapping(value = "/api/")
public class UserApi implements IUserApi  {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

	private Object httpResponseBody;

	private HttpStatus httpStatus;

	@Autowired
	private ConfiguredModelMapper modelMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserValidator userValidator;

	@Override
	public ResponseEntity<Object> saveUser(
			@ApiParam(required = true, value = "user", name = "user") @RequestBody @Validated(value = {
					IWebServicesValidators.class }) CreateUserDto userDto,
			BindingResult bindingResult) {
		LOGGER.info("Web service saveUser invoked with userDto {}", userDto);
		try {
			userValidator.createUserValidator(userDto, bindingResult);
			User savedUser = userService.save(userMapper.mapCreateUser(userDto));
			httpStatus = HttpStatus.OK;
			httpResponseBody = modelMapper.map(savedUser, ViewUserDto.class);
		} catch (CustomException e) {
			httpResponseBody = new HttpErrorResponse(HttpCostumCode.BAD_REQUEST.getValue(), e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<Object> getUser(
			@ApiParam(value = "Id of User that will be fetched", required = true) @PathVariable("id") int id) {
		LOGGER.info("Web service getUser invoked with id {}", id);

		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			httpStatus = HttpStatus.OK;
			httpResponseBody = userMapper.mapUserToViewUserDto(user.get());
		} else {
			httpResponseBody = new HttpErrorResponse(HttpCostumCode.BAD_REQUEST.getValue(), ApiMessage.USER_NOT_FOUND);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<Object> getAllUsers() {
		List<User> users = userService.findAll();
		httpStatus = HttpStatus.OK;
		httpResponseBody = !users.isEmpty()
				? users.stream().map(user -> modelMapper.map(user, ViewUserDto.class)).collect(Collectors.toList())
				: Collections.emptyList();
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<Object> updateUser(
			@ApiParam(required = true, value = "user", name = "user") @Validated(value = IWebServicesValidators.class) @RequestBody UserDto userDto,
			@ApiParam(required = true, value = "id", name = "id") @PathVariable("id") int id,
			BindingResult bindingResult) {
		if (!bindingResult.hasFieldErrors()) {
			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {
				User preUpdateUser = user.get();
				forceUserLogout(preUpdateUser, userDto);
				User updatedUser = userService.save(userMapper.mapUpdateUser(userDto, user, id));
				httpStatus = HttpStatus.OK;
				httpResponseBody = modelMapper.map(user, User.class);
				LOGGER.info("INFO level message: User updated {}", updatedUser);

			} else {
				httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.USER_NOT_FOUND);
				httpStatus = HttpStatus.NOT_FOUND;
				httpResponseBody = httpErrorResponse;
			}
		} else {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(),
					IValidatorError.getValidatorErrors(bindingResult));
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<?> delete(@ApiParam(required = true, value = "id", name = "id") @PathVariable("id") int id) {
		Optional<User> preDeleteUser = userService.findById(id);
		if (!preDeleteUser.isPresent()) {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.USER_NOT_FOUND);
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.NOT_FOUND;
		} else {
			if (!isSuperAdmin(preDeleteUser.get())) {
				userService.delete(preDeleteUser.get());
				httpResponseBody = new HttpMessageResponse("User deleted successfully");
				httpStatus = HttpStatus.OK;
				LOGGER.info("INFO level message: User with id = {} deleted ", id);
			}
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	private boolean isSuperAdmin(User user) {
		Set<Integer> userRolesId = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
		if (userRolesId.contains(1)) {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(),
					ApiMessage.USER_IS_SUPER_ADMIN);
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntity<?> updatePassword(
			@ApiParam(required = true, value = "Contains old,new passwords and username", name = "Password change") @Valid @RequestBody ChangePasswordDto changePasswordDto,
			BindingResult bindingResult) {
		Optional<User> userToChangeHisPassword = userService.findById(changePasswordDto.getId());
		if (userToChangeHisPassword.isPresent()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean passwordsMatch = encoder.matches(changePasswordDto.getOldPassword(),
					userToChangeHisPassword.get().getPassword());
			if (passwordsMatch && !bindingResult.hasFieldErrors()) {
				userToChangeHisPassword.get().setPassword(encoder.encode(changePasswordDto.getNewPassword()));
				userService.save(userToChangeHisPassword.get());
				userService.forceUserLogout(userToChangeHisPassword.get());
				httpResponseBody = new HttpMessageResponse("Password Updated");
				httpStatus = HttpStatus.ACCEPTED;
			} else {
				catchPasswordAndUsernameValidationMessage(!passwordsMatch, ApiMessage.WRONG_OLD_PASSWORD);
				catchPasswordAndUsernameValidationMessage(bindingResult.hasFieldErrors(),
						IValidatorError.getValidatorErrors(bindingResult));
			}
		} else {
			catchPasswordAndUsernameValidationMessage(!userToChangeHisPassword.isPresent(), ApiMessage.USER_NOT_FOUND);
		}

		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<Object> getConnectedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> connectedUser = userService.findByLogin(authentication.getName());
		if (connectedUser.isPresent()) {
			httpResponseBody = new UserInfoDto(connectedUser.get());
			httpStatus = HttpStatus.ACCEPTED;
		} else {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.USER_NOT_FOUND);
			httpStatus = HttpStatus.NOT_FOUND;
			httpResponseBody = httpErrorResponse;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	private void catchPasswordAndUsernameValidationMessage(boolean passwordsMatch, String message) {
		if (passwordsMatch) {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(), message);
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		}
	}

	@Override
	public ResponseEntity<?> forceUpdatePassword(
			@ApiParam(required = true, value = "Contains new passwords ", name = "Password change") @Valid @RequestBody ForceUpdatePassword forceUpdatePassword,
			BindingResult bindingResult) {
		Optional<User> userToChangeHisPassword = userService.findById(forceUpdatePassword.getId());
		if (userToChangeHisPassword.isPresent()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userToChangeHisPassword.get().setPassword(encoder.encode(forceUpdatePassword.getNewPassword()));
			userService.save(userToChangeHisPassword.get());
			userService.forceUserLogout(userToChangeHisPassword.get());
			httpResponseBody = new HttpMessageResponse("Password Updated");
			httpStatus = HttpStatus.ACCEPTED;
		} else {
			catchPasswordAndUsernameValidationMessage(!userToChangeHisPassword.isPresent(), ApiMessage.USER_NOT_FOUND);
		}

		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	private void forceUserLogout(User preUpdateUser, UserDto userDto) {
		Set<Integer> rolesId = preUpdateUser.getRoles().stream().map(role -> role.getId()).collect(Collectors.toSet());
		if (!userDto.getRolesId().equals(rolesId)) {
			roleService.forceUserLogout(preUpdateUser);
		}
	}

	@Override
	public ResponseEntity<Object> comparePasswords(@RequestBody String password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> connectedUser = userService.findByLogin(authentication.getName());
		if (connectedUser.isPresent()) {
			if (connectedUser.get().getPassword().equals(BCrypt.hashpw(password, connectedUser.get().getPassword()))) {
				httpResponseBody = new UserPasswordDto(connectedUser.get().getPassword(),
						connectedUser.get().getLogin());
				httpStatus = HttpStatus.ACCEPTED;
			} else {
				httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(),
						ApiMessage.PASSWORDS_ARE_NOT_MATCHING);
				httpStatus = HttpStatus.BAD_REQUEST;
				httpResponseBody = httpErrorResponse;
			}
		} else {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.USER_NOT_FOUND);
			httpStatus = HttpStatus.NOT_FOUND;
			httpResponseBody = httpErrorResponse;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

}