package com.sifast.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sifast.dto.genericTrack.GenericTrackDto;
import com.sifast.dto.genericTrack.GenericTrackPaginationDto;
import com.sifast.model.GenericTrack;
import com.sifast.service.utils.RequestHandlerInterceptor;

@Component
public class GenericTrackMapper {

    @Autowired
    private RequestHandlerInterceptor requestHandlerInterceptor;

    public GenericTrackPaginationDto mapGenericTrackToGenericTrackPaginationDto(Page<GenericTrack> history) {
        List<GenericTrackDto> genericTrackHistory = new ArrayList<>();
        for (GenericTrack genericTrack : history.getContent()) {
            GenericTrackDto genericTrackDto = new GenericTrackDto(genericTrack.getId(), requestHandlerInterceptor.getMessage(genericTrack.getEventType().toString()),
                    genericTrack.getEventDate(), genericTrack.getPerformedBy(), genericTrack.getEntityName(), genericTrack.getChangedProperties(), genericTrack.getChangedState());
            genericTrackDto.setEntityId(genericTrack.getEntityId());
            genericTrackDto.setPreviousState(genericTrack.getPreviousState());
            genericTrackHistory.add(genericTrackDto);
        }
        GenericTrackPaginationDto genericTrackPaginationDto = new GenericTrackPaginationDto();
        genericTrackPaginationDto.setGenericTrackDtos(genericTrackHistory);
        genericTrackPaginationDto.setTotalNumber(history.getTotalElements());
        return genericTrackPaginationDto;
    }
}
