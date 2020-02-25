package com.sifast.web.service.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.common.ApiMessage;
import com.sifast.common.constants.ApiStatus;
import com.sifast.common.utils.HttpErrorResponse;
import com.sifast.dto.genericTrack.GenericTrackPaginationDto;
import com.sifast.model.GenericTrack;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

public interface IGenericTrackApi {

    @RequestMapping(value = "/history/{entityName}/{offset}/{limit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all hisory by entity name ", response = GenericTrack.class, authorizations = {
            @Authorization(value = "oauth2schema", scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @ApiResponses(value = {
            @ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = GenericTrackPaginationDto.class, responseContainer = "List"),
            @ApiResponse(code = ApiStatus.STATUS_NOT_FOUND, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class) })
    ResponseEntity<?> getHistoryByEntityName(String entityName, int offset, int limit);

    @RequestMapping(value = "/history/{entityName}/{entityId}/{offset}/{limit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all hisory by entity name ", response = GenericTrack.class, authorizations = {
            @Authorization(value = "oauth2schema", scopes = { @AuthorizationScope(scope = "read", description = "") }) })
    @ApiResponses(value = {
            @ApiResponse(code = ApiStatus.STATUS_OK, message = ApiMessage.SUCCESSFUL_OPERATION, response = GenericTrackPaginationDto.class, responseContainer = "List"),
            @ApiResponse(code = ApiStatus.STATUS_NOT_FOUND, message = ApiMessage.NO_DATA, response = HttpErrorResponse.class) })
    ResponseEntity<?> getHistoryByEntityNameAndId(String entityName, Integer entityId, int offset, int limit);

}