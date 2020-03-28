package com.sifast.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.common.mapper.GenericTrackMapper;
import com.sifast.model.GenericTrack;
import com.sifast.service.IGenericTrackService;
import com.sifast.web.service.api.IGenericTrackApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin("*")
@Api(value = "Generic track Api", tags = "Generic track api")
@RequestMapping(path = "/api/")

public class GenericTrackApi implements IGenericTrackApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericTrackApi.class);

    @Autowired
    private GenericTrackMapper genericTrackMapper;

    @Autowired
    private IGenericTrackService genericTrackService;

    private Object httpResponseBody;

    private HttpStatus httpStatus;

    @Override
    public ResponseEntity<?> getHistoryByEntityName(
            @ApiParam(value = "entityName", required = true, example = "Lot", allowableValues = "Lot,Region,LotMere,SendingSheet,LotReception,LotConditioning,FiltrationSheet") @PathVariable(name = "entityName", required = true) String entityName,
            @ApiParam(value = "offset", required = true, example = "0", allowableValues = "range[0,infinity]") @PathVariable(name = "offset", required = true) int offset,
            @ApiParam(value = "limit", required = true, example = "0", allowableValues = "range[0,infinity]") @PathVariable(name = "limit", required = true) int limit) {

        LOGGER.info("Web service getHistoryByEntityName invoked with args {}", entityName);
        Page<GenericTrack> history = genericTrackService.findTrackByEntityName(entityName, offset, limit);
        httpStatus = HttpStatus.OK;
        httpResponseBody = genericTrackMapper.mapGenericTrackToGenericTrackPaginationDto(history);

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getHistoryByEntityNameAndId(
            @ApiParam(value = "entityName", required = true, example = "Lot", allowableValues = "Lot,Region,LotMere,SendingSheet,LotReception,FiltrationSheet") @PathVariable(name = "entityName", required = true) String entityName,
            @ApiParam(value = "entityId", required = true, example = "0", allowableValues = "range[0,infinity]") @PathVariable(name = "entityId", required = true) Integer entityId,
            @ApiParam(value = "offset", required = true, example = "0", allowableValues = "range[0,infinity]") @PathVariable(name = "offset", required = true) int offset,
            @ApiParam(value = "limit", required = true, example = "0", allowableValues = "range[0,infinity]") @PathVariable(name = "limit", required = true) int limit) {
        LOGGER.info("Web service getHistoryByEntityNameAndId invoked with args {}, {}", entityName, entityId);
        Page<GenericTrack> history = genericTrackService.findTrackByEntityNameAndEntityId(entityName, entityId, offset, limit);
        httpStatus = HttpStatus.OK;
        httpResponseBody = genericTrackMapper.mapGenericTrackToGenericTrackPaginationDto(history);
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

}