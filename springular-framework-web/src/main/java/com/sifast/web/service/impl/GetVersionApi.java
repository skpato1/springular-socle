package com.sifast.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.web.service.api.IGetVersionApi;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin("*")
@Api(value = "Version Api", tags = "Version Api")
@RequestMapping(value = "/api/")
public class GetVersionApi implements IGetVersionApi {

    @Autowired
    private BuildProperties buildProperties;

    @Override
    public ResponseEntity<?> getVersion() {
        return new ResponseEntity<>(buildProperties.getVersion(), HttpStatus.OK);
    }
}