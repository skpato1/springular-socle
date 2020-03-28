package com.sifast.service.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sifast.enumeration.EventType;
import com.sifast.model.GenericTrack;
import com.sifast.service.dto.JaversCompareResultDto;

@Component("trackBean")
public class TrackDtoBean {

	public List<JaversCompareResultDto> getAllChangedObjectDto(GenericTrack track) {
		List<JaversCompareResultDto> changesDto = new ArrayList<>();
		if (track != null && track.getEventType().compareTo(EventType.UPDATE) == 0) {
			Type listType = new TypeToken<List<JaversCompareResultDto>>() {
			}.getType();
			String jsonConverterDiff = track.getChangedProperties();
			changesDto = new Gson().fromJson(jsonConverterDiff, listType);
		} else if (track != null) {
			getSaveOrDeleteState(track, changesDto);
		}
		return changesDto;
	}

	private void getSaveOrDeleteState(GenericTrack track, List<JaversCompareResultDto> changesDto) {

		JaversCompareResultDto saveOrDeleteState = new JaversCompareResultDto();
		if (track.getEventType().compareTo(EventType.SAVE) == 0) {
			saveOrDeleteState.setRight(track.getAllChangedValues());
		} else if (track.getEventType().compareTo(EventType.DELETE) == 0) {
			saveOrDeleteState.setLeft(track.getAllChangedValues());
		}
		changesDto.add(saveOrDeleteState);
	}

}