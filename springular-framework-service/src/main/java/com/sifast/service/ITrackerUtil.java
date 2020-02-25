package com.sifast.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.javers.core.diff.Change;
import org.javers.core.json.JsonConverter;
import org.javers.core.json.JsonConverterBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sifast.exception.ITrackIdentifierNotImplementedException;
import com.sifast.service.dto.JaversCompareResultDto;
import com.sifast.utils.TrackIdentifier;

public interface ITrackerUtil {

	static List<JaversCompareResultDto> getOnlyChangedProperties(List<Change> diffBtwOldAndNewObject) {
		Type listType = new TypeToken<List<JaversCompareResultDto>>() {
		}.getType();
		JsonConverter jsonConverter = new JsonConverterBuilder().build();
		String jsonConverterDiff = jsonConverter.toJson(diffBtwOldAndNewObject);
		List<JaversCompareResultDto> javersCompareResultDto = new Gson().fromJson(jsonConverterDiff, listType);
		return javersCompareResultDto.stream().filter(change -> change.getLeft() != null && change.getRight() != null)
				.collect(Collectors.toList());
	}

	static TrackIdentifier getITrackIdentifier(Object result) {
		TrackIdentifier trackedIdentifierObject;
		if (result instanceof TrackIdentifier) {
			trackedIdentifierObject = (TrackIdentifier) result;
		} else {
			throw new ITrackIdentifierNotImplementedException("Your entity must implements ITrackIdentifier");
		}
		return trackedIdentifierObject;
	}

}
